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

public class LeaveChat extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
       
    public LeaveChat() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String parola = request.getParameter("passw");
		HttpSession session = request.getSession();
		String parolaUtiliz = (session.getAttribute("parola") == null) ? "" : (String) session.getAttribute("parola");
		int idUtiliz = (session.getAttribute("id") == null) ? 0 : (int) session.getAttribute("id");
		
		int p_corecta = 0;
		int idChat = (session.getAttribute("idChat") == null) ? 0 : Integer.valueOf(session.getAttribute("idChat").toString());
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		if(parola.equals(parolaUtiliz)){ // User-ul a introdus parola corecta pentru confirmare
			
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				
				String del = "DELETE FROM participa " + 
							 "WHERE user_id = ? AND chat_id = ?";
				
				PreparedStatement ps = myConn.prepareStatement(del);
				ps.setInt(1, idUtiliz);
				ps.setInt(2, idChat);
				
				int rowNum = ps.executeUpdate();
				if(rowNum == 0){ // Chat-ul a fost creat de user-ul logat
					
					String upd = "UPDATE chat " + 
								 "SET user_id = -1 " + 
								 "WHERE id = ? AND user_id = ?";

					PreparedStatement ps2 = myConn.prepareStatement(upd);
					ps2.setInt(1, idChat);
					ps2.setInt(2, idUtiliz);
					
					ps2.executeUpdate();
					
					ps2.close();
					ps.close();
					myConn.close();
					
					response.sendRedirect("index.jsp");
				} else{ // User-ul a fost invitat in chat-ul parasit
					
					ps.close();
					myConn.close();
					
					response.sendRedirect("index.jsp");
				}
				
			} catch(SQLException e){
				System.out.println("Problema legata de accesul la baza de date");
				e.printStackTrace();
			} catch(Exception e){
				System.out.println("Problema neclasificata");
				e.printStackTrace();
			}
		} else{ // User-ul a introdus parola gresit
			p_corecta = -1;
			request.setAttribute("parolacorecta", p_corecta);
			request.getRequestDispatcher("/leaveChat.jsp?id=" + idChat).forward(request, response);
		}
	}
}
