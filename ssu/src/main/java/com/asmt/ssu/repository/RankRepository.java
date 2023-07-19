package com.asmt.ssu.repository;

import com.asmt.ssu.domain.Hit;
import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RankRepository {
    private final EntityManager em;

    public List<Menu> findRankTopN(int n, School school){
        LocalDateTime now = LocalDateTime.now();
        return em.createQuery("select h.menu from Hit h join fetch h.menu.place p where h.hitTime between :yesterday and :now and p.school = :school group by h.menu order by count(h) desc ", Menu.class)
                .setParameter("yesterday",now.minusDays(1))
                .setParameter("now", now)
                .setParameter("school",school)
                .setFirstResult(0)
                .setMaxResults(n)
                .getResultList();
    }

    public void save(Long menuId){
        Menu menu = em.getReference(Menu.class, menuId);
        Hit hit = Hit.builder().menu(menu).hitTime(LocalDateTime.now()).build();
        em.persist(hit);
    }


}
