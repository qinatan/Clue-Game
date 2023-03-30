package tests;


import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clueGame.BoardCell;
import clueGame.Card;
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
		assertEquals(5, board.getNumHumanPlayers());
		assertEquals(1, board.getNumCompPlayers());
		
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
	
	//Test for if all the cards are dealt
	@Test
	public void testDealt()
	{
		board.dealtCard();// call dealt function
		assertEquals(0, board.getDealtDeckSize()); 
	}
	
	// Testing if every player has the same number of cards
	@Test
	public void testSameNumber()
	{
		int numPlayer = board.getNumPlayers(); 
		for (int i = 0; i < numPlayer; i++)
		{
			assertEquals(3, board.getPlayer(i).getHand().size());
		}
	}
	
	// Test that only one player has a card
	@Test
	public void uniqueCards ()
	{
		//TODO: This test need to initialize players and hands before running the tests
		//Card testCard = board.getPlayer(0).getHand().get(0);  
		boolean passTest = true ; 
		int thisCard = 0 ; 
		int numPlayer = board.getNumPlayers(); 
		
		if (board.getNumPlayers() == 0 ) {
			fail("Numplayers equals zero");
		}

		for (int i = 0; i < numPlayer; i++)
		{
			
			if (board.getPlayer(i).getHand().size() == 0 ) {
				fail("Player Hand Size is zero");
			}
			//TODO: change number of cards per player to be a variable
			for (int j = 0 ; j < 3 ; j ++) {
//				if (board.getPlayer(i).getHand().get(i) == testCard) {
//					thisCard ++ ; 
//				}
			}
			
		}
		//This tests to ensure that only one person has a given card
		assertEquals(1, thisCard) ; 
		
	}
}
