package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Assert.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;
import clueGame.computerPlayer;

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
		Assert.assertFalse(board.checkAccusation(correctRoom, wrongPerson, correctWeapon)); // Solution with wrong person
		Assert.assertFalse(board.checkAccusation(correctRoom, correctPerson, wrongWeapon)); // solution with wrong weapon
		Assert.assertFalse(board.checkAccusation(wrongRoom, correctPerson, correctWeapon)); // solution with wrong weapon
	}

	// Test player Disproves suggestion -- PASSED -- May need to cleanup code 
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

		// Expected: Broken DVD
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

	//Test handleSuggestion() for both CPU and human --DONE 
	@Test
	public void handleSuggestionTest() {
		Solution solution = Board.getSolution();

		Card solutionRoom = solution.getRoom();
		Card solutionPerson = solution.getPerson();
		Card solutionWeapon = solution.getWeapon();
		
		Player testingPlayer1 = board.getPlayer(0);  // Chihiro Ogino == human player
		Player testingPlayer2 = board.getPlayer(1);
		Player testingPlayer3 = board.getPlayer(2); 
		ArrayList<Card> testingHand1 = testingPlayer1.getHand(); 
		ArrayList<Card> testingHand2 = testingPlayer2.getHand(); 
		 
		Card suggestedRoom1 = testingHand1.get(0); //Exercise Room 
		Card suggestedPerson1 = null; //player1 has another room: Garage 
		Card suggestedWeapon1 = testingHand1.get(2); //Extension Cord 
		
		Card suggestedRoom2 = testingHand2.get(0); // Dog House 
		Card suggestedPerson2 = testingHand2.get(1); //Yubaba 
		Card suggestedWeapon2 = null; // player2 has another room: Basement
		
		

		// Tests that no one can disprove the solution
		Card disprovalCard = board.handleSuggestion(solutionRoom, solutionPerson, solutionWeapon, testingPlayer1); 
		Assert.assertEquals(null, disprovalCard ); 
		
		//Suggestion only suggesting player can disprove returns null
		disprovalCard  = board.handleSuggestion(solutionRoom, solutionPerson, suggestedWeapon1, testingPlayer1);
		Assert.assertEquals(null, disprovalCard); 
		
		//Query in order. No other players can show other disproval cards once a disproval card is shown from one player 
		disprovalCard  = board.handleSuggestion(suggestedRoom1, suggestedPerson2, solutionWeapon, testingPlayer3);
		Assert.assertEquals(suggestedRoom1, disprovalCard ); 
				
	}

	@SuppressWarnings("null")
	//@Test
	public void CPUSuggestion() {
		// Room matches current location
		Player YubabaCpuPlayer = board.getPlayerList().get(1); 
		// Set players location to plant room 
		YubabaCpuPlayer.setPlayerLocation(24, 10); // row 24, col 10 = plant room center
		Card currRoom = new Card(CardType.ROOM, "Plant Room");
		YubabaCpuPlayer.currRoom = currRoom;  // sets currRoom to plant room
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
		// Expect Yubaba to guess: Person: Yubaba, Weapon: Extension Cord, Room: Plant Room
		ArrayList<Card> suggestion = YubabaCpuPlayer.makeSuggestion();
		Assert.assertTrue(suggestion.size() == 3);
		
		for (Card card: suggestion) {
			System.out.println(card.toString());
		}
		Assert.assertTrue(suggestion.contains(new Card(CardType.PERSON, "Yubaba")));
		Assert.assertTrue(suggestion.contains(new Card(CardType.WEAPON, "Extension Cord")));
		Assert.assertTrue(suggestion.contains(new Card(CardType.ROOM, "Plant Room")));
		
		
		// TODO: IDK how to do this if multiple weapons not seen, one of them is randomly selected
		// TODO: IDK how to do this if multiple persons not seen, one of them is randomly selected
	}

	//DONE 
	@Test 
	public void CPUSelectTarget() {
		//get the second player from playerList
		computerPlayer CPUPlayer = (computerPlayer) board.getPlayerList().get(1);
		int row = CPUPlayer.getPlayerRow(); 
		int col = CPUPlayer.getPlayerCol();
		//get the second player's start location 
		BoardCell startLocation = board.getCell(row, col); 
		
		//find target list rolling 3 from start location -- target list contains no room 
		// we do not which specific location will be selected -- but test to make sure it is one of the location in target list 
		board.findAllTargets(startLocation, 3); 
		Set<BoardCell> CPUTargetList = board.getTargetList(); 
		BoardCell targetLocation = CPUPlayer.targetSelection(); 
		Assert.assertTrue(CPUTargetList.contains(targetLocation));
		
		//target list contins one room "Patio" by rolling 4 at startLocation 
		//room is not at seenMap - test to return the room as target selected
		board.findAllTargets(startLocation, 4); 
		targetLocation = CPUPlayer.targetSelection(); 	
		Assert.assertEquals(board.getCell(2,19), targetLocation);
		Set<BoardCell> targets = board.getTargetList(); 
	
		
		//add room to seenMap
		//test selected target not equal to room   
		//test that selected target to make sure it is one of the location in target list 
		Card seenCard = null; 
		ArrayList<Card> roomDeck = board.getRoomDeck(); 
		for (int i = 0; i < roomDeck.size(); i++)
		{
			//System.out.println(roomDeck.get(i)); 
			String cardName = roomDeck.get(i).getCardName(); 
			
			if (cardName.equals("Patio"))
			{
				seenCard = roomDeck.get(i); 
				
			}
		}
	
		CPUPlayer.addToSeenMap(CardType.ROOM, seenCard);
		targetLocation = CPUPlayer.targetSelection(); 
		Assert.assertNotEquals(board.getCell(2,19), targetLocation);
		Assert.assertTrue(CPUTargetList.contains(targetLocation));
		
	}

}
