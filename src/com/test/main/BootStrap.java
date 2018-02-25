package com.test.main;

import com.test.mapper.StudentMapper;
import com.test.model.Student;
import com.test.session.MyDefaultSqlSession;
import com.test.session.MySqlSession;


public class BootStrap {
	public static void main(String[] args) {
		MySqlSession sqlSession = new MyDefaultSqlSession();
		StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
		Student student = studentMapper.findStudentById(1);
		System.out.println(student.getName());
	}
}
