package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class computerPlayer extends Player {
	ArrayList<Card> targetList = new ArrayList<Card>();

	public computerPlayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);
	}
	
	@Override
	public void updateHand (Card card) {
		hand.add(card); 
	}
	
	public ArrayList<Card> makeSuggestion() {
		Boolean handContains = false;
		Boolean seenWeaponsContains = false;
		Boolean seenPersonContains = false;
		
		// Debug Printing all possible suggestions
		System.out.println("Printing seenMap");
		seenMap.forEach((key, value) -> System.out.println(key + ":" + value));
		
		System.out.println("Printing hand");
		for (int i = 0 ; i < hand.size(); i++) {
			System.out.println(hand.get(i).toString());
		}
		ArrayList <Card> finalSuggestion = new ArrayList<Card>();
		HashMap<CardType, ArrayList<Card>> possibleSuggestions = new HashMap<CardType, ArrayList<Card>>();
		// pick this players current room
		possibleSuggestions.computeIfAbsent(CardType.ROOM, k -> new ArrayList<>()).add(this.currRoom);
		
		//suggestion.put(CardType.ROOM, this.currRoom);
		Board board = Board.getInstance();
		
		// pick weapon that has not been seen and is not in their hand
		for (Card weaponCard : board.weaponDeck) {
			ArrayList<Card> seenWeapons = seenMap.get(CardType.WEAPON);
			for (int i = 0; i < seenWeapons.size(); i++) {
				if (weaponCard.equals(seenWeapons.get(i))) {
					System.out.println("seenWeapons contains " + weaponCard.toString());
					seenWeaponsContains = true;
					break;
				}
				
				else {
					System.out.println(seenWeapons.get(i).toString() + " != " + weaponCard.toString());
					continue;
				}		
			}
			
			
			for (int j =0 ; j < hand.size(); j++) {
				if (weaponCard.equals(hand.get(j))) {
					handContains = true;
				}
			}
	
		
		if (!handContains && !seenWeaponsContains) {
			possibleSuggestions.computeIfAbsent(CardType.WEAPON, k -> new ArrayList<>()).add(weaponCard);
		}
			
	
		
		// TODO: Fix bugs with this part
		// pick person that hasnt been seen and isnt in their hand
		for (Card personCard : board.peopleDeck) {
			ArrayList<Card> seenPeople = seenMap.get(CardType.PERSON);
				if(seenPeople.contains(personCard)) {
					continue;
				}
				else if (hand.contains(personCard)) {
					continue;
				}
				else {
					// oOoO fancy lambda expression
					possibleSuggestions.computeIfAbsent(CardType.PERSON, k -> new ArrayList<>()).add(personCard);
				}
			}
		
		// Debug Printing all possible suggestions
		System.out.println("Printing possibleSuggestions");
		possibleSuggestions.forEach((key, value) -> System.out.println(key + ":" + value));
		
		// Check if more than 3 total elements, if yes, pick randomly amongst items in the arrayList
		// populate finalSuggestion and return it
		Random rand = new Random();
		for (ArrayList<Card> cardList: possibleSuggestions.values()) {
			if (cardList.size() > 1) {
				Card randomCard = cardList.get(rand.nextInt(cardList.size()));
				finalSuggestion.add(randomCard);
				
			}
			// Else if only one possible card, add the card to finalSuggestion
			else if (cardList.size() == 1) {
				finalSuggestion.add(cardList.get(0));
			}
		}
		
		
		}
		return finalSuggestion;	
	}
}


