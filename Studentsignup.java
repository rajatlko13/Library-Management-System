import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Studentsignup
 */
@WebServlet("/Studentsignup")
public class Studentsignup extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		String name=request.getParameter("studentname");
		String contact=request.getParameter("studentcontact");
		String email=request.getParameter("studentemail");
		String username=request.getParameter("studentusername");
		String password=request.getParameter("studentpass");
		String url="jdbc:mysql://localhost:3306/ExampleDatabase?useSSL=false";
		String uname="root";
		String pass="Rajat123";
		String query1="insert into studentsignup values(?,?,?,?,?)";
		String query2="insert into studentlogin values(?,?)";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,uname,pass);
		PreparedStatement st1=con.prepareStatement(query1);
		st1.setString(1, name);
		st1.setString(2, contact);
		st1.setString(3, email);
		st1.setString(4, username);
		st1.setString(5, password);
		PreparedStatement st2=con.prepareStatement(query2);
		st2.setString(1, username);
		st2.setString(2, password);
		int count1=st1.executeUpdate();	
		int count2=st2.executeUpdate();	
		String url3="jdbc:mysql://localhost:3306/library?useSSL=false";
		Connection con3=DriverManager.getConnection(url3,uname,pass);
		//String subemail=email.substring(0,email.indexOf('@'));
		String query3="create table "+username+"(bookid varchar(10),bookname varchar(40),issuedate varchar(10),returndate varchar(20))";
		Statement st3=con3.createStatement();
		st3.executeUpdate(query3);
		if(count1>0 && count2>0)
			response.sendRedirect("main.jsp");	
		st1.close();
		st2.close();
		st3.close();
		con.close();
		}
		catch(Exception e)
		{
			return;
		}
		
	}

}
