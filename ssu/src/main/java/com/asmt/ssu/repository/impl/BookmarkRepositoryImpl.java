package com.asmt.ssu.repository.impl;


import static com.asmt.ssu.domain.QBookmark.*;
import static com.asmt.ssu.domain.QMenu.*;
import static com.asmt.ssu.domain.QPlace.*;

import com.asmt.ssu.domain.Bookmark;
import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.domain.QBookmark;
import com.asmt.ssu.domain.QMenu;
import com.asmt.ssu.domain.QPlace;
import com.asmt.ssu.form.BookmarkForm;
import com.asmt.ssu.repository.custom.BookmarkRepositoryCustom;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;


    public Bookmark save(BookmarkForm bookmarkForm){
        Menu menu = em.find(Menu.class, bookmarkForm.getMenuId());
        Bookmark bookmark = new Bookmark(bookmarkForm.getUserId(), menu);
        em.persist(bookmark);
        return bookmark;
    }

    public void delete(BookmarkForm bookmarkForm){
        // Bookmark singleResult = em.createQuery("select b from Bookmark b join fetch b.menu where b.menu.id = :menuId and b.userId = :uniqueId ", Bookmark.class)
        //         .setParameter("menuId", bookmarkForm.getMenuId())
        //         .setParameter("uniqueId", bookmarkForm.getUserId())
        //         .getSingleResult();

        Bookmark singleResult = queryFactory.selectFrom(bookmark)
            .join(bookmark.menu).fetchJoin()
            .where(bookmark.menu.id.eq(bookmarkForm.getMenuId()),
                bookmark.userId.eq(bookmarkForm.getUserId()))
            .fetchOne();
        em.remove(singleResult);
    }

    public List<Menu> getBookmarkedMenuList(String userId , String sortMethod){

        List<Menu> resultList = new ArrayList<>();
        // em.createQuery("select b from Bookmark b join fetch b.menu m join fetch m.place p where b.userId = :uniqueId order by " + sortMethod, Bookmark.class)
        //         .setParameter("uniqueId", userId)
        //         .getResultStream().forEach(bookmark -> resultList.add(bookmark.getMenu()));

        queryFactory.selectFrom(bookmark)
            .join(bookmark.menu, menu).fetchJoin()
            .join(menu.place).fetchJoin()
            .where(bookmark.userId.eq(userId))
            .orderBy(sortMethod(sortMethod))
            .fetch().forEach(result -> resultList.add(result.getMenu()));
        return resultList;
    }

    private OrderSpecifier<Integer> sortMethod(String method){
        switch (method) {
            case "lowPrice":
                return new OrderSpecifier<>(Order.ASC, menu.menuPrice);
            case "highPrice":
                return new OrderSpecifier<>(Order.DESC, menu.menuPrice);
            case "distance":
                return new OrderSpecifier<>(Order.ASC, place.placeDistance);
        }
        return null;
    }

    public Optional<Bookmark> findBookmarkByUniqueIdAndMenuId(BookmarkForm bookmarkForm){
        // return em.createQuery("select b from Bookmark b join fetch b.menu where b.menu.id = :menuId and b.userId = :uniqueId ", Bookmark.class)
        //         .setParameter("menuId", bookmarkForm.getMenuId())
        //         .setParameter("uniqueId", bookmarkForm.getUserId())
        //         .getResultStream().findAny();

        return queryFactory.selectFrom(bookmark)
            .join(bookmark.menu, menu).fetchJoin()
            .where(menu.id.eq(bookmarkForm.getMenuId()),
                bookmark.userId.eq(bookmarkForm.getUserId()))
            .fetch().stream().findAny();
    }

    public Bookmark findById(Long id){
        return em.find(Bookmark.class, id);
    }
}
