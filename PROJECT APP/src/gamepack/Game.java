package gamepack;

import javax.swing.JComponent;

public abstract class Game {
	public boolean isServer;
	protected String name;
	protected Object controls;
	protected Object baked;
	protected boolean active;
	protected int player_count=0;
	public JComponent drawer;
	protected int player_max;
	protected int current_player;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getControls() {
		return controls;
	}
	public void setControls(Object controls) {
		this.controls = controls;
	}
	public Object getBaked() {
		return baked;
	}
	public void setBaked(Object baked) {
		this.baked = baked;
	}
	public synchronized boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void tick(){
		
	}
	public int getPlayer_count() {
		return player_count;
	}
	public void setPlayer_count(int player_count) {
		this.player_count = player_count;
	}
	
	public void updatePack(Object o){
		
	}
	public boolean isFull(){
		if(player_count==player_max)
			return true;
		else
			return false;
	}
	public int addP(){
		if(player_count<=player_max){
			player_count++;
			return player_count;
		}
		else
			return -1;
	}
	public int getCurrent_player() {
		return current_player;
	}
	public void setCurrent_player(int current_player) {
		this.current_player = current_player;
	}
}
