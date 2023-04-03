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
		int row = 10;
		int col = 8;
		// System.out.println(row + " " + col);
		// get the second player's start location as a known board cell
		BoardCell startLocation = board.getCell(row, col);
		board.movePlayer(10, 8, board.getPlayerList().get(1)); 
		// find target list rolling 2 from start location -- target list contains no
		// room
		// we do not which specific location will be selected -- but test to make sure
		// it is one of the location in target list
		board.findAllTargets(startLocation, 2);
		Set<BoardCell> CPUTargetList = board.getTargetList();
		BoardCell targetLocation = CPUPlayer.targetSelection();

		boolean firstLocation = false;
		boolean secondLocation = false;
		boolean thirdLocation = false;
		boolean forthLocation = false;
		boolean fifthLocation = false;
		for (int i = 0; i < 20; i++) {
			targetLocation = CPUPlayer.targetSelection();
			
			if (targetLocation.getRowNum() == 8 && targetLocation.getColumnNum() == 8) {
				firstLocation = true;
			}

			if (targetLocation.getRowNum() == 11 && targetLocation.getColumnNum() == 7) {
				secondLocation = true;
			}

			if (targetLocation.getRowNum() == 10 && targetLocation.getColumnNum() == 6) {
				thirdLocation = true;
			}

			if (targetLocation.getRowNum() == 9 && targetLocation.getColumnNum() == 7) {
				forthLocation = true;
			}

			if (targetLocation.getRowNum() == 12 && targetLocation.getColumnNum() == 8) {
				fifthLocation = true;
			}
		}

		Assert.assertTrue(firstLocation);
		Assert.assertTrue(secondLocation);
		Assert.assertTrue(thirdLocation);
		Assert.assertTrue(forthLocation);
		Assert.assertTrue(fifthLocation);
		Assert.assertTrue(CPUTargetList.contains(targetLocation));

		// target list contins one room "Patio" by rolling 4 at startLocation
		// room is not at seenMap - test to return the room as target selected
		board.findAllTargets(startLocation, 4);
		targetLocation = CPUPlayer.targetSelection();
		Assert.assertEquals(board.getCell(2, 19), targetLocation);
		Set<BoardCell> targets = board.getTargetList();

		//Test for if the room is not seen go into it
		board.movePlayer(11, 5, board.getPlayerList().get(1)); 
		firstLocation = false;
		secondLocation = false;
		thirdLocation = false;
		forthLocation = false;
		fifthLocation = false;
		for (int i = 0; i < 20; i++) {
			targetLocation = CPUPlayer.targetSelection();
			
			if (targetLocation.getRowNum() == 11 && targetLocation.getColumnNum() == 3) {
				firstLocation = true;
			}

			if (targetLocation.getRowNum() == 10 && targetLocation.getColumnNum() == 4) {
				secondLocation = true;
			}

			if (targetLocation.getRowNum() == 10 && targetLocation.getColumnNum() == 6) {
				thirdLocation = true;
			}

			if (targetLocation.getRowNum() == 11 && targetLocation.getColumnNum() == 7) {
				forthLocation = true;
			}

			if (targetLocation.getRowNum() == 15 && targetLocation.getColumnNum() == 3) {
				fifthLocation = true;
			}
		}

		Assert.assertFalse(firstLocation);
		Assert.assertFalse(secondLocation);
		Assert.assertFalse(thirdLocation);
		Assert.assertFalse(forthLocation);
		Assert.assertTrue(fifthLocation);
		
		
		CPUPlayer.addToSeenMap(CardType.ROOM, new Card(CardType.ROOM, "Garage"));																// should have a random component so this test
																		
		
		//Test for if the room is seen go to a random location 
		// TODO:Move the player to 11, 5 
		board.movePlayer(11, 5, board.getPlayerList().get(1)); 
		firstLocation = false;
		secondLocation = false;
		thirdLocation = false;
		forthLocation = false;
		fifthLocation = false;
		for (int i = 0; i < 20; i++) {
			targetLocation = CPUPlayer.targetSelection();
			
			if (targetLocation.getRowNum() == 11 && targetLocation.getColumnNum() == 3) {
				firstLocation = true;
			}

			if (targetLocation.getRowNum() == 10 && targetLocation.getColumnNum() == 4) {
				secondLocation = true;
			}

			if (targetLocation.getRowNum() == 10 && targetLocation.getColumnNum() == 6) {
				thirdLocation = true;
			}

			if (targetLocation.getRowNum() == 11 && targetLocation.getColumnNum() == 7) {
				forthLocation = true;
			}

			if (targetLocation.getRowNum() == 15 && targetLocation.getColumnNum() == 3) {
				fifthLocation = true;
			}
		}

		Assert.assertTrue(firstLocation);
		Assert.assertTrue(secondLocation);
		Assert.assertTrue(thirdLocation);
		Assert.assertTrue(forthLocation);
		Assert.assertTrue(fifthLocation);
		

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

		// Not seen Weapon: Extension Cord
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Dog Bone"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Broken DVD"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Wrench"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Venus Fly Trap"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Dumbbell"));

		// Not seen person: Chi O
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

		// This prints the suggestions made
