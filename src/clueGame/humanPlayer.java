/**
 * Human class is an extension of abstraction of Player. 
 * It is responsible for actions for all human players, includes updating cards in a player's hand, and make suggestion
 *  @author: Mike Eact 
 * @author: John Omalley 
 * @author: Qina Tan 
 * @start Date: 4/9/2023
 * @collaborator: none 
 * @resources: none 
 * 
 */

package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class humanPlayer extends Player {
	Board board = Board.getInstance();

	public humanPlayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);

	}

	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}

	@Override
	public ArrayList<Card> makeSuggestion() {

		// TODO: Input here equals all the players in players list, instead of only the
		// player that was selected.
		// TODO: this is probably the next thing to work on.

		// TODO: These loops could probably be redone. They are only needed to get the
		// cards into a arrays of strings
		String[] players = new String[board.getPlayerList().size()];
		for (int i = 0; i < board.getPlayerList().size(); i++) {
			players[i] = board.getPlayerList().get(i).getPlayerName();
		}

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
		int result = JOptionPane.showConfirmDialog(null, inputs, "Make Suggestion", JOptionPane.PLAIN_MESSAGE);

		// This should move the player that the human player suggested to the human
		// players current room.
		// TODO: this can be refactored to remove a line
		String selectedPlayer = (String) playersBox.getSelectedItem();
		String selectedRoom = (String) roomsBox.getSelectedItem();
		String selectedWeapon = (String) weaponsBox.getSelectedItem();

		for (Player player : Board.getPlayerList()) {
			if (selectedPlayer.equals(player.getPlayerName())) {
				System.out.println(selectedPlayer + " " + player.getPlayerName());
				player.setPlayerLocation(getPlayerRow(), getPlayerCol());
			}
		}

		System.out.println(selectedPlayer);

		return null;
	}

}
