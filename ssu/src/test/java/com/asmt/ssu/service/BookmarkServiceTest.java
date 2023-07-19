package com.asmt.ssu.service;

import com.asmt.ssu.form.BookmarkForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookmarkServiceTest {

    @Autowired
    private BookmarkService bookmarkService;

    @Test
    void addBookmark() {
        BookmarkForm bookmarkForm = new BookmarkForm("user1",4L);
        bookmarkService.addBookmark(bookmarkForm);

        assertThrows(IllegalStateException.class, () -> bookmarkService.addBookmark(bookmarkForm));

    }

    @Test
    void removeBookmark() {

        BookmarkForm bookmarkForm = new BookmarkForm("user1",4L);
        assertThrows(IllegalStateException.class, () -> bookmarkService.removeBookmark(bookmarkForm));

        bookmarkService.addBookmark(bookmarkForm);

        bookmarkService.removeBookmark(bookmarkForm);

        Assertions.assertThat(bookmarkService.getBookmarkedMenuList("user1").size()).isEqualTo(0);

    }

    @Test
    void getBookmarkedMenuList() {
    }
}