package com.shiva.entity;




import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title cannot be more than 100 characters")
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(max = 100, message = "Author cannot be more than 100 characters")
    private String author;

    @NotBlank(message = "ISBN is mandatory")
    @Size(max = 13, message = "ISBN cannot be more than 13 characters")
    private String isbn;

    @Email(message = "Email should be valid")
    private String email;

    @CreationTimestamp
    @JsonIgnore

    private LocalDateTime createdTime;

    @UpdateTimestamp
    @JsonIgnore

    private LocalDateTime updatedTime;

    private String image;
}
