package clueGame;

public class Solution {
	private Card Room = new Card("Room", "Room"); // Testing purposes only 
	private Card Weapon = new Card("Weapon", "Weapon"); // Testing purposes only ;
	private Card Person = new Card("Person", "Person"); // Testing purposes only 
	
	public Solution(Card solutionPerson, Card solutionRoom , Card solutionWeapon)
	{
		Room = solutionRoom;  
		Weapon = solutionWeapon; 
		Person = solutionPerson; 
	}
	public Card getRoom() {
		return Room;
	}


	public Card getWeapon() {
		return Weapon;
	}


	public Card getPerson() {
		return Person;
	}
	
	
}
