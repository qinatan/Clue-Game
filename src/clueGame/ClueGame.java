package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	Board board = Board.getInstance();
	CardsPanel cardsPanel;
	GameControlPanel controlPanel;
	public static Player currPlayer; // TODO: make this private

	// Default constructor
	public ClueGame() {

		// Create board panel and add it to frame
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		initialTurn();
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

		controlPanel.getNextButton().addActionListener(new NextButtonListener());
		controlPanel.getACCButton().addActionListener(new ACCButtonListener());

		setVisible(true);

		// This is the splash panel.
		JOptionPane.showMessageDialog(null,
				"You are " + board.getPlayer(0).getPlayerName() + ".\n Can you find the solution before the computers?",
				"Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

	}

	public void initialTurn() {
		// human player is on the first turn
		currPlayer = Board.getPlayerList().get(0);

		// human player roll a die
		currPlayer.setRollNum();
		int currentRow = currPlayer.getPlayerRow();
		int currentCol = currPlayer.getPlayerCol();
		BoardCell currentCell = board.getCell(currentRow, currentCol);
		//this.currPlayer.setRollNum();
		int rolledDice = currPlayer.getRollNum();
		// System.out.print(rolledDice);
		board.calcTargets(currentCell, rolledDice);
		repaint();
	}

	// This method will drive display updates.
	// @SuppressWarnings("unused")
	private void updateDisplay() {
		// Call repaint
	}

	private class NextButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			nextButtonPressedLogic();
		}
	}

	private class movePlayerClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			int mouseX = (int) e.getPoint().getX();
			int mouseY = (int) e.getPoint().getY();
			int cellWidth = board.getCellWidth();
			int cellHeight = board.getCellHeight();
			int row = (int) (e.getPoint().getY() - 30) / cellHeight;
			int col = (int) e.getPoint().getX() / cellWidth;
			BoardCell cell = board.getCell(row, col);

			if (board.getTargets().contains(cell)) {
				// update player location after they click one of the board cell on target list
				currPlayer.setPlayerLocation(row, col);
				currPlayer.setHasPlayerMoved(true);
				for (BoardCell targetCell : board.getTargets()) {
					targetCell.setIsTargetCell(false);
				}
				repaint();
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

	/**
	 * This is where the logic of the next button pressed should be held
	 */
	public void nextButtonPressedLogic() {

		// when the click button clicked we should check if the current player finish
		// their move
		System.out.println(currPlayer.toString());
		
		//This proves that the ACC button is working correctly
		System.out.println(currPlayer.getIsHasPlayerACC() + " " + currPlayer.getIsHasPlayerMoved()) ; 
		if (currPlayer.getIsHasPlayerACC() || currPlayer.getIsHasPlayerMoved()) {
			// switch to get next player in the list
			board.nextTurn();
			// update current player
			currPlayer = board.getPlayersTurn();
			//playerColor = currPlayer.getPlayerColor();
			//playerNameText.setText(currPlayer.getPlayerName());
			//playerNameText.setBackground(playerColor);
			BoardCell currentLocation = board.getCell(currPlayer.getPlayerRow(), currPlayer.getPlayerCol());

			// roll a dice
			currPlayer.setRollNum();
			int randomRoll = currPlayer.getRollNum();
			// rollText.setText(String.valueOf(randomRoll));
			currPlayer.setRollNum();
			// calculate target list based on current board cell and dice number
			board.calcTargets(currentLocation, randomRoll);
			if (currPlayer instanceof humanPlayer) {
				// repaint to highlight cells in target list
				repaint();

			} else {
				// update player location
				// make animation??
			}

		} else {
			// This works
			JOptionPane.showMessageDialog(null, "Please finish your turn", "Players turn", JOptionPane.ERROR_MESSAGE);
		}

	}

	private class ACCButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			ACCButtonPressedLogic();

		}
	}

	public void ACCButtonPressedLogic() {
		System.out.print("here");
		currPlayer.setHasPlayerACC(true);
		System.out.print(currPlayer.getIsHasPlayerACC());
	}

	// Main entry point for game
	public static void main(String[] args) {
		ClueGame ThisClueGame = new ClueGame();

	}
}
