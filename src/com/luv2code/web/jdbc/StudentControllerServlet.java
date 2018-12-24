package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private StudentDBUtil studentDBUtil;   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Resource(name="jdbc/web_student")
	private DataSource dataSource;
	
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
	}



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//read the "command" parameter
			String thecommand = request.getParameter("command");
			
			//if thecommand is empty or null
			if(thecommand == null) {
				thecommand="LIST";
			}
			
			//route to the appropriate method
			switch(thecommand) {
			case "LIST":
				studentList(request, response);
				break;
				
			case "ADD":
				addStudent(request,response);
				break;
				
			case "LOAD":
				loadStudent(request,response);
				break;
			
			case "UPDATE":
				updateStudentList(request,response);
				break;
			
			case "DELETE":
				deleteStudent(request, response);
				break;
				
			default:
				studentList(request, response);
			}
			
			studentList(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}



	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		
		studentDBUtil.deleteStudent(studentId);
		
		studentList(request, response);
		
	}



	private void updateStudentList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// getting value from the user
		int studentId = Integer.parseInt(request.getParameter("id"));
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		
		// Store all the value in Student getter and setter
		Student theStudent = new Student(studentId, firstname, lastname, email);
		//Calling database method to update by passing id to it
		studentDBUtil.updateStudentRecord(theStudent);
		//forward the result to list_student.jsp
		studentList(request, response);
		
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//get the id from jsp
		String id = request.getParameter("studentId");
		//pass the id to the Util part
		Student theStudent = studentDBUtil.getStudentById(id);
		//Get the result
		request.setAttribute("STUDENT_ID", theStudent);
		//pass the information to the update-student-list.jsp
		RequestDispatcher rd = request.getRequestDispatcher("update-student-list.jsp");
		rd.forward(request, response);
		
	}



	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Read the Student
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		
		//New Student Object
		Student student = new Student(firstname,lastname,email);
		//Add Student to database
		studentDBUtil.addStudent(student);
		
		//And back to the list page
		studentList(request, response);
		
	}



	private void studentList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// Get student form util
		List<Student> student_list = studentDBUtil.getMethod();  
	
		//add student to request
		request.setAttribute("STUDENT_DATA", student_list);
		
		//send to jsp
		RequestDispatcher rd = request.getRequestDispatcher("/list_students.jsp");
		rd.forward(request, response);
	}


}
