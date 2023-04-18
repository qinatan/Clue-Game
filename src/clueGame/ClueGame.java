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
	private Player currPlayer;

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
		// this.currPlayer.setRollNum();
		int rolledDice = currPlayer.getRollNum();
		// System.out.print(rolledDice);
		board.calcTargets(currentCell, rolledDice);
		repaint();
	}

	// TODO: remove this method if it is being unsed
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
			int col = (int) mouseX / cellWidth;
			int row = (int) (mouseY - 30) / cellHeight;

			// TODO: This can be refactored to be more clean
			if (row > board.getNumRows() - 1 || col > board.getNumColumns() - 1) {
				// This should do nothing
				System.out.println(row + " " + col + " " + board.getNumRows() + " " + board.getNumColumns());
			} else {
				BoardCell cell = board.getCell(row, col);

				// This reads if it is in the targets list and it is either a room center or a
				// walkway
				// TODO: this can probably get cleaned up
				if (board.getTargets().contains(cell) && (cell.isRoomCenter() || cell.getCellSymbol() == 'W')) {
					// update player location after they click one of the board cell on target list
					currPlayer.setPlayerLocation(row, col);
					currPlayer.setHasPlayerMoved(true);

					for (BoardCell targetCell : board.getTargets()) {
						targetCell.setIsTargetCell(false);

					}
					board.getTargets().clear();
					repaint();
				} else if (board.getTargets().contains(cell) && cell.isRoom()) { // Else if it is a target cell but not
																					// a center or a walkway

					BoardCell thisRoomCenter = board.getRoom(cell).getCenterCell();
					currPlayer.setPlayerLocation(thisRoomCenter.getRowNum(), thisRoomCenter.getColumnNum());

					// TODO: This works currently but this might need to be changed when computers
					// start getting moved when suggestions happen
					for (Player thisPlayer : board.getPlayerList()) {
						if (thisPlayer.getCurrCell() == currPlayer.getCurrCell()) {
							currPlayer.drawOffset = 15;
							break;
						} else {
							continue;
						}
					}
//					if (thisRoomCenter.isOccupied()) {
//						currPlayer.drawOffset = 15 ; 
//					}
					currPlayer.setHasPlayerMoved(true);
					// TODO : there could be refactoring done here
					for (BoardCell targetCell : board.getTargets()) {
						targetCell.setIsTargetCell(false);
					}
					board.getTargets().clear();
					repaint();
				}

				else {
					JOptionPane.showMessageDialog(null, "Please click on a vaild tile", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} // End of the new if else
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

		// This proves that the ACC button is working correctly
		System.out.println(currPlayer.getIsHasPlayerACC() + " " + currPlayer.getIsHasPlayerMoved());
		if (currPlayer.getIsHasPlayerACC() || currPlayer.getIsHasPlayerMoved()) {
			// switch to get next player in the list

			board.nextTurn();
			// update current player
			currPlayer = board.getPlayersTurn();
			controlPanel.getPlayerNameText().setText(currPlayer.getPlayerName());
			controlPanel.getPlayerNameText().setBackground(currPlayer.getPlayerColor());
			BoardCell currentLocation = board.getCell(currPlayer.getPlayerRow(), currPlayer.getPlayerCol());

			// roll a dice
			currPlayer.setRollNum();
			int randomRoll = currPlayer.getRollNum();
			controlPanel.getRollText().setText(String.valueOf(randomRoll));
			// calculate target list based on current board cell and dice number
			board.calcTargets(currentLocation, randomRoll);
			if (currPlayer instanceof humanPlayer) {
				// repaint to highlight cells in target list
				repaint();

			} else {
				// update player location
				BoardCell targetCell = ((computerPlayer) currPlayer).targetSelection(board.getTargets());
				for (BoardCell cell : board.getTargets()) {
					cell.setIsTargetCell(false);
				}

				int targetRow = targetCell.getRowNum();
				int targetCol = targetCell.getColumnNum();
				currPlayer.setPlayerLocation(targetRow, targetCol);
				currPlayer.setHasPlayerMoved(true);
				repaint();
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
		for (BoardCell targetCell : board.getTargets()) {
			targetCell.setIsTargetCell(false);
		}
		repaint();
		System.out.print(currPlayer.getIsHasPlayerACC());
	}
	
	public Player getCurrPlayer() {
		return currPlayer;
	}

	public void setCurrPlayer(Player currPlayer) {
		this.currPlayer = currPlayer;
	}

	// Main entry point for game
	public static void main(String[] args) {
		ClueGame ThisClueGame = new ClueGame();

	}
}
