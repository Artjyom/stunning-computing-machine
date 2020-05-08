package res;

import java.io.Serializable;

public class Player  implements Serializable{
	/**
	 * 
	 */
	private String login;
	private int ID;
	private String password;
	private int op;
	
	private static final long serialVersionUID = 1L;
	public Player() {
	}

	public Player(String login, String password) {
		this.login = login;
		this.password=password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString(){
		return ID+" "+login+" "+password+" "+op;
	}

	public int getOp() {
		return op;
	}

	public void setOp(int op) {
		this.op = op;
	}
}
