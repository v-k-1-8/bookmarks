package com.progskipper.bookmarks.repository;

import com.progskipper.bookmarks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /// Extends JPA Repository for CRUD operations and for ORM - Object Relational Mapping

}
