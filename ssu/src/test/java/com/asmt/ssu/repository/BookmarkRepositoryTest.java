package com.asmt.ssu.repository;

import com.asmt.ssu.domain.Bookmark;
import com.asmt.ssu.form.BookmarkForm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;


    @Test
    void save() {
        BookmarkForm bookmarkForm = new BookmarkForm("user1",4L);
        Bookmark saved = bookmarkRepository.save(bookmarkForm);

        Assertions.assertThat(saved.getUserId()).isEqualTo("user1");
        Assertions.assertThat(saved.getMenu().getId()).isEqualTo(4L);


    }

    @Test
    void delete() {
        BookmarkForm bookmarkForm = new BookmarkForm("user1",4L);
        Bookmark saved = bookmarkRepository.save(bookmarkForm);

        bookmarkRepository.findById(saved.getId());


        bookmarkRepository.delete(bookmarkForm);

        Assertions.assertThat(bookmarkRepository.findById(saved.getId())).isNull();
    }

    @Test
    void getBookmarkedMenuList() {
    }

    @Test
    void findBookmarksByUniqueIdAndMenuId() {
        BookmarkForm bookmarkForm = new BookmarkForm("user1",4L);
        Bookmark saved = bookmarkRepository.save(bookmarkForm);

        Assertions.assertThat(bookmarkRepository.findBookmarkByUniqueIdAndMenuId(bookmarkForm)).isNotNull();
    }
}