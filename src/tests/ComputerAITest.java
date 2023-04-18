/**
 * 
 * This class tests the computer AI functions. 
 * @author: Mike Eack
 * @author: John Omalley 
 * @author: Qina Tan 
 * @start Date: 4/9/2023
 * @collaborator: none 
 * @resources: none  
 */

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
		
		// Tests that CPUPlayer selects randomly if no rooms in seenList
		// get the second player from playerList
		computerPlayer cpuPlayer = (computerPlayer) board.getPlayerList().get(1);
		int row = 10;
		int col = 8;
		// get the second player's start location as a known board cell
		BoardCell cpuStartLocation = board.getCell(row, col);
		board.movePlayer(10, 8, cpuPlayer); 

		// find target list rolling 2 from start location -- target list contains no
		// room
		// we do not which specific location will be selected -- but test to make sure
		// it is one of the location in target list
		board.findAllTargets(cpuStartLocation, 2);
		Set<BoardCell> cpuTargetList = board.getTargets(); 
		BoardCell targetLocation = cpuPlayer.targetSelection(cpuTargetList);
		

		boolean firstLocation = false;
		boolean secondLocation = false;
		boolean thirdLocation = false;
		boolean forthLocation = false;
		boolean fifthLocation = false;
		for (int i = 0; i < 100; i++) {
			targetLocation = cpuPlayer.targetSelection(cpuTargetList);
			
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
		Assert.assertTrue(cpuTargetList.contains(targetLocation));

		
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
			targetLocation = cpuPlayer.targetSelection(cpuTargetList);
			
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
		cpuPlayer.addToSeenMap(CardType.ROOM, new Card(CardType.ROOM, "Garage"));																// should have a random component so this test
																		
		
		// Test for if the room is seen go to a random location 
		// Move the player to 11, 5 
		board.movePlayer(11, 5, board.getPlayerList().get(1)); 
		firstLocation = false;
		secondLocation = false;
		thirdLocation = false;
		forthLocation = false;
		fifthLocation = false;
		for (int i = 0; i < 100; i++) {
			targetLocation = cpuPlayer.targetSelection(cpuTargetList);
			
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
	public void cpuSuggestion() {
		// Room matches current location
		Player yubabaCpuPlayer = board.getPlayerList().get(1);


		// Set players location to plant room
		yubabaCpuPlayer.setPlayerLocation(24, 10); // row 24, col 10 = plant room center
		Card currRoom = new Card(CardType.ROOM, "Plant Room");
		yubabaCpuPlayer.currRoom = currRoom; // sets currRoom to plant room

		// Not seen Weapon: Extension Cord
		yubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Dog Bone"));
		yubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Broken DVD"));
		yubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Wrench"));
		yubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Venus Fly Trap"));
		yubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Dumbbell"));

		// Not seen person: Chi O
		yubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Yubaba"));
		yubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Zeniba"));
		yubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "No-Face"));
		yubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Boh"));
		yubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "River Spirit"));

		// Only Player Yubaba, and Weapon Extension Cord not in seen list
		// Expect Yubaba to guess: Person: Yubaba, Weapon: Extension Cord, Room: Plant
		// Room
		ArrayList<Card> suggestion = yubabaCpuPlayer.makeSuggestion();
		Assert.assertTrue(suggestion.size() == 3);

		// This test that the CPU suggests the not seen Person
		Assert.assertTrue(suggestion.contains(new Card(CardType.PERSON, "Chihiro Ogino")));

		// This test that the CPU suggests the not seen Weapon
		Assert.assertTrue(suggestion.contains(new Card(CardType.WEAPON, "Extension Cord")));

		// This tests for if the suggestion matches the current room
		Assert.assertTrue(suggestion.contains(new Card(CardType.ROOM, "Plant Room")));

		// Section for testing if for multiple unseen cards
		// Creates a new player
		Player noFaceCpuPlayer = board.getPlayerList().get(3);
		noFaceCpuPlayer.setPlayerLocation(24, 10); // row 24, col 10 = plant room center
		noFaceCpuPlayer.currRoom = currRoom; // sets currRoom to plant room

		// Not seen Weapon: Extension Cord, Dog Bone
		noFaceCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Broken DVD"));
		noFaceCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Wrench"));
		noFaceCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Venus Fly Trap"));
		noFaceCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.WEAPON, "Dumbbell"));

		// Not seen person: Chi O, Yubaba
		noFaceCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Zeniba"));
		noFaceCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "No-Face"));
		noFaceCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Boh"));
		noFaceCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "River Spirit"));

		boolean seenFirstWeapon = false;
		boolean seenSecondWeapon = false;

		boolean seenFirstPerson = false;
		boolean seenSecondPerson = false;
		
		for (int i = 0; i < 100; i++) {
			ArrayList<Card> multiSuggestion = noFaceCpuPlayer.makeSuggestion();

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
