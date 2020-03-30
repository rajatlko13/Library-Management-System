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


@WebServlet("/Changecontact")
public class Changecontact extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String contact1=request.getParameter("presentcontact");
			String contact2=request.getParameter("newcontact");
		
		String url1="jdbc:mysql://localhost:3306/exampledatabase?useSSL=false";
		String uname="root";
		String pass="Rajat123";
		String query1="select * from studentsignup where contact='"+contact1+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1=DriverManager.getConnection(url1,uname,pass);
		Statement st1=con1.createStatement();
		ResultSet rs1=st1.executeQuery(query1);	
		System.out.println("query1 executed");
		if(rs1.next())
		{
			String query2="update studentsignup set contact=? where contact=?";
		    PreparedStatement st2=con1.prepareStatement(query2);
		    st2.setString(1, contact2);
		    st2.setString(2, contact1);
		    int count=st2.executeUpdate();
		    System.out.println("query2 executed");
		    
		    String url2="jdbc:mysql://localhost:3306/library?useSSL=false";
			Connection con2=DriverManager.getConnection(url2,uname,pass);
		    String query3="update bookhistory,bookissue set bookhistory.contact=?,bookissue.contact=? where bookhistory.contact=? and bookissue.contact=?";
		    PreparedStatement st3=con2.prepareStatement(query3);
		    st3.setString(1, contact2);
		    st3.setString(2, contact2);
		    st3.setString(3, contact1);
		    st3.setString(4, contact1);
		    count=st3.executeUpdate();
		    System.out.println("query3 executed");
		    
		    String query4="select * from booklist";
			Statement st4=con2.createStatement();
			ResultSet rs4=st4.executeQuery(query4);
			System.out.println("query4 executed");
			while(rs4.next())
			{
				String bookid=rs4.getString("id");
				String query5="update book"+bookid+" set contact=? where contact=?";
			    PreparedStatement st5=con2.prepareStatement(query5);
			    st5.setString(1, contact2);
			    st5.setString(2, contact1);
			    count=st5.executeUpdate();
			    System.out.println("query5 executed");
			    st5.close();
			}
			st2.close();
			st3.close();
			st4.close();
			con2.close();
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
