package tests;

import java.util.Map;
import org.junit.Assert.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
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
	
	//Test player Disproves suggestion
	@Test
	public void disprovesSuggestion () {
		Solution solution = Board.getSolution();
		
		Card correctRoom = solution.getRoom();
		Card correctPerson = solution.getPerson(); 
		Card correctWeapon = solution.getWeapon();

		Card wrongRoom = board.getPlayerList().get(0).getHand().get(0);
		Card wrongPerson = board.getPlayerList().get(0).getHand().get(0);
		Card wrongWeapon = board.getPlayerList().get(0).getHand().get(0);
		
		
		Card suggestedRoom = board.getPlayerList().get(0).getHand().get(0);
		Card suggestedPerson = board.getPlayerList().get(0).getHand().get(0);
		Card suggestedWeapon = board.getPlayerList().get(0).getHand().get(0);
		
		
		boolean isBadSuggestion = board.getPlayerList().get(0).makeSuggestion(suggestedRoom, correctPerson, correctWeapon) ; 
		Assert.assertTrue(isBadSuggestion) ; 
	}
	
	
//Test accusation for both CPU and human
	@Test
	public void handleSuggestionMade () {
		
	}

	@Test
	public void CPUSuggestion () {
		
	}
	
	@Test
	public void CPUSelectTarget () {
		
	}


	

}
