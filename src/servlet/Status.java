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
import javax.servlet.http.HttpSession;

public class Status extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
       
    public Status() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		int idUtiliz = (session.getAttribute("id") == null) ? 0 : (int) session.getAttribute("id");
		String status = request.getParameter("status");
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			String upd = "UPDATE utilizatori " + 
						 "SET status = ? " + 
						 "WHERE id = ?";
			PreparedStatement pst = myConn.prepareStatement(upd);
			pst.setInt(2, idUtiliz);
			
			if(status.equals("on")){
				
				pst.setInt(1, 2);
				pst.executeUpdate();
				pst.close();
				
				response.sendRedirect("/ChatWebApp/userPage");
			} else if(status.equals("inv")){
				
				pst.setInt(1, 1);
				pst.executeUpdate();
				pst.close();
				
				response.sendRedirect("/ChatWebApp/userPage");
			} else{
				pst.setInt(1, 0);
				pst.executeUpdate();
				pst.close();
				
				session.invalidate();
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			
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
