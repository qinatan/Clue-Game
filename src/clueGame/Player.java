package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Random;

public abstract class Player {
	private String name;
	private Color playerColor;
	private String color;
	private int row, col;
	protected ArrayList<Card> hand = new ArrayList<Card>();
	protected Map<CardType, ArrayList <Card>> seenMap = new HashMap<CardType, ArrayList <Card>>();
	public Card currRoom; // TODO: add update currRoom every time you move

	public Player(String playerName, String playerColor, String row, String col) {
		this.name = playerName;
		this.row = Integer.parseInt(row);
		this.col = Integer.parseInt(col);
		this.color = playerColor;
		switch (playerColor) {
		case "Red":
			this.playerColor = new Color(255, 0, 0);
			break;
		case "Pink":
			this.playerColor = new Color(255, 192, 203);
			break;
		case "Green":
			this.playerColor = new Color(0, 255, 0);
			break;
		case "Black":
			this.playerColor = new Color(0, 0, 0);
			break;
		case "Blue":
			this.playerColor = new Color(0, 0, 255);
			break;
		case "Yellow":
			this.playerColor = new Color(255, 255, 0);
			break;
		}
	}

	// ******** getters & setters  ********* // 
	public String getPlayerName() {
		return this.name;
	}

	public Color getPlayerColor() {
		return this.playerColor;
	}

	public String getPlayerColorString() {
		return color;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public int getPlayerRow() {
		return row;
	}

	public int getPlayerCol() {
		return col;
	}
	
	// *********************************** //

	// Abstract Methods
	protected abstract void updateHand(Card card);
	
	public abstract ArrayList<Card> makeSuggestion();

	// *********** Other Methods ********* // 
	public void printHand() {
		for (int i = 0; i < hand.size(); i++) {
			System.out.println(hand.get(i));
		}
	}

	// **** Other Methods ***************** // 
	public Card disproveSuggestion(Card suggestedCard1, Card suggestedCard2, Card suggestedCard3) {

		ArrayList<Card> matchingCard = new ArrayList<Card>();
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i) == suggestedCard1) {
				matchingCard.add(suggestedCard1);
			}
			if (hand.get(i) == suggestedCard2)

			{
				matchingCard.add(suggestedCard2);

			}
			if (hand.get(i) == suggestedCard3) {
				matchingCard.add(suggestedCard3);
			}
		}
		Random random = new Random();
		if (matchingCard.size() > 0) {
			int randomMatching = random.nextInt(matchingCard.size());
			return matchingCard.get(randomMatching);
		}
		return null;
	}
	
	public void addToSeenMap(CardType cardType, Card seenCard) {
		this.seenMap.computeIfAbsent(cardType, k -> new ArrayList<>()).add(seenCard);
	}
	
	// ********** TEST METHODS **************** //
	// These methods should only be used to facilitate unit testing and never run in prod code //
	
	public void setPlayerLocation(int row, int col) {
		this.row = row;
		this.col = col;
	}


}
