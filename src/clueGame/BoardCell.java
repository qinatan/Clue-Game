package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

/**
 * BoardCell
 * @author michaeleack @author johnOmalley
 * Date:
 * Collaborators:
 * Sources: 
 */

public class BoardCell {

	private DoorDirection doorDirection;
	private int rowNum;
	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	private int columnNum;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	private Boolean isRoom = false;
	private Boolean isOccupied = false;
	private Boolean isDoorway = false;
	private Character cellSymbol;
	private Boolean isLabel = false;
	private Boolean isRoomCenterCell = false;
	private Character secretPassage = null;
	

	public BoardCell(int rowNum, int columnNum) {
		super();
		this.rowNum = rowNum;
		this.columnNum = columnNum;
		this.isDoorway = false ; 
	}

	public boolean isDoorway() {
		return isDoorway;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(char c) {
		switch (c) {
		case '>':
			doorDirection = DoorDirection.RIGHT;
			break;
		case '<':
			doorDirection = DoorDirection.LEFT;
			break;
		case 'v':
			doorDirection = DoorDirection.DOWN;
			break;
		case '^':
			doorDirection = DoorDirection.UP;
			break;
		default:
			doorDirection = null;
		}
	}

	public void setIsDoor(Boolean isDoorway) {
		this.isDoorway = isDoorway;
	}

	public Character getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(Character secretPassage) {
		this.secretPassage = secretPassage;
	}

	public Boolean isRoom() {
		return isRoom;
	}

	public void setIsRoom(Boolean isRoom) {
		this.isRoom = isRoom;
	}

	public Character getCellSymbol() {
		return cellSymbol;
	}

	public void setCellSymbol(String cellSymbol) {
		this.cellSymbol = cellSymbol.charAt(0);
	}

	public Boolean isLabel() {
		return isLabel;
	}

	public void setIsLabel(Boolean isLabel) {
		this.isLabel = isLabel;
	}

	public Boolean isRoomCenter() {
		return isRoomCenterCell;
	}

	public void setIsRoomCenterCell(Boolean isRoomCenterCell) {
		this.isRoomCenterCell = isRoomCenterCell;
	}

	public void setOccupied(boolean isOccStatus) {
		if (this.isRoomCenterCell) {
			this.isOccupied = false;
		}
		else {
			this.isOccupied = isOccStatus ; 
		}
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	//adds it to the adj list. 
	void addAdjacency (BoardCell cell) {
		adjList.add(cell);
	}
	
	@Override
	public String toString() {
		//return "rowNum: "+ rowNum + "columnNum: " + columnNum ; 
		
		return "BoardCell [rowNum=" + rowNum + ", columnNum=" + columnNum
				 + ", isRoom=" + isRoom + ", isOccupied=" + isOccupied + ", isDoor=" + isDoorway
				+ ", secretPassage= " + secretPassage + "]";
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}


	


}
