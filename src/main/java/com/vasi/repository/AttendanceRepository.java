package com.vasi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vasi.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
		List<Attendance> findByStudentId(int studentId);
		List<Attendance> findByDate(LocalDate date);
		List<Attendance> findByStudentIdAndDate(int studentId , LocalDate date );
		
}
