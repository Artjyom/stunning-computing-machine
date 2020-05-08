package servpack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static ServerSocket serv;
	final int PORT=6000;
	public Server(){
		try {
			serv = new ServerSocket(PORT);
			while(!serv.isClosed()){
				Socket temp = serv.accept();
				new ClientHandler (temp).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void exit(){
		try {
			serv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
