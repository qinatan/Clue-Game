package clueGame;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
	private Boolean isOccupied = false;
	private Boolean isDoorway = false;
	private Character cellSymbol;
	private Boolean isLabel = false;
	private Boolean isRoomCenterCell = false;
	private Boolean isSecretPassage = false;
	private Character secretPassage = null;
	private DoorDirection doorDirection;
	private Boolean isTargetCell = false; 
	

	// This method will draw the initial board without room names or players
	public void drawBoardCell(int width, int height, Graphics g) {
		Graphics2D g2 = (Graphics2D) g; // cast g to Graphics2d object so we can use those methods
		Board board = Board.getInstance();	
		Color black = new Color(0,0,0); 
		Color skyblue = new Color(135, 206, 235); // Rooms will be sky blue
		Color grey = new Color(192, 192, 192); // unused space will be grey
		Color yellow = new Color(255, 255, 0); //walkway will be yellow 
		Color highlight = new Color(128, 0, 128); 
		int horOffset = width * columnNum; // calculates the offset of this cell
		int vertOffset = height * rowNum; // calculates the offset of this cell
		
		if (isRoom) {
			g2.setColor(skyblue);
			g2.fillRect(horOffset, vertOffset, width, height);
		}
		
		else if (isUnused) {
			g2.setColor(black); // Set Color MUST come before fill/draw
			g2.setStroke(new BasicStroke(2));
			g2.fillRect(horOffset, vertOffset, width, height);
		}
		
		else if (isWalkway) {
			g2.setColor(black); 
			g2.drawRect(horOffset, vertOffset, width, height);
			g2.setColor(grey);
			g.fillRect(horOffset, vertOffset, width, height);
		}
	
		
		if (isDoorway) {
			
			switch (doorDirection) {
				case UP:
					g2.setColor(black);
					g2.drawString("^", horOffset, vertOffset + 10);
					break;
					
				case DOWN:
					g2.setColor(black);
					g2.drawString("v", horOffset, vertOffset + 10);
					break;
				
				case RIGHT:
					g2.setColor(black);
					g2.drawString(">", horOffset, vertOffset + 10 );
					break;
					
				case LEFT:
					g2.setColor(black);
					g2.drawString("<", horOffset, vertOffset+ 10);
				
				default:
					break;
					
			}
			if (isTargetCell)
			{
				g2.setColor(highlight); 
				g.fillRect(horOffset, vertOffset, width, height);
			}
			
		}
	
		if (isSecretPassage) {
			g2.setColor(black); 
			String secretePassage = " " +this.secretPassage; 
			g2.drawString(secretePassage,  horOffset+10, vertOffset+10);
		}	
	}
	
	// Method draws room names over the board
	
	public void drawRoomNames(int width, int height, Graphics g) {
		
		Board board = Board.getInstance();
		int horOffset = width * columnNum;
		int vertOffset = height * rowNum;
		if (this.isLabel()) {
			String roomLabel = board.getRoomMap().get(cellSymbol).getName(); 
			Color pink = new Color(255, 192, 203); 
			Color black = new Color(0,0,0); 
			g.setColor(black);
			Font font = new Font("Arial", Font.BOLD, 12); 
			g.setFont(font);
			g.drawString(roomLabel, horOffset, vertOffset);
		}
	}
	
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
	
	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getColumnNum() {
		return columnNum;
	}
	
	public Boolean getIsSecretPassage() {
		return isSecretPassage;
	}

	public void setIsSecretPassage(Boolean isSecretPassage) {
		this.isSecretPassage = isSecretPassage;
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
	
	public void setIsTargetCell()
	{
		this.isTargetCell = true; 
	}

}
