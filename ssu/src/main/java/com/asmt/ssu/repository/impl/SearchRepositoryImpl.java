package com.asmt.ssu.repository.impl;

import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.domain.QBookmark;
import com.asmt.ssu.domain.QMenu;
import com.asmt.ssu.domain.QPlace;
import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.form.SearchForm;
import com.asmt.ssu.repository.custom.SearchRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    private static final int elementCountInPage = 150;

    private final QMenu m = QMenu.menu;
    private final QPlace p = QPlace.place;
    private final QBookmark b = QBookmark.bookmark;


    @Override
    public List<SearchDTO> findResultByLowPrice(SearchForm searchForm) {
        return queryFactory.select(
                Projections.constructor(
                    SearchDTO.class,
                    p.placeName,
                    p.placeAddress,
                    p.placeRating,
                    p.placeLink,
                    p.placeDistance,
                    p.school,
                    m.id,
                    m.menuName,
                    m.menuPrice,
                    m.menuImg,
                    new CaseBuilder().when(b.menu.isNull()).then(false).otherwise(true)
                )
           )
            .from(m)
            .join(m.place, p)
            .leftJoin(b).on(b.menu.eq(m)
                .and(b.userId.eq(searchForm.getUserId())))
            .where(p.school.eq(searchForm.getSchool())
                .and(m.menuPrice.between(searchForm.getMinimumPrice(), searchForm.getMaximumPrice())),
                searchKeyword(searchForm))
            .orderBy(orderMethod(searchForm))
            .offset((searchForm.getPage() - 1) * elementCountInPage)
            .limit(elementCountInPage)
            .fetch();
    }

    private BooleanBuilder searchKeyword(SearchForm searchForm){
        if (searchForm.getSearchKeywordList() == null || searchForm.getSearchKeywordList().size() == 0)
            return null;
        BooleanBuilder builder = new BooleanBuilder();
        searchForm.getSearchKeywordList().forEach(keyword -> builder.or(m.menuName.contains(keyword)));
        return builder;
    }

    private OrderSpecifier<Integer> orderMethod(SearchForm searchForm){
        switch (searchForm.getSortMethod()) {
            case "lowPrice":
                return new OrderSpecifier<>(Order.ASC, m.menuPrice);
            case "highPrice":
                return new OrderSpecifier<>(Order.DESC, m.menuPrice);
            case "distance":
                return new OrderSpecifier<>(Order.ASC, p.placeDistance);
        }
        return null;
    }

}
