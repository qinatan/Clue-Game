package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.junit.Assert.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;

public class AccusationSuggestionTests {
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
	public void testSolution() {
		Solution solution = Board.getSolution();

		Card correctRoom = solution.getRoom();
		Card correctPerson = solution.getPerson();
		Card correctWeapon = solution.getWeapon();

		Card wrongRoom = board.getPlayerList().get(0).getHand().get(0);
		Card wrongPerson = board.getPlayerList().get(0).getHand().get(0);
		Card wrongWeapon = board.getPlayerList().get(0).getHand().get(0);

		// Correct solution
		Assert.assertTrue(board.checkAccusation(correctRoom, correctPerson, correctWeapon));

		// Solution with wrong person
		Assert.assertFalse(board.checkAccusation(correctRoom, wrongPerson, correctWeapon));

		// solution with wrong weapon
		Assert.assertFalse(board.checkAccusation(correctRoom, correctPerson, wrongWeapon));

		// Solution with wrong room
		Assert.assertFalse(board.checkAccusation(wrongRoom, correctPerson, correctWeapon));
	}

	// Test player Disproves suggestion
	@Test
	public void disprovesSuggestion() {
		// TODO: make sure that these are using the cards we think that they are using
		// TODO: add all of our tests into documentation on the google drive
		// TODO: try to refactor to remove the magic ints in the tests
		Solution solution = Board.getSolution();

		Card correctRoom = solution.getRoom(); // Expected: PlantRoom
		Card correctPerson = solution.getPerson(); // Expected: Dog Bone
		Card correctWeapon = solution.getWeapon(); // Expected: Chihiro Ogino

		Card suggestedRoom = board.getPlayerList().get(0).getHand().get(0); // Expected: Exercise Room
		Card suggestedPerson = board.getPlayerList().get(1).getHand().get(2); // Expected: Yubaba
		Card suggestedWeapon = board.getPlayerList().get(2).getHand().get(1); // Expected: Broken DVD

		// Section for testing that we can get one players matching card
		// Expected: ExersciseRoom
		Assert.assertEquals(suggestedRoom,
				board.getPlayerList().get(0).disproveSuggestion(suggestedRoom, correctPerson, correctWeapon));

		// Expected: Yubaba
		Assert.assertEquals(suggestedPerson,
				board.getPlayerList().get(1).disproveSuggestion(correctRoom, suggestedPerson, correctWeapon));

		// EXpected: Broken DVD
		Assert.assertEquals(suggestedWeapon,
				board.getPlayerList().get(2).disproveSuggestion(correctRoom, correctPerson, suggestedWeapon));

		// Section for testing if we can get both of players matching card
		suggestedRoom = board.getPlayerList().get(0).getHand().get(0); // Expected: Exercise Room
		suggestedPerson = board.getPlayerList().get(0).getHand().get(2); //Expected: Extension cord
		
		boolean foundFirstCard = false;
		boolean foundSecondCard = false;

		int numLoops = 0;

		while (foundFirstCard == false || foundSecondCard == false) {
			Card foundCard = board.getPlayerList().get(0).disproveSuggestion(suggestedRoom, suggestedPerson,
					correctWeapon);
			if (foundCard == suggestedRoom) {
				foundFirstCard = true;
			}

			if (foundCard == suggestedPerson) {
				foundSecondCard = true;
			}
			numLoops++;
			if (numLoops > 10) {
				break;
			}
		}

		Assert.assertTrue(foundFirstCard);
		Assert.assertTrue(foundSecondCard);

		// Section for testing if a player has no matching cards

		Assert.assertEquals(null,
				board.getPlayerList().get(0).disproveSuggestion(correctRoom, correctPerson, correctWeapon));

	}

//Test accusation for both CPU and human
	@Test
	public void handleSuggestionMade() {
		Assert.fail();

		Solution solution = Board.getSolution();

		Card correctRoom = solution.getRoom();
		Card correctPerson = solution.getPerson();
		Card correctWeapon = solution.getWeapon();
		Card suggestedRoom = board.getPlayerList().get(0).getHand().get(0);
		Card suggestedPerson = board.getPlayerList().get(0).getHand().get(0);
		Card suggestedWeapon = board.getPlayerList().get(0).getHand().get(0);

		// Suggestion no one can disprove returns null

		// Tests that no one can disprove the solution
		Assert.assertEquals(null,
				board.handleSuggestion(correctRoom, correctPerson, correctWeapon, board.getPlayerList().get(0)));

		// Suggestion only suggesting player can disprove returns null
		// TODO: This needs to be changed to make sure that the players card that we are
		// testing is correct
		Assert.assertEquals(null,
				board.handleSuggestion(suggestedRoom, suggestedPerson, suggestedWeapon, board.getPlayerList().get(0)));

		// Suggestion only human can disprove returns answer (i.e., card that disproves
		// suggestion)
		// TODO: This needs to be changed to make sure that we are testing the
		Assert.assertEquals(suggestedRoom,
				board.handleSuggestion(suggestedRoom, suggestedPerson, suggestedWeapon, board.getPlayerList().get(0)));

		// Suggestion that two players can disprove, correct player (based on starting
		// with next player in list) returns answer
		// TODO: This needs to be changed to make sure that we are testing the
		Assert.assertEquals(suggestedRoom,
				board.handleSuggestion(suggestedRoom, suggestedPerson, suggestedWeapon, board.getPlayerList().get(0)));

		Assert.assertEquals(suggestedRoom,
				board.handleSuggestion(suggestedRoom, suggestedPerson, suggestedWeapon, board.getPlayerList().get(0)));
	}

	@Test
	public void CPUSuggestion() {
		// Room matches current location
		Player YubabaCpuPlayer = board.getPlayerList().get(1); 
		// Set players location to plant room 
		YubabaCpuPlayer.setPlayerLocation(24, 10); // row 24, col 10 = plant room center
		Card currRoom = new Card(CardType.ROOM, "Plant Room");
		YubabaCpuPlayer.currRoom = currRoom;  // sets currRoom to plant room
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.ROOM, "Dog Bone"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.ROOM, "Broken DVD"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.ROOM, "Wrench"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.ROOM, "Venus Fly Trap"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.ROOM, "Dumbbell"));
		YubabaCpuPlayer.addToSeenMap(CardType.PERSON, new Card(CardType.PERSON, "Chihiro Ogino"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.ROOM, "Zeniba"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.ROOM, "No-Face"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.ROOM, "Boh"));
		YubabaCpuPlayer.addToSeenMap(CardType.WEAPON, new Card(CardType.ROOM, "River Spirit"));
		// Only Player Yubaba, and Weapon Extension Cord not in seen list
		// Expect Yubaba to guess: Person: Yubaba, Weapon: Extension Cord, Room: Plant Room
		ArrayList<Card> suggestion = YubabaCpuPlayer.makeSuggestion();
		ArrayList<Card> expectedSuggestion = null; 
		expectedSuggestion.add(new Card(CardType.PERSON, "Yubaba"));
		expectedSuggestion.add(new Card(CardType.WEAPON, "Extension Cord"));
		expectedSuggestion.add(new Card(CardType.ROOM, "Plant Room"));
		
		
		// if multiple weapons not seen, one of them is randomly selected
		// if multiple persons not seen, one of them is randomly selected
		Assert.fail();
	}

	@Test
	public void CPUSelectTarget() {
		Assert.fail();
	}

}
