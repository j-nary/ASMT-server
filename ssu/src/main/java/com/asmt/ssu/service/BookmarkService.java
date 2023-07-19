package com.asmt.ssu.service;


import com.asmt.ssu.domain.SearchDTO;
import com.asmt.ssu.form.BookmarkForm;
import com.asmt.ssu.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public void addBookmark(BookmarkForm bookmarkForm){
        validateDuplicate(bookmarkForm);
        bookmarkRepository.save(bookmarkForm);
    }

    private void validateDuplicate(BookmarkForm bookmarkForm){
        bookmarkRepository.findBookmarkByUniqueIdAndMenuId(bookmarkForm).ifPresent(b -> {
            throw new IllegalStateException("이미 북마크된 메뉴입니다.");
        });

    }

    public void removeBookmark(BookmarkForm bookmarkForm){
        if (bookmarkRepository.findBookmarkByUniqueIdAndMenuId(bookmarkForm).isEmpty())
            throw new IllegalStateException("북마크되지 않았습니다.");

        bookmarkRepository.delete(bookmarkForm);
    }

    public List<SearchDTO> getBookmarkedMenuList(String userId){
        List<SearchDTO> resultList = new ArrayList<>();
        bookmarkRepository.getBookmarkedMenuList(userId).forEach(menu -> resultList.add(new SearchDTO(menu, true)));
        return resultList;
    }


}
