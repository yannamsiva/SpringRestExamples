
package com.shiva.rest;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shiva.entity.User;
import com.shiva.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<User> createUser(
            @Valid @RequestPart("user") User user,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            return ResponseEntity.ok(userService.createUser(user, imageFile));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestPart("user") User userDetails,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userDetails, imageFile));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Other methods...
}
