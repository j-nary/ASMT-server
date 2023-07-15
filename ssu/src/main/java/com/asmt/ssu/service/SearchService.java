package com.asmt.ssu.service;

import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.form.SearchForm;
import com.asmt.ssu.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    public List<SearchDTO> getResult(SearchForm searchForm){
        searchForm.processZeroPrice();
        try {
            if (searchForm.getSearchKeywordList() == null || searchForm.getSearchKeywordList().size() == 0)
                return searchRepository.findResultByLowPrice(searchForm);
            else
                return searchRepository.findResultByPriceWithName(searchForm);
        }
        catch (Exception e){
            throw new IllegalArgumentException("잘못된 입력값으로 인해 쿼리 과정중 오류가 발생했습니다.\n" + e.getMessage());
        }
    }
}
