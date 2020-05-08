package servpack;

public class Main {
	static Server server;
	static PlayerContainer database;
	static GameBox gamebox;
	static ChatBox cbox;
	public static void main(String[] args) {
		database = new PlayerContainer();
		gamebox =new GameBox();
		cbox = new ChatBox();
		server = new Server();
	}

}
