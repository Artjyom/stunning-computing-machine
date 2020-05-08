package clientpack;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import res.Player;

public class ClienW extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Client client;
	private Player player;
	StartMenu sm;
	LoginMenu lm;
	
	public ClienW(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		player = null;
		client=new Client("127.0.0.1", 6000);
		Dimension dim=Toolkit.getDefaultToolkit ().getScreenSize();
		this.setBounds(dim.width/4,dim.height/4,dim.width/3,dim.height/3);
		
		sm = new StartMenu(dim.width/2,dim.height/2);
		sm.setVisible(false);
		add(sm);
		
		lm = new LoginMenu(dim.width/2,dim.height/2);
		add(lm);
		setSize(dim.width/2,dim.height/2);
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
