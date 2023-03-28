package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import clueGame.Room;

class PlayerTest306 {
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

	@Test
	void testTestPlayerCreation() {
		fail("Not yet implemented");
	}

}
