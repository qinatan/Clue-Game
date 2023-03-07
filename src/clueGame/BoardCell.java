package clueGame;

import java.util.ArrayList;

//import experiment.BoardCell;

public class BoardCell {

	private DoorDirection doorDirection;
	public int rowNum;
	public int columnNum;
	private ArrayList<BoardCell> adjList = new ArrayList<BoardCell>();
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
		//System.out.println(cellSymbol) ; 
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

	@Override
	public String toString() {
		return "BoardCell [doorDirection=" + doorDirection + ", rowNum=" + rowNum + ", columnNum=" + columnNum
				+ ", adjList=" + adjList + ", isRoom=" + isRoom + ", isOccupied=" + isOccupied + ", isDoor=" + isDoorway
				+ "]";
	}



	public void setIsDoor(Boolean isDoorway) {
		this.isDoorway = isDoorway;
		if (isDoorway == true) {
			//set
		}
	}

	public Character getSecretPassage() {
		return secretPassage;
	}

	public void setSecretPassage(Character secretPassage) {
		this.secretPassage = secretPassage;
	}

	public Boolean IsRoom() {
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

	public void setOccupied(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
