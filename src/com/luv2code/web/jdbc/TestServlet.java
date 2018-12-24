package com.luv2code.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//Define DataSource/Connection pool with Resource Injection
	@Resource(name="jdbc/web_student")
	private DataSource dataSource;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		//Get connection to database
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			//create sql statement
			con = dataSource.getConnection();
			//execute sql
			String sql = "select * from student";
			stmt = con.createStatement();
			//process the resultSet
			resultSet = stmt.executeQuery(sql);
			
			while(resultSet.next()) {
				String email = resultSet.getString("email");
				out.println(email);
				out.println("<br>");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
