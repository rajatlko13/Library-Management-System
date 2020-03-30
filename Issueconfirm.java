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


@WebServlet("/Issueconfirm")
public class Issueconfirm extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String id=request.getParameter("bookid");	
			String issuerusername=request.getParameter("issuerusername");
			String issueremail=request.getParameter("issueremail");
		String url1="jdbc:mysql://localhost:3306/exampledatabase?useSSL=false";
		String uname="root";
		String pass="Rajat123";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1=DriverManager.getConnection(url1,uname,pass);
		String query1="select * from studentsignup where username='"+issuerusername+"' and email='"+issueremail+"'";
		Statement st1=con1.createStatement();
		ResultSet rs1=st1.executeQuery(query1);
		
		if(rs1.next())
		{
			String issuername=rs1.getString("name");
			String issuercontact=rs1.getString("contact");
			String url2="jdbc:mysql://localhost:3306/library?useSSL=false";
			Connection con2=DriverManager.getConnection(url2,uname,pass);
			String query="select * from bookissue where email='"+issueremail+"'";
			Statement st=con2.createStatement();
			ResultSet rs=st.executeQuery(query);
			if(rs.next())
			{
				
				HttpSession session=request.getSession();
				session.setAttribute("user", issuerusername);
				st.close();
				response.sendRedirect("ExtraIssue.jsp");
			}
			else
			{
				String query2="select * from booklist where id='"+id+"'";
				Statement st2=con2.createStatement();	
				ResultSet rs2=st2.executeQuery(query2);
				//System.out.println("query2 executed");
				rs2.next();
				String bookname=rs2.getString("name");
				int copies=rs2.getInt("copies");
				
				if(copies>0)
				{
				String query3="update booklist set copies=? where id=?";
				PreparedStatement st3=con2.prepareStatement(query3);
				copies=copies-1;
				st3.setInt(1, copies);
				st3.setString(2, id);
				int count=st3.executeUpdate();
				//System.out.println("query3 executed");

				String query4="insert into bookissue values(?,?,?,?,?,?,?)";
				PreparedStatement st4=con2.prepareStatement(query4);
				st4.setString(1, id);
				st4.setString(2, bookname);
				st4.setString(3, issuername);
				st4.setString(4, issuercontact);
				st4.setString(5, issueremail);
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				String issuedate=formatter.format(date);
				st4.setString(6, issuedate);
				st4.setInt(7, 0);
				count=st4.executeUpdate();
				System.out.println("query4 executed");
		
				String query5="insert into book"+id+" values(?,?,?,?,?)";
				PreparedStatement st5=con2.prepareStatement(query5);	
				st5.setString(1, issuername);
				st5.setString(2, issuercontact);
				st5.setString(3, issueremail);
				st5.setString(4, issuedate);
				st5.setString(5, "---");
				count=st5.executeUpdate();
				System.out.println("query5 executed");

				//String subemail=issueremail.substring(0,issueremail.indexOf('@'));
				String query6="insert into "+issuerusername+" values(?,?,?,?)";
				PreparedStatement st6=con2.prepareStatement(query6);	
				st6.setString(1, id);
				st6.setString(2, bookname);
				st6.setString(3, issuedate);
				st6.setString(4, "---");
				count=st6.executeUpdate();
				System.out.println("query6 executed");
				
				String query7="update originalbooklist set copies=? where id=?";
				PreparedStatement st7=con2.prepareStatement(query7);
				st7.setInt(1, copies);
				st7.setString(2, id);
				count=st7.executeUpdate();
				
				String query8="insert into bookhistory values(?,?,?,?,?,?,?,?)";
				PreparedStatement st8=con2.prepareStatement(query8);
				st8.setString(1, id);
				st8.setString(2, bookname);
				st8.setString(3, issuername);
				st8.setString(4, issuercontact);
				st8.setString(5, issueremail);
				st8.setString(6, issuedate);
				st8.setString(7, "---");
				st8.setInt(8, 0);
				count=st8.executeUpdate();
		
				st2.close();
				st3.close();
				st4.close();
				st5.close();
				st6.close();
				st7.close();
				st8.close();
				}
				else
				{
					response.sendRedirect("IssueBook2.jsp");
				}
			}
			con2.close();
			response.sendRedirect("IssueBook.jsp");	
		}
		else
		{
			response.sendRedirect("IssueBook2.jsp");		
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
