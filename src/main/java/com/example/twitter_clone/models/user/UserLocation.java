package com.example.twitter_clone.models.user;

import com.example.twitter_clone.util.enums.Country;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "User_Location")
@Data
public class UserLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Enumerated(value = EnumType.ORDINAL)
    private Country country;
    @Column(name = "city")
    private String city;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
