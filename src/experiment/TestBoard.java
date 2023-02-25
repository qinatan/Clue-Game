package experiment;

import java.util.Set;

public class TestBoard {
	
	/**
	 * Calculates legal targets for a move from startCell of length pathLength
	 * @param startCell
	 * @param pathlength
	 */
	void calcTargets (TestBoardCell startCell, int pathlength) {
		System.out.println("In calcTargets");
	}
	
	TestBoardCell getCell(int row, int col) {
		System.out.println("In getCell");
	}
	
	Set <TestBoardCell> getTargets() {
		System.out.println("In getTargets");
	}

}
