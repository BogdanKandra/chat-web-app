<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>WebChatKB</title>
	</head>
	
	<body>
		<h1>WebChatApp</h1>
		<%
			String username = (session.getAttribute("username") == null) ? "" : (String)session.getAttribute("username");
			if(username == ""){
		%>
			<a href="register.jsp">Inregistrare</a> <br>
			<a href="login.jsp">Autentificare</a>
		<%
			} else{
		%>
			<%= session.getAttribute("username") %> <br>
			<a href="/ChatWebApp/userPage">Mergi la pagina ta</a> <br><br>
			<a href="/ChatWebApp/logout">Deconectare</a> <br><br>
		<%
			}
		%>
	</body>
</html>