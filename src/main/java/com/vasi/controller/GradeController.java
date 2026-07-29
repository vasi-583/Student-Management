package com.vasi.controller;

import com.vasi.model.Grade;
import com.vasi.model.Student;
import com.vasi.repository.GradeRepository;
import com.vasi.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "*")
public class GradeController {

    private final GradeRepository repo;
    private final StudentRepository studentRepo;

    public GradeController(GradeRepository repo, StudentRepository studentRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
    }

    @GetMapping
    public List<Grade> getAll() {
        return repo.findAll();
    }

    @GetMapping("/student/{studentId}")
    public List<Grade> getByStudent(@PathVariable int studentId) {
        return repo.findByStudentId(studentId);
    }

    // Expects: { "studentId": 1, "subject": "DSA", "marks": 78, "maxMarks": 100 }
    @PostMapping
    public Grade create(@RequestBody Map<String, Object> body) {
        int studentId = (Integer) body.get("studentId");
        Student student = studentRepo.findById(studentId).orElseThrow();

        String subject = (String) body.get("subject");
        double marks = ((Number) body.get("marks")).doubleValue();
        double maxMarks = ((Number) body.get("maxMarks")).doubleValue();

        Grade grade = new Grade(student, subject, marks, maxMarks);
        return repo.save(grade);
    }

    @PutMapping("/{id}")
    public Grade update(@PathVariable int id, @RequestBody Map<String, Object> body) {
        Grade existing = repo.findById(id).orElseThrow();
        existing.setSubject((String) body.get("subject"));
        existing.setMarks(((Number) body.get("marks")).doubleValue());
        existing.setMaxMarks(((Number) body.get("maxMarks")).doubleValue());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repo.deleteById(id);
    }
}