//		for (Card card : suggestion) {
//			System.out.println(card.toString());
//		}

		// This test that the CPU suggests the not seen Person
		Assert.assertTrue(suggestion.contains(new Card(CardType.PERSON, "Chihiro Ogino")));

		// This test that the CPU suggests the not seen Weapon
		Assert.assertTrue(suggestion.contains(new Card(CardType.WEAPON, "Extension Cord")));

		// This tests for if the suggestion matches the current room
		Assert.assertTrue(suggestion.contains(new Card(CardType.ROOM, "Plant Room")));

		// Section for testing if for multiple unseen cards
		// Creates a new player
		Player NoFaceCpuPlayer = board.getPlayerList().get(3);
		NoFaceCpuPlayer.setPlayerLocation(24, 10); // row 24, col 10 = plant room center
		NoFaceCpuPlayer.currRoom = currRoom; // sets currRoom to plant room

		// Not seen Weapon: Extension Cord, Dog Bone
		NoFaceCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Broken DVD"));
		NoFaceCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Wrench"));
		NoFaceCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Venus Fly Trap"));
		NoFaceCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Dumbbell"));

		// Not seen person: Chi O, Yubaba
		NoFaceCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Zeniba"));
		NoFaceCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "No-Face"));
		NoFaceCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Boh"));
		NoFaceCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "River Spirit"));

		boolean seenFirstWeapon = false;
		boolean seenSecondWeapon = false;

		boolean seenFirstPerson = false;
		boolean seenSecondPerson = false;
		
		for (int i = 0; i < 10; i++) {
			ArrayList<Card> multiSuggestion = NoFaceCpuPlayer.makeSuggestion();

			System.out.println(multiSuggestion) ; 
			// suggestion.contains(new Card(CardType.PERSON, "Chihiro Ogino") ;

			if (multiSuggestion.contains(new Card(CardType.WEAPON, "Extension Cord"))) {
				seenFirstWeapon = true;
			}

			if (multiSuggestion.contains(new Card(CardType.WEAPON, "Dog Bone"))) {
				seenSecondWeapon = true;
			}

			if (multiSuggestion.contains(new Card(CardType.PERSON, "Chihiro Ogino"))) {
				seenFirstPerson = true;
			}

			if (multiSuggestion.contains(new Card(CardType.PERSON, "Yubaba"))) {
				seenSecondPerson = true;
			}

		}

		Assert.assertTrue(seenFirstWeapon);
		Assert.assertTrue(seenSecondWeapon);
		Assert.assertTrue(seenFirstPerson);
		Assert.assertTrue(seenSecondPerson);

	}
}
