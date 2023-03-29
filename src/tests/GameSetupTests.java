package tests;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;

public class GameSetupTests {
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout306.csv", "ClueSetup306.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}
	
	// Test for how many player objects.. There should always be 6 players
	@Test
	void testNumberPlayers() {
		assertEquals(2, Board.getNumPlayers());
	}
	// test for how many cards in deck
	
	// test for a workable solution
	
	// Are all cards dealt?
	
	// Do all players have roughly the same number of cards?
	
	// same card should not be given to >1 player

}
