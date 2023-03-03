/**
 * Michael Eack
 * John O'Malley
 * Clue
 */

package experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
//import <TestBoardCell> ; 




public class TestBoard {
	final static int COLS = 4; 
	final static int ROWS = 4;
	private Map<TestBoardCell, ArrayList<TestBoardCell>> adjMtx = new HashMap<TestBoardCell, ArrayList<TestBoardCell>> ();
	private TestBoardCell[][] grid = new TestBoardCell[COLS][ROWS]; 
	ArrayList<TestBoardCell> visitedList = new ArrayList<TestBoardCell> (); 
	Set<TestBoardCell> targetsSet = new HashSet<TestBoardCell> (); 

	
	//private ArrayList<TestBoardCell> targets
	/**
	 * Default Constructor
	 */
	public TestBoard() {
		super();

		// Build grid
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				grid[row][col] = new TestBoardCell(row, col);
			}
		}
		
		// Traverse grid building adjacency list
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				// Check if on top edge
				if (row == 0) {
					// if yes, check if on left
					if (col == 0) {
						grid[row][col].addAdjacency(grid[row][col + 1]);  // Add cell(0,1) to cell(0,0)'s adjList
						grid[row][col].addAdjacency(grid[row + 1][col]);  // Add cell(1,0) to cell(0,0)'s adjList
					}
					// check if on right edge
					else if (col == 3) {
						grid[row][col].addAdjacency(grid[row][col - 1]);  // Add cell(0,2) to cell(0, 3)'s adjList
						grid[row][col].addAdjacency(grid[row + 1][col]);  // Add cell (1,3) to cell(0, 3)'s adjList
					}
					// otherwise, the normal top edge case
					else if (col != 3 && col != 0){
						grid[row][col].addAdjacency(grid[row][col + 1]);  // add cell to the right
						grid[row][col].addAdjacency(grid[row][col - 1]);  // add cell to left
						grid[row][col].addAdjacency(grid[row + 1][col]);  // add cell beneath
					}
				}
				// check if on bottom edge
				else if (row == 3) {
					// if yes, check if on left
					if(col == 0) {
						grid[row][col].addAdjacency(grid[row - 1 ][col]); // add cell (2,0) to cell (3, 0)'s adjList
						grid[row][col].addAdjacency(grid[row][col + 1]); // add cell (3,1) to cell (3, 0)'s adjList
					}
					// check if on right edge
					if(col == 3) {
						grid[row][col].addAdjacency(grid[row][col -1]); // add cell (3,2) to cell (3, 3)'s adjList
						grid[row][col].addAdjacency(grid[row - 1][col]); // add cell (2,3) to cell (3,3)'s adjList
					}
					// Else normal bottom edge case
					else if (col != 0) {
						grid[row][col].addAdjacency(grid[row][col + 1]);  // add cell to the right
						grid[row][col].addAdjacency(grid[row][col - 1]);  // add cell to left
						grid[row][col].addAdjacency(grid[row - 1][col]);  // add cell above
					}
				}
				
				// Check if on left edge 
				else if (col == 0) {
					grid[row][col].addAdjacency(grid[row + 1][col]);  // add cell above
					grid[row][col].addAdjacency(grid[row - 1][col]);  // add cell below
					grid[row][col].addAdjacency(grid[row][col + 1]);  // add cell to the right
				}
				
				// Check if on right edge 
				else if (col == 3) {
					grid[row][col].addAdjacency(grid[row + 1][col]);  // add cell above
					grid[row][col].addAdjacency(grid[row - 1][col]);  // add cell below
					grid[row][col].addAdjacency(grid[row][col - 1]);  // add cell to the left
				}
				
				// Else add all surrounding cells to adjList
				else {
					grid[row][col].addAdjacency(grid[row + 1][col]);  // add cell above
					grid[row][col].addAdjacency(grid[row - 1][col]);  // add cell below
					grid[row][col].addAdjacency(grid[row][col - 1]);  // add cell to the left
					grid[row][col].addAdjacency(grid[row][col + 1]);  // add cell to the right
				}
			}
		}
		
		// Traverse grid populating AdjMatrix
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				adjMtx.put(grid[row][col], grid[row][col].getAdjList());
			}
		}
	}

	
	
	public void findAllTargets(TestBoardCell startCell, int pathLength) {
		// for each adjCell in adjCells
		for (TestBoardCell adjCell : startCell.getAdjList()) {
			if (visitedList.contains(adjCell)) {
				continue;
			}
			visitedList.add(adjCell);
			if(pathLength == 1) {
				targetsSet.add(adjCell);
				visitedList.remove(adjCell);
			}
			else {
				findAllTargets(adjCell, pathLength-1);
				visitedList.remove(adjCell);
			}
		}
	}
	/**
	 * Calculates legal targets for a move from startCell of length pathLength
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		visitedList.clear();
		targetsSet.clear();
		// Add start location to visited list
		visitedList.add(startCell);
		findAllTargets(startCell, pathLength);
	}

	/**
	 * Returns the cell from the board at row, col
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public TestBoardCell getCell(int row, int col) {
		TestBoardCell testBoardCell = grid[col][row];
		return testBoardCell;
	}

	/**
	 * gets the targets last created by calcTargets()
	 * 
	 * @return
	 */
	public Set<TestBoardCell> getTargets() {
		return targetsSet;
	}


}
