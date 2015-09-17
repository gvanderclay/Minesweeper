package package1;

/*****************************************************************
 * Class for a cell that contains all of the properties of a cell in a
 * game of MineSweeper
 * 
 * @author Gage Vander Clay
 * @version 2/7/2014
 *****************************************************************/
public class Cell {

	/** Amount of mines touching the current cell */
	private int mineCount;

	/** Boolean that says if the current cell is flagged or not */
	private boolean isFlagged;

	/** Boolean that says if the current cell is exposed or not */
	private boolean isExposed;

	/** Boolean that says if the current cell is a mine or not */
	private boolean isMine;

	/**************************************************************
	 * Constructor that instantiates all of the properties of a cell.
	 * 
	 * @param mineCount
	 *            amount of mines that are touching the cell
	 * @param isFlagged
	 *            whether the current cell is flagged or not
	 * @param isExposed
	 *            whether the current cell is exposed or not
	 * @param isMine
	 *            whether the current cell is a mine or not
	 **************************************************************/
	public Cell(int mineCount, boolean isFlagged, boolean isExposed,
			boolean isMine) {
		this.mineCount = mineCount;
		this.isFlagged = isFlagged;
		this.isExposed = isExposed;
		this.isMine = isMine;
	}

	/***************************************************************
	 * Method that returns the amount of mines touching the current cell
	 * 
	 * @return amount of mines on the current cell
	 ***************************************************************/
	public int getMineCount() {
		return mineCount;
	}

	/*************************************************************
	 * Method that sets the amount of mines touching the current cell
	 * 
	 * @param mineCount
	 *            amount of mines touching the current cell
	 *************************************************************/
	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	/*************************************************************
	 * Returns a boolean based on whether or not the current cell is
	 * flagged or not
	 * 
	 * @return whether the current cell is flagged or not
	 *************************************************************/
	public boolean isFlagged() {
		return isFlagged;
	}

	/*********************************************************
	 * Changes the state of whether or not the current cell is flagged
	 * or not
	 * 
	 * @param isFlagged
	 *            if the current cell is flagged or not
	 *********************************************************/
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	/*************************************************************
	 * Returns a boolean based on whether or not the current cell is
	 * exposed
	 * 
	 * @return whether or not the current cell is exposed
	 *************************************************************/
	public boolean isExposed() {
		return isExposed;
	}

	/**************************************************************
	 * Sets the current cells exposed status
	 * 
	 * @param isExposed
	 *            whether or not the current cell is exposed
	 **************************************************************/
	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
	}

	/*************************************************************
	 * Returns a boolean based on whether or not the current cell is a
	 * mine
	 * 
	 * @return whether or not the current cell is a mine
	 *************************************************************/
	public boolean isMine() {
		return isMine;
	}

	/**********************************************************
	 * Sets the status of the cell as a mine or not a mine
	 * 
	 * @param isMine
	 *            whether or not the current cell is a mine
	 **********************************************************/
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

}
