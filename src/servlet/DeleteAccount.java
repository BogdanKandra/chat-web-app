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

public class DeleteAccount extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
       
    public DeleteAccount() {
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
		String numeUtiliz = (session.getAttribute("username") == null) ? "" : (String) session.getAttribute("username");
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		if(parola.equals(parolaUtiliz)){ // User-ul a introdus parola corect pentru confirmare
			
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
				
				String del = "DELETE FROM utilizatori " + 
							 "WHERE nume = ?";
				
				PreparedStatement pst = myConn.prepareStatement(del);
				pst.setString(1, numeUtiliz);

				pst.execute();
				
				pst.close();
				myConn.close();
				
				session.invalidate();
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
			request.getRequestDispatcher("/deleteAccount.jsp").forward(request, response);
		}
	}

}
