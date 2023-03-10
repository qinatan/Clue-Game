package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and
	// then do all the tests.
	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// Initialize will load config files
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms() {
		// we want to test a couple of different rooms.
		
		//Testing the B room with passage
		Set<BoardCell> testList = board.getAdjList(1, 3);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(4, 6)));
		assertTrue(testList.contains(board.getCell(25, 18)));

		// Test the T Room
		testList = board.getAdjList(8, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(10, 2)));

	}

	// Ensure door locations include their rooms and also additional walkways

	@Test
	public void testAdjacencyDoor() {
		Set<BoardCell> testList = board.getAdjList(21, 5);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(24, 2)));
		assertTrue(testList.contains(board.getCell(20, 5)));
		assertTrue(testList.contains(board.getCell(22, 5)));
		assertTrue(testList.contains(board.getCell(21, 6)));

		testList = board.getAdjList(21, 10);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(24, 10)));
		assertTrue(testList.contains(board.getCell(21, 9)));
		assertTrue(testList.contains(board.getCell(21, 11)));
		assertTrue(testList.contains(board.getCell(20, 10)));

	}

	// Test a variety of walkway scenarios
	//Dark Green Tests
	@Test
	public void testAdjacencyWalkways() {

		// Test near a door but not adjacent
		Set<BoardCell> testList = board.getAdjList(19, 6);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(19, 5)));
		assertTrue(testList.contains(board.getCell(19, 7)));
		assertTrue(testList.contains(board.getCell(20, 6)));

		// Test adjacent to walkways
		testList = board.getAdjList(13, 14);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(13, 13)));
		assertTrue(testList.contains(board.getCell(13, 15)));
		assertTrue(testList.contains(board.getCell(12, 14)));
		assertTrue(testList.contains(board.getCell(14, 14)));

		// Test adjacent to walkways
		testList = board.getAdjList(4, 15);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(4, 14)));
		assertTrue(testList.contains(board.getCell(4, 16)));
		assertTrue(testList.contains(board.getCell(3, 15)));
		assertTrue(testList.contains(board.getCell(5, 15)));

	}

	
	
	@Test
	//Magenta test
	public void testTargetsInRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(2, 12), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(3, 15)));

		// test a roll of 2
		board.calcTargets(board.getCell(2, 12), 2);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2, 15)));
		assertTrue(targets.contains(board.getCell(3, 16)));
		assertTrue(targets.contains(board.getCell(4, 15)));

	}

	//Magenta Test
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(24, 14), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(23, 14)));
		assertTrue(targets.contains(board.getCell(25, 14)));
		assertTrue(targets.contains(board.getCell(25, 18)));

		// test a roll of 2
		board.calcTargets(board.getCell(24, 14), 2);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(22, 14)));
		assertTrue(targets.contains(board.getCell(23, 15)));
		assertTrue(targets.contains(board.getCell(26, 14)));
		assertTrue(targets.contains(board.getCell(25, 18)));
		;

	}

	@Test
	public void testTargetsInWalkway() {

		board.calcTargets(board.getCell(14, 14), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(14, 13)));
		assertTrue(targets.contains(board.getCell(14, 15)));
		assertTrue(targets.contains(board.getCell(13, 14)));
		assertTrue(targets.contains(board.getCell(15, 14)));

		board.calcTargets(board.getCell(14, 14), 2);
		targets = board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(13, 13)));
		assertTrue(targets.contains(board.getCell(13, 15)));
		assertTrue(targets.contains(board.getCell(15, 13)));
		assertTrue(targets.contains(board.getCell(15, 15)));
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertTrue(targets.contains(board.getCell(14, 16)));
		assertTrue(targets.contains(board.getCell(16, 14)));

	}

	@Test
	// test to make sure occupied locations do not cause problems
	//Magenta and Red Test
	public void testTargetsOccupied() {

		board.getCell(17, 15).setOccupied(true);
		board.calcTargets(board.getCell(17, 14), 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(16, 14)));
		assertTrue(targets.contains(board.getCell(17, 13)));
		assertTrue(targets.contains(board.getCell(18, 14)));

		board.calcTargets(board.getCell(17, 14), 2);
		targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(16, 13)));
		assertTrue(targets.contains(board.getCell(16, 15)));
		assertTrue(targets.contains(board.getCell(18, 13)));
		assertTrue(targets.contains(board.getCell(18, 15)));

		assertTrue(targets.contains(board.getCell(19, 14)));
		assertTrue(targets.contains(board.getCell(15, 14)));

		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(13, 19).setOccupied(true);
		board.calcTargets(board.getCell(16, 16), 1);

		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 19)));
		assertTrue(targets.contains(board.getCell(15, 16)));
		assertTrue(targets.contains(board.getCell(17, 16)));
		assertTrue(targets.contains(board.getCell(16, 15)));

	}
}
