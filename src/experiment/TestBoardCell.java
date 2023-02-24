package experiment;

import java.util.Map;
import java.util.Set;

public class TestBoardCell {

	int rowNum ; 
	int columnNum ;
	
	private Set<TestBoardCell> cellSet ; 
	 
	// TestBoardCell[] adjList ; 

	// Map<intager, intger> adjList = new HaspMap<int, int>() ; 
	
	private Map<TestBoardCell, Set<TestBoardCell>> adjMtx ; 
	
	
	
	public TestBoardCell(int rowNum, int columnNum) {
		super();
		this.rowNum = rowNum;
		this.columnNum = columnNum;
	} 
	
	
	//Takes in cell (not our current position)
	//adds it to the adj list. 
	void addAdjacency (TestBoardCell cell) {
		
		
		//TestBoardCell tempCell1 = new TestBoardCell(cell.rowNum -1 , cell.columnNum) ;

		
		//cellSet.add(cell) ; 

		
		//adjMtx.put(cell, cellSet.add(cell))  ; 
		
		//cell.columnNum = 2 ; 
		 
		adjMtx.put(cell, Set.add(cell))) ; 
		
		
		}


	}
	
	
}