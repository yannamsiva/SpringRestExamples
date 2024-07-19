package com.shiva.service;

import com.shiva.entity.Course;

import java.util.List;

public interface CourseService {

    Course createCourse(Course course);

    Course updateCourse(Long id, Course courseDetails);

    List<Course> getAllCourses();

    Course getCourseById(Long id);

    void deleteCourse(Long id);
}
