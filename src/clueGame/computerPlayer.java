package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class computerPlayer extends Player {

	public computerPlayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);
	}
	
	@Override
	public void updateHand (Card card) {
		hand.add(card); 
	}
	
	public ArrayList<Card> makeSuggestion() {
		// Debug Printing all possible suggestions
		System.out.println("Printing seenMap");
		seenMap.forEach((key, value) -> System.out.println(key + ":" + value));
		
		ArrayList <Card> finalSuggestion = new ArrayList<Card>();
		HashMap<CardType, ArrayList<Card>> possibleSuggestions = new HashMap<CardType, ArrayList<Card>>();
		// pick this players current room
		possibleSuggestions.computeIfAbsent(CardType.ROOM, k -> new ArrayList<>()).add(this.currRoom);
		//suggestion.put(CardType.ROOM, this.currRoom);
		Board board = Board.getInstance();
		
		// pick weapon that has not been seen and is not in their hand
		for (Card card : board.weaponDeck) {
			ArrayList<Card> seenWeapons = seenMap.get(CardType.WEAPON);
			System.out.println(hand.size());
			if (seenWeapons.contains(card)){
				System.out.println("seenWeapons contains " + card.toString());
			}
			if (hand.contains(card)){
				System.out.println("hand contains " + card.toString());
			}
			
			if (seenWeapons.contains(card) && hand.contains(card)) {
						System.out.println("Adding " + card.toString() + " to possibleSuggestions.");
						possibleSuggestions.computeIfAbsent(CardType.WEAPON, k -> new ArrayList<>()).add(card);
					}
			}
	
		
		
		// pick person that hasnt been seen and isnt in their hand
		for (Card card : board.peopleDeck) {
			ArrayList<Card> seenPeople = seenMap.get(CardType.PERSON);
				if(seenPeople.contains(card)) {
					continue;
				}
				else if (hand.contains(card)) {
					continue;
				}
				else {
					// oOoO fancy lambda expression
					possibleSuggestions.computeIfAbsent(CardType.PERSON, k -> new ArrayList<>()).add(card);
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
		
		return finalSuggestion;
	}
	
}
