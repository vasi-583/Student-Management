package com.vasi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(unique = true)
    private String email;

    public Student() {}

    public Student(String name, Course course, String email) {
        this.name = name;
        this.course = course;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}