<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Parasire chatroom</title>
	</head>
	
	<body>
		<jsp:include page="header.jsp"/>
		
		<c:set var="idChat" value="${param.id}" scope="session"/>
		
		<h1>Parasire Chatroom</h1>
		<h3>Reintroduceti parola pentru confirmare parasire chatroom</h3>
		
		<form action="/ChatWebApp/leaveChat" method="post">
		
			<table>
				<tr>
					<td>Parola:</td>
					<td><input type="password" name="passw" required autofocus></td>
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
			
			<input type="submit" value="Paraseste Chat">
		
		</form>
		
		<jsp:include page="footer.jsp"/>
	</body>
</html>