package com.asmt.ssu.controller;


import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.form.SearchForm;
import com.asmt.ssu.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SearchController {
    private final SearchService searchService;


    @RequestMapping("/low/ssu")
    public List<SearchDTO> lowPrice(@RequestBody SearchForm searchForm){
        return searchService.getResult(searchForm);
    }
    @RequestMapping("/")
    public String basic(){
        return "hello!";
    }
}
