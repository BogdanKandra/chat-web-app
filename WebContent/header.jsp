<!-- Header Master Page -->

<div style="float:left; display:inline-block" id="headerLeft">
	<h2><a href="index.jsp">WebChatApp home</a></h2>
</div>

<div style="float:right; display:inline-block" id="headerRight">
		<%
			String username = (session.getAttribute("username") == null) ? "" : (String)session.getAttribute("username");
			if(username == ""){
			
			} else{
		%>
			<a href="/ChatWebApp/logout">Deconectare</a> <br><br>
			<form action="/ChatWebApp/status" method="post">
				<table>
					<tr>
						<td>Status:</td>
						<td>
						  <select name="status" required>
						    <option value="on">Online</option>
						    <option value="inv">Invizibil</option>
						    <option value="off">Offline</option>
						  </select>
						</td>
					</tr>
				</table> <br>
				
				<input type="submit" value="Modifica">
			
			</form>
		<%
			}
		%>
</div>

<div style="clear:both"></div>