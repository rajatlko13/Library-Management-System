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


@WebServlet("/Returnconfirm")
public class Returnconfirm extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String email=request.getParameter("email");
		
		String url1="jdbc:mysql://localhost:3306/library?useSSL=false";
		String uname="root";
		String pass="Rajat123";
		String query1="select * from bookissue where email='"+email+"'";
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con1=DriverManager.getConnection(url1,uname,pass);
		Statement st1=con1.createStatement();
		ResultSet rs1=st1.executeQuery(query1);	
		
		if(rs1.next())
		{
		String bookid=rs1.getString("bookid");
		String issuedate=rs1.getString("issuedate");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String returndate=formatter.format(date);
		
		Date dateBefore = formatter.parse(issuedate);
	    Date dateAfter = formatter.parse(returndate);
	    long totaldays = (dateAfter.getTime() - dateBefore.getTime())/(1000*60*60*24);
	    String query2="update bookissue set totaldays="+totaldays+" where bookid='"+bookid+"'";
	    Statement st2=con1.createStatement();
	    int count=st2.executeUpdate(query2);
	    
	    String query3="select * from booklist where id='"+bookid+"'";
	    Statement st3=con1.createStatement();
	    ResultSet rs3=st3.executeQuery(query3);
	    
	    rs3.next();
	    int copies=rs3.getInt("copies");
	    copies=copies+1;
	    String query4="update booklist set copies="+copies+" where id='"+bookid+"'";
	    Statement st4=con1.createStatement();
	    count=st4.executeUpdate(query4);
	    
	    String query5="update book"+bookid+" set returndate='"+returndate+"' where email='"+email+"'";
	    Statement st5=con1.createStatement();
	    count=st5.executeUpdate(query5);
	    
	    String url2="jdbc:mysql://localhost:3306/exampledatabase?useSSL=false";
	    Connection con2=DriverManager.getConnection(url2,uname,pass);
	    String query6="select * from studentsignup where email='"+email+"'";
		Statement st6=con2.createStatement();
		ResultSet rs6=st6.executeQuery(query6);
		rs6.next();
		String username=rs6.getString("username");
	    
	    //String subemail=email.substring(0,email.indexOf('@'));
	    String query7="update "+username+" set returndate='"+returndate+"' where bookid='"+bookid+"'";
	    Statement st7=con1.createStatement();
	    count=st7.executeUpdate(query7);
	    
	    String query8="delete from bookissue where email='"+email+"'";
	    Statement st8=con1.createStatement();
	    count=st8.executeUpdate(query8);
	    
	    String query9="update bookhistory set returndate='"+returndate+"', totaldays="+totaldays+" where bookid='"+bookid+"'";
	    Statement st9=con1.createStatement();
	    count=st9.executeUpdate(query9);
	    
	    String query10="update originalbooklist set copies="+copies+" where id='"+bookid+"'";
	    Statement st10=con1.createStatement();
	    count=st10.executeUpdate(query10);
	    
		response.sendRedirect("ReturnBook.jsp");		
		st1.close();
		st2.close();
		st3.close();
		st4.close();
		st5.close();
		st6.close();
		st7.close();
		st8.close();
		st9.close();
		st10.close();
		con1.close();
		con2.close();
		}
		else
		{
			response.sendRedirect("ReturnBook1.jsp");		
		}
		
		}
		catch(Exception e)
		{
			return;
		}
		
	}

}
