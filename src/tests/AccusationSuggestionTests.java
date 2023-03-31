package tests;

import java.util.Map;
import org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;

import org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.DoorDirection;
import clueGame.Player;
import clueGame.Room;
import clueGame.Board;
import clueGame.CardType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import clueGame.Board;
import clueGame.BoardCell;


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
	public void testSolution () {
		//Solution solution = Board.getSolution();
		//Card correctRoom = solution.getRoom();
		//Card correctPerson = solution.getPerson();
		//Card correctWeapon = solution.getWeapon();
		
		Assert.fail();
		// Correct solution
		//Assert.assertTrue(board.checkAccusation(correctRoom, correctPerson, correctWeapon));
		
		// Solution with wrong person
		// solution with wrong weapon
		// Solution with wrong room
	}
	
	// Disproven suggestion tests
}
