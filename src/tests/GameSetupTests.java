package tests;

import org.junit.Assert.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.DoorDirection;
import clueGame.Player;
import clueGame.Room;
import clueGame.Board;
import clueGame.CardType;

public class GameSetupTests {
	private static Board board ;

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
	// 5 Computer players
	// 1 Human player
	@Test
	public void testNumberPlayers() {
		int numHumanPlayers = board.getNumHumanPlayers();
		int numCompPlayers = board.getNumCompPlayers();
		int totalPlayers = numHumanPlayers + numCompPlayers;
		Assert.assertEquals(1, board.getNumHumanPlayers());
		Assert.assertEquals(5, board.getNumCompPlayers());
		Assert.assertEquals(6, totalPlayers);
	}
	

	// We should have 21 cards in the deck initially (6 players + 6 weapons + 9
	// rooms)
	@Test
	public void testCards() {
		Assert.assertEquals(21, board.getNumCards());
		Assert.assertEquals(6, board.getNumWeaponCards());
		Assert.assertEquals(9, board.getNumRoomCards());
		Assert.assertEquals(6, board.getNumPlayerCards());
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
		Assert.assertEquals(0, board.getDealtDeckSize());
	}

	// Testing if every player has approximately the same number of cards
	@Test
	public void testSameNumber() {
		int numPlayer = board.getNumPlayerCards();
		int apxNumCards = board.getPlayer(0).getHand().size();
		for (int i = 0; i < numPlayer; i++) {
			Assert.assertEquals(apxNumCards, board.getPlayer(i).getHand().size(), 1); 
		}
	}

	// Test that only one player has a card
	@Test
	public void uniqueCards() {
		Card testCard = board.getPlayer(0).getHand().get(0);
		boolean passTest = true;
		int thisCard = 0;
		int numPlayer = board.getNumPlayerCards();

		if (board.getNumPlayerCards() == 0) {
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
	
	@Test
	public void playerColor() {
		for (Player player: board.getPlayerList()) {
			Assert.assertNotNull(player.getPlayerColorString());
		}
	}
	
	
	// TODO: Test to make sure each player has a start location
	@Test
	public void testStartLocation() {
		for (Player player: board.getPlayerList()) {
			switch (player.getPlayerName()) {
				case "Chihiro Ogino":
					Assert.assertEquals(0, player.getPlayerRow());
					Assert.assertEquals(7, player.getPlayerCol());
					break;
				case "Yubaba":
					Assert.assertEquals(0, player.getPlayerRow());
					Assert.assertEquals(16, player.getPlayerCol());
					break;
				case "Zeniba":
					Assert.assertEquals(5, player.getPlayerRow());
					Assert.assertEquals(0, player.getPlayerCol());
					break;
				case "No-Face":
					Assert.assertEquals(20, player.getPlayerRow());
					Assert.assertEquals(0, player.getPlayerCol());
					break;
				case "Boh":
					Assert.assertEquals(26, player.getPlayerRow());
					Assert.assertEquals(6, player.getPlayerCol());
					break;
				case "River Spirit":
					Assert.assertEquals(21, player.getPlayerRow());
					Assert.assertEquals(21, player.getPlayerCol());
					break;
			}
				
		}
	}
}
