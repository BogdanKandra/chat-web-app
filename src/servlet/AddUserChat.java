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

public class AddUserChat extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
       
    public AddUserChat() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userToAdd = request.getParameter("username"); // Username-ul introdus pentru adaugare in chat
		int chatID = Integer.valueOf(request.getParameter("chat")); // ID-ul chat-ului curent
		int u_exista = 2;
		List<Integer> listaIDs = new ArrayList<>();
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			String sel1 = "SELECT id FROM utilizatori " + 
						 "WHERE nume = ?";
			String sel2 = "SELECT user_id FROM chat " + 
						  "WHERE id = ?";
			String sel3 = "SELECT user_id FROM participa " + 
						  "WHERE chat_id = ?";
			
			PreparedStatement ps2 = myConn.prepareStatement(sel2);
			ps2.setInt(1, chatID);
			ResultSet rs2 = ps2.executeQuery();
			rs2.next();
			
			int userID = rs2.getInt("user_id");			
			listaIDs.add(userID);
			
			rs2.close();
			ps2.close();
			
			PreparedStatement ps3 = myConn.prepareStatement(sel3);
			ps3.setInt(1, chatID);
			ResultSet rs3 = ps3.executeQuery();

			while(rs3.next()){
				
				listaIDs.add(rs3.getInt("user_id"));
			}
			
			rs3.close();
			ps3.close();
			
			PreparedStatement ps1 = myConn.prepareStatement(sel1);
			ps1.setString(1, userToAdd);
			ResultSet rs1 = ps1.executeQuery();
			
			if(!rs1.next()){ // Username-ul introdus nu exista in BD
				
				u_exista = 0;
				request.setAttribute("userexista", u_exista);
//				request.getRequestDispatcher("/chatpage.jsp").forward(request, response);
				response.sendRedirect("/ChatWebApp/chatPage?id=" + chatID + "&not=1");
			} else{ // Username-ul introdus exista in BD
				
				int id_user = rs1.getInt("id");
				if(listaIDs.contains(id_user)){ // User-ul introdus este deja membru in chat
					
					u_exista = 1;
					request.setAttribute("userexista", u_exista);
//					request.getRequestDispatcher("/chatpage.jsp").forward(request, response);
					response.sendRedirect("/ChatWebApp/chatPage?id=" + chatID + "&is=1");
				} else{ // User-ul introdus nu este membru in chat
					
					String ins = "INSERT INTO participa " + 
								 "VALUES (?,?)";
					PreparedStatement psi = myConn.prepareStatement(ins);
					psi.setInt(1, id_user);
					psi.setInt(2, chatID);
					
					psi.executeUpdate();
					psi.close();
					
					response.sendRedirect("/ChatWebApp/chatPage?id=" + chatID);
				}
			}
			
			rs1.close();
			ps1.close();
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
