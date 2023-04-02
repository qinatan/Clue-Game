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
	public void accusationTest() {
		Solution solution = Board.getSolution();

		Card correctRoom = solution.getRoom();
		Card correctPerson = solution.getPerson();
		Card correctWeapon = solution.getWeapon();

		Card wrongRoom = board.getPlayerList().get(0).getHand().get(0);
		Card wrongPerson = board.getPlayerList().get(0).getHand().get(0);
		Card wrongWeapon = board.getPlayerList().get(0).getHand().get(0);

		// Correct solution
		Assert.assertTrue(board.checkAccusation(correctRoom, correctPerson, correctWeapon)); //Check for correct solution
		Assert.assertFalse(board.checkAccusation(correctRoom, wrongPerson, correctWeapon)); // Solution with wrong person
		Assert.assertFalse(board.checkAccusation(correctRoom, correctPerson, wrongWeapon)); // solution with wrong weapon
		Assert.assertFalse(board.checkAccusation(wrongRoom, correctPerson, correctWeapon)); // solution with wrong weapon
	}

	// Test player Disproves suggestion -- PASSED -- May need to cleanup code 
	@Test
	public void disprovesSuggestion() {
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

		// Section for testing if suggestion no one can disprove returns null
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
		
		Player humanPlayerForTesting = board.getPlayer(0);  // Chihiro Ogino == human player
		Player CPUPlayerForTesting0 = board.getPlayer(1); // CPU Player 
		Player CPUPlayerForTesting1 = board.getPlayer(2); //CPU Player
		ArrayList<Card> humanHand = humanPlayerForTesting.getHand(); 
		ArrayList<Card> CPUHand = CPUPlayerForTesting0.getHand(); 
		 
		Card humanSuggestedRoom = humanHand.get(0); //Exercise Room 
		Card humanSuggestedPerson = null; //player1 has another room: Garage 
		Card humanSuggestedWeapon = humanHand.get(2); //Extension Cord 
		
		Card CPU0SuggestedRoom = CPUHand.get(0); // Dog House 
		Card CPU0SuggesterPerson = CPUHand.get(1); //Yubaba 
		Card CPU0SuggestedRoom0 = null; // player2 has another room: Basement
		
		

		// Tests that no one can disprove the solution
		Card disprovalCard = board.handleSuggestion(solutionRoom, solutionPerson, solutionWeapon, humanPlayerForTesting); 
		Assert.assertEquals(null, disprovalCard ); 
		
		//Suggestion only suggesting player can disprove returns null
		disprovalCard  = board.handleSuggestion(solutionRoom, solutionPerson, humanSuggestedWeapon, humanPlayerForTesting);
		Assert.assertEquals(null, disprovalCard); 
		
		
		//Test for Suggestion only the human player can disprove returns card
		disprovalCard  = board.handleSuggestion(solutionRoom, solutionPerson, humanHand.get(2), humanPlayerForTesting);
		Assert.assertEquals(humanHand.get(2), disprovalCard); 
		
	
		//Query in order. No other players can show other disproval cards once a disproval card is shown from one player 
		disprovalCard  = board.handleSuggestion(humanSuggestedRoom, CPU0SuggesterPerson, solutionWeapon, CPUPlayerForTesting1);
		Assert.assertEquals(humanSuggestedRoom, disprovalCard ); 
				
	}

	

	
	

}
