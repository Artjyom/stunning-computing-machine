package clientpack;

import gamepack.x_o;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import res.Player;

public class StartMenu extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel plab;
	private JButton cabbut;
	private JButton back;
	private JButton adBut;
	private JButton adBut1;
	
	private JTextArea uniT;
	private JTextField pole;
	private JButton polBut;

	private JButton playT;
	int x, y;

	public StartMenu(int x, int y) {
		this.x = x;
		this.y = y;
		setSize(x, y);
		plab = new JLabel("PLAYER");
		plab.setBounds(2 * x / 3 + (x / 85), 0, x / 6, y / 10);
		add(plab);

		cabbut = new JButton("CABINET");
		cabbut.setBounds(16 * x / 20, y / 40, x / 6, y / 20);
		add(cabbut);

		back = new JButton("EXIT");
		back.setBounds(x / 40, y / 40, x / 6, y / 20);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				uniT.setText("");
				Main.window.sm.setVisible(false);
				Main.window.setPlayer(null);
				Main.window.lm.setVisible(true);
			}
		});
		add(back);

		adBut = new JButton("ListUsers");
		adBut.setBounds(20 * x / 40, y / 40, x / 6, y / 20);
		adBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Player> ta = Main.window.client.listP();
				String temp = "";
				for (Player pp : ta)
					temp += pp.toString() + "\n";
				uniT.setText(temp);
			}
		});
		add(adBut);
		
		adBut1 = new JButton("Kill Server");
		adBut1.setBounds((int)(13.5 * x) / 40, y / 40, x / 6, y / 20);
		adBut1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.window.client.kill(Main.window.getPlayer().getLogin());
			}
		});
		add(adBut1);

		playT = new JButton("Play X/O");
		playT.setBounds(x / 40, 33 * y / 40, x / 6, y / 18);
		playT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.window.client.start_game("xo", new x_o(false));
				setVisible(false);
				Thread waiter = new Thread() {
					@Override
					public void run() {
						while (Main.window.client.current_game.isActive())
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						Main.window.client.current_game.drawer.setVisible(false);
						setVisible(true);
					}
				};
				waiter.start();
			}
		});
		add(playT);

		uniT = new JTextArea();
		uniT.setBounds(x / 5, y / 5, x / 4, x / 4);
		add(uniT);

		pole = new JTextField();
		pole.setBounds(x / 5, 4 * y / 6, x / 4, x / 20);
		add(pole);

		polBut = new JButton("Send");
		polBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Player tempp = new Player(Main.window.getPlayer().getLogin(),
						pole.getText());
				Main.window.client.AddC(tempp);
				uniT.setText(Main.window.client.listC());
			}
		});
		polBut.setBounds(x / 5, 4 * y / 6 + (x / 19), x / 4, x / 20);
		add(polBut);
	}

	public void showMe() {
		if (Main.window.getPlayer().getOp() == 0){
			adBut.setVisible(false);
			adBut1.setVisible(false);
		}
		else{
			adBut.setVisible(true);
			adBut1.setVisible(true);
		}
		this.setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D turtle = (Graphics2D) g;
		turtle.setStroke(new BasicStroke(1));
		turtle.setColor(Color.GRAY);
		turtle.fillRect(0, 0, x, y / 10);
		turtle.setColor(Color.WHITE);
		turtle.fillRect(2 * x / 3, y / 60, (int) (x / 3.3), 4 * y / 60);
		turtle.setColor(Color.BLACK);
		turtle.setStroke(new BasicStroke(2));
		turtle.drawRect(2 * x / 3, y / 60, (int) (x / 3.3), 4 * y / 60);
	}

	public void recognize(Player p) {
		plab.setText(p.getLogin());
	}
}
