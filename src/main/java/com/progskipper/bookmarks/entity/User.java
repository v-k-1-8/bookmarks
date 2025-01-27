package com.progskipper.bookmarks.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "text")
    private String name;
    @Column(nullable = false,columnDefinition = "timestamp")
    private Timestamp dob;
    @Column(nullable = false, columnDefinition = "text")
    private String email;
    @Column(nullable = false, columnDefinition = "text")
    private String address;

}
