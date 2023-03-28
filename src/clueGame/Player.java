package clueGame;

public abstract class Player {
	private String name;
	private String color; // Maybe we can change this to type "color" and implement a color enum
	private int row, col; 
	
	
	public void updateHand (Card card) {}; // abstract method
}
