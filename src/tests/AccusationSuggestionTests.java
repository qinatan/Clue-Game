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
		System.out.println("Here");
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void testSolution() {
		Solution solution = Board.getSolution();

		System.out.println(solution);
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

	// Disproven suggestion tests
}
