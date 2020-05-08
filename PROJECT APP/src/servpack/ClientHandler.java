package servpack;

import java.io.*;
import java.net.*;
import gamepack.x_o;
import res.*;

public class ClientHandler extends Thread {
	Socket connection;
	ObjectInputStream is;
	ObjectOutputStream os;
	int ingame;
	int ign;

	public ClientHandler(Socket s) {
		connection = s;
		System.out.println("sr con");
		ingame=0;
	}

	@Override
	public void run() {
		try {
			os = new ObjectOutputStream(connection.getOutputStream());
			System.out.println("sr step");
			is = new ObjectInputStream(connection.getInputStream());
			System.out.println(connection.getInetAddress());
			PackQ temp;
			while ((temp = (PackQ) is.readObject()) != null) {
				// login
				if (temp.getReq().equals("log")) {
					System.out.println(temp.getPlayer());
					boolean checker = Main.database.check(temp.getPlayer()
							.getLogin(), temp.getPlayer().getPassword());
					if (checker) {
						Player tempp = Main.database.getByLog(temp.getPlayer()
								.getLogin());
						os.writeObject(new PackQ("logged", tempp));
					} else
						os.writeObject(new PackQ("error"));
				}

				if (temp.getReq().equals("reg")) {
					if (!Main.database.check(temp.getPlayer().getLogin(), temp
							.getPlayer().getPassword())) {
						Main.database.addPlayer(temp.getPlayer());
						os.writeObject(new PackQ("regged", Main.database
								.getByLog(temp.getPlayer().getLogin())));
					}
				}
				
				if (temp.getReq().equals("addC")) {
					Player tmp=(Player)temp.getObject();
					Main.cbox.add(tmp.getPassword(), tmp.getLogin());
				}
				
				if (temp.getReq().equals("listC")) {
					os.writeObject(Main.cbox.get());
				}
				
				if (temp.getReq().equals("list")) {
					os.writeObject(Main.database.players);
				}
				
				if (temp.getReq().equals("start_game")) {
					int[]tmp=new int[2];
					if(((String)temp.getObject()).equals("xo"))
						tmp=Main.gamebox.addGame(new x_o(true));
					ingame=tmp[0];
					ign=tmp[1];
					PackQ tmpQ= new PackQ();
					tmpQ.setReq("G_rdy");
					tmpQ.setObject(tmp);
					os.writeObject(tmpQ);
					Thread GThread=new Thread(){
						@Override
						public void run(){
							while(ingame!=-1)
								try {
									os.writeUnshared(Main.gamebox.getG(ingame));
									os.reset();
									Thread.sleep(50);
								} catch (IOException | InterruptedException e) {
									e.printStackTrace();
								}
						}
					};
					GThread.start();
				}
				
				if(temp.getReq().equals("u_game")) {
					if(ingame!=-1){
						String ts=temp.getPlayer().getLogin();
						int[] ti=new int[3];
						ti[0]=ts.charAt(0)-48;
						ti[1]=ts.charAt(1)-48;
						ti[2]=ts.charAt(2)-48;
						if(!ts.contains("-"))
							Main.gamebox.updG(ingame, ti);
						else
							Main.gamebox.updG(ingame, temp.getObject());
						//System.out.println(Arrays.toString((ti)));
						//System.out.println(temp.getPlayer());
					}
				}
				
				if(temp.getReq().equals("exit_game")) {
					ingame=-1;
				}
				
				if(temp.getReq().equals("quit")){
					is.close();
					os.close();
					connection.close();
					break;
				}
				
				if(temp.getReq().equals("kill")){
					String log=(String)temp.getObject();
					Player p=Main.database.getByLog(log);
					if(p.getOp()>0){
						System.out.println("ALERT");	
						Server.exit();
						is.close();
						os.close();
						connection.close();
						break;
					}
				}
				
				System.out.print(temp.getReq());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
