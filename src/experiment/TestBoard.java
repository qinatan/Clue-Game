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
//import <TestBoardCell> ; 




public class TestBoard {
	final static int COLS = 4; 
	final static int ROWS = 4;
	private Map<TestBoardCell, ArrayList<TestBoardCell>> adjMtx = new HashMap<TestBoardCell, ArrayList<TestBoardCell>> ();
	private TestBoardCell[][] grid = new TestBoardCell[COLS][ROWS]; 
	ArrayList<TestBoardCell> visitedList = new ArrayList<TestBoardCell> (); 
	ArrayList<TestBoardCell> targetsList = new ArrayList<TestBoardCell> (); 

	
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



	
	// Helper function to check if a cell is in the visited List
	public Boolean inVisitedList(TestBoardCell cell) {
		for (int i =0 ; i < visitedList.size(); i++) {
			if (cell == visitedList.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calculates legal targets for a move from startCell of length pathLength
	 * 
	 * @param startCell
	 * @param pathLength
	 */
	@SuppressWarnings("null")
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		visitedList.add(startCell);
		if (pathLength > 1) { //This is the recurse case where there is more than one space left to run.
			pathLength -- ; 
			ArrayList<TestBoardCell> currTargetsList = startCell.getAdjList();
			for(int cell = 0; cell < currTargetsList.size(); cell++) {
				TestBoardCell currCell = currTargetsList.get(cell);
				visitedList.add(currCell);
				calcTargets(currCell, pathLength); 
			}
			
			
		} else {  // Base case: check curr cell's adjList, subtract all cells in the visited list, and add the resulting list to targetsList. 
			ArrayList<TestBoardCell> currTargetsList = startCell.getAdjList();
			for (int cell = 0; cell < currTargetsList.size(); cell++) {
				// check if the cell in targetsList is in the visited list, remove it from targetsList
				if (inVisitedList(startCell)) {
					currTargetsList.remove(cell);
				}
				targetsList.add(startCell);
			
			}
			visitedList.clear();
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
		TestBoardCell testBoardCell = grid[col][row];
		return testBoardCell;
	}

	/**
	 * gets the targets last created by calcTargets()
	 * 
	 * @return
	 */
	public ArrayList<TestBoardCell> getTargets() {
		return targetsList;
	}


}
