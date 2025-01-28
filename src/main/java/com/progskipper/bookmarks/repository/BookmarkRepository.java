package com.progskipper.bookmarks.repository;

import com.progskipper.bookmarks.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    /// Extends JPA Repository for CRUD operations and for ORM - Object Relational Mapping

    List<Bookmark> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
