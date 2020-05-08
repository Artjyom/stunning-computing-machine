package servpack;

import gamepack.Game;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameBox {
	private CopyOnWriteArrayList<Game> games;
	private Thread Core;
	
	public GameBox(){
		games=new CopyOnWriteArrayList<Game>();
		Core=new Thread(){
			@Override
			public void run(){
				while(true){
					if(games.size()>0)
						for(Game g:games)
							if(g.isActive())
								try{
									g.tick();
									}catch(Exception e){e.printStackTrace();}
							else if((!g.isActive())&(g.isFull()))
								g.setActive(true);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Core.start();
	}
	
	public synchronized int[] addGame(Game g){
		int[] t=new int[2];
		if(checkG(g)!=-1){
			t[0]=checkG(g);
			t[1]=games.get(checkG(g)).addP();
		}
		else{
			games.add(g);
			games.get(games.size()-1).addP();
			t[0]=(games.size()-1);
			t[1]=1;
		}
		System.out.println(Arrays.toString(t));//
		return t;
	}
	
	public synchronized Object getG(int i){
		return games.get(i).getBaked();
	}
	
	public synchronized void updG(int i, Object o){
		games.get(i).setControls(o);
	}
	private synchronized int checkG(Game g){
		int check=-1;
		for(int i=0; i<games.size(); i++){
			 Game ch=games.get(i);
			if((ch.getClass().equals(g.getClass()))&(!ch.isFull()))
				check=i;
		}
		return check;
	}
}
