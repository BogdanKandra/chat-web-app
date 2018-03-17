package servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

public class Login extends HttpServlet{

	// Atunci cand se intra in login page, sa verific daca userul e deja logat din alt browser, ca sa nu se logeze de doua ori
	
	private static final long serialVersionUID = 1L;
       
    public Login(){
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		out.print("SE NUMESTE TEAPA, NAI MAI LOAT PANA ACUM TEAPA??? FORTZA STEAUA FORTZA STEAUA HEY HEY HEY!");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String nume = request.getParameter("user");
		String parola = request.getParameter("pass");
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		int u_gasit = 0;
		int p_corecta = 0;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			String sql1 = "SELECT * FROM utilizatori " +
						  "WHERE nume = ? AND parola = ?";
			String sql2 = "SELECT * FROM utilizatori " +
						  "WHERE nume = ?";
			
			PreparedStatement pst1 = myConn.prepareStatement(sql1);
			pst1.setString(1, nume);
			pst1.setString(2, parola);
			
			PreparedStatement pst2 = myConn.prepareStatement(sql2);
			pst2.setString(1, nume);
			
			ResultSet rs1 = pst1.executeQuery();
			ResultSet rs2 = pst2.executeQuery();
			
			if(!rs2.next()){ // Daca nu exista intrari, atunci user-ul introdus nu se afla in baza de date
				
				u_gasit = -1;
				request.setAttribute("usergasit", u_gasit);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				
				rs2.close();
				pst2.close();
				rs1.close();
				pst1.close();
				myConn.close();
			} else{
				
				if(rs1.next()){ // Daca exista intrari, atunci user-ul a introdus parola corect
					
					// Setez statusul user-ului ca fiind "online (2)"
					// In cazul in care nu era setat pe "invizibil (1)" inainte
					int id_user = rs1.getInt("id");
					
					String stat1 = "SELECT status FROM utilizatori " +
								   "WHERE id = ?";
					PreparedStatement ps1 = myConn.prepareStatement(stat1);
					ps1.setInt(1, id_user);
					ResultSet rsStat1 = ps1.executeQuery();
					
					rsStat1.next();
					int statusul = rsStat1.getInt("status");
					
					if(statusul == 0){ // Daca statusul era "Offline", il setez "Online"
						
						String stat2 = "UPDATE utilizatori " + 
									   "SET status = 2 " + 
									   "WHERE id = ?";
						PreparedStatement ps2 = myConn.prepareStatement(stat2);
						ps2.setInt(1, id_user);
						ps2.executeUpdate();
						
						ps2.close();
					} // Daca statusul era "Invizibil", il las asa
					
					rsStat1.close();
					ps1.close();
					rs2.close();
					pst2.close();
					rs1.close();
					pst1.close();
					myConn.close();
					
					PrintWriter out = response.getWriter();
					HttpSession session = request.getSession();
					session.setAttribute("username", nume);
					session.setAttribute("parola", parola);
					session.setAttribute("id", id_user);
					
					out.println("Bun venit, " + nume + "! Veti fi redirectionat catre pagina dumneavoastra...");
					
					// Dau un delay de 3 secunde pana la redirectionare

					response.setHeader("Refresh", "3;url=/ChatWebApp/userPage"); // Redirect delayed la servlet
//					response.sendRedirect("/ChatWebApp/userPage"); // Redirect normal la servlet
//					response.setHeader("Refresh", "3;url=/index.jsp"); // Redirect delayed la jsp
					out.close();
				} else{ // User-ul nu a introdus parola corect
					
					p_corecta = -1;
					request.setAttribute("parolacorecta", p_corecta);
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					
					rs2.close();
					pst2.close();
					rs1.close();
					pst1.close();
					myConn.close();
				}
			}
		} catch(SQLException e){
			System.out.println("Problema legata de accesul la baza de date");
			e.printStackTrace();
		} catch(Exception e){
			System.out.println("Problema neclasificata");
			e.printStackTrace();
		}
	}
}
