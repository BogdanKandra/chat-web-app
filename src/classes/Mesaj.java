package classes;

import java.sql.Timestamp;

public class Mesaj{
	
	private int ID;
	private int user_id;
	private String username;
	private int chat_id;
	private Timestamp data;
	private String continut;
	
	public Mesaj(int IDul, int userIDul, String user, int chatIDul, Timestamp dataa, String continutul){
		
		ID = IDul;
		user_id = userIDul;
		username = user;
		chat_id = chatIDul;
		data = dataa;
		continut = continutul;
	}
	
	public Mesaj(){
		this(0,0,"",0,new Timestamp(System.currentTimeMillis()),"");
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}

	public int getChat_id() {
		return chat_id;
	}

	public void setChat_id(int chat_id) {
		this.chat_id = chat_id;
	}

	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public String getContinut() {
		return continut;
	}

	public void setContinut(String continut) {
		this.continut = continut;
	}
}
