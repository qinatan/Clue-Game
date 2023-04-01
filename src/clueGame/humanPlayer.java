package clueGame;

import java.util.ArrayList;

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
		// TODO Auto-generated method stub
		return null;
	}

}
