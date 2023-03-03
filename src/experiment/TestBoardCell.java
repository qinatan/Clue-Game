/**
 * Michael Eack
 * John O'Malley
 * Clue
 */

package experiment;

import java.util.ArrayList;


public class TestBoardCell {
	public int rowNum; 
	public int columnNum;
	private ArrayList<TestBoardCell> adjList = new ArrayList<TestBoardCell> ();	
	private Boolean isRoom = false;
	private Boolean isOccupied= false;

	/**
	 * Parameterized Constructor
	 * @param rowNum
	 * @param columnNum
	 */
	public TestBoardCell(int rowNum, int columnNum) {
		super();
		this.rowNum = rowNum;
		this.columnNum = columnNum;
	} 
	
	
	//Takes in cell (not our current position)
	//adds it to the adj list. 
	void addAdjacency (TestBoardCell cell) {
		adjList.add(cell);
	}
	
	
	public ArrayList<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	
	//returns if a cell in in a given room
	boolean inRoom() {
		return false; 
	}
	
	//Section: setters and getter for if a player is in a given room 
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	} 
	
	public Boolean isOccupied() {
		return isOccupied;
	}


	public Boolean isRoom() {
		return isRoom;		
	}
	
	public void setRoom(Boolean b) {
		isRoom = b;
	}


	@Override
	public String toString() {
		return "TestBoardCell [rowNum=" + rowNum + ", columnNum=" + columnNum + "]";
	}
	
	
	
	
}

