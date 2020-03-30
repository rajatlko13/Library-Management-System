<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.sql.*"%>
<%@page import="javax.servlet.http.*"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.servlet.ServletException"%>
<%@page import="java.io.IOException"%>
<%@ page import="java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*" %>

<title>Central Institute Library</title>
</head>
<body>

<%
try
{
	String url="jdbc:mysql://localhost:3306/library?useSSL=false";
	String uname="root";
	String pass="Rajat123";
	String query="select * from bookissue";
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con=DriverManager.getConnection(url,uname,pass);
	PreparedStatement st=con.prepareStatement(query);
	ResultSet rs=st.executeQuery();
	//Creating a result for getting status that messsage is delivered or not!
			String result="";
	while(rs.next())
	{
		String bookname=rs.getString("bookname");
		String issuedate=rs.getString("issuedate");
		String email=rs.getString("email");
		int totaldays=rs.getInt("totaldays");
		// Get recipient's email-ID, message & subject-line from index.html page
		final String to = email;
		final String subject ="Library Book Return Reminder";
		String messg ="";
		if(totaldays<6)
			messg="You have issued the book "+bookname+" on "+issuedate+" from the Central Institute Library. It has been "+totaldays+" days.";
		else if(totaldays==6 || totaldays==7)
			messg="You have issued the book "+bookname+" on "+issuedate+" from the Central Institute Library. It has been "+totaldays+" days. You are requested to return the book within 7 days from the issue date to get away from any fine.";
		if(totaldays==6 || totaldays==7)
			messg="You have issued the book "+bookname+" on "+issuedate+" from the Central Institute Library. It has been "+totaldays+" days. You are requested to return the book within 7 days from the issue date to get away from any fine.";
		else if(totaldays==8)
			messg="You have issued the book "+bookname+" on "+issuedate+" from the Central Institute Library. It has been "+totaldays+" days & you have exceeded the time limit of 7 days for book issue. You will be fined from today onwards, so you are requested to return the book as soon as possible to pay minimum fine.";
		else if(totaldays>8)
			messg="You have issued the book "+bookname+" on "+issuedate+" from the Central Institute Library. It has been "+totaldays+" days & you have exceeded the time limit of 7 days for book issue. You are requested to return the book as soon as possible to pay minimum fine.";
			
		// Sender's email ID and password needs to be mentioned
		final String from = "rajatlko13@gmail.com";
		final String mailpass = "humraj2018";
		// Defining the gmail host
		String host = "smtp.gmail.com";
		// Creating Properties object
		Properties props = new Properties();
		// Defining properties
		props.put("mail.smtp.host", host);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.user", from);
		props.put("mail.password", mailpass);
		props.put("mail.port", "443");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");  
		//props.put("mail.smtp.socketFactory.port", "465");
		//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//props.put("mail.smtp.socketFactory.fallback", "false");
		// Authorized the Session object.
		Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() 
		{
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() 
		    {
		        return new PasswordAuthentication(from, mailpass);
		    }
		});
		try 
		{
		    // Create a default MimeMessage object.
		    MimeMessage message = new MimeMessage(mailSession);
		    // Set From: header field of the header.
		    message.setFrom(new InternetAddress(from));
		    // Set To: header field of the header.
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
		    // Set Subject: header field
		    message.setSubject(subject);
		    // Now set the actual message
		    message.setText(messg);
		    // Send message
		    Transport.send(message);
		    result = "Your mail sent successfully....";
		}	
		catch (MessagingException mex) 
		{
		    mex.printStackTrace();
		    result = "Error: unable to send mail....";
		}
		System.out.println(email+"---- "+result);
	}
	%>
	<h1><center><font color="blue">Sending Mail Using JSP</font></center></h1>
	<b><center><font color="red"><% out.println(result);%></font></center></b>
	<center><a href="AdminPage.jsp"><button style="margin-top:20px; background-color:#3399ff; color:white; border-radius:3px">Go to Home Page</button></a></center>
	<%
st.close();
con.close();
}
catch(Exception e)
{
  return;
}
%>



</body>
</html>