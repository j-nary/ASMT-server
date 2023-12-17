package com.asmt.ssu.repository.impl;

import static com.asmt.ssu.domain.QHit.*;

import com.asmt.ssu.domain.Hit;
import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.domain.QHit;
import com.asmt.ssu.domain.School;
import com.asmt.ssu.repository.custom.RankRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RankRepositoryImpl implements RankRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Menu> findRankTopN(int n, School school){
        LocalDateTime now = LocalDateTime.now();
        return queryFactory.
            select(hit.menu)
            .from(hit)
            .join(hit.menu.place).fetchJoin()
            .where(hit.hitTime.between(now.minusDays(1), now),
                hit.menu.place.school.eq(school))
            .groupBy(hit.menu)
            .orderBy(hit.count().desc())
            .fetch();
    }

    @Override
    public void save(Long menuId){
        Menu menu = em.getReference(Menu.class, menuId);
        Hit hit = Hit.builder().menu(menu).hitTime(LocalDateTime.now()).build();
        em.persist(hit);
    }
}
