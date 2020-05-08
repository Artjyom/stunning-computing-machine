package clientpack;

import java.awt.Container;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import res.Player;

public class LoginMenu extends Container {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField login;
	private JLabel ll;
	private JPasswordField password;
	private JLabel pl;
	private JPasswordField passcon;
	private JLabel cl;
	private JButton log;
	private JButton reg;
	private JButton sub;
	private JButton exit;

	public LoginMenu(int x, int y) {
		int delay = y / 20;
		this.setSize(x, y);
		int c1 = x / 3;
		int c2 = ((y - (delay * 6)) / 6);

		login = new JTextField("");
		login.setBounds(c1, delay, c1, c2);
		add(login);
		ll = new JLabel("LOGIN");
		ll.setBounds((int) (c1 / 1.5), delay, c1 / 2, c2);
		add(ll);

		password = new JPasswordField("");
		password.setBounds(c1, c2 + (2 * delay), c1, c2);
		add(password);
		pl = new JLabel("PASSWORD");
		pl.setBounds((int) (c1 / 1.5), c2 + (2 * delay), c1 / 2, c2);
		add(pl);

		passcon = new JPasswordField("");
		passcon.setBounds(c1, c2 * 2 + (3 * delay), c1, c2);
		add(passcon);
		cl = new JLabel("CONFIRM");
		cl.setBounds((int) (c1 / 1.5), c2 * 2 + (3 * delay), c1 / 2, c2);
		add(cl);

		log = new JButton("LOG-MODE");
		log.setBounds(c1, c2 * 3 + (4 * delay), (c1 - delay) / 2, c2);
		log.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.setVisible(false);
				passcon.setVisible(false);
				sub.setText("LOGIN");
			}
		});
		add(log);

		reg = new JButton("REG-MODE");
		reg.setBounds((int) (c1 * 1.5 + delay / 2), c2 * 3 + (4 * delay),
				(c1 - delay) / 2, c2);
		reg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.setVisible(true);
				passcon.setVisible(true);
				sub.setText("REGISTER");
			}
		});
		add(reg);

		sub = new JButton("REGISTER");
		sub.setBounds(c1, c2 * 4 + (5 * delay), c1, c2);
		sub.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player temp = null;
				String templog = login.getText();
				String temppas = new String(password.getPassword());
				if (sub.getText().equals("LOGIN")) {
					if (!templog.equals(""))
						if (!temppas.equals(""))
							temp = Main.window.client.login(templog, temppas);
					if (!temp.getLogin().equals("")) {
						clean();
						Main.window.setPlayer(temp);
						hidem();
						Main.window.sm.recognize(temp);
						Main.window.sm.showMe();
					}
				} else {
					if (!templog.equals(""))
						if (!temppas.equals(""))
							if (temppas.equals(new String(passcon.getPassword()))) {
								temp = Main.window.client.registr(new Player(templog, temppas));
								if (!temp.getLogin().equals("")) {
									Main.window.setPlayer(temp);
									clean();
									hidem();
									Main.window.sm.recognize(temp);
									Main.window.sm.setVisible(true);
								}
							}
				}
			}
		});
		add(sub);
		
		exit = new JButton("X");
		exit.setBounds((int)(22*x/25), 0, x/10, x/10);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.window.client.exit();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Main.window.dispose();
			}
		});
		add(exit);
	}

	void hidem() {
		this.setVisible(false);
	}
	private void clean(){
		login.setText("");
		password.setText("");
		passcon.setText("");
	}
}
