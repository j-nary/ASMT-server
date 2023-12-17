package com.asmt.ssu.repository;

import com.asmt.ssu.domain.Bookmark;
import com.asmt.ssu.form.BookmarkForm;
import com.asmt.ssu.repository.impl.BookmarkRepositoryImpl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BookmarkRepositoryImplTest {

    @Autowired
    private BookmarkRepositoryImpl bookmarkRepositoryImpl;


    @Test
    void save() {
        BookmarkForm bookmarkForm = new BookmarkForm("user1",4L);
        Bookmark saved = bookmarkRepositoryImpl.save(bookmarkForm);

        Assertions.assertThat(saved.getUserId()).isEqualTo("user1");
        Assertions.assertThat(saved.getMenu().getId()).isEqualTo(4L);


    }

    @Test
    void delete() {
        BookmarkForm bookmarkForm = new BookmarkForm("user1",4L);
        Bookmark saved = bookmarkRepositoryImpl.save(bookmarkForm);

        bookmarkRepositoryImpl.findById(saved.getId());


        bookmarkRepositoryImpl.delete(bookmarkForm);

        Assertions.assertThat(bookmarkRepositoryImpl.findById(saved.getId())).isNull();
    }

    @Test
    void getBookmarkedMenuList() {
    }

    @Test
    void findBookmarksByUniqueIdAndMenuId() {
        BookmarkForm bookmarkForm = new BookmarkForm("user1",4L);
        Bookmark saved = bookmarkRepositoryImpl.save(bookmarkForm);

        Assertions.assertThat(bookmarkRepositoryImpl.findBookmarkByUniqueIdAndMenuId(bookmarkForm)).isNotNull();
    }
}