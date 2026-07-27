package com.vasi.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;

@Entity
@Table(name="students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String name;
	
	
	private String course;
	
	@Column (unique=true)
	private String email;
	
	public Student() {
		
	}


	public Student( String name, String course ,String email) {
		super();
		
		this.name = name;
		this.course = course;
		this.email=email;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCourse() {
		return course;
	}


	public void setCourse(String course) {
		this.course = course;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String toString() {
		return "ID:" + id + "| Name: " + name + "| Course: "+ course + "|Email: "+ email;
	}
	
	
	
}
