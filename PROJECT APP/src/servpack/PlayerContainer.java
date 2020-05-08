package servpack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import res.Player;

public class PlayerContainer {

	ArrayList<Player> players;

	public PlayerContainer() {
		players = new ArrayList<Player>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor()
					.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		load();
	}
	
	private void load() {
		try {
			Connection connection = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/project?serverTimezone=Europe/Moscow&useSSL=false",
							"root", "");
			ResultSet done = connection.createStatement().executeQuery(
					"SELECT * FROM users");
			ArrayList<Player> temp = new ArrayList<Player>();
			while (done.next()) {
				Player tp = new Player();
				tp.setID(done.getInt(1));
				tp.setLogin(done.getString(2));
				tp.setPassword(done.getString(3));
				tp.setOp(done.getInt(4));
				System.out.println(tp);
				temp.add(tp);
			}
			players = temp;
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addPlayer(Player p) {
		try {
			Connection connection = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/project?serverTimezone=Europe/Moscow&useSSL=false",
							"root", "");
			ResultSet done = connection.createStatement().executeQuery(
					"SELECT * FROM users WHERE login=" + '"' + p.getLogin()
							+ '"');
			if (done.next())
				return false;
			else {
				if ((p.getLogin() != "" & p.getPassword() != "")
						& (p.getLogin() != null & p.getPassword() != null))
					connection.createStatement().execute(
							"INSERT INTO users (login, password) VALUES('"
									+ p.getLogin() + "','" + p.getPassword()
									+ "')");

			}
			connection.close();
			this.load();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void deletePlayer(int i) {
		try {
			Connection connection = DriverManager
					.getConnection(
							"jdbc:mysql://localhost/project?serverTimezone=Europe/Moscow&useSSL=false",
							"root", "");
			connection.createStatement().execute(
					"DELETE FROM users WHERE id=" + i);
			connection.close();
			this.load();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean check(String log, String pas) {
		boolean flag = false;
		for (Player p : players) {
			if (log.equals(p.getLogin()) & (pas.equals(p.getPassword())))
				flag = true;
		}
		return flag;
	}
	
	public Player getByLog(String login){
		for(Player p:players){
		if(login.equals(p.getLogin())) return p;
		}
		return null;
	}
	
	@Override
	public String toString() {
		String t = "";
		for (int i = 0; i < players.size(); i++)
			t += players.get(i).toString() + "\n";
		return t;
	}
}
