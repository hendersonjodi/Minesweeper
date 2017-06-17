import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * @author Jodi Henderson
 *
 */

public class Minesweeper extends JFrame implements ActionListener, MouseListener{

	private static int numMines,row,column,numFlags; 
	private static final int MINE = 10;

	int[][] mines;

	private JPanel panel;
	private JButton[][] button;
	private JLabel time,flags;
	private JButton newGame;
	private Container grid;	

	public Minesweeper() {

		setTitle("Minesweeper");
		setSize(600,400);

		numMines = Integer.parseInt(JOptionPane.showInputDialog("How many mines would you like? "));
		row = Integer.parseInt(JOptionPane.showInputDialog("How many rows would you like? "));
		column = Integer.parseInt(JOptionPane.showInputDialog("How many columns would you like? "));
		

		while(numMines>(row*column)){
			JOptionPane.showMessageDialog(this, "More mines than buttons please enter again");
			numMines = Integer.parseInt(JOptionPane.showInputDialog("How many mines would you like? "));
			row = Integer.parseInt(JOptionPane.showInputDialog("How many rows would you like? "));
			column = Integer.parseInt(JOptionPane.showInputDialog("How many columns would you like? "));	
		}
		numFlags = numMines;
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		panel.setVisible(true);

		grid = new Container();
		grid.setLayout(new GridLayout(row,column));
		grid.setVisible(true);

		time = new JLabel("Time",SwingConstants.CENTER);
		time.setBackground(Color.LIGHT_GRAY);
		time.setVisible(true);

		flags = new JLabel(numFlags+"",SwingConstants.CENTER);
		flags.setBackground(Color.LIGHT_GRAY);
		flags.setVisible(true);

		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		newGame.setVisible(true);

		button = new JButton[row][column];
		mines = new int[row][column];

		panel.add(flags);
		panel.add(newGame);
		panel.add(time);

		for(int i = 0; i<row; i++)
			for(int j = 0; j<column; j++){
				button[i][j]= new JButton();
				button[i][j].addActionListener(this);
				button[i][j].addMouseListener(this);
				grid.add(button[i][j]);
				button[i][j].setVisible(true);
			}		

		add(panel,BorderLayout.NORTH);
		add(grid);
		createMines();

		setVisible(true);
		validate();
	}
	/**
	 * Left clicking new game or any tile on grid
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(newGame)){
			for(int x = 0; x<row;x++)
				for(int y =0 ; y<column;y++){
					button[x][y].setEnabled(true);
					button[x][y].setText("");
				}
			numFlags = numMines;
			flags.setText(numFlags+"");
			createMines();
		}else{
			for(int x = 0; x<row;x++)
				for(int y =0 ; y<column;y++)

					if (e.getSource().equals(button[x][y]))
						if (mines[x][y]== MINE){
							button[x][y].setText("X");
							showMines();
						}else if (mines[x][y] == 0){
							ArrayList<Integer> needsCleared = new ArrayList<Integer>();
							if(mines[x][y]!=0)//&& !button[x][y].getText().equals("*"))
								button[x][y].setText(mines[x][y]+"");

							button[x][y].setEnabled(false);
							needsCleared.add((x*100)+y);
							clearZeros(needsCleared);
							checkWin();

						}else{
							//if(!button[x][y].getText().equals("*"))
								button[x][y].setText(mines[x][y]+"");
							button[x][y].setEnabled(false);
							checkWin();
						}
		}
		allZeros();

	}
	/**
	 * Right click for flags
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e))
			for(int x = 0; x<row;x++)
				for(int y =0 ; y<column;y++)
					if(e.getSource().equals(button[x][y])){
						button[x][y].setText("*");
						numFlags--;
						if(numFlags <= -1)
							JOptionPane.showMessageDialog(this, "More Flags than mines");
						flags.setText(numFlags+"");
					}
	}     


	/**
	 * Checks surrounding buttons and if they are also a zero it clears them
	 * @param needsCleared
	 */
	private void clearZeros(ArrayList<Integer> needsCleared){
		if(needsCleared.size() == 0)
			return;
		else{
			int x = needsCleared.get(0)/100;
			int y = needsCleared.get(0)%100;
			needsCleared.remove(0);

			//UP
			if(y>0 && button[x][y-1].isEnabled()){
				button[x][y-1].setText(mines[x][y-1]+"");
				button[x][y-1].setEnabled(false);
				if(mines[x][y-1]==0)
					needsCleared.add((x*100)+(y-1));
			}

			//DOWN
			if(y<(row-1) && button[x][y+1].isEnabled()){
				button[x][y+1].setText(mines[x][y+1]+"");
				button[x][y+1].setEnabled(false);
				if(mines[x][y+1]==0)
					needsCleared.add((x*100)+(y+1));

			}	

			//UP RIGHT
			if(x<(column-1) && y > 0 && button[x+1][y-1].isEnabled()){
				button[x+1][y-1].setText(mines[x+1][y-1]+"");
				button[x+1][y-1].setEnabled(false);
				if(mines[x+1][y-1]==0)
					needsCleared.add((x+1)*100+(y-1));
			}

			//RIGHT
			if(x<(column-1)&& button[x+1][y].isEnabled()){
				button[x+1][y].setText(mines[x+1][y]+"");
				button[x+1][y].setEnabled(false);
				if(mines[x+1][y]==0)
					needsCleared.add(((x+1)*100)+y);

			}

			//DOWN RIGHT
			if(x<(column-1) && y<(row-1) && button[x+1][y+1].isEnabled()){
				button[x+1][y+1].setText(mines[x+1][y+1]+"");
				button[x+1][y+1].setEnabled(false);
				if(mines[x+1][y+1]==0)
					needsCleared.add((x+1)*100+(y+1));

			}

			//UP LEFT
			if(x>0 && y>0 && button[x-1][y-1].isEnabled()){
				button[x-1][y-1].setText(mines[x-1][y-1]+"");
				button[x-1][y-1].setEnabled(false);
				if(mines[x-1][y-1]==0)
					needsCleared.add((x-1)*100+(y-1));

			}

			//LEFT
			if(x>0 && button[x-1][y].isEnabled()){
				button[x-1][y].setText(mines[x-1][y]+"");
				button[x-1][y].setEnabled(false);
				if(mines[x-1][y]==0)
					needsCleared.add(((x-1)*100)+y);

			}

			//DOWN LEFT
			if(x>0 && y<(row-1) && button[x-1][y+1].isEnabled()){
				button[x-1][y+1].setText(mines[x-1][y+1]+"");
				button[x-1][y+1].setEnabled(false);
				if(mines[x-1][y+1]==0)
					needsCleared.add((x-1)*100+(y+1));

			}
		}
		clearZeros(needsCleared);
	}
	/**
	 * Shows mine or number of neighbour mines
	 */
	private void showMines(){
		for(int x = 0; x<row; x++)
			for(int y =0 ; y<column; y++)
				if(button[x][y].isEnabled())
					if (mines[x][y]!=MINE){
						button[x][y].setText(mines[x][y]+"");
						button[x][y].setEnabled(false);
					}else{
						button[x][y].setText("X");
						button[x][y].setEnabled(false);
					}	
		allZeros();
		JOptionPane.showMessageDialog(this, "You Lose. Better Luck Next Time");
	}


