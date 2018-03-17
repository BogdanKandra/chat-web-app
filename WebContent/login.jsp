<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Autentificare utilizator</title>
	</head>
	
	<body>
		<jsp:include page="header.jsp"/>
		<h1>Autentificare</h1>
		
		<form action="/ChatWebApp/login" method="post" autocomplete="on">
		
		<table>
			<tr>
				<td>Nume de utilizator:</td>
				<td><input type="text" name="user" required autofocus /></td>
				<%
					int ugasit = (request.getAttribute("usergasit") == null) ? 0 : (int)request.getAttribute("usergasit");
					if(ugasit == -1){
				%>
					<td>Numele de utilizator introdus nu exista in baza de date!</td>
				<%
				    }
				%>
			</tr>
			
			<tr>
				<td>Parola:</td>
				<td><input type="password" name="pass" required></td>
				<%
					int pgasita = (request.getAttribute("parolacorecta") == null) ? 0 : (int)request.getAttribute("parolacorecta");
					if(pgasita == -1){
				%>
					<td>Parola este gresita!</td>
				<%
					}
				%>
			</tr>
		</table> <br>
		
		<input type="submit" value="Autentificare">
		
		</form>
		
		<jsp:include page="footer.jsp"/>
	</body>
</html>