package com.vasi.controller;

import com.vasi.repository.AttendanceRepository;
import com.vasi.repository.CourseRepository;
import com.vasi.repository.GradeRepository;
import com.vasi.repository.StudentRepository;
import com.vasi.model.Attendance;
import com.vasi.model.Grade;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final GradeRepository gradeRepo;
    private final AttendanceRepository attendanceRepo;

    public DashboardController(StudentRepository studentRepo, CourseRepository courseRepo,
                                GradeRepository gradeRepo, AttendanceRepository attendanceRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.gradeRepo = gradeRepo;
        this.attendanceRepo = attendanceRepo;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalStudents = studentRepo.count();
        long totalCourses = courseRepo.count();

        List<Grade> grades = gradeRepo.findAll();
        double avgPercentage = grades.stream()
                .mapToDouble(g -> (g.getMarks() / g.getMaxMarks()) * 100)
                .average()
                .orElse(0.0);

        List<Attendance> attendanceRecords = attendanceRepo.findAll();
        long presentCount = attendanceRecords.stream()
                .filter(a -> "PRESENT".equalsIgnoreCase(a.getStatus()))
                .count();
        double attendanceRate = attendanceRecords.isEmpty()
                ? 0.0
                : (presentCount * 100.0) / attendanceRecords.size();

        stats.put("totalStudents", totalStudents);
        stats.put("totalCourses", totalCourses);
        stats.put("avgGradePercentage", Math.round(avgPercentage * 10.0) / 10.0);
        stats.put("attendanceRate", Math.round(attendanceRate * 10.0) / 10.0);
        stats.put("totalGradeRecords", grades.size());
        stats.put("totalAttendanceRecords", attendanceRecords.size());

        return stats;
    }
}