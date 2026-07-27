package com.vasi.controller;

import com.vasi.model.Course;
import com.vasi.repository.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseRepository repo;

    public CourseController(CourseRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Course> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable int id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        return repo.save(course);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable int id, @RequestBody Course updated) {
        Course existing = repo.findById(id).orElseThrow();
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repo.deleteById(id);
    }
}