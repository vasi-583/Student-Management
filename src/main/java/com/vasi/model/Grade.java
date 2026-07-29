package com.vasi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private double marks;

    @Column(nullable = false)
    private double maxMarks;

    public Grade() {}

    public Grade(Student student, String subject, double marks, double maxMarks) {
        this.student = student;
        this.subject = subject;
        this.marks = marks;
        this.maxMarks = maxMarks;
    }

    public int getId() { return id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }
    public double getMaxMarks() { return maxMarks; }
    public void setMaxMarks(double maxMarks) { this.maxMarks = maxMarks; }
}