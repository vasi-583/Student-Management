package com.vasi.controller;

import com.vasi.model.Course;
import com.vasi.model.Student;
import com.vasi.repository.CourseRepository;
import com.vasi.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentRepository repo;
    private final CourseRepository courseRepo;

    public StudentController(StudentRepository repo, CourseRepository courseRepo) {
        this.repo = repo;
        this.courseRepo = courseRepo;
    }

    @GetMapping
    public List<Student> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable int id) {
        return repo.findById(id).orElse(null);
    }

    // Expects: { "name": "...", "email": "...", "courseId": 1 }
    @PostMapping
    public Student create(@RequestBody Map<String, Object> body) {
        Student student = new Student();
        student.setName((String) body.get("name"));
        student.setEmail((String) body.get("email"));

        Integer courseId = (Integer) body.get("courseId");
        if (courseId != null) {
            Course course = courseRepo.findById(courseId).orElse(null);
            student.setCourse(course);
        }

        return repo.save(student);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable int id, @RequestBody Map<String, Object> body) {
        Student existing = repo.findById(id).orElseThrow();
        existing.setName((String) body.get("name"));
        existing.setEmail((String) body.get("email"));

        Integer courseId = (Integer) body.get("courseId");
        if (courseId != null) {
            Course course = courseRepo.findById(courseId).orElse(null);
            existing.setCourse(course);
        }

        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repo.deleteById(id);
    }
}