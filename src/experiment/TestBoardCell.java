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
	
	static Set<TestBoardCell> getAdjList() {
		return adjSet;
	}
	
}