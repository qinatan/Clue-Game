/**
 * Player Class contains attribute of a Player (Human and AI player), includes name, playColor, row, col(start location)
 * card hands' of a player and a list of seen cards. 
 * 
 * Player class is responsible for all actions of a player including make suggestion, 
 * disprove suggestion by showing a matching card to a suggested card by another player
 * 
 * @author: Mike Eact 
 * @author: John Omalley 
 * @author: Qina Tan 
 * @start Date: 8/3/2025
 * @collaborator: none 
 * @resources: none 
 */
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
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
	private int row, col; // TODO: These do virtually the same thing
	private BoardCell currCell; // TODO: These do virtually the same thing
	private Room currRoom;
	private int rollNum;
	private int drawOffset = 0; // Players should be drawn a little to the right if there is already a player in
								// the current room center

	// check for both AI and human player
	private boolean hasPlayerMoved = false;
	private boolean hasPlayerACC = false;

	protected ArrayList<Card> hand = new ArrayList<Card>();
	protected Map<CardType, ArrayList<Card>> seenMap = new HashMap<CardType, ArrayList<Card>>();

	protected Player(String playerName, String playerColor, String row, String col) {
		this.name = playerName;
		this.row = Integer.parseInt(row);
		this.col = Integer.parseInt(col);
		currCell = new BoardCell(this.row, this.col);
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
		case "Teal":
			this.playerColor = new Color(0, 175, 206);
			break;
		case "Orange":
			this.playerColor = new Color(255, 108, 0);
			break;
		case "Yellow":
			this.playerColor = new Color(255, 255, 0);
			break;
		}
	}

	// ************ THESE were made to deal with game flow *********
	// TODO: these need to be moved
	public void setHasPlayerMoved(boolean ACC) {
		hasPlayerMoved = ACC;
	}

	// TODO: This needs to be moved
	public void setHasPlayerACC(boolean ACC) {
		hasPlayerACC = ACC;
	}

	public boolean getIsHasPlayerMoved() {
		return hasPlayerMoved;
	}

	public boolean getIsHasPlayerACC() {
		return hasPlayerACC;
	}

	// ******** getters & setters ********* //
	public BoardCell getCurrCell() {
		return currCell;
	}

	public void setCurrCell(BoardCell currCell) {
		this.currCell = currCell;
	}

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

	public Map<CardType, ArrayList<Card>> getSeenMap() {
		return seenMap;
	}

	public int getDrawOffset() {
		return drawOffset;
	}

	public void setDrawOffset(int drawOffset) {
		this.drawOffset = drawOffset;
	}

	public Room getCurrRoom() {
		return currRoom;
	}

	public void setCurrRoom(Room currRoom) {
		this.currRoom = currRoom;
	}

	public Card getCurrRoomCard() {
		Card roomCard = new Card(CardType.ROOM, currRoom.getName());
		return roomCard;
	}

	public void setCurrRoomCard(Card room) {
		this.currRoom = new Room(room.getCardName());
	}

	// *********************************** //

	// Abstract Methods
	protected abstract void updateHand(Card card);

	public abstract ArrayList<Card> makeSuggestion();

	// *********** Other Methods ********* //

	public void drawPlayer(int width, int height, Graphics g) {
		int horOffset = width * col + drawOffset;
		int vertOffset = height * row;
		g.setColor(playerColor);
		g.drawOval(horOffset, vertOffset, width, height);
		g.fillOval(horOffset, vertOffset, width, height);
	}

	public int getRollNum() {
		return rollNum;
	}

	public void setRollNum() {
		Random randomRoll = new Random();
		this.rollNum = randomRoll.nextInt(6) + 1;

	}

	public void printHand() {
		for (int i = 0; i < hand.size(); i++) {
			System.out.println(hand.get(i));
		}
	}

	// every player check if they have a card in hand to disprove a suggestedCard
	// return null if they do not have
	// return disapproval card if they have one
	// randomly pick one disapproval card if they have more than one
	public Card disproveSuggestion(Card suggestedCard1, Card suggestedCard2, Card suggestedCard3) {

		ArrayList<Card> matchingCard = new ArrayList<Card>();
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).equals(suggestedCard1)) {
				matchingCard.add(suggestedCard1);
			}
			if (hand.get(i).equals(suggestedCard2))

			{
				matchingCard.add(suggestedCard2);

			}
			if (hand.get(i).equals(suggestedCard3)) {
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
		if (seenMap.containsKey(cardType)) {
			// Only add the card to the seenMap if it doesn't already exist
			if (!seenMap.get(cardType).contains(seenCard)) {
			seenMap.get(cardType).add(seenCard);
			}
		} else {
			ArrayList<Card> seenCards = new ArrayList<Card>();
			seenCards.add(seenCard);
			seenMap.put(cardType, seenCards);
		}

	}

	/**
	 * This is the method that moved the players to any location on the board
	 */
	public void setPlayerLocation(int row, int col) {
		this.row = row;
		this.col = col;
		Board board = Board.getInstance(); //TODO: why is this in the method and not just in the player class
		this.currCell = board.getCell(this.row, this.col);
		int playerInRoomCount = 0; //I moved this out of the if statement 
		setDrawOffset(0);
		if (Boolean.TRUE.equals(currCell.isRoomCenter())) {
			currRoom = board.getRoomMap().get(currCell.getCellSymbol());
			// check the number of player in a room
			for (Player player : board.getPlayerList()) {
				if (player.currCell == currCell) {
					playerInRoomCount++;
				}
			}
			setDrawOffset(15 * (playerInRoomCount -1));
			
		} else {
			setDrawOffset(0);
		}
		System.out.println(getDrawOffset() + " " + playerInRoomCount + " " + currCell.isRoom() + " " + this.getPlayerName()) ; 
	}
	


	// ********** TEST METHODS **************** //
	// These methods should only be used to facilitate unit testing and never run in
	// production code //

	@Override
	public String toString() {
		return "Player [name=" + name + "]";
	}

	public abstract boolean canMakeAccusation();

	public abstract ArrayList<Card> makeAccusation();

}
