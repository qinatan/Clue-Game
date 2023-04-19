package clueGame;


/**
 * Room class contains information of a cell indicated as a room, includes room name, room initial
 * special condition: whether the cell indicates the center or label of a room 
 * 
 * @author michaeleack 
 * @author johnOmalley
 * Date: 3/7/23
 * Collaborators:
 * Sources: 
 */
public class Room {
	private String Name;
	private char Symbol;
	private BoardCell labelCell;
	private BoardCell centerCell;
	private boolean hasSecretPassage ; 
	private Character passageRoom ; 
	
	// ********* Constructors *********** // 
	public Room(String name, char symbol) {
		//super();
		Name = name;
		Symbol = symbol;
	}
	
	public Room(String name) {
		Name = name; 
	}
	
	// ******** Getter's and Setters ****** // 
	public Character getPassageRoom() {
		return passageRoom;
	}

	public void setPassageRoom(Character passageRoom) {
		this.passageRoom = passageRoom;
	}

	
	public void setPassageRoom(char charAt) {
		passageRoom = charAt;
	}


	public void setHasSecretPassage(boolean hasSecretPassage) {
		this.hasSecretPassage = hasSecretPassage;
	}
	
	
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}


	public String getName() {
		return Name;
	}

	
	public BoardCell getLabelCell() {
		return labelCell;
	}

	
	public BoardCell getCenterCell() {
		return centerCell;
	}

	
	public char getSymbol() {
		return Symbol;
	}
	
	
	// ******** Other Methods ************** // 

	@Override
	public String toString() {
		return Name;
	}
	
	
	public boolean isHasSecretPassage() {
		return hasSecretPassage;
	}
}
