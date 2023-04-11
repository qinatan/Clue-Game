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

public class humanPlayer extends Player {
	
	private boolean hasPlayerMoved = false; // TODO: I think that this should be move to another class
	private boolean hasPlayerACC = false; // TODO: This should be moved to a different class
	
	public humanPlayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);
		
	}
	
	@Override
	public void updateHand (Card card) {
		hand.add(card); 
	}

	@Override
	public ArrayList<Card> makeSuggestion() {
		return null;
	}
	
	//************THESE were made to deal with game flow*********
	//TODO: these need to be moved
	public void setHasPlayerMoved (boolean ACC) {
		hasPlayerMoved = ACC ; 
	}
	
	//TODO: This needs to be moved
	public void setHasPlayerACC (boolean ACC) {
		hasPlayerACC = ACC  ;
	}

	public boolean getIsHasPlayerMoved() {
		return hasPlayerMoved;
	}

	public boolean getIsHasPlayerACC() {
		return hasPlayerACC;
	}

}
