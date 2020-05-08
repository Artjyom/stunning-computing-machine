package gamepack;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

public class x_o extends Game {
	private int turn;
	private int[] tc;
	char[][] field;
	JButton[][] batones=new JButton[3][3];

	public x_o(boolean i_s) {
		isServer = i_s;
		name = "TicTacToe";
		turn = 1;
		field = new char[3][3];
		tc = new int[3]; // 2 - player
		active = true;
		player_count = 0;
		player_max = 2;
		// initializing
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				field[i][j] = '#';
		baked=field;
		tc[0] = -1;
		tc[1] = -1;
		controls = tc;

		if (!isServer) {
			drawer = new JComponent() {
				/**
			 * 
			 */
				private static final long serialVersionUID = 1L;

				@Override
				public void paintComponent(Graphics g) {
					setSize(600, 600);
					if(check_win()!='#'){
						g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
						if(check_win()=='x'){
							batones[0][0].setText("");
							batones[0][1].setText("X");
							batones[0][2].setText("");
							batones[1][0].setText("W");
							batones[1][1].setText("I");
							batones[1][2].setText("N");
							batones[2][0].setText("!");
							batones[2][1].setText("!");
							batones[2][2].setText("!");
						}
						else{
							batones[0][0].setText("");
							batones[0][1].setText("O");
							batones[0][2].setText("");
							batones[1][0].setText("W");
							batones[1][1].setText("I");
							batones[1][2].setText("N");
							batones[2][0].setText("!");
							batones[2][1].setText("!");
							batones[2][2].setText("!");
						}
						active=false;
					}
					
				}
			};
			ActionListener alis=new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String temp = e.getActionCommand();
					System.out.println(temp);
					tc[0]=((int)temp.charAt(0)-48);
					tc[1]=((int)temp.charAt(1)-48);
					tc[2]=current_player;
					controls=tc;
				}
				
			};
			
			//batones = new JButton[3][3];
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					batones[i][j] = new JButton("s");
					batones[i][j].setBounds(100*j, 100*i, 100,100);
					batones[i][j].setActionCommand(""+i+j);
					batones[i][j].addActionListener(alis);
					batones[i][j].setFont(new Font("Arial", Font.PLAIN, 50));
					batones[i][j].setEnabled(true);
					drawer.add(batones[i][j]);
				}
		}
	}

	@Override
	public void tick() {
		if (isServer&(check_win()=='#')) {
			tc = (int[]) controls;
			if ((tc[0] != -1) & (tc[1] != -1)) {
				//System.out.println(turn);
				if ((turn == 1) & (tc[2] == 1) & (field[tc[0]][tc[1]] == '#')) {
					field[tc[0]][tc[1]] = 'x';
					turn=2;
				}
				if ((turn == 2) & (tc[2] == 2) & (field[tc[0]][tc[1]] == '#')) {
					field[tc[0]][tc[1]] = 'o';
					turn=1;
				}
			}
			baked = field;
		} else {
			active=false;
		}
		//System.out.println(Arrays.toString((int[]) controls));
		//System.out.println(Arrays.deepToString((char[][])baked));
	}

	private boolean check_char(char a) {
		for (int i = 0; i < 3; i++) {
			if ((field[i][0] == a) & (field[i][1] == a) & (field[i][2] == a))
				return true;
		}
		for (int i = 0; i < 3; i++) {
			if ((field[0][i] == a) & (field[1][i] == a) & (field[2][i] == a))
				return true;
		}
		if ((field[0][0] == a) & (field[1][1] == a) & (field[2][2] == a))
			return true;
		if ((field[0][2] == a) & (field[1][1] == a) & (field[2][0] == a))
			return true;
		return false;
	}

	private char check_win() {
		if (check_char('o'))
			return 'o';
		else if (check_char('x'))
			return 'x';
		else
			return '#';
	}

	@Override
	public void updatePack(Object o) {
		baked = o;
		field = (char[][])o;
		drawer.repaint();
		char[][]tb=(char[][]) o;
		//System.out.println(Arrays.deepToString(tb));
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++){
				try{
				batones[i][j].setText(tb[i][j]+"");}
				catch(Exception e){e.printStackTrace();}
				//System.out.println(tb[i][j]+"");
			}
	}

	@Override
	public void setControls(Object o){
		int[]t_check=(int[])o;
		if(t_check[2]==turn)
			controls=o;
	}
	
	@Override
	public String toString() {
		String temp = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++)
				temp += field[i][j] + " ";
			temp += "\n";
		}
		return temp;
	}
}
