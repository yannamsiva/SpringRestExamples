package com.shiva.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shiva.entity.Book;
import com.shiva.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private MessageSource messageSource;

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<?> createBook(
            @Valid @RequestBody BookDTO bookDTO,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        try {
            // Create a new Book entity from the DTO
            Book book = new Book();
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setIsbn(bookDTO.getIsbn());
            book.setEmail(bookDTO.getEmail());

            // Save the file locally if an image is provided
            if (imageFile != null && !imageFile.isEmpty()) {
                Path filePath = Paths.get(UPLOAD_DIR + imageFile.getOriginalFilename());
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, imageFile.getBytes());

                // Set the image URL in the book
                String imageUrl = "/uploads/" + imageFile.getOriginalFilename();
                book.setImage(imageUrl);
            }

            Book savedBook = bookService.saveBook(book);

            // Customize success message
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book inserted successfully");
            response.put("bookId", savedBook.getId().toString());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to upload image and create book");
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails) {
        Book book = bookService.getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setEmail(bookDetails.getEmail());
        book.setImage(bookDetails.getImage());
        Book updatedBook = bookService.saveBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("book.deleted", null, locale);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
//http://localhost:8080/api/books/4/uploadImage
    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<Map<String, String>> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile imageFile) {
        Book book = bookService.getBookById(id);
        try {
            // Save the file locally
            Path filePath = Paths.get(UPLOAD_DIR + imageFile.getOriginalFilename());
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, imageFile.getBytes());

            // Update the book's image URL
            String imageUrl = "/uploads/" + imageFile.getOriginalFilename();
            book.setImage(imageUrl);
            bookService.saveBook(book);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Image uploaded successfully");
            response.put("imageUrl", imageUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to upload image");
            return ResponseEntity.status(500).body(response);
        }
    }
}
