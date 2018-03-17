package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KickUser extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
       
    public KickUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idUtiliz = Integer.valueOf(request.getParameter("u"));
		int idChat = Integer.valueOf(request.getParameter("c"));
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			String del = "DELETE FROM participa " + 
						 "WHERE user_id = ? AND chat_id = ?";
			PreparedStatement ps = myConn.prepareStatement(del);
			ps.setInt(1, idUtiliz);
			ps.setInt(2, idChat);
			ps.executeUpdate();
			
			response.sendRedirect("/ChatWebApp/chatPage?id=" + idChat);
			
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
