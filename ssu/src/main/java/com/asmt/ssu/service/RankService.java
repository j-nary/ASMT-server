package com.asmt.ssu.service;


import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.domain.School;
import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.repository.impl.RankRepository;
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

    private final RankRepository rankRepository;

    public List<SearchDTO> getDailyRank(int rankCount, School school){
        List<Menu> rankTopN = rankRepository.findRankTopN(rankCount, school);
        List<SearchDTO> resultList = new ArrayList<>();
        for (Menu menu : rankTopN) {
            resultList.add(new SearchDTO(menu));
        }
        return resultList;
    }

    public void addMenuToRank(Long menuId){
        rankRepository.save(menuId);
    }
}
