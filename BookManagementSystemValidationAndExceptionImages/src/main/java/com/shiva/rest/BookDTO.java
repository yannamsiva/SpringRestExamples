package com.shiva.rest;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDTO {
    
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
}
