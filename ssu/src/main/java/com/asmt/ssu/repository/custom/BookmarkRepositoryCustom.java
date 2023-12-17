package com.asmt.ssu.repository.custom;

import java.util.List;

import com.asmt.ssu.domain.Bookmark;
import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.form.BookmarkForm;

public interface BookmarkRepositoryCustom {
	Bookmark save(BookmarkForm bookmarkForm);
	void delete(BookmarkForm bookmarkForm);
	List<Menu> getBookmarkedMenuList(String userId , String sortMethod);
	
}
