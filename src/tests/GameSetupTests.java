package tests;

import org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.DoorDirection;
import clueGame.Room;
import clueGame.Board;
import clueGame.CardType;

public class GameSetupTests {
	private static Board board ;

	//@BeforeEach
	@BeforeAll
	public static void  setUp() {
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
		Assert.assertEquals(6, board.getNumPlayers());
		Assert.assertEquals(1, board.getNumHumanPlayers());
		Assert.assertEquals(5, board.getNumCompPlayers());

	}

	@Test
	public void testNumberRooms() {

		Assert.assertEquals(9, board.getNumRooms());
	}

	@Test
	public void testNumberWeapons() {
		Assert.assertEquals(6, board.getNumWeapons());
	}

	// We should have 21 cards in the deck initially (6 players + 6 weapons + 9
	// rooms)
	@Test
	public void testDeckSize() {
		Assert.assertEquals(21, board.getNumCards());
	}

	// Solution contains 1 room, 1 player, 1 weapon
	@Test
	public void testSolution() {
	
		Assert.assertEquals(CardType.ROOM, Board.getSolution().getRoom().getCardType());
		Assert.assertEquals(CardType.WEAPON, Board.getSolution().getWeapon().getCardType());
		Assert.assertEquals(CardType.PERSON, Board.getSolution().getPerson().getCardType());
	}

	// Test for if all the cards are dealt
	@Test
	public void testDealt() {
		// board.dealCards();// call dealt function
		Assert.assertEquals(0, board.getDealtDeckSize());
	}

	// Testing if every player has the same number of cards
	@Test
	public void testSameNumber() {
		int numPlayer = board.getNumPlayers();
		for (int i = 0; i < numPlayer; i++) {
			Assert.assertEquals(3, board.getPlayer(i).getHand().size());
		}
	}

	// Test that only one player has a card
	@Test
	public void uniqueCards() {

		Card testCard = board.getPlayer(0).getHand().get(0);
		boolean passTest = true;
		int thisCard = 0;
		int numPlayer = board.getNumPlayers();

		if (board.getNumPlayers() == 0) {
			Assert.fail("Numplayers equals zero");
		}

		for (int i = 0; i < numPlayer; i++) {

			if (board.getPlayer(i).getHand().size() == 0) {
				Assert.fail("Player Hand Size is zero");
			}

			for (int j = 0; j < board.getPlayer(i).getHand().size(); j++) {
				if (board.getPlayer(i).getHand().get(j) == testCard) {
					thisCard++;
				}
			}

		}
		// This tests to ensure that only one person has a given card
		Assert.assertEquals(1, thisCard);

	}
}