	/**
	 * Randomly creates and positions mines in the grid
	 */
	private void createMines(){
		//Initialise list of random pairs
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int x = 0; x<row; x++)
			for(int y =0 ; y<column; y++)
				// 1223 12 is x 23 is y
				list.add(x*100+y);


		//Reset mine array and add MINES new mines in random positions
		mines = new int [row][column];
		for (int i = 0; i<numMines; i++){
			int position = (int)(Math.random()*list.size());
			mines[list.get(position) / 100][list.get(position) % 100]=MINE;
			list.remove(position);
		}


		for(int x = 0; x<row; x++)
			for(int y =0; y<column; y++)
				if(mines[x][y]!=MINE){
					int neighbourMines = 0;
					//UP
					if(y > 0 && mines[x][y-1] == MINE)
						neighbourMines++;
					//DOWN
					if(y<(row-1) && mines[x][y+1] == MINE)
						neighbourMines++;
					//UP RIGHT
					if(x<(column-1) && y > 0 && mines [x+1][y-1] == MINE)
						neighbourMines++;
					//RIGHT
					if(x<(column-1) && mines [x+1][y] == MINE)
						neighbourMines++;
					//DOWN RIGHT
					if(x<(column-1) && y<(row-1) && mines[x+1][y+1] == MINE)
						neighbourMines++;
					//UPPER LEFT
					if(x > 0 && y >0 && mines[x-1][y-1] == MINE)
						neighbourMines++;
					//LEFT
					if(x>0 && mines[x-1][y] == MINE)
						neighbourMines++;
					//DOWN LEFT
					if(x>0 && y<(row-1) && mines[x-1][y+1] == MINE)
						neighbourMines++;
					mines[x][y] = neighbourMines;
				}
	}

	private void checkWin(){
		boolean win = true;
		for(int x = 0; x<row; x++)
			for(int y =0 ; y<column; y++)
				if(mines[x][y] != MINE && button[x][y].isEnabled())
					win = false;
		if(win){
			allZeros();
			JOptionPane.showMessageDialog(this, "Congratulations! You've Won!");
		}
	}

	private void allZeros(){
		for(int x = 0; x<row; x++)
			for(int y =0 ; y<column; y++)
				if(mines[x][y] == 0 && !button[x][y].getText().equals("*"))
					button[x][y].setText("");

	}
	/*
	private void pause(long millisecs){  
		long current = Calendar.getInstance().getTimeInMillis();
	    while(Calendar.getInstance().getTimeInMillis()-current<millisecs);
	}

	//TODO runs forever
	private void timer(){
		int seconds = 0;
		while(true){  	
			time.setText(seconds+"");
			pause(1000);
			seconds++;
		}
	}
	 */


	public static void main(String[] args) {
		new Minesweeper();
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
