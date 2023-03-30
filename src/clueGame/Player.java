package clueGame;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public abstract class Player {
	private String name;
	private Color playerColor; // Maybe we can change this to type "color" and implement a color enum
	private String color; 
	private int row, col;
	protected ArrayList <Card> hand = new ArrayList<Card>(); 
	
	
	public Player(String playerName, String playerColor, String row, String col)
	{
	 this.name = playerName; 
	 this.row = (int)row.charAt(0); 
	 this.col = (int)col.charAt(0); 
	 this.color = playerColor; 
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
	
	public ArrayList<Card> getHand()
	{
		return hand; 
	}
	
	public void updateHand (Card card) {
	}; // abstract method
	
	public void printHand() {
		for (int i = 0 ; i < hand.size(); i ++) {
			System.out.println(hand.get(i)) ;
		}
	}
}
