package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SendMessage extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public SendMessage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		int idUtiliz = (session.getAttribute("id") == null) ? 0 : (int) session.getAttribute("id");
		int idChat = Integer.valueOf(request.getParameter("c")); // ID-ul chat-ului curent
		String continutMesaj = request.getParameter("message").trim();
		int mesajGol = 0;
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			String ins = "INSERT INTO mesaje (user_id, chat_id, data, continut) " + 
						 "VALUES (?,?,?,?)";
			PreparedStatement ps = myConn.prepareStatement(ins);
			ps.setInt(1, idUtiliz);
			ps.setInt(2, idChat);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.setString(4, continutMesaj);
			
			if(continutMesaj.length() == 0){
				mesajGol = 1;
				session.setAttribute("mesajGol", mesajGol);
				response.sendRedirect("/ChatWebApp/chatPage?id=" + idChat);
			} else{
				ps.executeUpdate();
				session.setAttribute("mesajGol", mesajGol);
				response.sendRedirect("/ChatWebApp/chatPage?id=" + idChat);
			}
			
			ps.close();
			myConn.close();
		} catch(SQLException e){
			System.out.println("Problema legata de accesul la baza de date");
			e.printStackTrace();
		} catch(Exception e){
			System.out.println("Problema neclasificata");
			e.printStackTrace();
		}
	}
}
