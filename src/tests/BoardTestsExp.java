package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
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
	@Test
	public void testAdjacency() {
		// Top left corner
		TestBoardCell cell1 = board.getCell(0,0);
		List<TestBoardCell> testList = cell1.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());

		// bottom right corner [3][3]
		TestBoardCell cell2 = board.getCell(3,3);
		List<TestBoardCell> testList2 = cell2.getAdjList();

		Assert.assertTrue(testList2.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList2.contains(board.getCell(2, 3)));
		Assert.assertEquals(2, testList2.size());

		// right edge [1][3]
		TestBoardCell cell3 = board.getCell(1, 3);
		List<TestBoardCell> testList3 = cell3.getAdjList();

		Assert.assertTrue(testList3.contains(board.getCell(0, 3)));
		Assert.assertTrue(testList3.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList3.contains(board.getCell(1, 2)));
		Assert.assertEquals(3, testList3.size());

		// left edge [3][0]
		TestBoardCell cell4 = board.getCell(3, 0);
		List<TestBoardCell> testList4 = cell4.getAdjList();

		Assert.assertTrue(testList4.contains(board.getCell(2, 0)));
		Assert.assertTrue(testList4.contains(board.getCell(3, 1)));
		Assert.assertEquals(2, testList4.size());

		// Test of cell away from the current cell
		// required 5th test
		// This might accidently pass

		TestBoardCell cell5 = board.getCell(2, 2);
		List<TestBoardCell> testList5 = cell5.getAdjList();
		Assert.assertTrue(testList5.contains(board.getCell(3, 2)));
		Assert.assertTrue(testList5.contains(board.getCell(1, 2)));
		Assert.assertTrue(testList5.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList5.contains(board.getCell(2, 1)));
		Assert.assertEquals(4, testList5.size());

	}
/*
	// Methods to test target creation on a 4x4 board

	// Test targets Normal
	@Test
	public void testTargetsNormal() {
		// tests targets
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());

		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));

		// Second Test for different location and distance
		TestBoardCell cell1 = board.getCell(3, 3);
		board.calcTargets(cell1, 2);
		Set<TestBoardCell> targets1 = board.getTargets();
		Assert.assertEquals(3, targets1.size());

		Assert.assertTrue(targets1.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 1)));

		// TEST2*
		TestBoardCell cell2 = board.getCell(3, 3);
		board.calcTargets(cell2, 1);
		Set<TestBoardCell> targets2 = board.getTargets();
		Assert.assertEquals(4, targets2.size());

		Assert.assertTrue(targets2.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets2.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets2.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets2.contains(board.getCell(2, 1)));
		
		// TEST3*
		TestBoardCell cell3 = board.getCell(0, 3);
		board.calcTargets(cell3, 4);
		Set<TestBoardCell> targets3 = board.getTargets();
		Assert.assertEquals(6, targets3.size());

		Assert.assertTrue(targets3.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets3.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets3.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets3.contains(board.getCell(2, 3)));
		Assert.assertTrue(targets3.contains(board.getCell(3, 2)));
		
		
		// TEST 4*
		TestBoardCell cell4 = board.getCell(3, 0);
		board.calcTargets(cell4, 2);
		Set<TestBoardCell> targets4 = board.getTargets();
		Assert.assertEquals(3, targets4.size());

		Assert.assertTrue(targets4.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets4.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets4.contains(board.getCell(3, 2)));

		// TEST 5*
		TestBoardCell cell5 = board.getCell(3, 3);
		board.calcTargets(cell5, 2);
		Set<TestBoardCell> targets5 = board.getTargets();
		Assert.assertEquals(6, targets5.size());

		Assert.assertTrue(targets5.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets5.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets5.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets5.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets5.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets5.contains(board.getCell(3, 3)));
		
		// TEST 6*
		TestBoardCell cell6 = board.getCell(1, 1);
		board.calcTargets(cell6, 1);
		Set<TestBoardCell> targets6 = board.getTargets();
		Assert.assertEquals(4, targets6.size());

		Assert.assertTrue(targets6.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets6.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets6.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets6.contains(board.getCell(2, 1)));
	}

	// Testing targets with rooms cells
	@Test 
	public void testTargetsRoom() {
		board.getCell(1, 2).isRoom(true);

		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());

		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));

		// Second test for different cells
		board.getCell(3, 2).isRoom(true);
		TestBoardCell cell1 = board.getCell(3, 3);
		board.calcTargets(cell1, 2);
		Set<TestBoardCell> targets1 = board.getTargets();
		Assert.assertEquals(6, targets1.size());

		Assert.assertTrue(targets1.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 1)));

	}

	@Test
	public void testTargetsOccupied() {
		board.getCell(1, 2).setOccupied(true);

		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());

		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));

		// Second test for different cells
		board.getCell(3, 2).setOccupied(true);
		TestBoardCell cell1 = board.getCell(3, 3);
		board.calcTargets(cell1, 2);
		Set<TestBoardCell> targets1 = board.getTargets();
		Assert.assertEquals(6, targets1.size());

		Assert.assertTrue(targets1.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 1)));

	}

	// Tests a combined of occupied and is room arrangements
	@Test
	public void testTargetsMixed() {
		board.getCell(1, 2).setOccupied(true);
		board.getCell(2, 2).isRoom(true);
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertEquals(6, targets.size());

		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));

		// Second test for different cells
		board.getCell(3, 2).setOccupied(true);
		board.getCell(1, 0).isRoom(true);
		TestBoardCell cell1 = board.getCell(3, 3);
		board.calcTargets(cell1, 2);
		Set<TestBoardCell> targets1 = board.getTargets();
		Assert.assertEquals(6, targets1.size());

		Assert.assertTrue(targets1.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets1.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets1.contains(board.getCell(3, 1)));

	}
*/
}
