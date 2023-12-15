package com.asmt.ssu.repository;

import com.asmt.ssu.domain.Hit;
import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.repository.impl.RankRepository;
import com.asmt.ssu.repository.impl.SearchRepositoryImpl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

@SpringBootTest
@Transactional
class RankRepositoryTest {
    @Autowired
    private RankRepository rankRepository;
    @Autowired
    private SearchRepositoryImpl searchRepositoryImpl;

    @Autowired
    private EntityManager em;

    @Test
    void doTest(){

    }

    @Test
    void findRankTopN() {
//        List<Menu> rankTopN = rankRepository.findRankTopN(2);
//        for (Menu menu : rankTopN) {
//            System.out.println("menu.getMenuName() = " + menu.getMenuName());
//            System.out.println("menu.getPlace().getPlaceName() = " + menu.getPlace().getPlaceName());
//        }
    }

    @Test
    @Commit
    void save() {
        List<Menu> resultList = em.createQuery("select m from Menu m", Menu.class)
                .setFirstResult(0)
                .setMaxResults(3)
                .getResultList();
//        for (Menu menu : resultList) {
//            Hit hit = new Hit();
//            hit.setHitTime(LocalDateTime.now());
//            hit.setMenu(menu);
//            rankRepository.save(hit);
//        }

        em.createQuery("select h from Hit h", Hit.class)
                .getResultStream()
                .forEach(hit -> System.out.println("hit = " + hit));


    }
}