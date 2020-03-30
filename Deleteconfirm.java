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


@WebServlet("/Deleteconfirm")
public class Deleteconfirm extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String id=request.getParameter("bookid");
			int copies=Integer.parseInt(request.getParameter("copies"));
			System.out.println("id="+id);
			System.out.println("copies="+copies);
		
		String url="jdbc:mysql://localhost:3306/library?useSSL=false";
		String uname="root";
		String pass="Rajat123";
		String query1="select * from originalbooklist where id='"+id+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,uname,pass);
		Statement st1=con.createStatement();
		ResultSet rs1=st1.executeQuery(query1);	
		rs1.next();
		int copies1=rs1.getInt("copies");
		int totalcopies=rs1.getInt("totalcopies");
		if(copies>=0 && copies<=copies1)
		{
			totalcopies=totalcopies-copies;
			copies=copies1-copies;
			String query2="update booklist, originalbooklist set booklist.copies="+copies+", originalbooklist.copies="+copies+", originalbooklist.totalcopies="+totalcopies+" where booklist.id='"+id+"' and originalbooklist.id='"+id+"'";
			Statement st2=con.createStatement();
			int count=st2.executeUpdate(query2);
			st2.close();
			response.sendRedirect("DeleteBook.jsp");	
		}
		else
			response.sendRedirect("DeleteError.jsp");		
		st1.close();
		con.close();
		
		}
		catch(Exception e)
		{
			return;
		}
		
	}

}
