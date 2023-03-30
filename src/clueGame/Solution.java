package clueGame;

public class Solution {
	private Card Room; // Testing purposes only 
	private Card Weapon;// Testing purposes only ;
	private Card Person;  // Testing purposes only 
	
	public Solution(Card solutionPerson, Card solutionRoom , Card solutionWeapon)
	{
		this.Room = solutionRoom;  
		this.Weapon = solutionWeapon; 
		this.Person = solutionPerson; 
		
	}
	public Card getRoom() {
		return this.Room;
	}


	public Card getWeapon() {
		return this.Weapon;
	}


	public Card getPerson() {
		return this.Person;
	}
	
	
}
