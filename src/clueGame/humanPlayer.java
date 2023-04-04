/**
 * Human class is an extension of abstraction of Player. 
 * It is responsible for actions for all human players, includes updating cards in a player's hand, and make suggestion
 *  @author: Mike Eact 
 * @author: John Omalley 
 * @author: Qina Tan 
 * @start Date: 8/3/2025
 * @collaborator: none 
 * @resources: none 
 * 
 */


package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class humanPlayer extends Player {

	public humanPlayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);
		
	}
	
	@Override
	public void updateHand (Card card)
	{
		hand.add(card); 
	}

	@Override
	public ArrayList<Card> makeSuggestion() {
	
		return null;
	}
	
	
	

}
