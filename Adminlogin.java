import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Adminlogin
 */
@WebServlet("/Adminlogin")
public class Adminlogin extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
		String username=request.getParameter("adminuname");
		String password=request.getParameter("adminpass");
		String url="jdbc:mysql://localhost:3306/exampledatabase?useSSL=false";
		String uname="root";
		String pass="Rajat123";
		String query="select * from login";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,uname,pass);
		PreparedStatement st=con.prepareStatement(query);
		ResultSet rs=st.executeQuery();
		while(rs.next())
		{
			String un=rs.getString(1);
			String ps=rs.getString(2);
			if(un.equals(username) && ps.equals(password))
			{
				HttpSession session=request.getSession();
				session.setAttribute("adminusername",username);
				response.sendRedirect("AdminPage.jsp");
			}
		}
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
