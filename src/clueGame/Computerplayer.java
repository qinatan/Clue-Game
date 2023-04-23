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

	public Computerplayer(String playerName, String playerColor, String row, String col) {
		super(playerName, playerColor, row, col);
	}

	@Override
	public void updateHand(Card card) {
		hand.add(card);
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
		int randomNumber = randomTarget.nextInt(targets.size());
		return targets.get(randomNumber);

	}

	/**
	 * This method checks to see if the computer player has seen enough to make an
	 * accusation this method is untested.
	 * 
	 * @return
	 */
	public boolean canMakeAccusation() {
		int unseenWeapons = 0, unseenPeople = 0, unseenRooms = 0;

		for (Card weaponCard : board.getWeaponDeck()) {
			if (this.seenMap.containsKey(weaponCard)) {
				continue;
			} else {
				unseenWeapons++;
			}
		}

		for (Card playerCard : board.getPeopleDeck()) {
			if (this.seenMap.containsKey(playerCard)) {
				continue;
			} else {
				unseenPeople++;
			}
		}

		for (Card roomCard : board.getRoomDeck()) {
			if (this.seenMap.containsKey(roomCard)) {
				continue;
			} else {
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
		int unseenWeapons = 1, unseenPeople = 1, unseenRooms = 1;
		ArrayList<Card> accCards = new ArrayList<Card>();

		for (Card weaponCard : board.getWeaponDeck()) {
			if (this.seenMap.containsKey(weaponCard)) {
				continue;
			} else {
				accCards.add(weaponCard);
			}
		}

		for (Card playerCard : board.getPeopleDeck()) {
			if (this.seenMap.containsKey(playerCard)) {
				continue;
			} else {
				accCards.add(playerCard);
			}
		}

		for (Card roomCard : board.getRoomDeck()) {
			if (this.seenMap.containsKey(roomCard)) {
				continue;
			} else {
				accCards.add(roomCard);
			}
		}

		return accCards;
	}

}
