package clueGame;

import java.util.HashMap;
import java.util.Map;

public class Solution {
	private Card Room; 
	private Card Weapon;
	private Card Person; 
	Map <CardType, Card> solutionMap = new HashMap<CardType, Card>(); 
	
	public Solution(Card solutionPerson, Card solutionRoom , Card solutionWeapon) {
		Room = solutionRoom;  
		Weapon = solutionWeapon; 
		Person = solutionPerson; 	
		solutionMap.put(CardType.ROOM, Room);
		solutionMap.put(CardType.PERSON, Person);
		solutionMap.put(CardType.WEAPON, Weapon);
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
	
	public Map <CardType, Card> getSolutionMap() {
		return solutionMap;
	}

	@Override
	public String toString() {
		return "Solution [Room=" + Room + ", Weapon=" + Weapon + ", Person=" + Person + ", solutionMap=" + solutionMap
				+ "]";
	}
	
	
}