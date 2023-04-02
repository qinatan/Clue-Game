package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.computerPlayer;

class ComputerAITest {
	private static Board board;

	@BeforeAll
	public static void setUp() {

		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initializeForTest();
	}

	@Test
	public void CPUSelectTarget() {
		// get the second player from playerList
		computerPlayer CPUPlayer = (computerPlayer) board.getPlayerList().get(1);
		int row = CPUPlayer.getPlayerRow();
		int col = CPUPlayer.getPlayerCol();
		// get the second player's start location
		BoardCell startLocation = board.getCell(row, col);

		// find target list rolling 3 from start location -- target list contains no
		// room
		// we do not which specific location will be selected -- but test to make sure
		// it is one of the location in target list
		board.findAllTargets(startLocation, 3);
		Set<BoardCell> CPUTargetList = board.getTargetList();
		BoardCell targetLocation = CPUPlayer.targetSelection();
		Assert.assertTrue(CPUTargetList.contains(targetLocation));

		// target list contins one room "Patio" by rolling 4 at startLocation
		// room is not at seenMap - test to return the room as target selected
		board.findAllTargets(startLocation, 4);
		targetLocation = CPUPlayer.targetSelection();
		Assert.assertEquals(board.getCell(2, 19), targetLocation);
		Set<BoardCell> targets = board.getTargetList();

		// add room to seenMap
		// test selected target not equal to room
		// test that selected target to make sure it is one of the location in target
		// list
		Card seenCard = null;
		ArrayList<Card> roomDeck = board.getRoomDeck();
		for (int i = 0; i < roomDeck.size(); i++) {
			// System.out.println(roomDeck.get(i));
			String cardName = roomDeck.get(i).getCardName();

			if (cardName.equals("Patio")) {
				seenCard = roomDeck.get(i);

			}
		}

		CPUPlayer.addToSeenMap(CardType.ROOM, seenCard);
		targetLocation = CPUPlayer.targetSelection();
		Assert.assertNotEquals(board.getCell(2, 19), targetLocation);
		Assert.assertTrue(CPUTargetList.contains(targetLocation));

	}

	@SuppressWarnings("null")
	@Test
	public void CPUSuggestion() {
		// Room matches current location
		Player YubabaCpuPlayer = board.getPlayerList().get(1);
		// Set players location to plant room
		YubabaCpuPlayer.setPlayerLocation(24, 10); // row 24, col 10 = plant room center
		Card currRoom = new Card(CardType.ROOM, "Plant Room");
		YubabaCpuPlayer.currRoom = currRoom; // sets currRoom to plant room
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Dog Bone"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Broken DVD"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Wrench"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Venus Fly Trap"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Dumbbell"));
		YubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Yubaba"));
		YubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Zeniba"));
		YubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "No-Face"));
		YubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Boh"));
		YubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "River Spirit"));

		// Only Player Yubaba, and Weapon Extension Cord not in seen list
		// Expect Yubaba to guess: Person: Yubaba, Weapon: Extension Cord, Room: Plant
		// Room
		ArrayList<Card> suggestion = YubabaCpuPlayer.makeSuggestion();
		Assert.assertTrue(suggestion.size() == 3);

		for (Card card : suggestion) {
			System.out.println(card.toString());
		}
		Assert.assertTrue(suggestion.contains(new Card(CardType.PERSON, "Yubaba")));
		Assert.assertTrue(suggestion.contains(new Card(CardType.WEAPON, "Extension Cord")));
		Assert.assertTrue(suggestion.contains(new Card(CardType.ROOM, "Plant Room")));

		// TODO: IDK how to do this if multiple weapons not seen, one of them is
		// randomly selected
		// TODO: IDK how to do this if multiple persons not seen, one of them is
		// randomly selected
	}
}
