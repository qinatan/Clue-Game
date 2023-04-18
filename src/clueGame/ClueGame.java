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
	private static final long serialVersionUID = 1L;
	Board board = Board.getInstance();
	CardsPanel cardsPanel;
	GameControlPanel controlPanel;
	//private Player currPlayer;

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
		// human player roll a die
		board.getPlayersTurn().setRollNum();
		int currentRow = board.getPlayersTurn().getPlayerRow();
		int currentCol = board.getPlayersTurn().getPlayerCol();
		BoardCell currentCell = board.getCell(currentRow, currentCol);
		int rolledDice = board.getPlayersTurn().getRollNum();		
		board.calcTargets(currentCell, rolledDice);
		repaint();
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

			int cellWidth = board.getCellWidth();
			int cellHeight = board.getCellHeight();
			int col =(int) e.getPoint().getX() / cellWidth;
			int row = ((int) e.getPoint().getY() - 30) / cellHeight;

			if (!(row > board.getNumRows() - 1 || col > board.getNumColumns() - 1)) {
				BoardCell cell = board.getCell(row, col);

				// This reads if it is in the targets list and it is either a room center or a
				// walkway
				if (board.getTargets().contains(cell) && (cell.isRoomCenter() || cell.getCellSymbol() == 'W')) {
					// update player location after they click one of the board cell on target list
					board.getPlayersTurn().setPlayerLocation(row, col);
					board.getPlayersTurn().setHasPlayerMoved(true);
					clearTargetCells();
					
				} else if (board.getTargets().contains(cell) && cell.isRoom()) {				
					BoardCell thisRoomCenter = board.getRoom(cell).getCenterCell();
					board.getPlayersTurn().setPlayerLocation(thisRoomCenter.getRowNum(), thisRoomCenter.getColumnNum());

					for (Player thisPlayer : board.getPlayerList()) {
						if (thisPlayer.getCurrCell() == board.getPlayersTurn().getCurrCell()) {
							board.getPlayersTurn().drawOffset = 15;
							break;
						} else {
							continue;
						}
					}

					board.getPlayersTurn().setHasPlayerMoved(true);
					clearTargetCells();
				}

				else {
					JOptionPane.showMessageDialog(null, "Please click on a vaild tile", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
	

		private void clearTargetCells() {
			for (BoardCell targetCell : board.getTargets()) {
				targetCell.setIsTargetCell(false);
			}
			board.getTargets().clear();
			repaint();
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
		if (board.getPlayersTurn().getIsHasPlayerACC() || board.getPlayersTurn().getIsHasPlayerMoved()) {
			// switch to get next player in the list

			board.nextTurn();
			controlPanel.getPlayerNameText().setText(board.getPlayersTurn().getPlayerName());
			controlPanel.getPlayerNameText().setBackground(board.getPlayersTurn().getPlayerColor());
			BoardCell currentLocation = board.getCell(board.getPlayersTurn().getPlayerRow(), board.getPlayersTurn().getPlayerCol());

			// roll a dice
			board.getPlayersTurn().setRollNum();
			int randomRoll = board.getPlayersTurn().getRollNum();
			controlPanel.getRollText().setText(String.valueOf(randomRoll));
			// calculate target list based on current board cell and dice number
			board.calcTargets(currentLocation, randomRoll);
			if (board.getPlayersTurn() instanceof humanPlayer) {
				// repaint to highlight cells in target list
				repaint();

			} else {
				// update player location
				BoardCell targetCell = ((computerPlayer) board.getPlayersTurn()).targetSelection(board.getTargets());
				for (BoardCell cell : board.getTargets()) {
					cell.setIsTargetCell(false);
				}

				int targetRow = targetCell.getRowNum();
				int targetCol = targetCell.getColumnNum();
				board.getPlayersTurn().setPlayerLocation(targetRow, targetCol);
				board.getPlayersTurn().setHasPlayerMoved(true);
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
		board.getPlayersTurn().setHasPlayerACC(true);
		for (BoardCell targetCell : board.getTargets()) {
			targetCell.setIsTargetCell(false);
		}
		repaint();
		System.out.print(board.getPlayersTurn().getIsHasPlayerACC());
	}

	// Main entry point for game
	public static void main(String[] args) {
		ClueGame ThisClueGame = new ClueGame();

	}
}
