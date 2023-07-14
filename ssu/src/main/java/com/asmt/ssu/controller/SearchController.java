package com.asmt.ssu.controller;


import com.asmt.ssu.ErrorResult;
import com.asmt.ssu.IllegalArgumentException;
import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.form.SearchForm;
import com.asmt.ssu.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "Search", description = "메뉴 리스트 반환 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SearchController {
    private final SearchService searchService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalArgumentHandler(IllegalArgumentException e){
        log.error("illegalArgumentException = {}", e);
        return new ErrorResult("BAD_REQUEST", e.getMessage());
    }

    @PostMapping("/api/search")
    @Operation(summary = "메뉴 검색", description = "json을 받아 조건에 맞춰 메뉴 리스트를 반환합니다.")
    public List<SearchDTO> lowPrice(@RequestBody @Valid SearchForm searchForm,
                                    BindingResult bindingResult) throws IllegalArgumentException {
        log.info("value = {}", searchForm.getSortMethod());
        if (bindingResult.hasErrors()) {
            log.error("binding = {}", bindingResult.getAllErrors());
            throw new IllegalArgumentException("입력이 잘못되었습니다." + bindingResult.getAllErrors());
        }

        try {
            return searchService.getResult(searchForm);
        } catch (Exception e){
            throw new IllegalArgumentException("입력이 잘못되었습니다.");
        }
    }

}
