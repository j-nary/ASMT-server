package com.asmt.ssu.controller;


import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.exception.IllegalArgumentException;
import com.asmt.ssu.form.BookmarkForm;
import com.asmt.ssu.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Bookmark", description = "북마크 API")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @GetMapping("/api/bookmark")
    @Operation(summary = "북마크된 리스트 가져오기", description = "북마크 리스트는 무한스크롤 없이 모두 가져옵니다.")
    public List<SearchDTO> getBookmark(@RequestParam String userId){

        return bookmarkService.getBookmarkedMenuList(userId);
    }

    @PostMapping("/api/bookmark")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "북마크 추가")
    public ResponseEntity<String> addBookmark(@ParameterObject @ModelAttribute @Valid BookmarkForm bookmarkForm,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("입력이 잘못되었습니다." + bindingResult.getAllErrors());
        }
        bookmarkService.addBookmark(bookmarkForm);
        return new ResponseEntity<>("Success to create", HttpStatus.CREATED);
    }

    @DeleteMapping("/api/bookmark")
    @Operation(summary = "북마크 삭제")
    public ResponseEntity<String> deleteBookmark(@ParameterObject @ModelAttribute @Valid BookmarkForm bookmarkForm,
                                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new IllegalArgumentException("입력이 잘못되었습니다." + bindingResult.getAllErrors());
        }
        bookmarkService.removeBookmark(bookmarkForm);
        return new ResponseEntity<>("Success to delete.",HttpStatus.OK);
    }

}
