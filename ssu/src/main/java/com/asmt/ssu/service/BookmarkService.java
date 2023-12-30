package com.asmt.ssu.service;


import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.form.BookmarkForm;
import com.asmt.ssu.repository.impl.BookmarkRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookmarkService {
    private final BookmarkRepositoryImpl bookmarkRepositoryImpl;

    public void addBookmark(BookmarkForm bookmarkForm){
        validateDuplicate(bookmarkForm);
        bookmarkRepositoryImpl.save(bookmarkForm);
    }

    private void validateDuplicate(BookmarkForm bookmarkForm){
        bookmarkRepositoryImpl.findBookmarkByUniqueIdAndMenuId(bookmarkForm)
            .ifPresent(b -> {throw new IllegalStateException("이미 북마크된 메뉴입니다.");});
    }

    public void removeBookmark(BookmarkForm bookmarkForm){
        if (bookmarkRepositoryImpl.findBookmarkByUniqueIdAndMenuId(bookmarkForm).isEmpty())
            throw new IllegalStateException("북마크되지 않았습니다.");
        bookmarkRepositoryImpl.delete(bookmarkForm);
    }

    public List<SearchDTO> getBookmarkedMenuList(String userId, String sortMethod){
        return bookmarkRepositoryImpl.getBookmarkedMenuList(userId, sortMethod)
            .stream()
            .map(menu -> new SearchDTO(menu, true))
            .collect(Collectors.toList());
    }


}
