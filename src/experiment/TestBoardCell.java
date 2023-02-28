package experiment;

import java.util.Map;
import java.util.Set;

public class TestBoardCell {

	public int rowNum ; 
	public int columnNum ;
	private static Set<TestBoardCell> adjSet;	
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx ; 
	
	
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
		// Method Stub only
		
	}
	
	
	public Set<TestBoardCell> getAdjList() {
		return adjSet;
	}
	
	//Section: Setters and getters for if a cell is part of a room
	//sets a cell as part of a room
	void setRoom(boolean inRoom) {
		
	}
	
	//returns if a cell in in a given room
	boolean inRoom() {
		
		
		return false ; 
	}
	
	//Section: setters and getter for if a player is in a given room 
	public void setOccupied(boolean inRoom) {
		
	} 
	
	//TODO: This need to be included with the data type of the rooms
	void getOccupied( ) {
		
	}


	//This might be the same as inRoom above. This will get fixed when we 
	//update and fill in these methods
	public void isRoom(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	
}

