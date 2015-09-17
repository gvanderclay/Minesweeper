package package1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/****************************************
 * Panel class that creates a game board
 * 
 * @author Gage Vander Clay
 * @version 2/7/2014
 ****************************************/
public class MineSweeperPanel extends JPanel {
	/** 2-dim array that represents the GUI board the user sees */
	private JButton[][] board;

	/** One cell, and is the parameter received from the game object */
	private Cell iCell;

	/** The game object */
	private MineSweeperGame game;

	/** Ints that represent the amount of mines and size of the board */
	private int size, mines;

	/** JLabel that represents the title of the game */
	private JLabel title;

	/** JMenuBar that contains the settings for the game */
	private JMenuBar bar;

	/** JMenu that contains the buttons for the settings */
	private JMenu file;

	/** JMenuItems that quit, restart the game, and reset the stats */
	private JMenuItem quitButton, restart, resetStats;

	/** JMenuItems that set the difficulty of the game */
	private JMenuItem hard, medium, easy, custom;

	/** JMenu that contains the difficulties */
	private JMenu difficulty;

	/** Panel for the settings buttons */
	private JPanel settings;

	/** Panel for the game board */
	private JPanel gameBoard;

	/** Panel that contains the win, loss, and flag JLabel */
	private JPanel stats;

	/** Master contains the game and settings, King contains master */
	private JPanel king, master;

	/** JLabels that will show the wins, losses, and amount of flags */
	private JLabel winDisplay, lossDisplay, flagDisplay;

	/** Icon for an emptyCell, flag, and bomb */
	private ImageIcon emptyIcon, flag, bomb;

	/** MouseListener for the JButtons */
	private ButtonListener listener;

	/** ActionListener for the menuItems */
	private MenuListener menuListener;

	/** Integers that keep track of the amount of wins and losses */
	private int wins, losses;

	/**************************************************************
	 * Constructor that instantiates all of the instance variables
	 **************************************************************/
	public MineSweeperPanel() {
		bar = new JMenuBar();
		file = new JMenu("Game");
		difficulty = new JMenu("Change Difficulty");
		quitButton = new JMenuItem("Quit");
		restart = new JMenuItem("Reset");
		resetStats = new JMenuItem("Clear Stats");
		custom = new JMenuItem("Custom");
		hard = new JMenuItem("Hard");
		medium = new JMenuItem("Medium");
		easy = new JMenuItem("Easy");
		settings = new JPanel();
		gameBoard = new JPanel();
		stats = new JPanel();
		master = new JPanel();
		king = new JPanel();
		title = new JLabel("!!!!!!  Mine Sweeper  !!!!", JLabel.CENTER);

		// sets the size and mines to their default value
		size = 10;
		mines = 10;
		listener = new ButtonListener();
		menuListener = new MenuListener();
		game = new MineSweeperGame(size, mines);

		// sets the wins and losses to zero for the player
		wins = 0;
		losses = 0;
		winDisplay = new JLabel("Wins: " + wins);
		lossDisplay = new JLabel("Losses: " + losses);
		flagDisplay = new JLabel("Flags: " + game.getMines());
		emptyIcon = new ImageIcon("emptyIcon.jpg");
		flag = new ImageIcon("flag.png");
		bomb = new ImageIcon("bomb.png");
		setBoard();
		quitButton.addActionListener(menuListener);
		custom.addActionListener(menuListener);
		restart.addActionListener(menuListener);
		hard.addActionListener(menuListener);
		medium.addActionListener(menuListener);
		easy.addActionListener(menuListener);
		resetStats.addActionListener(menuListener);
		displayBoard();
		king.setLayout(new BorderLayout());
		master.setLayout(new BorderLayout());
		stats.setLayout(new GridLayout(1, 2));
		settings.setLayout(new GridLayout(1, 2));
		difficulty.add(easy);
		difficulty.add(medium);
		difficulty.add(hard);
		difficulty.add(custom);
		file.add(quitButton);
		file.add(resetStats);
		file.add(restart);
		file.add(difficulty);
		bar.add(file);
		stats.add(winDisplay);
		stats.add(lossDisplay);
		stats.add(flagDisplay);
		master.add(gameBoard, BorderLayout.CENTER);
		master.add(settings, BorderLayout.SOUTH);
		master.add(stats, BorderLayout.NORTH);
		king.add(title, BorderLayout.NORTH);
		king.add(master, BorderLayout.SOUTH);
		king.add(bar);
		add(king);
	}

