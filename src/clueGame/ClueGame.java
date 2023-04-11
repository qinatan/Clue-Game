package clueGame;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	Board board = Board.getInstance();
	CardsPanel cardsPanel;
	GameControlPanel controlPanel;

	// Default constructor
	public ClueGame() {

		// Create board panel and add it to frame
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		add(board, BorderLayout.CENTER);

		// Create game panel and add it to frame
		controlPanel = new GameControlPanel();
		add(controlPanel, BorderLayout.SOUTH);

		// Create cards panel and add it to frame
		cardsPanel = new CardsPanel();
		add(cardsPanel, BorderLayout.EAST);

		// Set default frame size, title etc.
		setSize(750, 630); // 630 was changed from 930 so that it fit on my screen
		setTitle("Clue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close

		addMouseListener(new movePlayerClick());

		setVisible(true);
	
		// This is the splash panel.
		JOptionPane.showMessageDialog(null,
				"You are " + board.getPlayer(0).getPlayerName() + ".\n Can you find the solution before the computers?",
				"Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
		//calculate targets based on current boardCell and die number 
		//board.calcTargets(board.getCell(board.getPlayer(0).getRowNum(), currPlayer.getColNum()), ABORT);
		
		//intial roll
	}

	// This method will drive display updates.
	// @SuppressWarnings("unused")
	private void updateDisplay() {
		//Call repaint
	}

	private class movePlayerClick implements MouseListener {
		humanPlayer player = (humanPlayer) board.getPlayer(0);

		@Override
		public void mouseClicked(MouseEvent e) {

			int mouseX = (int) e.getPoint().getX();
			int mouseY = (int) e.getPoint().getY();
			int cellWidth = board.getCellWidth();
			int cellHeight = board.getCellHeight();
			int row = (int) e.getPoint().getX() / cellWidth;
			int col = (int) e.getPoint().getY() / cellHeight;
			if (board.clickContainsTarget(mouseX, mouseY)) {
				// update player location after they click one of the board cell on target list
				player.setPlayerLocation(row, col);
				player.setHasPlayerMoved(true);
			}

			else {
				JOptionPane.showMessageDialog(null, "Please click on a vaild tile", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}// This should be left blank

		@Override
		public void mouseReleased(MouseEvent e) {
		}// This should be left blank

		@Override
		public void mouseEntered(MouseEvent e) {
		}// This should be left blank

		@Override
		public void mouseExited(MouseEvent e) {
		} // This should be left blank
	}

	// Main entry point for game
	public static void main(String[] args) {
		ClueGame clueGame =  new ClueGame();
		

	}
}
