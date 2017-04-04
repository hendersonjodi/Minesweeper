import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Board extends JFrame implements ActionListener{

	private static final int MINES = 10;
	private static final int ROW = 10;
	private static final int COLUMN = 10;
	private static final int MINE = 10;

	int[][] mines;

	private JPanel panel;
	private JButton[][] button;
	private JLabel time;
	private JLabel flags;
	private JButton newGame;
	private Container grid;

	public Board() {

		setTitle("Minesweeper");
		setSize(600,400);

		panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		panel.setVisible(true);

		grid = new Container();
		grid.setLayout(new GridLayout(ROW,COLUMN));
		grid.setVisible(true);

		time = new JLabel("Time",SwingConstants.CENTER);
		time.setBackground(Color.LIGHT_GRAY);
		time.setVisible(true);

		flags = new JLabel("Flags",SwingConstants.CENTER);
		flags.setBackground(Color.LIGHT_GRAY);
		flags.setVisible(true);

		newGame = new JButton("New Game");
		newGame.addActionListener(this);
		newGame.setVisible(true);

		button = new JButton[ROW][COLUMN];
		mines = new int[ROW][COLUMN];

		panel.add(flags);
		panel.add(newGame);
		panel.add(time);

		for(int i = 0; i<ROW; i++)
			for(int j = 0; j<COLUMN; j++){
				button[i][j]= new JButton();
				button[i][j].addActionListener(this);
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
	 * What happens when a button is clicked
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(newGame)){
			for(int x = 0; x<ROW;x++)
				for(int y =0 ; y<COLUMN;y++){
					button[x][y].setEnabled(true);
					button[x][y].setText("");
				}
			createMines();

		}else{
			for(int x = 0; x<ROW;x++)
				for(int y =0 ; y<COLUMN;y++)
					if (e.getSource().equals(button[x][y]))
						if (mines[x][y]== MINE){
							button[x][y].setText("X");
							showMines();
						}else if (mines[x][y] == 0){
							ArrayList<Integer> needsCleared = new ArrayList<Integer>();
							button[x][y].setText(mines[x][y]+"");
							button[x][y].setEnabled(false);
							needsCleared.add((x*100)+y);
							clearZeros(needsCleared);

						}else{
							button[x][y].setText(mines[x][y]+"");
							button[x][y].setEnabled(false);
						}
		}
	}
	/**
	 * Checks surrounding buttons and if they are also a zero it clears them
	 * @param needsCleared
	 */
	public void clearZeros(ArrayList<Integer> needsCleared){
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
			if(y<(ROW-1) && button[x][y+1].isEnabled()){
				button[x][y+1].setText(mines[x][y+1]+"");
				button[x][y+1].setEnabled(false);
				if(mines[x][y+1]==0)
					needsCleared.add((x*100)+(y+1));

			}	

			//UP RIGHT
			if(x<(COLUMN-1) && y > 0 && button[x+1][y-1].isEnabled()){
				button[x+1][y-1].setText(mines[x+1][y-1]+"");
				button[x+1][y-1].setEnabled(false);
				if(mines[x+1][y-1]==0)
					needsCleared.add((x+1)*100+(y-1));
			}

			//RIGHT
			if(x<(COLUMN-1)&& button[x+1][y].isEnabled()){
				button[x+1][y].setText(mines[x+1][y]+"");
				button[x+1][y].setEnabled(false);
				if(mines[x+1][y]==0)
					needsCleared.add(((x+1)*100)+y);

			}

			//DOWN RIGHT
			if(x<(COLUMN-1) && y<(ROW-1) && button[x+1][y+1].isEnabled()){
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
			if(x>0 && y<(ROW-1) && button[x-1][y+1].isEnabled()){
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
	private void showMines() {

		for(int x = 0; x<ROW;x++)
			for(int y =0 ; y<COLUMN;y++)
				if(button[x][y].isEnabled())
					if (mines[x][y]!=MINE){
						button[x][y].setText(mines[x][y]+"");
						button[x][y].setEnabled(false);
					}else{
						button[x][y].setText("X");
						button[x][y].setEnabled(false);
					}
	}
	/**
	 * Randomly creates and positions mines in the grid
	 */
	public void createMines(){
		//Initialise list of random pairs
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int x = 0; x<ROW; x++)
			for(int y =0 ; y<COLUMN; y++)
				// 1223 12 is x 23 is y
				list.add(x*100+y);


		//Reset mine array and add MINES new mines in random positions
		mines = new int [ROW][COLUMN];
		for (int i = 0; i<MINES; i++){
			int position = (int)(Math.random()*list.size());
			mines[list.get(position) / 100][list.get(position) % 100]=MINE;
			list.remove(position);
		}


		for(int x = 0; x<ROW; x++)
			for(int y =0; y<COLUMN; y++)
				if(mines[x][y]!=MINE){
					int neighbourMines = 0;
					//UP
					if(y > 0 && mines[x][y-1] == MINE)
						neighbourMines++;
					//DOWN
					if(y<(ROW-1) && mines[x][y+1] == MINE)
						neighbourMines++;
					//UP RIGHT
					if(x<(COLUMN-1) && y > 0 && mines [x+1][y-1] == MINE)
						neighbourMines++;
					//RIGHT
					if(x<(COLUMN-1) && mines [x+1][y] == MINE)
						neighbourMines++;
					//DOWN RIGHT
					if(x<(COLUMN-1) && y<(ROW-1) && mines[x+1][y+1] == MINE)
						neighbourMines++;
					//UPPER LEFT
					if(x > 0 && y >0 && mines[x-1][y-1] == MINE)
						neighbourMines++;
					//LEFT
					if(x>0 && mines[x-1][y] == MINE)
						neighbourMines++;
					//DOWN LEFT
					if(x>0 && y<(ROW-1) && mines[x-1][y+1] == MINE)
						neighbourMines++;
					mines[x][y] = neighbourMines;
				}
	}

	public static void main(String[] args) {
		new Board();

	}

}