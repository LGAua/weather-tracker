package com.lga.weathertracker.entity;

import com.lga.weathertracker.annotation.StrongPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email(message = "Invalid email")
    private String login;

    @NotBlank(message = "Invalid password")
    @Size(min = 3, max = 64,message = "Password should contain at least 3 letters")
    @StrongPassword
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Location> locations;

    public void addNewLocation(Location location) {
        locations.add(location);
    }
}
