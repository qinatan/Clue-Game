package experiment;

import java.util.Set;

public class TestBoard {
	public int numRows = 4;
	public int numColumns = 4;
	
	
	/**
	 * Default Constructor
	 */
	public TestBoard() {
		super();
	}
	
	
	/**
	 * Calculates legal targets for a move from startCell of length pathLength
	 * @param startCell
	 * @param pathlength
	 */
	void calcTargets (TestBoardCell startCell, int pathlength) {
	}
	
	
	
	/**
	 * Returns the cell from the board at row, col
	 * @param row
	 * @param col
	 * @return
	 */
	TestBoardCell getCell(int row, int col) {
		TestBoardCell testBoardCell = new TestBoardCell(row, col);
		System.out.println("In getCell");
		return testBoardCell;
	}
	
	/**
	 *  gets the targets last created by calcTargets()
	 * @return
	 */
	Set <TestBoardCell> getTargets() {
		Set <TestBoardCell> targetSet = TestBoardCell.getAdjList();
		return targetSet;
	}

}

