/**
 * computerPlayer is an extension of abstract class of Players
 * It is responsible for for actions of AI players including making suggestion and select a target from target list when making a move 
 * @author: Mike Eact 
 * @author: John Omalley 
 * @author: Qina Tan 
 * @start Date: 8/3/2025
 * @collaborator: none 
 * @resources: none 
 * 
 * 
 */

package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Computerplayer extends Player {
	Board board = Board.getInstance();
	Boolean isAccusation = false; //initiate to false
	ArrayList<Card> potentialAccusation = new ArrayList<Card>(); 
	public Computerplayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);
	}

	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	// I don't like this name, this is just a setter right? 
	public void accusationMakeReady() {
		this.isAccusation = true; 
	}
	
	
	public Boolean getIsAccusation(){
		return isAccusation; 
	}

	public ArrayList<Card> makeSuggestion() {

		ArrayList<Card> finalSuggestion = new ArrayList<>();
		HashMap<CardType, ArrayList<Card>> possibleSuggestions = new HashMap<CardType, ArrayList<Card>>();

		// pick this players current room
		possibleSuggestions.computeIfAbsent(CardType.ROOM, k -> new ArrayList<>()).add(this.getCurrRoomCard());
		// adds the room we're in to the possible suggestions

		for (Card weaponCard : board.getWeaponDeck()) { // Loop through all the weapons we have

			ArrayList<Card> seenWeapons = seenMap.get(CardType.WEAPON);
			if (seenWeapons == null || !seenWeapons.contains(weaponCard)) {
				if (!hand.contains(weaponCard)) {
					possibleSuggestions.computeIfAbsent(CardType.WEAPON, k -> new ArrayList<>()).add(weaponCard);
				}
			}
		}

		for (Card personCard : board.getPeopleDeck()) { // Loop through all the weapons we have
			ArrayList<Card> seenPeople = seenMap.get(CardType.PERSON);
			if (seenPeople == null || !seenPeople.contains(personCard)) {
				if (!hand.contains(personCard)) {
					possibleSuggestions.computeIfAbsent(CardType.PERSON, k -> new ArrayList<>()).add(personCard);
				}
			}
		}

		// Check if more than 3 total elements, if yes, pick randomly amongst items in
		// the arrayList
		// populate finalSuggestion and return it
		Random rand = new Random();
		for (ArrayList<Card> cardList : possibleSuggestions.values()) {
			if (cardList.size() > 1) {
				Card randomCard = cardList.get(rand.nextInt(cardList.size()));
				finalSuggestion.add(randomCard);

			}
			// Else if only one possible card, add the card to finalSuggestion
			else if (cardList.size() == 1) {
				finalSuggestion.add(cardList.get(0));
			}
		}
		
		for(Card suggestedCard : finalSuggestion)
		{
			if (suggestedCard.getCardType() == CardType.PERSON)
			{
				for (Player player : Board.getPlayerList())
				{
					if (player.getPlayerName().equals(suggestedCard.getCardName()))
					{
						player.setPlayerLocation(board.getPlayersTurn().getPlayerRow(), board.getPlayersTurn().getPlayerCol());
						board.resetPlayersLocations();
					}
				}
			}
		}
 
		this.potentialAccusation = finalSuggestion; 
		return finalSuggestion;
	}

	// AI player select one target from targetList to move toward
	// return the first room found in targetList if room is not in seenMap, or
	// return random target location if no room exit or room is already in seenMap
	Random randomTarget = new Random();

	public BoardCell targetSelection(Set<BoardCell> targetList) {
		Board board = Board.getInstance();
		ArrayList<BoardCell> targets = new ArrayList<BoardCell>(targetList);
		ArrayList<String> cardNames = new ArrayList<String>();

		ArrayList<Card> roomCards = seenMap.get(CardType.ROOM);
		if (roomCards != null) {
			for (int j = 0; j < roomCards.size(); j++) {
				cardNames.add(roomCards.get(j).getCardName());
			}
		}
		
		for (BoardCell target : targetList) {
			// TODO: Check if that room is in the players seenList, if it is, don't enter
			if (target.isRoom()) {
				// get the cellSymbol in order to get the matching room from roomMap
				Character cellSymbol = target.getCellSymbol();
				String roomName = board.getRoomMap().get(cellSymbol).getName();

				// check if the room is in the seenMap
				// No Room Card was seen: we can return this room
				if (!cardNames.contains(roomName)) {
					return target;

				}
			}
		}

		// No room in target list or room in target list has been seen, return random
		// BoardCell from target list
		if (targets.size() <= 0) {
			return null; 
		}
		int randomNumber = randomTarget.nextInt(targets.size());
		return targets.get(randomNumber);

	}

	/**
	 * This method checks to see if the computer player has seen enough to make an
	 * accusation this method is untested.
	 * TODO: I don't think this code ever runs - Mike... Also, unseenWeapons, People, and Rooms will never be 0. 
	 * TODO: I'm considering deleting this method so that AI can only make accusations if they have made a suggestion 
	 * TODO: I'm not sure how to approach this method anymore because they don't have their hand cards in their seen list
	 * which is not disproved
	 * @return
	 */
	public boolean canMakeAccusation() {
		int unseenWeapons = 1;
		int unseenPeople = 1;
		int unseenRooms = 1;

		for (Card weaponCard : board.getWeaponDeck()) {
			if (this.seenMap.containsKey(weaponCard.getCardType()));
			else {
				unseenWeapons++;
			}
		}

		for (Card playerCard : board.getPeopleDeck()) {
			if (this.seenMap.containsKey(playerCard.getCardType()));
			else {
				unseenPeople++;
			}
		}

		for (Card roomCard : board.getRoomDeck()) {
			if (this.seenMap.containsKey(roomCard.getCardType()));
			else {
				unseenRooms++;
			}
		}

		if (unseenWeapons > 1 || unseenPeople > 1 || unseenRooms > 1) {
			return false;
		} else {
			return true;
		}

	}

	
	/**
	 * This is the method that figures out which cards the computer will return as its accusation
	 * This is an untested method
	 */
	@Override
	public ArrayList<Card> makeAccusation() {
		return this.potentialAccusation; 
	}

}
