<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Stergere Cont</title>
	</head>
	
	<body>
		<jsp:include page="header.jsp"/>
		<h1>Stergere Cont</h1>
		<h3>Reintroduceti parola pentru confirmare stergere cont:</h3>
		
		<form action="/ChatWebApp/deleteAccount" method="post" autocomplete="on">
		
			<table>
				<tr>
					<td>Parola:</td>
					<td><input type="password" name="passw" required></td>
					<%
						int pcorecta = (request.getAttribute("parolacorecta") == null) ? 0 : (int)request.getAttribute("parolacorecta");
						if(pcorecta == -1){
					%>
						<td>Parola introdusa este gresita!</td>
					<%
						}
					%>
				</tr>
			</table> <br>
			
			<input type="submit" value="Stergere Cont">
		
		</form>
		
		<jsp:include page="footer.jsp"/>
	</body>
</html>