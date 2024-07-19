package com.shiva.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CourseDTO {

    private Long id;

    @NotEmpty
    private String name;

    @JsonIgnore
    private Boolean deleted; // For soft delete
}
