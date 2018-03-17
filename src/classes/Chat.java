package classes;

import java.sql.Date;

public class Chat {

	private int ID;
	private String nume;
	private int user_ID; // ID-ul celui care a creat chat-ul
	private String user_name; // Username-ul celui care a creat chat-ul
	private Date created_at;
	private byte defaultMode;

	public Chat(int IDul, String numele, int userIDul, String userNameul, Date data, byte modul) {

		ID = IDul;
		nume = numele;
		user_ID = userIDul;
		user_name = userNameul;
		created_at = data;
		defaultMode = modul;
	}

	public Chat() {
		this(0, "", 0, "", new Date(System.currentTimeMillis()), (byte) 1);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public int getUser_ID() {
		return user_ID;
	}

	public void setUser_ID(int user_ID) {
		this.user_ID = user_ID;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public byte getDefaultMode() {
		return defaultMode;
	}

	public void setDefaultMode(byte defaultMode) {
		this.defaultMode = defaultMode;
	}
}
