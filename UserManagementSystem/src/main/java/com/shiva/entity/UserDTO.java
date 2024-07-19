package com.shiva.entity;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    private String phoneNumber;

    private String address;

    @NotEmpty
    private String gender; // Storing gender as String for simplicity

    private List<Long> courseIds; // List of Course IDs

    @NotEmpty
    @Size(min = 8)
    private String password;

    private String imageUrl;

    private Boolean deleted; // For soft delete
}
