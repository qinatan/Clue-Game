package clueGame;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
	private Boolean isUnused = false;
	private Boolean isWalkway = false;
	public Boolean getIsWalkway() {
		return isWalkway;
	}

	public void setIsWalkway(Boolean isWalkway) {
		this.isWalkway = isWalkway;
	}

	public Boolean getIsUnused() {
		return isUnused;
	}

	public void setIsUnused(Boolean isUnused) {
		this.isUnused = isUnused;
	}

	private Boolean isOccupied = false;
	private Boolean isDoorway = false;
	private Character cellSymbol;
	private Boolean isLabel = false;
	private Boolean isRoomCenterCell = false;
	private Boolean isSecretPassage = false;
	public Boolean getIsSecretPassage() {
		return isSecretPassage;
	}

	public void setIsSecretPassage(Boolean isSecretPassage) {
		this.isSecretPassage = isSecretPassage;
	}

	private Character secretPassage = null;
	private DoorDirection doorDirection;
	

	// This method will draw the initial board without room names or players
	public void draw(int width, int height, Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // cast g to Graphics2d object so we can use those methods
		Board board = Board.getInstance();
		
		Color black = new Color(0,0,0); // Unused cells will be set to black
		Color lightBlue = new Color(98, 212, 246); // Rooms will be light blue
		Color grey = new Color(192, 192, 192); // Walkways will be grey
		int horOffset = width * columnNum; // calculates the offset of this cell
		int vertOffset = height * rowNum; // calculates the offset of this cell
		//System.out.println("width = " + width + " height = " + height);
		
		//if (isUnused) {
		//	System.out.println("Unused Cell found");
		//	g2.fillRect(horOffset, vertOffset, width, height);
		//	g2.setColor(black);
		//}
		
		if (isRoom) {
			System.out.println("Room found");
			g2.setColor(lightBlue);
			g2.fillRect(horOffset, vertOffset, width, height);
		}
		
		else if (isUnused) {
			System.out.println("horOffset = " + horOffset + " vertOffset = " + vertOffset);
			System.out.println("Unused cell found");
			g2.setColor(black); // Set Color MUST come before fill/draw
			g2.fillRect(horOffset, vertOffset, width, height);
			
		}
		
		
		else if (isWalkway ) {
			System.out.println("Walkway found");
			g2.drawRect(horOffset, vertOffset, width, height);
			g2.setColor(black);
	
		}
	
		
		if (isDoorway) {
			System.out.println("Doorway found");
		}
		
		if (isSecretPassage) {
			System.out.println("Secret Passage Found");
		}
		/*
		else {
			g2.drawRect(width,  height, horOffset, vertOffset);
			g2.setColor(grey);
		}
		*/
		/*
		if (board.getRoomMap().get(cellSymbol).getName().equals("Unused")) {
			System.out.println("Unused found");
			g.fillRect(width, height, horOffset, vertOffset);
			g.setColor(black);
		}
		*/
		
	}
	
	// Method draws room names over the board
	
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
