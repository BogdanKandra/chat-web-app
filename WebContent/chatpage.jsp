<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Pagina chatului</title>
	</head>
	
	<body>
		<jsp:include page="header.jsp"/>
		
		<div style="float:left">
			<form action="/ChatWebApp/addUserChat?chat=${requestScope.chatID}" method="post">
				<input type="text" name="username" required/>
				<input type="submit" value="Adauga">
				<%
					int userexista = (request.getAttribute("userexista") == null) ? -1 : (int)request.getAttribute("userexista");
					if(userexista == 0){
				%>
					Username-ul introdus nu exista!
				<%
					} else{
						if(userexista == 1){
				%>
					Username-ul introdus este deja membru al chat-ului!
				<%
						}
					}
				%>
			</form>
			
			<table>
				<tr>
					<th>Nume Utilizator</th>
					<th>Status</th>
				</tr>
	
				<c:forEach items="${listaMembri}" var="user">
					<tr>
						<td>${user.getNume()}</td>
						
						<c:set var="status" value="${user.getStatus()}"/>
						<c:choose>
							<c:when test="${status == 2}">
								<td>(Online)</td>
							</c:when>
							<c:otherwise>
								<td>(Offline)</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</table> <br>
		</div>
		
		<div style="float:left">
			<textarea rows="30" cols="100" readonly>
				<c:forEach items="${listaMesaje}" var="mesaj">
${mesaj.getUsername()} (${mesaj.getData()}): ${mesaj.getContinut()} &#13;
				</c:forEach>
			</textarea>
			
			<form action="/ChatWebApp/sendMessage?c=${requestScope.chatID}" method="post" autocomplete="off">
				<table>
					<tr>
						<td><input type="text" name="message" style="height: 20px;" size="100" maxlength="500" autocomplete="off" required autofocus /></td>
						<td><input type="submit" value="Trimite"></td>
						<%
							int mesajGol = (session.getAttribute("mesajGol") == null) ? 0 : (int)session.getAttribute("mesajGol");
							if(mesajGol == 1){
						%>
							<td>Introdu un mesaj in campul de text!</td>
						<%
							}
						%>
					</tr>
				</table> <br>
			</form>
		</div>
		
		<div style="clear:both"></div>
		
		<jsp:include page="footer.jsp"/>
	</body>
</html>