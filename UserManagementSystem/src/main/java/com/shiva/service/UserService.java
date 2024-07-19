package com.shiva.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shiva.entity.Course;
import com.shiva.entity.User;
import com.shiva.exception.ResourceNotFoundException;
import com.shiva.repo.CourseRepository;
import com.shiva.repo.UserRepository;

@Service
public class UserService {

    private static final String UPLOAD_DIR = "user-images/";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public User createUser(User user, MultipartFile imageFile) throws IOException {
        // Ensure that all associated courses are managed entities
        Set<Course> managedCourses = new HashSet<>();
        for (Course course : user.getCourses()) {
            Course managedCourse = courseRepository.findById(course.getId()).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
            managedCourses.add(managedCourse);
        }
        user.setCourses(managedCourses);

        // Save the new user
        return userRepository.save(user);
    }


    public User updateUser(Long id, User userDetails, MultipartFile imageFile) throws IOException {
        // Fetch the existing user
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Update fields
        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhoneNumber(userDetails.getPhoneNumber());
        existingUser.setAddress(userDetails.getAddress());
        existingUser.setGender(userDetails.getGender());
        existingUser.setPassword(userDetails.getPassword());
        existingUser.setCourses(userDetails.getCourses());

        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile); // Implement this method to save the image and get the URL
            existingUser.setImageUrl(imageUrl);
        }

        // Merge the existing user
        return userRepository.save(existingUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        user.setDeleted(true); // Soft delete
        userRepository.save(user);
    }

    public void addCoursesToUser(Long userId, Set<Course> courses) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        for (Course course : courses) {
            courseRepository.findById(course.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found for this id :: " + course.getId()));
        }
        user.getCourses().addAll(courses);
        userRepository.save(user);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath);
        return filePath.toString();
    }
}
