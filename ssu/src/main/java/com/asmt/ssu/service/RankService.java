package com.asmt.ssu.service;


import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.domain.School;
import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.repository.impl.RankRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RankService {

    private final RankRepositoryImpl rankRepositoryImpl;

    public List<SearchDTO> getDailyRank(int rankCount, School school){
        List<Menu> rankTopN = rankRepositoryImpl.findRankTopN(rankCount, school);

        return rankTopN.stream().map(SearchDTO::new).collect(Collectors.toList());
    }

    public void addMenuToRank(Long menuId){
        rankRepositoryImpl.save(menuId);
    }
}
