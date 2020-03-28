package com.test.jdbcTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JDBCTest
 */
@WebServlet("/JDBCTest")
public class JDBCTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = "springstudent";
		String pass = "springstudent";
		
		String jdbcURL = "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false";

		String driver = "com.mysql.jdbc.Driver";
		
		// get connection
		
		try {
			PrintWriter out = response.getWriter();
			
			out.println("Connecting to db");
			
			Class.forName(driver);
			
			Connection myConn = DriverManager.getConnection(jdbcURL,user,pass);
			out.println("Success");
			myConn.close();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		
	}


}

