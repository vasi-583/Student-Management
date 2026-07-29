package com.vasi.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name="attendence")
public class Attendance {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	
	@ManyToOne
	@JoinColumn(name="student_id" ,nullable=false)
	private Student student;

	@Column(nullable=false)
	private LocalDate date;
	
	@Column(nullable=false)
	String status; //presenty /absenty
	
	

	public Attendance() { }

	public Attendance(Student student, LocalDate date, String status) {
		this.student = student;
		this.date = date;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
