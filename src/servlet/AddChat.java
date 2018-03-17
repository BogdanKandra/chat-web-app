package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddChat extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
       
    public AddChat(){
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		PrintWriter out = response.getWriter();
		out.print("SE NUMESTE TEAPA, NAI MAI LOAT PANA ACUM TEAPA??? FORTZA STEAUA FORTZA STEAUA HEY HEY HEY!");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		
		String numeChat = request.getParameter("chatname");
		String numeUser = session.getAttribute("username").toString();
		int idUser = (int) session.getAttribute("id");
		
		Date data = new Date(System.currentTimeMillis());
		byte defaultMode = 0;
		if(numeUser.equals("admin")){
			defaultMode = 1;
		}
		
		final String DB_URL = "jdbc:mysql://localhost:3306/chat_db?verifyServerCertificate=false&useSSL=true";
		final String DB_USER = "bughyman1000";
		final String DB_PASS = "lightning_ff13";
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection myConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			
			String ins = "INSERT INTO chat (nume, user_id, created_at, defaultMode) " + 
						 "VALUES (?,?,?,?)";
			
			PreparedStatement updt = myConn.prepareStatement(ins);
			updt.setString(1, numeChat);
			updt.setInt(2, idUser);
			updt.setDate(3, data);
			updt.setByte(4, defaultMode);
			
			updt.executeUpdate();
			
			updt.close();
			myConn.close();
			
			PrintWriter out = response.getWriter();
			out.println("Chatroom creat cu succes! Veti fi redirectionat catre pagina dumneavoastra...");

//			response.setHeader("Refresh", "3;url=userpage.jsp"); // Redirect delayed la jsp
			response.setHeader("Refresh", "3;url=/ChatWebApp/userPage"); // Redirect delayed la servlet
			out.close();
		} catch(SQLException e){
			System.out.println("Problema legata de accesul la baza de date");
			e.printStackTrace();
		} catch(Exception e){
			System.out.println("Problema neclasificata");
			e.printStackTrace();
		}
	}

}
