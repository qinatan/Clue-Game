package tests;

import experiment.TestBoard;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	public void setUp() {
		board = new TestBoard();
	}
	// Methods to test the creation of adjacency lists for a 4x4 board including:
	// Top left corner [0][0]
	// bottom right corner [3][3]
	// right edge [1][3]
	// left edge [3][0]
	
	
	// Methods to test target creation on a 4x4 board
	// Test for behavior on empty board
	// test for behavior with at least one cell being flagged as occupied
	// test for behavior with at least one cell being flagged as a room 
}
