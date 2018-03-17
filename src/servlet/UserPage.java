package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import classes.Chat;

public class UserPage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public UserPage() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		int userid = (session.getAttribute("id") == null) ? 0 : (int) session.getAttribute("id");

		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

			String sql1 = "SELECT * FROM chat " + 
						  "WHERE user_id = ? OR (defaultMode = 1 AND id IN " +
						  			"(SELECT chat_id FROM participa " + 
						  			"WHERE user_id = ?))";
			String sql2 = "SELECT chat_id FROM participa " + 
						  "WHERE user_id = ?";
			String sql3 = "SELECT * FROM chat " + 
						  "WHERE id = ? AND defaultMode <> 1";

			List<Chat> listaChaturi = new ArrayList<>();

			// Recuperez chat-urile initiate de admin in care user-ul participa, sau de user-ul curent
			PreparedStatement pst1 = myConn.prepareStatement(sql1);
			pst1.setInt(1, userid);
			pst1.setInt(2, userid);

			ResultSet rs1 = pst1.executeQuery();

			while (rs1.next()){

				Chat c = new Chat();
				c.setID(rs1.getInt("id"));
				c.setNume(rs1.getString("nume"));
				int userID = rs1.getInt("user_id");
				c.setUser_ID(userID);

				if(!(userID == 0)){ // User-ul initiator inca exista in BD
					
					String q = "SELECT nume FROM utilizatori " +
							   "WHERE id = ?";
					PreparedStatement ps = myConn.prepareStatement(q);
					ps.setInt(1, userID);
					ResultSet r = ps.executeQuery();
					r.next();
					
					String userNume = r.getString("nume");
					c.setUser_name(userNume);
					
					r.close();
					ps.close();
				} else{ // User-ul initiator nu mai exista in BD
					
					c.setUser_name("Utilizator sters");
				}

				c.setCreated_at(rs1.getDate("created_at"));
				c.setDefaultMode(rs1.getByte("defaultMode"));

				listaChaturi.add(c);
			}
			
			// Recuperez chat-urile in care user-ul curent a fost invitat
			PreparedStatement pst2 = myConn.prepareStatement(sql2);
			pst2.setInt(1, userid);

			ResultSet rs2 = pst2.executeQuery();
			
			while(rs2.next()){

				int chatID = rs2.getInt("chat_id");
				
				PreparedStatement pst3 = myConn.prepareStatement(sql3);
				pst3.setInt(1, chatID);
				
				ResultSet rs3 = pst3.executeQuery();
				
				while(rs3.next()){
					
					Chat c = new Chat();
					c.setID(rs3.getInt("id"));
					c.setNume(rs3.getString("nume"));
					int userID = rs3.getInt("user_id");
					c.setUser_ID(userID);

					if(!(userID == 0)){
						
						String q = "SELECT nume FROM utilizatori " +
								   "WHERE id = ?";
						PreparedStatement ps = myConn.prepareStatement(q);
						ps.setInt(1, userID);
						ResultSet r = ps.executeQuery();
						r.next();
						
						String userNume = r.getString("nume");
						c.setUser_name(userNume);
						
						r.close();
						ps.close();
					} else{
						
						c.setUser_name("Utilizator sters");
					}

					c.setCreated_at(rs3.getDate("created_at"));
					c.setDefaultMode(rs3.getByte("defaultMode"));

					listaChaturi.add(c);
				}
				
				rs3.close();
				pst3.close();
			}
			
			rs2.close();
			pst2.close();

			request.setAttribute("listaChaturi", listaChaturi);
			request.getRequestDispatcher("/userpage.jsp").forward(request, response);

			rs1.close();
			pst1.close();
			myConn.close();

		} catch (SQLException e) {
			System.out.println("Problema legata de accesul la baza de date");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Problema neclasificata");
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
