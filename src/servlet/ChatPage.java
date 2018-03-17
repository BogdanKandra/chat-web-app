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

import classes.Mesaj;
import classes.User;

public class ChatPage extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
       
    public ChatPage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String username = (session.getAttribute("username") == null) ? "" : (String) session.getAttribute("username");
		int idUtiliz = (session.getAttribute("id") == null) ? 0 : (int) session.getAttribute("id");
		int idChat = Integer.valueOf(request.getParameter("id"));
		int userNotExists = (request.getParameter("not") == null) ? 0 : Integer.valueOf(request.getParameter("not"));
		int userIsMember = (request.getParameter("is") == null) ? 0 : Integer.valueOf(request.getParameter("is"));

		List<User> listaMembri = new ArrayList<>();
		List<Integer> listaIDs = new ArrayList<>();
		List<Mesaj> listaMesaje = new ArrayList<>();
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			String sel1 = "SELECT user_id FROM chat " + 
						  "WHERE id = ?";
			String sel2 = "SELECT user_id FROM participa " + 
						  "WHERE chat_id = ?";
			
			PreparedStatement ps1 = myConn.prepareStatement(sel1);
			ps1.setInt(1, idChat);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			
			int id_user = rs1.getInt("user_id");
			
			if(id_user != -1){
				listaIDs.add(id_user);
			}
			
			PreparedStatement ps2 = myConn.prepareStatement(sel2);
			ps2.setInt(1, idChat);
			ResultSet rs2 = ps2.executeQuery();

			while(rs2.next()){
				
				listaIDs.add(rs2.getInt("user_id"));
			}
			
			for(int id : listaIDs){
				
				String selUser = "SELECT * FROM utilizatori " + 
							     "WHERE id = ?";
				PreparedStatement ps = myConn.prepareStatement(selUser);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				rs.next();
				
				String numeUser = rs.getString("nume");
				if(!numeUser.equals("admin") && !numeUser.equals(username)){
					
					User u = new User();
					u.setID(rs.getInt("id"));
					u.setNume(rs.getString("nume"));
					u.setParola(rs.getString("parola"));
					u.setEmail(rs.getString("email"));
					u.setStatus(rs.getInt("status"));
					
					listaMembri.add(u);
				}
				
				rs.close();
				ps.close();
			}
			
			String sel3 = "SELECT * FROM mesaje " + 
						  "WHERE chat_id = ?";
			PreparedStatement ps3 = myConn.prepareStatement(sel3);
			ps3.setInt(1, idChat);
			ResultSet rs3 = ps3.executeQuery();
			
			while(rs3.next()){
				
				Mesaj m = new Mesaj();
				m.setID(rs3.getInt("id"));
				int userID = rs3.getInt("user_id");
				m.setUser_id(userID);
				
				if(!(userID == 0)){
					
					String q = "SELECT nume FROM utilizatori " +
							   "WHERE id = ?";
					PreparedStatement ps = myConn.prepareStatement(q);
					ps.setInt(1, userID);
					ResultSet r = ps.executeQuery();
					r.next();
					
					String userNume = r.getString("nume");
					m.setUsername(userNume);
					
					r.close();
					ps.close();
				} else{
					
					m.setUsername("Utilizator sters");
				}
				
				m.setChat_id(rs3.getInt("chat_id"));
				m.setData(rs3.getTimestamp("data"));
				m.setContinut(rs3.getString("continut"));
				
				listaMesaje.add(m);
			}
			
			rs3.close();
			ps3.close();
			
			request.setAttribute("listaIDs", listaIDs);
			request.setAttribute("listaMembri", listaMembri);
			request.setAttribute("listaMesaje", listaMesaje);
			request.setAttribute("chatID", idChat);
			
			if(userNotExists == 1){
				request.setAttribute("userexista", 0);
			} else if(userIsMember == 1){
				request.setAttribute("userexista", 1);
			}
			
			if(id_user == idUtiliz){ // User-ul curent este initiatorul chat-ului
				request.getRequestDispatcher("/chatpage2.jsp").forward(request, response);
			} else{
				request.getRequestDispatcher("/chatpage.jsp").forward(request, response);
			}
			
			rs2.close();
			ps2.close();
			rs1.close();
			ps1.close();
			myConn.close();
			
		} catch (SQLException e) {
			System.out.println("Problema legata de accesul la baza de date");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Problema neclasificata");
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
