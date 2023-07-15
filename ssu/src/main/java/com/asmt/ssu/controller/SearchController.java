package com.asmt.ssu.controller;


import com.asmt.ssu.exception.ErrorResult;
import com.asmt.ssu.exception.IllegalArgumentException;
import com.asmt.ssu.domain.School;
import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.form.SearchForm;
import com.asmt.ssu.service.RankService;
import com.asmt.ssu.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "Search", description = "메뉴 리스트 반환 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {
    private final SearchService searchService;
    private final RankService rankService;


    @PostMapping("/api/search")
    @Operation(summary = "메뉴 검색", description = "json을 받아 조건에 맞춰 메뉴 리스트를 반환합니다.")
    public List<SearchDTO> lowPrice(@RequestBody @Valid SearchForm searchForm,
                                    BindingResult bindingResult) throws IllegalArgumentException {
        log.info("value = {}", searchForm.getSortMethod());
        if (bindingResult.hasErrors()) {
            log.error("binding = {}", bindingResult.getAllErrors());
            throw new IllegalArgumentException("입력이 잘못되었습니다." + bindingResult.getAllErrors());
        }

        return searchService.getResult(searchForm);
    }

    @GetMapping("/api/rank")
    public List<SearchDTO> getDailyRanking(@RequestParam int rankCount,
                                           @RequestParam School school){
        return rankService.getDailyRank(rankCount, school);
    }

    @PostMapping("/api/rank")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addHitDailyRanking(@RequestParam @Positive @Valid Long menuId){

        rankService.addMenuToRank(menuId);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

}
