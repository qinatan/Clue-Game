/**
 * Michael Eack
 * John O'Malley
 * Clue
 */

package experiment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//import <TestBoardCell> ; 




public class TestBoard {
	
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx ; 
	
	private TestBoardCell[][] grid ; 
	
	//Creates the visited list of the board
	//TODO: This might fail with player crossing paths
	ArrayList<TestBoardCell> visitedList = new ArrayList<TestBoardCell> (); 
	ArrayList<TestBoardCell> tragetsList = new ArrayList<TestBoardCell> (); 
	
	final static int COLS = 4; 
	final static int ROWS = 4 ;
	
	//private ArrayList<TestBoardCell> targets
	/**
	 * Default Constructor
	 */
	public TestBoard() {
		super();
		
		
		for (int i = 0 ; ) {
			for() {
				
			}
		}
		
	}



	
	/**
	 * Calculates legal targets for a move from startCell of length pathLength
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	@SuppressWarnings("null")
	public void calcTargets(TestBoardCell startCell, int pathLength) {

		TestBoardCell currCell = new TestBoardCell(startCell.rowNum, startCell.columnNum) ; 
		
		if (pathLength > 1) { //This is the recurse case where there is more than one space left to run.
			
			
			pathLength -- ; 
			calcTargets(currCell, pathLength) ; 
			
			
		} else { //This is the base case
			//Add the adjList to the targets list
			for (int i = 0 ; i < adjMtx.get(currCell).size() ;  i++ ) {
				
				
				
			}
			
			//Return
		}
		
		

		 
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
		return testBoardCell;
	}

	/**
	 * gets the targets last created by calcTargets()
	 * 
	 * @return
	 */
	public Set<TestBoardCell> getTargets() {
		Set<TestBoardCell> targetSet = new HashSet<TestBoardCell>();
		return targetSet;
	}

}
