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

public class Register extends HttpServlet{
	
	// sa creez un DAO (Data Access Object) care sa administreze interactiunile cu BD
	    // si sa import acea clasa in servlet
	
	private static final long serialVersionUID = 1L;
       
    public Register(){
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		out.print("SE NUMESTE TEAPA, NAI MAI LOAT PANA ACUM TEAPA??? FORTZA STEAUA FORTZA STEAUA HEY HEY HEY!");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String nume = request.getParameter("user");
		String parola = request.getParameter("pass");
		String parola2 = request.getParameter("pass2");
		String email = request.getParameter("email");
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		int u_gasit = 0; // Variabila folosita pentru a verifica daca userul introdus exista deja
		int e_gasit = 0; // Variabila folosita pentru a verifica daca emailul introdus exista deja
		int p_coincid = 1; // Variabila folosita pentru a verifica daca parolele introduse coincid
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			String ins = "INSERT INTO utilizatori (nume, parola, email) " +
						 "VALUES (?,?,?)";
			String sel1 = "SELECT * FROM utilizatori " +
						  "WHERE nume = ?";
			String sel2 = "SELECT * FROM utilizatori " +
						  "WHERE email = ?";
			
			PreparedStatement updt = myConn.prepareStatement(ins);
			updt.setString(1, nume);
			updt.setString(2, parola);
			updt.setString(3, email);
			
			PreparedStatement pst1 = myConn.prepareStatement(sel1);
			pst1.setString(1, nume);
			
			PreparedStatement pst2 = myConn.prepareStatement(sel2);
			pst2.setString(1, email);
			
			ResultSet rs1 = pst1.executeQuery();
			if(rs1.next()){ // Daca exista intrari, atunci userul introdus se afla deja in BD
				u_gasit = 1;
			}
			
			ResultSet rs2 = pst2.executeQuery();
			if(rs2.next()){ // Daca exista intrari, atunci email-ul introdus se afla deja in BD
				e_gasit = 1;
			}
			
			if(u_gasit == 0 && e_gasit == 0){ // Adica user-ul si email-ul nu se afla deja in BD
				if(parola.equals(parola2)){ // Daca parolele introduse coincid
					
					updt.executeUpdate();
					
					// Introducem in tabela participa intrari pentru user-ul nou creat
					String sel3 = "SELECT id FROM utilizatori " + 
								  "WHERE nume = ?";
					String sel4 = "SELECT id FROM chat " + 
								  "WHERE defaultMode = 1";
					String sel5 = "INSERT INTO participa " +
							      "VALUES (?,?)";
					
					PreparedStatement sell3 = myConn.prepareStatement(sel3);
					sell3.setString(1, nume);
					
					ResultSet rs3 = sell3.executeQuery();
					rs3.next();
					
					int IDul = rs3.getInt("id");
					
					PreparedStatement sell4 = myConn.prepareStatement(sel4);
					ResultSet rs4 = sell4.executeQuery();
					
					while(rs4.next()){
						
						int IDchat = rs4.getInt("id");
						
						PreparedStatement sell5 = myConn.prepareStatement(sel5);
						sell5.setInt(1, IDul);
						sell5.setInt(2, IDchat);
						
						sell5.executeUpdate();
						
						sell5.close();
					}
					
					rs4.close();
					sell4.close();
					rs3.close();
					sell3.close();
					rs1.close();
					pst1.close();
					rs2.close();
					pst2.close();
					updt.close();
					myConn.close();
					
					PrintWriter out = response.getWriter();
					out.println("Utilizator inregistrat cu succes! Veti fi redirectionat catre pagina principala...");
	
					//response.sendRedirect("index.jsp");
					// Dau un delay de 3 secunde pana la redirectionare
					response.setHeader("Refresh", "3;url=index.jsp");
					out.close();
				} else{ // Parolele introduse nu coincid
					
					p_coincid = 0;
					request.setAttribute("badPass", p_coincid);
					request.getRequestDispatcher("/register.jsp").forward(request, response);
					
					rs1.close();
					pst1.close();
					rs2.close();
					pst2.close();
					updt.close();
					myConn.close();
				}
			} else{ // Cel putin o valoare se gaseste deja in BD
				
				request.setAttribute("emailgasit", e_gasit);
				request.setAttribute("usergasit", u_gasit);
				request.getRequestDispatcher("/register.jsp").forward(request, response);

				rs1.close();
				pst1.close();
				rs2.close();
				pst2.close();
				updt.close();
				myConn.close();
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
