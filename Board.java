import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
public class Board extends JFrame{
	
	private JPanel panel;
	private JPanel panel2;
	private JButton[] button;
	private JLabel time;
	private JLabel flags;
	private JButton newGame;
	
	public Board() {
		
		setTitle("Minesweeper");
		setSize(600,400);
		//setLayout(new GridLayout(2,1));
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		panel.setVisible(true);
		
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(10,10));
		panel2.setVisible(true);
		
		time = new JLabel("Time",SwingConstants.CENTER);
		time.setBackground(Color.LIGHT_GRAY);
		time.setVisible(true);
		
		flags = new JLabel("Flags",SwingConstants.CENTER);
		flags.setBackground(Color.LIGHT_GRAY);
		flags.setVisible(true);
		
		newGame = new JButton("New Game");
		newGame.setVisible(true);
		
		button = new JButton[100];
			
		panel.add(flags);
		panel.add(newGame);
		panel.add(time);
		
		for(int i = 0; i<100; i++){
			button[i]= new JButton();
			//button[i].setText(Integer.toString(i));
			panel2.add(button[i]);
			button[i].setVisible(true);
		}
		
		add(panel,BorderLayout.NORTH);
		add(panel2);
		setVisible(true);
		validate();
	}

	public static void main(String[] args) {
		Board b = new Board();

	}

}