	/****************************************************
	 * Method that sets up the buttons on the game board
	 ****************************************************/
	private void displayBoard() {

		// goes through each cell in the game class
		// to get information for each JButton
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				iCell = game.getCell(row, col);
				board[row][col].setText("");
				board[row][col].setBackground(null);
				board[row][col].setFont(new Font("Sans-serif",
						Font.BOLD, 20));
				if (iCell.isMine())
					board[row][col].setText("");
				if (iCell.isExposed())
					board[row][col].setEnabled(false);
				else
					board[row][col].setEnabled(true);

				if (iCell.isExposed()) {
					if (iCell.getMineCount() == 0) {
						board[row][col].setText("");
					} else
						board[row][col].setText(""
								+ iCell.getMineCount());

				}
				if (iCell.isFlagged()) {
					board[row][col].setIcon(flag);
				} else
					board[row][col].setIcon(emptyIcon);
			}
		}

	}

	/************************************************
	 * Method that sets up the board by setting each cell to the normal
	 * state
	 ************************************************/
	private void setBoard() {
		gameBoard.setLayout(new GridLayout(size, size));
		board = new JButton[size][size];
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++) {
				board[row][col] = new JButton("", emptyIcon);
				board[row][col]
						.setHorizontalTextPosition(JButton.CENTER);
				board[row][col].setVerticalTextPosition(JButton.CENTER);
				board[row][col].setPreferredSize(new Dimension(20, 20));
				board[row][col].setMargin(new Insets(0, 0, 0, 0));
				board[row][col].addMouseListener(listener);
				gameBoard.add(board[row][col]);
			}
	}

	/***************************************************
	 * Prompts the user to input the size they want the board to be and
	 * the amount of mines they want on the board
	 ***************************************************/
	private void inputSizeAndMines() {
		String x = JOptionPane
				.showInputDialog("Enter size of board between 3-30");
		try {
			size = Integer.parseInt(x);
			if (size > 30 || size < 3) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			size = 10;
			JOptionPane.showMessageDialog(null,
					"Invalid input, size will be set to default");
		}
		String y = JOptionPane
				.showInputDialog("Enter amount of mines"
						+ "\n Must be greater than 3 and less than amount of cells");
		try {
			mines = Integer.parseInt(y);
			if (mines >= (size * size) || mines < 3) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException nfe) {
			mines = 10;
			JOptionPane.showMessageDialog(null,
					"Invalid input, mines will be set to default");
		}
	}

	/**********************************
	 * Exposes every mine on the board
	 **********************************/
	private void exposeMines() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				iCell = game.getCell(r, c);
				if (iCell.isMine()) {
					board[r][c].setIcon(bomb);
					board[r][c].setEnabled(true);
				}
			}
		}
	}

	/***********************************************
	 * Resets the game object and updates the board accordingly
	 ***********************************************/
	private void resetBoard() {
		game.reset();
		flagDisplay.setText("Flags: "
				+ (game.getMines() - game.getFlags()));
		displayBoard();
	}

	/*************************************************
	 * Resizes the board based on the given size and amount of mines
	 * 
	 * @param size
	 *            size of the new board
	 * @param mines
	 *            amount of mines for the new board
	 *************************************************/
	private void resizeBoard(int size, int mines) {
		game = new MineSweeperGame(size, mines);
		flagDisplay.setText("Flags: "
				+ (game.getMines() - game.getFlags()));
		setBoard();
		displayBoard();

		// gets the window that king is contained in and packs it to
		// resize
		SwingUtilities.getWindowAncestor(king).pack();
		revalidate();
		repaint();
	}

	/*******************************************************************
	 * ActionListener class that responds to the presses of the buttons
	 * 
	 * @author Gage Vander Clay
	 * @version 2/7/2014
	 *******************************************************************/
	private class ButtonListener implements MouseListener {

		/*****************************************
		 * If the mouse is clicked, these actions will be performed
		 *****************************************/
		@Override
		public void mouseClicked(MouseEvent e) {

			// selects what cell the user selects
			for (int r = 0; r < size; r++)
				for (int c = 0; c < size; c++) {
					if (board[r][c] == e.getSource()
							&& e.getButton() == 1)
						game.select(r, c);
				}
			displayBoard();

			// if the game is lost the board resets and
			// anda new game starts
			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				exposeMines();
				JOptionPane.showMessageDialog(null,
						"You Lose \n The game will reset");
				losses++;
				lossDisplay.setText("Losses: " + losses);
				resetBoard();

			}

			// if the game is won, the board resets
			// and a new game starts
			if (game.getGameStatus() == GameStatus.Won) {
				displayBoard();
				exposeMines();
				JOptionPane
						.showMessageDialog(null,
								"You Win: all mines have been found!\n The game will reset");
				wins++;
				winDisplay.setText("Wins: " + wins);
				resetBoard();
			}

			// if the user right clicks, a flag is placed
			for (int r = 0; r < size; r++)
				for (int c = 0; c < size; c++) {
					if (board[r][c] == e.getSource()
							&& e.getButton() == 3) {
						game.flag(r, c);
						flagDisplay.setText("Flags: "
								+ (game.getMines() - game.getFlags()));

					}
				}
			displayBoard();

		}

		/**************************************
		 * implemented method that is not used
		 **************************************/
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		/**************************************
		 * implemented method that is not used
		 **************************************/
		@Override
		public void mouseExited(MouseEvent e) {
		}

		/**************************************
		 * implemented method that is not used
		 **************************************/
		@Override
		public void mousePressed(MouseEvent e) {
		}

		/**************************************
		 * implemented method that is not used
		 **************************************/
		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}

	/********************************************************************
	 * ActionListener that is used for the buttons in the JMenuItem file
	 * 
	 * @author Gage Vander Clay
	 * @version 2/7/2014
	 ********************************************************************/
	private class MenuListener implements ActionListener {

		/**********************************************
		 * Performs an action based off of what button is pressed
		 **********************************************/
		@Override
		public void actionPerformed(ActionEvent e) {

			// quits the game
			if (e.getSource() == quitButton) {
				System.exit(0);
			}

			// sets the board to custom size
			if (e.getSource() == custom) {
				for (int r = 0; r < size; r++)
					for (int c = 0; c < size; c++) {
						// removes buttons from the board
						gameBoard.remove(board[r][c]);

					}
				inputSizeAndMines();
				resizeBoard(size, mines);
			}

			// sets the difficulty of the game to hard
			if (e.getSource() == hard) {
				for (int r = 0; r < size; r++)
					for (int c = 0; c < size; c++) {
						gameBoard.remove(board[r][c]);
					}
				JOptionPane.showMessageDialog(null,
						"The difficulty is now hard");
				size = 30;
				mines = 99;
				resizeBoard(size, mines);
			}

			// sets the difficulty of the game to medium
			if (e.getSource() == medium) {
				for (int r = 0; r < size; r++)
					for (int c = 0; c < size; c++) {
						gameBoard.remove(board[r][c]);
					}
				JOptionPane.showMessageDialog(null,
						"The difficulty is now medium");
				size = 20;
				mines = 40;
				resizeBoard(size, mines);
			}

			// sets the difficulty of the game to easy
			if (e.getSource() == easy) {
				for (int r = 0; r < size; r++)
					for (int c = 0; c < size; c++) {
						gameBoard.remove(board[r][c]);
					}
				JOptionPane.showMessageDialog(null,
						"The difficulty is now easy");
				size = 10;
				mines = 10;
				resizeBoard(size, mines);
			}

			// restarts the game with the same difficulty
			if (e.getSource() == restart) {
				JOptionPane.showMessageDialog(null, "Game Reset");
				game.reset();
				flagDisplay.setText("Flags: "
						+ (game.getMines() - game.getFlags()));
				displayBoard();
			}

			// sets wins and losses to zero
			if (e.getSource() == resetStats) {
				wins = 0;
				losses = 0;
				winDisplay.setText("Wins: " + wins);
				lossDisplay.setText("Losses: " + losses);
			}
		}

	}

}
