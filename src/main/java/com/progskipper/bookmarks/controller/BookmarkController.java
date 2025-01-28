package com.progskipper.bookmarks.controller;


import com.progskipper.bookmarks.entity.Bookmark;
import com.progskipper.bookmarks.services.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;


@RestController
public class BookmarkController {
    ///  Handles all functionalities related to Bookmarks

    // Template to interact with External APIs
    private final RestTemplate restTemplate;
    // Class contains all Bookmarks Services
    private final BookmarkService bookmarkService;
    public BookmarkController(RestTemplate restTemplate, BookmarkService bookmarkService) {
        this.restTemplate = restTemplate;
        this.bookmarkService = bookmarkService;
    }

    // Create New Bookmark
    @PostMapping("/create-bookmark/user/{id}")
    public ResponseEntity<Bookmark> CreateBookmark(@RequestBody Bookmark bookmark,@PathVariable Long id) {
        try {
            String apiUrl = "https://12ft.io/" + bookmark.getLink();
            String cleanContent =restTemplate.getForObject(apiUrl, String.class);
            bookmark.setContent(cleanContent);
            bookmark.setUserId(id);
            bookmarkService.SaveBookmarkService(bookmark);
            return new ResponseEntity<>(bookmark, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    // Get All Existing Bookmarks
    @GetMapping("/bookmarks")
    public ResponseEntity<List<Bookmark>> GetAllBookmarks() {
        try{
            List<Bookmark> bookmarks = bookmarkService.GetAllBookmarksService();
            return new ResponseEntity<>(bookmarkService.GetAllBookmarksService(),HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get Bookmarks by id
    @GetMapping("/bookmark/{id}")
    public ResponseEntity<Bookmark> GetBookmark(@PathVariable Long id){
        try{
            Optional<Bookmark> bookmark = bookmarkService.GetBookmarkService(id);
            return bookmark.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete Bookmarks by id
    @DeleteMapping("/bookmark/{id}")
    public void DeleteBookmark(@PathVariable Long id) {
        bookmarkService.DeleteBookmarkService(id);
    }

    // Get All Bookmarks related to Userid
    @GetMapping("/bookmark/user/{id}")
    public ResponseEntity<List<Bookmark>> GetBookmarksByUserId(@PathVariable Long id){
        try{
            List<Bookmark> bookmarks = bookmarkService.GetBookmarksByUserIdService(id);
            if(!bookmarks.isEmpty()) return new ResponseEntity<>(bookmarks,HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
