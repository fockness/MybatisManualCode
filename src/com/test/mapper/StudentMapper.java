package com.test.mapper;

import com.test.model.Student;

public interface StudentMapper {
	public Student findStudentById(Integer id);
	public void insertStudent(Student student);
}
