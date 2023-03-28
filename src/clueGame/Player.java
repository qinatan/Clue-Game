package clueGame;
import java.awt.Color;
public abstract class Player {
	private String name;
	private Color playerColor; // Maybe we can change this to type "color" and implement a color enum
	private int startRow, startCol;
	private BoardCell playerLocation; //????
	
	public Player(String playerName, String playerColor, int row, int col)
	{
	 this.name = playerName; 
	 this.startRow = row; 
	 this.startCol = col; 
	 this.playerLocation = Board.getInstance().getCellLocation(row, col); 
	 
	 switch(playerColor) {
	 case "Red": this.playerColor = new Color(255, 0, 0); 
	 			 break;
	 case "Pink": this.playerColor = new Color(255, 192, 203); 
	 			 break; 
	 case "Green": this.playerColor = new Color (0, 255, 0); 
	 			 break; 
	 case "Black": this.playerColor = new Color(0, 0, 0); 
			 	 break;
	 case "Blue": this.playerColor = new Color (0, 0, 255); 
	 			 break; 
	 case "Yellow": this.playerColor = new Color (255, 255, 0); 
	 			 break; 
	 	}
	}
	
	//getters 
	public String getPlayerName()
	{
		return this.name; 
	}
	public Color getPlayerColor()
	{
		return this.playerColor; 
	}
	
	public BoardCell getPlayerLocation()
	{
		return playerLocation; 
	}
	public void updateHand (Card card) {}; // abstract method
}
