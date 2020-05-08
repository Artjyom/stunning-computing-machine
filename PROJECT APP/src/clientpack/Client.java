package clientpack;

import gamepack.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import res.*;

public class Client {
	Socket con;
	ObjectInputStream is;
	ObjectOutputStream os;
	Game current_game;
	int ingame;
	
	
	public Client(String address, int port){
			try {
				ingame=-1;
				con = new Socket(address, port);
				os = new ObjectOutputStream(con.getOutputStream());
				is=new ObjectInputStream(con.getInputStream());
				System.out.print("cl con");
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public Player login(String login, String password){
		try {
			PackQ temp = new PackQ("log");
			temp.setPlayer(new Player(login, password));
			os.writeObject(temp);
			temp = (PackQ)is.readObject();
			if(temp.getReq().equals("logged"))
				return temp.getPlayer();
			else
				return new Player("","");
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();
			return(new Player("",""));
		}
		
	}
	
	
	
	public Player registr(Player p){
		try {
			PackQ temp = new PackQ("reg");
			temp.setPlayer(p);
			os.writeObject(temp);
			temp = (PackQ)is.readObject();
			if(temp.getReq().equals("regged"))
				return temp.getPlayer();
			else
				return new Player("","");
		} catch (IOException|ClassNotFoundException e) {
			e.printStackTrace();
			return(new Player("",""));
		}
	}
	
	public void AddC(Player p){
		PackQ temq=new PackQ();
		temq.setObject(p);
		temq.setReq("addC");
		try {
			os.writeObject(temq);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String listC(){
		String t="";
		try {
			os.writeObject(new PackQ("listC"));
			t= (String)is.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Player> listP(){
		ArrayList<Player>temp=null;
		try{
			os.writeObject(new PackQ("list"));
			temp=(ArrayList<Player>)is.readObject();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return temp;
	}
	
	public int[] start_game(String g,Game ga){
		current_game=ga;
		Main.window.add(current_game.drawer);
		Thread GThread=new Thread(){
			@Override
			public void run(){
				while(ingame!=-1)
					try {
						if(!current_game.isActive()){
							ingame=-1;
							os.writeObject(new PackQ("exit_game"));
							break;
						}
						current_game.updatePack(is.readObject());
						//System.out.println(Arrays.deepToString((char[][])is.readObject()));
						sendG();
					} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
					}
			}
		};
		
		int[]got=new int[2];
		PackQ temp = new PackQ("start_game");
		temp.setObject(g);
		try {
			os.writeObject(temp);
			temp=(PackQ) is.readObject();
			got=(int[]) temp.getObject();
			ingame=got[0];
			current_game.setCurrent_player(got[1]);
			GThread.start();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return got;
	}
	
	public void sendG(){
		try {
			PackQ temp=new PackQ("u_game");
			int[] t_i=((int[])current_game.getControls());
			temp.setObject((int[])current_game.getControls());
			temp.setPlayer(new Player(""+t_i[0]+t_i[1]+t_i[2],"y"));
			//System.out.println(Arrays.toString((int[]) temp.getObject()));
			os.writeObject(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void kill(String s){
		try {
			PackQ pq=new PackQ("kill");
			pq.setObject(s);
			os.writeObject(pq);
			//os.close();
			//is.close();
			//con.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exit(){
		try {
			os.writeObject(new PackQ("quit"));
			os.close();
			is.close();
			con.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
