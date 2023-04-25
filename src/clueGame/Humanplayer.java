/**
 * Human class is an extension of abstraction of Player. 
 * It is responsible for actions for all human players, includes updating cards in a player's hand, and make suggestion
 *  @author: Mike Eack 
 * @author: John Omalley 
 * @author: Qina Tan 
 * @start Date: 4/9/2023
 * @collaborator: none 
 * @resources: none 
 * 
 */

package clueGame;

import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Humanplayer extends Player {
	Board board = Board.getInstance();

	public Humanplayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);

	}

	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}

	@Override
	public ArrayList<Card> makeSuggestion() {

		ArrayList<Card> suggestedCards = new ArrayList<Card>();

		String[] players = new String[board.getPlayerList().size()];
		for (int i = 0; i < board.getPlayerList().size(); i++) {
			players[i] = board.getPlayerList().get(i).getPlayerName();
		}

		String currRoom = board.getPlayersTurn().getCurrRoom().getName();

		String[] weapons = new String[board.getWeaponDeck().size()];
		for (int k = 0; k < board.getWeaponDeck().size(); k++) {
			weapons[k] = board.getWeaponDeck().get(k).getCardName(); // There is also a to string method
		}

		JComboBox playersBox = new JComboBox(players);
		JTextField roomsBox = new JTextField(currRoom);
		JComboBox weaponsBox = new JComboBox(weapons);

		final JComponent[] inputs = new JComponent[] { new JLabel("Room"), roomsBox, new JLabel("Player"), playersBox,
				new JLabel("Weapon"), weaponsBox };
		int result = JOptionPane.showConfirmDialog(null, inputs, "Make Suggestion", JOptionPane.PLAIN_MESSAGE);

		// This should move the player that the human player suggested to the human
		// players current room.
		String selectedPlayer = (String) playersBox.getSelectedItem();
		String selectedWeapon = (String) weaponsBox.getSelectedItem();

		for (Player player : Board.getPlayerList()) {
			if (selectedPlayer.equals(player.getPlayerName())) {

				// Moves the player
				player.setPlayerLocation(getPlayerRow(), getPlayerCol());
				player.setMovedForSuggestion(true);
				
				board.resetPlayersLocations();

				// Loop to find that players card
				for (int q = 0; q < board.getPeopleDeck().size(); q++) {
					if (board.getPeopleDeck().get(q).getCardName().equals(player.getPlayerName())) {

						Card playerCard = board.getPeopleDeck().get(q);
						suggestedCards.add(playerCard); // Adds that card to the suggested list
					}
				}
			}
		}

		// Getting the weapon Card
		for (int w = 0; w < board.getWeaponDeck().size(); w++) {
			if (board.getWeaponDeck().get(w).getCardName().equals(selectedWeapon)) {
				suggestedCards.add(board.getWeaponDeck().get(w));
			}
		}

		// Getting the room card
		for (int w = 0; w < board.getRoomDeck().size(); w++) {
			if (board.getRoomDeck().get(w).getCardName().equals(currRoom)) {
				suggestedCards.add(board.getRoomDeck().get(w));
			}
		}

		return suggestedCards;
	}

	
	@Override
	public boolean canMakeAccusation() {
		
		return false;
	}

	@Override
	public  ArrayList<Card> makeAccusation() {
		return null;	
	}

}
