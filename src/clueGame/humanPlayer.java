package clueGame;

public class humanPlayer extends Player {

	public humanPlayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void updateHand (Card card)
	{
		hand.add(card); 
	}
	

}
