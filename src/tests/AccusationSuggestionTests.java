package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

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

	

	
	

}
