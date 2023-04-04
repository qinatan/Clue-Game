package tests;

import org.junit.jupiter.api.Test; // Must always include this to declare Junit5
import java.util.ArrayList;
import java.util.Set;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
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
		//Assert.fail(); 
		
		// Tests that CPUPlayer selects randomly if no rooms in seenList
		// get the second player from playerList
		computerPlayer CPUPlayer = (computerPlayer) board.getPlayerList().get(1);
		int row = 10;
		int col = 8;
		// get the second player's start location as a known board cell
		BoardCell CPUstartLocation = board.getCell(row, col);
		board.movePlayer(10, 8, CPUPlayer); 
		//computerPlayer CPUPlayer = new computerPlayer("TestOne", "RED", "10", "8");
		// find target list rolling 2 from start location -- target list contains no
		// room
		// we do not which specific location will be selected -- but test to make sure
		// it is one of the location in target list
		board.findAllTargets(CPUstartLocation, 2);
		//System.out.println("Target List = ");
		//System.out.println(board.getTargetList().toString());
		Set<BoardCell> CPUTargetList = board.getTargetList(); 
		BoardCell targetLocation = CPUPlayer.targetSelection(CPUTargetList);
		

		boolean firstLocation = false;
		boolean secondLocation = false;
		boolean thirdLocation = false;
		boolean forthLocation = false;
		boolean fifthLocation = false;
		for (int i = 0; i < 100; i++) {
			targetLocation = CPUPlayer.targetSelection(CPUTargetList);
			
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

		
		// #2 Test whether the player always selects a room that it has not seen from targets
		board.movePlayer(11, 5, board.getPlayerList().get(1)); 
		BoardCell secondStartLocation = board.getCell(11, 5);
		board.findAllTargets(secondStartLocation, 2);
		firstLocation = false;
		secondLocation = false;
		thirdLocation = false;
		forthLocation = false;
		fifthLocation = false;
		for (int i = 0; i < 100; i++) {
			targetLocation = CPUPlayer.targetSelection(CPUTargetList);
			
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
		
		
		// #3 Test that all targets are randomly chosen
		CPUPlayer.addToSeenMap(CardType.ROOM, new Card(CardType.ROOM, "Garage"));																// should have a random component so this test
																		
		
		// Test for if the room is seen go to a random location 
		// Move the player to 11, 5 
		board.movePlayer(11, 5, board.getPlayerList().get(1)); 
		firstLocation = false;
		secondLocation = false;
		thirdLocation = false;
		forthLocation = false;
		fifthLocation = false;
		for (int i = 0; i < 60; i++) {
			targetLocation = CPUPlayer.targetSelection(CPUTargetList);
			
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
		
		for (int i = 0; i < 100; i++) {
			ArrayList<Card> multiSuggestion = NoFaceCpuPlayer.makeSuggestion();

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
