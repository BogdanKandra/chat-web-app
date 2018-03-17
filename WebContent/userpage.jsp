<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList,classes.Chat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<style type="text/css">
			table{
			    font-family: arial, sans-serif;
			    border-collapse: collapse;
			    width: 100%;
			}
			td, th{
			    border: 1px solid #dddddd;
			    text-align: center;
			    padding: 8px;
			}
			tr:nth-child(even){
			    background-color: #dddddd;
			}
		</style>
		<meta charset="ISO-8859-1">
		<title>Profilul lui <c:out value="${sessionScope.username}"/></title>
	</head>
	
	<body>
		<jsp:include page="header.jsp"/>
		
		<h1><c:out value="${sessionScope.username}"/></h1>
		<h2>Chatroom-urile tale:</h2>
		
		<h3 style="text-align:center"><a href="addChat.jsp">Creare Chatroom nou</a></h3> <br> <br>
		
		<table>
			<col style="width:30%">
			<col style="width:30%">
			<col style="width:20%">
			<col style="width:10%">
			<col style="width:10%">
			
			<tr>
				<th>Nume Chatroom</th>
				<th>Initiator</th>
				<th>Data crearii</th>
				<th>Sterge</th>
				<th>Paraseste</th>
			</tr>

			<c:forEach items="${listaChaturi}" var="chat">
				<tr>
					<td><a href="/ChatWebApp/chatPage?id=${chat.getID()}">${chat.getNume()}</a></td>
					<td>${chat.getUser_name()}</td>
					<td>${chat.getCreated_at()}</td>
					
					<c:set var="chatID" value="${chat.getUser_ID()}"/>
					<c:set var="chatIDaici" value="${sessionScope.id}"/>
					<c:set var="idChat" value="${chat.getID()}"/>
					
					<c:choose>
						<c:when test="${chatID == chatIDaici}">
							<td><a href="deleteChat.jsp?id=${idChat}">x</a></td>
						</c:when>
						<c:otherwise>
							<td>-</td>
						</c:otherwise>
					</c:choose>
					
					<td><a href="leaveChat.jsp?id=${idChat}">x</a></td>
				</tr>
			</c:forEach>
		</table> <br>
		
		<a href="deleteAccount.jsp">Stergere Cont</a>
		
		<jsp:include page="footer.jsp"/>
	</body>
</html>