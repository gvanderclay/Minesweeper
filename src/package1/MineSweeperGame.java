package package1;

import java.util.Random;

/*****************************************************************
 * Class that develops the gameboard and sets the characteristics for
 * each cell on the board
 * 
 * @author Gage Vander Clay
 * @version 2/11/2014
 *****************************************************************/
public class MineSweeperGame {

	/** Cells that make up the game board */
	private Cell[][] board;

	/** Status of the game */
	private GameStatus status;

	/** Size of the board */
	private int size;

	/** Amount of mines on the board */
	private int mines;

	/** Amount of flags on the board */
	private int flags;

	/****************************************
	 * Constructor that creates a game board
	 ****************************************/
	public MineSweeperGame(int size, int mines) {
		this.size = size;
		this.mines = mines;
		flags = 0;
		board = new Cell[size][size];
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines(mines);
		setNeighboringMines();
	}

	/*******************************************
	 * Returns the amount of mines on the board
	 * 
	 * @return amount of mines on the board
	 *******************************************/
	public int getMines() {
		return mines;
	}

	/*******************************************
	 * Returns the amount of flags on the board
	 * 
	 * @return amount of flags on the board
	 *******************************************/
	public int getFlags() {
		return flags;
	}

	/*******************************************************
	 * Returns the game status and determines if the player has won the
	 * game
	 * 
	 * @return status of the game
	 *******************************************************/
	public GameStatus getGameStatus() {
		return status;
	}

	/**********************************************
	 * Returns the cell for a given row and column
	 * 
	 * @param row
	 *            row of the cell
	 * @param col
	 *            column of the cell
	 * @return cell on the board
	 **********************************************/
	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/**************************************
	 * Flags or unflags the given cell
	 * 
	 * @param row
	 *            row of the given cell
	 * @param col
	 *            column of the given cell
	 **************************************/
	public void flag(int row, int col) {

		if (!board[row][col].isFlagged()
				&& !board[row][col].isExposed() && flags < mines) {
			board[row][col].setFlagged(true);
			flags++;
		} else if (board[row][col].isFlagged()
				&& !board[row][col].isExposed()) {
			board[row][col].setFlagged(false);
			flags--;
		}
	}

	/*********************************************
	 * Resets the game back to the default state
	 *********************************************/
	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines(mines);
		setNeighboringMines();
		flags = 0;
	}

	/******************************************************
	 * Method that is invoked when a user has selected one one of the
	 * cells
	 * 
	 * @param row
	 *            row of the cell being selected
	 * @param col
	 *            column of the cell being selected
	 ******************************************************/
	public void select(int row, int col) {
		if (board[row][col].isFlagged())// if flagged do nothing.
			return;
		board[row][col].setExposed(true);
		if (board[row][col].isMine()) {
			status = GameStatus.Lost;
		} else {
			status = GameStatus.Won;
			for (int r = 0; r < size; r++)
				// are only mines left
				for (int c = 0; c < size; c++)
					if (!board[r][c].isExposed()
							&& !board[r][c].isMine())
						status = GameStatus.NotOverYet;
		}
		cascade(row, col);
	}

	/*******************************************************
	 * Method that selects every cell that isn't touching a bomb
	 * surrounding the selected cell
	 * 
	 * @param row
	 *            row of the selected cell
	 * @param col
	 *            column of the selected cell
	 *******************************************************/
	private void cascade(int row, int col) {
		if (board[row][col].isExposed()
				&& board[row][col].getMineCount() == 0
				&& !board[row][col].isMine()) {
			for (int r = row - 1; r <= row + 1; r++) {
				for (int c = col - 1; c <= col + 1; c++) {
					if ((r >= 0 && r < size) && (c >= 0 && c < size))
						if (!board[r][c].isExposed()) {
							select(r, c);
						}
				}
			}
		}

	}

	/************************************************
	 * Counts the amount of neighboring mines by the current cell
	 * 
	 * @param row
	 *            row of the cell
	 * @param col
	 *            column of the cell
	 ************************************************/
	private int countNeighbors(int row, int col) {
		int count = 0;
		for (int r = row - 1; r <= row + 1; r++) {
			for (int c = col - 1; c <= col + 1; c++) {
				if ((r >= 0 && r < size) && (c >= 0 && c < size))
					if (board[r][c].isMine())
						count++;
			}
		}
		return count;
	}

	/************************************************
	 * Sets the amount of neighboring mines into the cell object
	 ************************************************/
	private void setNeighboringMines() {
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if (!board[r][c].isMine())
					board[r][c].setMineCount(countNeighbors(r, c));
			}
		}
	}

	/****************************************************
	 * Method that Sets all cells to have mineCount = 0, flagged =
	 * false, exposed = false isMine = false.
	 ****************************************************/
	private void setEmpty() {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				board[row][col] = new Cell(0, false, false, false);
			}
		}
	}

	/*********************************************
	 * Method that lays the mines in random spots on the board
	 *********************************************/
	private void layMines(int mineCount) {
		Random random = new Random();
		int i = 0;
		while (i < mineCount) {
			int col = random.nextInt(size);
			int row = random.nextInt(size);

			if (!board[row][col].isMine()) {
				board[row][col].setMine(true);
				i++;
			}
		}
	}

	/************************************
	 * Converts the object into a string
	 ************************************/
	public String toString() {
		String properties = "   ";
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++)
				properties += board[r][c].getMineCount();
			properties += "\n";
		}

		return properties;
	}

}
