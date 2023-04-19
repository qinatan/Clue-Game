package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ClueGame extends JFrame {
	private static final long serialVersionUID = 1L;
	Board board = Board.getInstance();
	CardsPanel cardsPanel;
	GameControlPanel controlPanel;

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
			// TODO: there are functions in here that could probably be moved out into their
			// own functions

			int cellWidth = board.getCellWidth();
			int cellHeight = board.getCellHeight();
			int col = (int) e.getPoint().getX() / cellWidth;
			int row = ((int) e.getPoint().getY() - 30) / cellHeight;

			// TODO: we should probably distribute the ! to clean this line up
			if (!(row > board.getNumRows() - 1 || col > board.getNumColumns() - 1)) {
				BoardCell cell = board.getCell(row, col);

				// This reads if it is in the targets list and it is either a room center or a
				// walkway
				if (board.getTargets().contains(cell) && (cell.isRoomCenter() || cell.getCellSymbol() == 'W')) {
					// update player location after they click one of the board cell on target list
					board.getPlayersTurn().setPlayerLocation(row, col);
					board.getPlayersTurn().setHasPlayerMoved(true);
					clearTargetCells();
					board.getPlayersTurn().setDrawOffset(0);

				} else if (board.getTargets().contains(cell) && cell.isRoom()) {
					BoardCell thisRoomCenter = board.getRoom(cell).getCenterCell();
					board.getPlayersTurn().setPlayerLocation(thisRoomCenter.getRowNum(), thisRoomCenter.getColumnNum());

					// Make suggestion
					ArrayList<Card> suggestionCards = board.getPlayersTurn().makeSuggestion();

					controlPanel.setGuess(
							suggestionCards.get(0) + " " + suggestionCards.get(1) + " " + suggestionCards.get(2));

					for (Player thisPlayer : board.getPlayerList()) {

						// TODO: check to make sure the disprove suggestion doesn't return one of our
						// owncards.
						Card disprovenCard = thisPlayer.disproveSuggestion(suggestionCards.get(0),
								suggestionCards.get(1), suggestionCards.get(2));

						System.out.println(disprovenCard);
						// This is where we handle the human disproven suggestions
						if (disprovenCard != null) {
							controlPanel.setGuessResult(disprovenCard.getCardName());
						} else {
							controlPanel.setGuessResult("Suggestion Upheld");
						}
						// disprovenCard = null ; //Resets the disproven card. I don't think that this
						// is needed.

						if (thisPlayer.getCurrCell() == board.getPlayersTurn().getCurrCell()) {
							board.getPlayersTurn().setDrawOffset(15);
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
			BoardCell currentLocation = board.getCell(board.getPlayersTurn().getPlayerRow(),
					board.getPlayersTurn().getPlayerCol());

			// roll a dice
			board.getPlayersTurn().setRollNum();
			int randomRoll = board.getPlayersTurn().getRollNum();
			controlPanel.getRollText().setText(String.valueOf(randomRoll));

			// calculate target list based on current board cell and dice number
			board.calcTargets(currentLocation, randomRoll);

			// If Human Player
			if (board.getPlayersTurn() instanceof humanPlayer) {
				// repaint to highlight cells in target list
				repaint();

			}
			// Else it's a CPU Player
			else {

				BoardCell targetCell = ((computerPlayer) board.getPlayersTurn()).targetSelection(board.getTargets());
				// update player location
				board.getPlayersTurn().setPlayerLocation(targetCell.getRowNum(), targetCell.getColumnNum());
				if (targetCell.isRoom()) {
					ArrayList<Card> suggestedCards = board.getPlayersTurn().makeSuggestion();
					String guess = suggestedCards.get(0).getCardName() + " + " + suggestedCards.get(1).getCardName()
							+ " + " + suggestedCards.get(2).getCardName();
					controlPanel.setGuess(guess);

					// TODO: This code is literal trash
					// TODO: what is this trying to do
					for (Card card : suggestedCards) {
						if (card.getCardType().equals(CardType.PERSON)) {
							ArrayList<Player> playerList = Board.getPlayerList();
							for (Player player : playerList) {
								if (player.getPlayerName().equals(card.getCardName())) {
									player.setPlayerLocation(targetCell.getRowNum(), targetCell.getColumnNum());
								}
							}

						}
					}
				}
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
			JOptionPane.showMessageDialog(null, "Please finish your turn", "Players turn", JOptionPane.ERROR_MESSAGE);
		}

	}

	private class ACCButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// TODO: this need to include a check so that the player cannont move and then
			// make an accusation. This logic could be simmilar or included wiht the next
			// button listener
			accButtonPressedLogic();

		}
	}

	public void accButtonPressedLogic() {
		System.out.print("here");
		board.getPlayersTurn().setHasPlayerACC(true);
		for (BoardCell targetCell : board.getTargets()) {
			targetCell.setIsTargetCell(false);
		}
		// TODO: this doesn't repaint the room
		repaint();

		String[] players = new String[board.getPlayerList().size()];
		for (int i = 0; i < board.getPlayerList().size(); i++) {
			players[i] = board.getPlayerList().get(i).getPlayerName();
		}

		// String currRoom = board.getPlayersTurn().getCurrRoom().getName();
		String[] rooms = new String[board.getRoomDeck().size()];
		for (int j = 0; j < board.getRoomDeck().size(); j++) {
			rooms[j] = board.getRoomDeck().get(j).getCardName();
		}

		String[] weapons = new String[board.getWeaponDeck().size()];
		for (int k = 0; k < board.getWeaponDeck().size(); k++) {
			weapons[k] = board.getWeaponDeck().get(k).getCardName(); // There is also a to string method
		}

		JComboBox playersBox = new JComboBox(players);
		JComboBox roomsBox = new JComboBox(rooms);
		JComboBox weaponsBox = new JComboBox(weapons);

		final JComponent[] inputs = new JComponent[] { new JLabel("Room"), roomsBox, new JLabel("Player"), playersBox,
				new JLabel("Weapon"), weaponsBox };
		int result = JOptionPane.showConfirmDialog(null, inputs, "Make Accusation", JOptionPane.PLAIN_MESSAGE);

		ArrayList<Card> ACCCards = new ArrayList<Card>();

		String selectedPlayer = (String) playersBox.getSelectedItem();
		String selectedWeapon = (String) weaponsBox.getSelectedItem();
		String selectedRoom = (String) roomsBox.getSelectedItem();
		// Getting the weapon Card
		for (int w = 0; w < board.getWeaponDeck().size(); w++) {
			if (board.getWeaponDeck().get(w).getCardName().equals(selectedWeapon)) {
				ACCCards.add(board.getWeaponDeck().get(w));
			}
		}

		// Getting the room card
		for (int w = 0; w < board.getRoomDeck().size(); w++) {
			if (board.getRoomDeck().get(w).getCardName().equals(selectedRoom)) {
				ACCCards.add(board.getRoomDeck().get(w));
			}
		}

		// Getting the player card
		for (int q = 0; q < board.getPeopleDeck().size(); q++) {
			if (board.getPeopleDeck().get(q).getCardName().equals(selectedPlayer)) {

				Card playerCard = board.getPeopleDeck().get(q);
				ACCCards.add(playerCard); // Adds that card to the suggested list
			}
		}

		// TODO: close the game after this
		if (board.checkAccusation(ACCCards.get(0), ACCCards.get(1), ACCCards.get(2))) {
			JOptionPane.showMessageDialog(null, "You Won!", "Congradulations", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Sorry you Lost", "Loser", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// Main entry point for game
	public static void main(String[] args) {
		ClueGame ThisClueGame = new ClueGame();

	}
}
