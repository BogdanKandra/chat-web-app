<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Creare Chatroom</title>
	</head>
	
	<body>
		<jsp:include page="header.jsp"/>
		
		<h1>Creare Chatroom</h1>
		
		<form action="/ChatWebApp/addChat" method="post" autocomplete="on">
		
		<table>
			<tr>
				<td>Nume Chatroom:</td>
				<td><input type="text" name="chatname" pattern=".{3,}" maxlength="50"
				           title="Numele chatroom-ului trebuie sa contina cel putin 3 caractere"
				           required autofocus /></td>
			</tr>
		</table> <br>
		
		<input type="submit" value="Adaugare">
		
		</form>
		
		<jsp:include page="footer.jsp"/>
	</body>
</html>