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
