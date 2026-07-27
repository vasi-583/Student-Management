package com.vasi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vasi.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>{

}
