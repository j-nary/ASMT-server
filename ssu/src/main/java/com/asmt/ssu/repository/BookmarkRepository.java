package com.asmt.ssu.repository;


import com.asmt.ssu.domain.Bookmark;
import com.asmt.ssu.domain.Menu;
import com.asmt.ssu.form.BookmarkForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BookmarkRepository {

    private final EntityManager em;

    public Bookmark save(BookmarkForm bookmarkForm){
        Menu menu = em.find(Menu.class, bookmarkForm.getMenuId());
        Bookmark bookmark = new Bookmark(bookmarkForm.getUserId(), menu);
        em.persist(bookmark);
        return bookmark;
    }

    public void delete(BookmarkForm bookmarkForm){
        Bookmark singleResult = em.createQuery("select b from Bookmark b join fetch b.menu where b.menu.id = :menuId and b.userId = :uniqueId ", Bookmark.class)
                .setParameter("menuId", bookmarkForm.getMenuId())
                .setParameter("uniqueId", bookmarkForm.getUserId())
                .getSingleResult();

        em.remove(singleResult);
    }

    public List<Menu> getBookmarkedMenuList(String userId){
        List<Menu> resultList = new ArrayList<>();
        em.createQuery("select b from Bookmark b join fetch b.menu m join fetch m.place  where b.userId = :uniqueId", Bookmark.class)
                .setParameter("uniqueId", userId)
                .getResultStream().forEach(bookmark -> resultList.add(bookmark.getMenu()));
        return resultList;
    }

    public Optional<Bookmark> findBookmarkByUniqueIdAndMenuId(BookmarkForm bookmarkForm){
        return em.createQuery("select b from Bookmark b join fetch b.menu where b.menu.id = :menuId and b.userId = :uniqueId ", Bookmark.class)
                .setParameter("menuId", bookmarkForm.getMenuId())
                .setParameter("uniqueId", bookmarkForm.getUserId())
                .getResultStream().findAny();
    }

    public Bookmark findById(Long id){
        return em.find(Bookmark.class, id);
    }
}
