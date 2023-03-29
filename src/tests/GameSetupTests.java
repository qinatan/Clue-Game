package tests;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.CardType;

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
	public void testNumberPlayers() {
		assertEquals(6, Board.getNumPlayers());
	}
	
	// We should have 21 cards in the deck initially (6 players + 6 weapons + 9 rooms)
	@Test
	public void testDeckSize() {
		assertEquals(21, Board.getNumCards());
	}
	
	// Solution contains 1 room, 1 player, 1 weapon
	@Test
	public void testSolution() {
		assertEquals(CardType.ROOM, Board.getSolution().getRoom().getCardType());
		assertEquals(CardType.WEAPON, Board.getSolution().getWeapon().getCardType());
		assertEquals(CardType.PERSON, Board.getSolution().getPerson().getCardType());
	}
	
	// Are all cards dealt?
	
	// Do all players have roughly the same number of cards?
	
	// same card should not be given to >1 player

}
