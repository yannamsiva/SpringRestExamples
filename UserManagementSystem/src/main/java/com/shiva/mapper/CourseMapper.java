package com.shiva.mapper;

import org.springframework.stereotype.Component;

import com.shiva.entity.Course;
import com.shiva.entity.CourseDTO;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setName(course.getName());
        dto.setDeleted(course.isDeleted());
        return dto;
    }

    public Course toEntity(CourseDTO dto) {
        Course course = new Course();
        course.setId(dto.getId());
        course.setName(dto.getName());
        course.setDeleted(dto.getDeleted());
        return course;
    }
}
