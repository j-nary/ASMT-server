package com.asmt.ssu.repository;

import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.form.SearchForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SearchRepository {
    private final EntityManager em;


    public List<SearchDTO> findResultByLowPrice(SearchForm searchForm) {
        return em.createQuery("select new com.asmt.ssu.domain.SearchDTO(p.placeName, p.placeAddress, p.placeRating, p.placeLink, p.placeDistance, p.school, m.menuName, m.menuPrice, m.menuImg)" +
                        " from Menu m join m.place p where p.school = :school and m.menuPrice between :minValue and :maxValue order by m.menuPrice "+ searchForm.getSortMethod(), SearchDTO.class)
                .setParameter("school", searchForm.getSchool())
                .setParameter("minValue", searchForm.getMinimumPrice())
                .setParameter("maxValue", searchForm.getMaximumPrice())
                .getResultList();
    }

    public List<SearchDTO> findResultByPriceWithName(SearchForm searchForm) {
        StringBuilder jpqlBuilder = new StringBuilder();
        for (int i = 0; i < searchForm.getSearchKeywordList().size(); i++) {
            if (i > 0) {
                jpqlBuilder.append(" OR ");
            }
            jpqlBuilder.append("m.menuName LIKE :searchString").append(i);
        }
        String jpql = jpqlBuilder.toString();
        TypedQuery<SearchDTO> resultQuery = em.createQuery("select new com.asmt.ssu.domain.SearchDTO(p.placeName, p.placeAddress, p.placeRating, p.placeLink, p.placeDistance, p.school, m.menuName, m.menuPrice, m.menuImg)" +
                        " from Menu m join m.place p where p.school = :school and " + jpql + " and  m.menuPrice between :minValue and :maxValue order by m.menuPrice "+ searchForm.getSortMethod(), SearchDTO.class)
                .setParameter("school", searchForm.getSchool())
                .setParameter("minValue", searchForm.getMinimumPrice())
                .setParameter("maxValue", searchForm.getMaximumPrice());
        for (int i = 0; i < searchForm.getSearchKeywordList().size(); i++) {
            resultQuery.setParameter("searchString" + i, "%" + searchForm.getSearchKeywordList().get(i) + "%");
        }
        return resultQuery.getResultList();
    }
}
