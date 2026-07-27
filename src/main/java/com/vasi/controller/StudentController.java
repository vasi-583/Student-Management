package com.vasi.controller;

import com.vasi.model.Student;
import com.vasi.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentRepository repo;

    public StudentController(StudentRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Student> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable int id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return repo.save(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable int id, @RequestBody Student updated) {
        Student existing = repo.findById(id).orElseThrow();
        existing.setName(updated.getName());
        existing.setCourse(updated.getCourse());
        existing.setEmail(updated.getEmail());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repo.deleteById(id);
    }
}