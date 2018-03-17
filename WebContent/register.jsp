<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Inregistrare utilizator</title>
	</head>
	
	<body>
		<jsp:include page="header.jsp"/>
		
		<h1>Inregistrare utilizator</h1>
		
		<form action="/ChatWebApp/register" method="post" autocomplete="on">
<!-- 		action reprezinta URI-ul la care se trimit datele din formular -->
<!--        URI-ul este adresa de unde se ia un fisier, anume Register.java (conform web.xml) -->
		
		<table>
			<tr>
				<td>Nume de utilizator:</td>
				<td><input type="text" name="user" pattern=".{3,}" maxlength="50"
				           title="Numele de utilizator trebuie sa contina cel putin 3 caractere"
				           required autofocus /></td>
				<%
					int ugasit = (request.getAttribute("usergasit") == null) ? 0 : (int)request.getAttribute("usergasit");
					if(ugasit == 1){
				%>
					<td>Numele de utilizator introdus exista deja in baza de date!</td>
				<%
				    }
				%>
			</tr>
			
			<tr>
				<td>Parola:</td>
				<td><input type="password" name="pass" pattern=".{3,}" maxlength="50"
				           title="Parola trebuie sa contina cel putin 3 caractere" required></td>
			</tr>
			
			<tr>
				<td>Repetare parola:</td>
				<td><input type="password" name="pass2" maxlength="50" required></td>
				<%
					int pcoincid = (request.getAttribute("badPass") == null) ? 1 : (int)request.getAttribute("badPass");
					if(pcoincid == 0){
				%>
					<td>Parolele introduse nu coincid!</td>
				<%
				    }
				%>
			</tr>
			
			<tr>
				<td>Adresa de e-mail:</td>
				<td><input type="text" name="email" value="exemplu@provider.domeniu" maxlength="50"
						   pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
						   title="Adresa trebuie sa fie de forma exemplu@provider.domeniu" required></td>
				<%
				int egasit = (request.getAttribute("emailgasit") == null) ? 0 : (int)request.getAttribute("emailgasit"); 
				    if(egasit == 1){
				%>
					<td>Email-ul introdus exista deja in baza de date!</td>
				<%
				    }
				%>
			</tr>
		</table> <br>
		
		<input type="reset" value="Resetare campuri" />
		<input type="submit" value="Inregistrare">
		
		</form>
		
		<jsp:include page="footer.jsp"/>
	</body>
</html>