package experiment;

import java.util.Set;

public class TestBoard {
	/**
	 * Default Constructor
	 */
	public TestBoard() {
		super();
	}

	/**
	 * Calculates legal targets for a move from startCell of length pathLength
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	@SuppressWarnings("null")
	public
	void calcTargets(TestBoardCell startCell, int pathLength) {

		/*
		 * int i = 0 ; int j = 0 ;
		 * 
		 * int numLoops = 0 ;
		 * 
		 * 
		 * Set<TestBoardCell> cellTargets = null;
		 * 
		 * while (true) { if (numLoops > pathLength) { //Check if this is either >= or >
		 * break; }
		 * 
		 * //Sets the i and j values of the target cell i = pathLength - numLoops ; j =
		 * 0 + numLoops;
		 * 
		 * //create the cell with the i and j TestBoardCell tempCell = new
		 * TestBoardCell(i, j) ;
		 * 
		 * //add the cell to the set of cell targets cellTargets.add(tempCell) ;
		 * 
		 * //incements the number of loops numLoops ++ ;
		 * 
		 * 
		 * }
		 */
	}

	/**
	 * Returns the cell from the board at row, col
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell testBoardCell = new TestBoardCell(row, col);
		System.out.println("In getCell");
		return testBoardCell;
	}

	/**
	 * gets the targets last created by calcTargets()
	 * 
	 * @return
	 */
	public Set<TestBoardCell> getTargets() {
		Set<TestBoardCell> targetSet = null;
		return targetSet;
	}

}
