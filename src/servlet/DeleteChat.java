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

public class DeleteChat extends HttpServlet{

	private static final long serialVersionUID = 1L;
       
    public DeleteChat(){
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String parola = request.getParameter("passw");
		HttpSession session = request.getSession();
		String parolaUtiliz = (session.getAttribute("parola") == null) ? "" : (String) session.getAttribute("parola");
		int p_corecta = 0;
		int idChat = (session.getAttribute("idChat") == null) ? 0 : Integer.valueOf(session.getAttribute("idChat").toString());
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		if(parola.equals(parolaUtiliz)){ // User-ul a introdus parola corecta pentru confirmare
			
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				
				String del = "DELETE FROM chat " + 
							 "WHERE id = ?";
				String del2 = "DELETE FROM participa " + 
							  "WHERE chat_id = ?";

				PreparedStatement pst = myConn.prepareStatement(del);
				pst.setInt(1, idChat);
				pst.executeUpdate();
				
				PreparedStatement pst2 = myConn.prepareStatement(del2);
				pst2.setInt(1, idChat);
				pst2.executeUpdate();
				
				pst2.close();
				pst.close();
				myConn.close();
				
				response.sendRedirect("index.jsp");
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
			request.getRequestDispatcher("/deleteChat.jsp?id=" + idChat).forward(request, response);
		}
	}
}
