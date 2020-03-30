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


@WebServlet("/Addconfirm")
public class Addconfirm extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String id=request.getParameter("bookid");	
			String name=request.getParameter("name");
			String author=request.getParameter("author");
			int pages=Integer.parseInt(request.getParameter("pages"));	
			String publisher=request.getParameter("publisher");
			int copies=Integer.parseInt(request.getParameter("copies"));
		String url="jdbc:mysql://localhost:3306/library?useSSL=false";
		String uname="root";
		String pass="Rajat123";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1=DriverManager.getConnection(url,uname,pass);
		String query1="select * from originalbooklist where name='"+name+"' and publisher='"+publisher+"'";
		Statement st1=con1.createStatement();
		ResultSet rs1=st1.executeQuery(query1);
		
		if(rs1.next())                        //to increase copies if the book already exists
		{
			int copies1=rs1.getInt("copies");
			int totalcopies=rs1.getInt("totalcopies");
			String id1=rs1.getString("id");
			//String query2="update booklist set copies=? where id=?";
			String query2="update booklist,originalbooklist set booklist.copies=?,originalbooklist.copies=?,originalbooklist.totalcopies=? where booklist.id=? and originalbooklist.id=?";
			PreparedStatement st2=con1.prepareStatement(query2);
			copies1=copies1+copies;
			totalcopies=totalcopies+copies;
			st2.setInt(1, copies1);
			st2.setInt(2, copies1);
			st2.setInt(3, totalcopies);
			st2.setString(4, id1);
			st2.setString(5, id1);
			int count=st2.executeUpdate();
			st2.close();
		}
		else                                  
		{
			String query3="select * from booklist where id='"+id+"'";
			Statement st3=con1.createStatement();
			ResultSet rs3=st1.executeQuery(query3);
			if (rs3.next())						//to print error message if bookid entered by admin already exists
			{
				response.sendRedirect("AddBookExists.jsp");
			}
			else								
			{
				String query4="insert into booklist values(?,?,?,?,?,?)";
				PreparedStatement st4=con1.prepareStatement(query4);	
				st4.setString(1, id);
				st4.setString(2, name);
				st4.setString(3, author);
				st4.setInt(4, pages);
				st4.setString(5, publisher);
				st4.setInt(6, copies);
				int count=st4.executeUpdate();
				
				String query5="insert into originalbooklist values(?,?,?,?,?,?,?)";
				PreparedStatement st5=con1.prepareStatement(query5);	
				st5.setString(1, id);
				st5.setString(2, name);
				st5.setString(3, author);
				st5.setInt(4, pages);
				st5.setString(5, publisher);
				st5.setInt(6, copies);
				st5.setInt(7, copies);
				count=st5.executeUpdate();
				
				String query6="create table book"+id+"(name varchar(40),contact varchar(11),email varchar(40),issuedate varchar(10),returndate varchar(10))";
				PreparedStatement st6=con1.prepareStatement(query6);
				st6.executeUpdate();
				st4.close();
				st5.close();
				st6.close();
			}
			st3.close();
		}
		
		response.sendRedirect("AddBook.jsp");		
		st1.close();		
		con1.close();
		
		}
		catch(Exception e)
		{
			return;
		}
		
	}

}
