package res;

import java.io.Serializable;

public class PackQ implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean stay;
	private String req;
	private Player player;
	private Object object;
	
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public PackQ(){
		stay=true;
		req="";
	}
	
	public PackQ(String s){
		req=s;
		stay=false;
	}
	
	public PackQ(String s, Player p){
		req=s;
		player=p;
	}

	public boolean isStay() {
		return stay;
	}

	public void setStay(boolean stay) {
		this.stay = stay;
	}

	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
