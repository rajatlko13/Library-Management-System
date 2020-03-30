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


@WebServlet("/GetFeedback")
public class GetFeedback extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			String contact=request.getParameter("contact");
			String message=request.getParameter("message");
		
		String url="jdbc:mysql://localhost:3306/library?useSSL=false";
		String uname="root";
		String pass="Rajat123";
		String query="insert into feedback values(?,?,?,?)";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,uname,pass);
		PreparedStatement st=con.prepareStatement(query);
		st.setString(1, name);
		st.setString(2, email);
		st.setString(3, contact);
		st.setString(4, message);
		int count=st.executeUpdate();	
		System.out.println("query executed");
		HttpSession session=request.getSession();
		if(session.getAttribute("username")!=null)
			response.sendRedirect("StudentPage.jsp");	
		else if(session.getAttribute("adminusername")!=null)
			response.sendRedirect("AdminPage.jsp");
		else
			response.sendRedirect("main.jsp");		
		st.close();
		con.close();
		
		}
		catch(Exception e)
		{
			return;
		}
		
	}

}
