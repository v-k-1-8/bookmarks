package com.progskipper.bookmarks.services;

import com.progskipper.bookmarks.entity.Bookmark;
import com.progskipper.bookmarks.repository.BookmarkRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {
    ///  Handles all Services related to Bookmarks

    // Interface with ORM functionalities
    private final BookmarkRepository bookmarkRepository;
    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    // Save New Bookmark
    public void SaveBookmarkService(Bookmark bookmark){
        bookmarkRepository.save(bookmark);
    }

    // Delete Bookmark by id
    public void DeleteBookmarkService(Long id){
        bookmarkRepository.deleteById(id);
    }

    // Get Bookmark by id
    public Optional<Bookmark> GetBookmarkService(Long id){
        return bookmarkRepository.findById(id);
    }

    // Get all existing Bookmarks
    public List<Bookmark> GetAllBookmarksService(){
        return bookmarkRepository.findAll();
    }

    // Get All bookmarks for Userid
    public List<Bookmark> GetBookmarksByUserIdService(Long id){
        return bookmarkRepository.findByUserId(id);
    }

    // Delete All Bookmarks for Userid
    public void DeleteBookmarksByUserIdService(Long id){
        bookmarkRepository.deleteByUserId(id);
    }
}
