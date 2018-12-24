package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	
	private DataSource dataSource;
	
	public StudentDBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Student> getMethod() throws Exception{
		
		List<Student> student = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rslt = null;
		
		try {
			//Get a connection
			conn = dataSource.getConnection();
			String sql = "select * from student order by lastname";
			//execute the query
			stmt = conn.createStatement();
			rslt = stmt.executeQuery(sql);
			//process the query
			while(rslt.next()) {
				int id = rslt.getInt("id");
				String firstname = rslt.getString("firstname");
				String lastname = rslt.getString("lastname");
				String email = rslt.getString("email");
				
				Student student_list = new Student(id, firstname, lastname, email);
				
				student.add(student_list);
			}
			return student;
		}
		finally{
			//close JDBC connection
			closeConnection(conn, stmt, rslt);
		}
	}

	private void closeConnection(Connection conn, Statement stmt, ResultSet rslt) {
		
		try {
			if(rslt != null) {
				rslt.close();
			}
			
			if(stmt != null) {
				stmt.close();
			}
			
			if(conn != null) {
				conn.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void addStudent(Student student)throws Exception {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "insert into student(firstname, lastname, email) values(?,?,?)";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			stmt.setString(3, student.getEmailAddress());
			
			stmt.execute();
			
		}
		finally {
			closeConnection(conn,stmt,null);
		}
		
	}

	public Student getStudentById(String id) throws Exception {
		Student theStudent=null; 
		
		Connection myConn = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int studentId;
		
		try {
			studentId = Integer.parseInt(id);
			myConn = dataSource.getConnection();
			String sql = "select * from student where id=?";
			preparedStatement = myConn.prepareStatement(sql);
			
			preparedStatement.setInt(1, studentId);
			rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				String email = rs.getString("email");
				
				theStudent = new Student(studentId, firstname, lastname, email);
			}
			else {
				throw new Exception("Could not find Student id: "+studentId);
			}
			return theStudent;	
		}
		finally {
			closeConnection(myConn, preparedStatement, rs);
		}
	}

	public void updateStudentRecord(Student theStudent) throws Exception {
		
		Connection myConn = null;
		PreparedStatement preparedStatement = null;
		
		try {
			myConn = dataSource.getConnection();
			String sql = "update student set firstname = ?, lastname = ?, email = ? where id = ?";
			preparedStatement = myConn.prepareStatement(sql);
			
			preparedStatement.setString(1, theStudent.getFirstName());
			preparedStatement.setString(2, theStudent.getLastName());
			preparedStatement.setString(3, theStudent.getEmailAddress());
			preparedStatement.setInt(4, theStudent.getId());
			
			preparedStatement.execute();
		}
		finally {
			closeConnection(myConn, preparedStatement, null);
		}
		
	}

	public void deleteStudent(int studentId) throws Exception {
		
		Connection myConn = null;
		PreparedStatement preparedStatement = null;

		try {
			myConn = dataSource.getConnection();
			String sql = "delete from student where id=?";
			preparedStatement = myConn.prepareStatement(sql);
			
			preparedStatement.setInt(1, studentId);
			
			preparedStatement.execute();
		}
		finally {
			closeConnection(myConn, preparedStatement, null);
		}
	}
	
}
