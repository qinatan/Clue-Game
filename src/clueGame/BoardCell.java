package clueGame;

import java.util.ArrayList;

//import experiment.BoardCell;

@SuppressWarnings("unused")
public class BoardCell {

	private DoorDirection doorDirection;

	public int rowNum; 
	public int columnNum;
	private ArrayList<BoardCell> adjList = new ArrayList<BoardCell> ();	
	@SuppressWarnings("unused")
	private Boolean isRoom = false;
	@SuppressWarnings("unused")
	private Boolean isOccupied= false;
	
	public BoardCell(int rowNum, int columnNum) {
		super();
		this.rowNum = rowNum;
		this.columnNum = columnNum;
	} 
	
	
	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub

		return doorDirection;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}

}
