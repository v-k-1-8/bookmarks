package com.progskipper.bookmarks.controller;


import com.progskipper.bookmarks.entity.Bookmark;
import com.progskipper.bookmarks.services.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;

@Log4j2
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

    @Operation(
            summary = "Create a new bookmark",
            description = "Add a new bookmark by providing its details"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Create a new bookmark",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            name = "CreateBookmarkExample",
                            value = "{ \"link\": \"https://codeforces.com\", \"title\": \"codeforces\", \"note\": \"CP\" }"
                    )
            )
    )
    @PostMapping("/create-bookmark/user/{id}")
    public ResponseEntity<Bookmark> CreateBookmark(@RequestBody Bookmark bookmark,@PathVariable Long id) {
        try {
            bookmark.setUserId(id);
            String cleanContent =GetCleanContent(bookmark.getLink());
            bookmark.setContent(cleanContent);
            bookmarkService.SaveBookmarkService(bookmark);
            return new ResponseEntity<>(bookmark, HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Exception regarding userid {}: ",id,e);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    // Calling External API for clean content
    private String GetCleanContent(String link){
        try{
            String apiUrl = "https://12ft.io/" + link;
            log.info("Calling external API: {}", apiUrl);
            long startTime = System.currentTimeMillis();
            String response = restTemplate.getForObject(apiUrl, String.class);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            log.info("API Call Completed: {} | Time Taken: {} ms", apiUrl, duration);
            return response;
        }
        catch(Exception e){
           log.error("Exception in external API call : ",e);
           return null;
        }
    }

    @Operation(
            summary = "Get all bookmarks",
            description = "Retrieve a list of all bookmarks in the system"
    )
    @GetMapping("/bookmarks")
    public ResponseEntity<List<Bookmark>> GetAllBookmarks() {
        try{
            List<Bookmark> bookmarks = bookmarkService.GetAllBookmarksService();
            return new ResponseEntity<>(bookmarks,HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Get bookmark by ID",
            description = "Retrieve a specific bookmark using its unique ID"
    )
    @GetMapping("/bookmark/{id}")
    public ResponseEntity<Bookmark> GetBookmark(@PathVariable Long id){
        try{
            Optional<Bookmark> bookmark = bookmarkService.GetBookmarkService(id);
            return bookmark.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Delete bookmark by ID",
            description = "Delete a specific bookmark identified by its unique ID"
    )
    @DeleteMapping("/bookmark/{id}")
    public ResponseEntity<String> DeleteBookmark(@PathVariable Long id) {
        try {
            bookmarkService.DeleteBookmarkService(id);
            return new ResponseEntity<>("Bookmark Deleted",HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Get all bookmarks for a specific user",
            description = "Retrieve all bookmarks that belong to the user identified by the provided user ID."
    )
    @GetMapping("/bookmark/user/{id}")
    public ResponseEntity<List<Bookmark>> GetBookmarksByUserId(@PathVariable Long id){
        try{
            List<Bookmark> bookmarks = bookmarkService.GetBookmarksByUserIdService(id);
            if(!bookmarks.isEmpty()) return new ResponseEntity<>(bookmarks,HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            log.error("Exception: ",e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
