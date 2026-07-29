package com.vasi.controller;

import com.vasi.model.Attendance;
import com.vasi.model.Student;
import com.vasi.repository.AttendanceRepository;
import com.vasi.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceRepository repo;
    private final StudentRepository studentRepo;

    public AttendanceController(AttendanceRepository repo, StudentRepository studentRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
    }

    @GetMapping
    public List<Attendance> getAll() {
        return repo.findAll();
    }

    @GetMapping("/student/{studentId}")
    public List<Attendance> getByStudent(@PathVariable int studentId) {
        return repo.findByStudentId(studentId);
    }

    @GetMapping("/date/{date}")
    public List<Attendance> getByDate(@PathVariable String date) {
        return repo.findByDate(LocalDate.parse(date));
    }

    // Expects: { "studentId": 1, "date": "2026-07-25", "status": "PRESENT" }
    @PostMapping
    public Attendance mark(@RequestBody Map<String, Object> body) {
        int studentId = (Integer) body.get("studentId");
        LocalDate date = LocalDate.parse((String) body.get("date"));
        String status = (String) body.get("status");

        Student student = studentRepo.findById(studentId).orElseThrow();

        // Prevent duplicate entries for the same student+date — update instead
        List<Attendance> existing = repo.findByStudentIdAndDate(studentId, date);
        if (!existing.isEmpty()) {
            Attendance record = existing.get(0);
            record.setStatus(status);
            return repo.save(record);
        }

        Attendance attendance = new Attendance(student, date, status);
        return repo.save(attendance);
    }

    @PutMapping("/{id}")
    public Attendance update(@PathVariable int id, @RequestBody Map<String, Object> body) {
        Attendance existing = repo.findById(id).orElseThrow();
        existing.setStatus((String) body.get("status"));
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repo.deleteById(id);
    }
}