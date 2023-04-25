/**
 * 
 * Solution class contains three different types of cards: Person, Weapon, and Room, one card from each type
 * @author: Mike Eack
 * @author: John Omalley 
 * @author: Qina Tan 
 * @start Date: 4/24/2023
 * @collaborator: none 
 * @resources: none  
 */



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
		return "Room=" + Room + ", Weapon=" + Weapon + ", Person=" + Person;
	}
	
	
}