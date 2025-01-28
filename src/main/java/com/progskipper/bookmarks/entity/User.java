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
    /// User Schema

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "text", name = "name")
    private String name;
    @Column(nullable = false,columnDefinition = "timestamp", name = "dob")
    private Timestamp dob;
    @Column(nullable = false, columnDefinition = "text", name= "email", unique = true)
    private String email;
    @Column(nullable = false, columnDefinition = "text", name = "address")
    private String address;

}
