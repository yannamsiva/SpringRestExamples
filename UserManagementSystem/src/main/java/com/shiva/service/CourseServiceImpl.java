package com.shiva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shiva.entity.Course;
import com.shiva.exception.ResourceNotFoundException;
import com.shiva.repo.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

	    @Autowired
	    private CourseRepository courseRepository;

	    public Course createCourse(Course course) {
	        return courseRepository.save(course);
	    }

	    public Course updateCourse(Long id, Course courseDetails) {
	        Course course = courseRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));

	        course.setName(courseDetails.getName());
	        return courseRepository.save(course);
	    }

	    public List<Course> getAllCourses() {
	        return courseRepository.findByDeletedFalse();
	    }

	    public Course getCourseById(Long id) {
	        Course course = courseRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
	        
	        if (course.isDeleted()) {
	            throw new ResourceNotFoundException("Course not found with id " + id);
	        }

	        return course;
	    }

	    public void deleteCourse(Long id) {
	        Course course = courseRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
	        course.setDeleted(true);
	        courseRepository.save(course);
	    }
	}
