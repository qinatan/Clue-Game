package clueGame;

public class humanPlayer extends Player {

	public humanPlayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);
		
	}
	
	@Override
	public void updateHand (Card card)
	{
		hand.add(card); 
	}
	

}
