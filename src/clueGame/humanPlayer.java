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

		String[] players = new String[board.getPlayerList().size()];

		for (int i = 0; i < board.getPlayerList().size(); i++) {
			players[i] = board.getPlayerList().get(i).getPlayerName();
		}

		String input = (String) JOptionPane.showInputDialog(null, "Make Suggestion", "this suggestion",
				JOptionPane.QUESTION_MESSAGE, null, // Use
													// default
													// icon
				players, // Array of choices
				board.getPlayerList().get(0).getPlayerName()); // Initial choice
		
		//The duplication doesn't work
		String input0 = (String) JOptionPane.showInputDialog(null, "Make Suggestion", "this suggestion",
				JOptionPane.QUESTION_MESSAGE, null, // Use
													// default
													// icon
				players, // Array of choices
				board.getPlayerList().get(0).getPlayerName()); // Initial choice
		//********************************
		
//		JTextField firstName = new JTextField();
//		JTextField lastName = new JTextField();
//		JTextField password = new JTextField();
//		final JComponent[] inputs = new JComponent[] {
//		        new JLabel("First"),
//		        firstName,
//		        new JLabel("Last"),
//		        lastName,
//		        new JLabel("Password"),
//		        password
//		};
//		int result = JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
//		if (result == JOptionPane.OK_OPTION) {
//		    System.out.println("You entered " +
//		            firstName.getText() + ", " +
//		            lastName.getText() + ", " +
//		            password.getText());
//		} else {
//		    System.out.println("User canceled / closed the dialog, result = " + result);
//		}
		return null;
	}

}
