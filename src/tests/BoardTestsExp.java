package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;
import experiment.TestBoard;
import experiment.TestBoardCell;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;

class BoardTestsExp {
	
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	// Methods to test the creation of adjacency lists for a 4x4 board including:
	// Top left corner [0][0]
	public void testAdjacency() {
		// Top left corner
		TestBoardCell cell1 = new TestBoardCell(0, 0);
		Set<TestBoardCell> testList = cell1.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2,  testList.size());
		
		// bottom right corner [3][3]
		TestBoardCell cell2 = new TestBoardCell(3, 3);
		Set<TestBoardCell> testList2 = cell1.getAdjList();
		Assert.assertTrue(testList2.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList2.contains(board.getCell(2, 3)));
		Assert.assertEquals(2,  testList2.size());
		
		// right edge [1][3]
		TestBoardCell cell3 = new TestBoardCell(1, 3);
		Set<TestBoardCell> testList3 = cell1.getAdjList();
		Assert.assertTrue(testList3.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList3.contains(board.getCell(2, 3)));
		Assert.assertEquals(2,  testList3.size());
		
		// left edge [3][0]
		TestBoardCell cell4 = new TestBoardCell(3, 0);
		Set<TestBoardCell> testList4 = cell1.getAdjList();
		Assert.assertTrue(testList4.contains(board.getCell(2, 0)));
		Assert.assertTrue(testList4.contains(board.getCell(3, 1)));
		Assert.assertEquals(2,  testList4.size());

	}
	
	// Methods to test target creation on a 4x4 board
	// Test for behavior on empty board
	// test for behavior with at least one cell being flagged as occupied
	// test for behavior with at least one cell being flagged as a room 

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
