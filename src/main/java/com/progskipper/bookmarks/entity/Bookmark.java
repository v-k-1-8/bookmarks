package com.progskipper.bookmarks.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@Table(name = "bookmarks")
public class Bookmark {
    /// Bookmark Schema

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "text", name = "link")
    private String link;
    @Column(nullable = false, columnDefinition = "text", name = "title")
    private String title;
    @Column(nullable = false, columnDefinition = "text", name = "content")
    private String content;
    @Column(nullable = false, columnDefinition = "text", name = "note")
    private String note;
    @Column(nullable = false,columnDefinition = "bigint",name = "userid")
    private Long userId;

}
