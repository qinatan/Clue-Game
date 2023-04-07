package clueGame;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * BoardCell contains variables that store information of a boardCell, including: row and column location,
 * initial and other special conditions (room, roomLable, roomCenter, doorway, secrete passage, occupied, adjacency list)
 * 
 * @author michaeleack 
 * @author johnOmalley
 * @author Qina Tan
 * Date: 3/10/23
 * Collaborators: N/A
 * Sources: None
 */

public class BoardCell {
	private int columnNum;
	private int rowNum;
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	private Boolean isRoom = false;
	private Boolean isOccupied = false;
	private Boolean isDoorway = false;
	private Character cellSymbol;
	private Boolean isLabel = false;
	private Boolean isRoomCenterCell = false;
	private Character secretPassage = null;
	private DoorDirection doorDirection;
	
	/**
	 * INFO FROM RUBRIC
	Drawing each cell. Board cells should draw themselves. This will require you to:
		Add a draw method to the BoardCell class.
		Pass information to the board cell, like the cell size and perhaps a board offset.
		The draw method will be called from paintComponent. Remember that the parameter to paintComponent
		 is a Graphics object. You can simply pass that object to the draw method of BoardCell.  
		Then in BoardCell you can use the drawing commands (drawRect, fillRect, etc.).
	**/
	
	
	public void draw(int width, int height, Graphics g) {
		Board board = Board.getInstance();
		Color lightBlue = new Color(98, 212, 246);
		Color black = new Color(19, 20, 20); 
		int horOffset = width * rowNum;
		int vertOffset = height * columnNum;
		g.drawRect(width, height, horOffset, vertOffset);  // Can change to fillRect for filled in color
		System.out.println(board.getRoomMap().get(cellSymbol).getName());
		if (board.getRoomMap().get(cellSymbol).getName().equals("Walkway") || board.getRoomMap().get(cellSymbol).getName().equals("Unused")) {
			g.setColor(black);
		}
		// TODO: IDK why walkways are still being set to blue
		else {
			g.setColor(lightBlue);
		}
		
	}
	
	// TODO: This is super buggy for some reason. seems like our roomMap might be jacked
	public void drawRoomNames(int width, int height, Graphics g) {
		Board board = Board.getInstance();
		int horOffset = width * rowNum;
		int vertOffset = height * columnNum;
		if ( this.isLabel()) {
			String roomLabel = board.getRoomMap().get(cellSymbol).getName();
			g.drawString(roomLabel, horOffset, vertOffset);
		}
	}
	
	
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
	
	void addAdjacency (BoardCell cell) {
		adjList.add(cell);
	}
	
	@Override
	public String toString() {		
		return "BoardCell [rowNum=" + rowNum + ", columnNum=" + columnNum
				 + ", isRoom=" + isRoom + ", isOccupied=" + isOccupied + ", isDoor=" + isDoorway
				+ ", secretPassage= " + secretPassage + "]";
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}

}
