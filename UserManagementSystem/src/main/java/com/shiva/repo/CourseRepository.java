package com.shiva.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shiva.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByDeletedFalse(); // Fetch non-deleted courses
}
