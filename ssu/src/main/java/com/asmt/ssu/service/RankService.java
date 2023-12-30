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

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RankService {

    private final RankRepositoryImpl rankRepositoryImpl;

    public List<SearchDTO> getDailyRank(int rankCount, School school){
        List<Menu> rankTopN = rankRepositoryImpl.findRankTopN(rankCount, school);
        List<SearchDTO> resultList = new ArrayList<>();
        rankTopN.forEach(menu -> resultList.add(new SearchDTO(menu)));
        return resultList;
    }

    public void addMenuToRank(Long menuId){
        rankRepositoryImpl.save(menuId);
    }
}
