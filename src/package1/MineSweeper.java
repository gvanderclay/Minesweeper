package package1;

import javax.swing.JFrame;

/***************************************************
 * Creates the GUI and makes it visible to the user
 * 
 * @author Gage Vander Clay
 ***************************************************/
public class MineSweeper {

	/**********************************
	 * Main class that does everything
	 **********************************/
	public static void main(String[] args) {
		JFrame frame = new JFrame("MineSweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

}
