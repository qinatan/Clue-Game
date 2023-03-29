package tests;


import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;
import clueGame.Board;
import clueGame.CardType;

public class GameSetupTests {
	private static Board board = null;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		
	}
	
	// Test for how many player objects.. There should always be 6 players
	@Test
	public void testNumberPlayers() {
		assertEquals(6, board.getNumPlayers());
	}
	
	@Test
	public void testNumberRooms()
	{
		
		assertEquals(9, board.getNumRooms()); 
	}
	
	@Test 
	public void testNumberWeapons()
	{
		assertEquals(6, board.getNumWeapons()); 
	}
	
	// We should have 21 cards in the deck initially (6 players + 6 weapons + 9 rooms)
	@Test
	public void testDeckSize() {
		assertEquals(21, board.getNumCards());
	}
	
	// Solution contains 1 room, 1 player, 1 weapon
	@Test
	public void testSolution() {
		board.createSolution();
		assertEquals(CardType.ROOM, Board.getSolution().getRoom().getCardType());
		assertEquals(CardType.WEAPON, Board.getSolution().getWeapon().getCardType());
		assertEquals(CardType.PERSON, Board.getSolution().getPerson().getCardType());
	}
	
	// Are all cards dealt?
	@Test
	public void testDealt()
	{
		board.dealtCard();// call dealt function
		assertEquals(0, board.getDealtDeckSize()); 
	}
	
	// Do all players have roughly the same number of cards?
	@Test
	public void testSameNumber()
	{
		int numPlayer = board.getNumPlayers(); 
		for (int i = 0; i < numPlayer; i++)
		{
			assertEquals(3, board.getPlayer(i).getHand().size());
		}
	}
	
	// same card should not be given to >1 player
	

}
