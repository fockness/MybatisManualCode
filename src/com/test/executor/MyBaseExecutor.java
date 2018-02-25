package com.test.executor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.test.model.Student;

public class MyBaseExecutor implements MyExecutor {
	
	private String URL = "jdbc:mysql://localhost:3306/mybatis?useSSL=true";
	private String USERNAME = "root";
	private String PASSWORD = "871255";

	@Override
	public <T> T query(String statement) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Student student = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String sql = statement;
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			//此处为简便硬编码
			if(resultSet.next()){
				student = new Student();
				student.setName(resultSet.getString("s_name"));
				student.setId(resultSet.getInt("s_id"));
				student.setClassId(resultSet.getInt("class_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T)student;
	}

}
