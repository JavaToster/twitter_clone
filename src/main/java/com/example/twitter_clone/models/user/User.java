package com.example.twitter_clone.models.user;

import com.example.twitter_clone.util.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Person")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phone;
    @Column(name = "password")
    private String password;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "bio")
    private String bio;
    @OneToOne(mappedBy = "user")
    private UserLocation location;
    @Column(name = "birth_date")
    private Date birth_date;
    @Column(name = "avatar_url")
    private String avatar_url;
    @Column(name = "verification")
    private boolean verification;
    @Column(name = "is_private")
    private boolean is_private;
    @Enumerated(value = EnumType.ORDINAL)
    private Status status;
    @Column(name = "created_at")
    private Date created_at;
}
