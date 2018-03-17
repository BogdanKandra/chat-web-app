package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
       
    public Logout() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		int idUtiliz = (session.getAttribute("id") == null) ? 0 : (int) session.getAttribute("id");
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			String stat1 = "SELECT status FROM utilizatori " +
						   "WHERE id = ?";
			PreparedStatement ps1 = myConn.prepareStatement(stat1);
			ps1.setInt(1, idUtiliz);
			ResultSet rsStat1 = ps1.executeQuery();
			
			rsStat1.next();
			int statusul = rsStat1.getInt("status");
			
			rsStat1.close();
			ps1.close();
			
			if(statusul == 2){ // Daca statusul era "Online", il setez "Offline"
				
				String stat2 = "UPDATE utilizatori " + 
							   "SET status = 0 " + 
							   "WHERE id = ?";
				PreparedStatement ps2 = myConn.prepareStatement(stat2);
				ps2.setInt(1, idUtiliz);
				ps2.executeUpdate();
				
				ps2.close();
			} // Daca statusul era "Invizibil", il las asa
			
			myConn.close();
		} catch(SQLException e){
			System.out.println("Problema legata de accesul la baza de date");
			e.printStackTrace();
		} catch(Exception e){
			System.out.println("Problema neclasificata");
			e.printStackTrace();
		}
		
		session.invalidate();
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
