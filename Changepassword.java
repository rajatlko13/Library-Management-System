import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Changepassword")
public class Changepassword extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String password1=request.getParameter("presentpassword");
			String password2=request.getParameter("newpassword");
		
		String url1="jdbc:mysql://localhost:3306/exampledatabase?useSSL=false";
		String uname="root";
		String pass="Rajat123";
		String query1="select * from studentsignup where password='"+password1+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1=DriverManager.getConnection(url1,uname,pass);
		Statement st1=con1.createStatement();
		ResultSet rs1=st1.executeQuery(query1);	
		System.out.println("query1 executed");
		if(rs1.next())
		{
			String query2="update studentsignup set password=? where password=?";
		    PreparedStatement st2=con1.prepareStatement(query2);
		    st2.setString(1, password2);
		    st2.setString(2, password1);
		    int count=st2.executeUpdate();
		    System.out.println("query2 executed");
		    st2.close();
			response.sendRedirect("StudentPage.jsp");	
		}
		
		else
		{
			response.sendRedirect("InvalidDetails.jsp");
		}			
		st1.close();
		con1.close();
		
		}
		catch(Exception e)
		{
			return;
		}
		
	}

}
