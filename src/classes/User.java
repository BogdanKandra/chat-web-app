package classes;

public class User {
	
	private int ID;
	private String nume;
	private String parola;
	private String email;
	private int status;
	
	public User(int IDul, String numele, String pass, String emailul, int statusul){
		
		ID = IDul;
		nume = numele;
		parola = pass;
		email = emailul;
		status = statusul;
	}
	
	public User(){
		this(0, "", "", "", 0);
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

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
}
