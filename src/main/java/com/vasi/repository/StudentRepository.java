package com.vasi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vasi.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
