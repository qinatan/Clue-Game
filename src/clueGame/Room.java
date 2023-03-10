package clueGame;


/**
 * BoardCell
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
	
	
	public Character getPassageRoom() {
		return passageRoom;
	}

	public void setPassageRoom(Character passageRoom) {
		this.passageRoom = passageRoom;
	}
	
	public void setPassageRoom(char charAt) {
		passageRoom = charAt;
	}

	public boolean isHasSecretPassage() {
		return hasSecretPassage;
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

	public Room(String name, char symbol) {
		super();
		Name = name;
		Symbol = symbol;
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
	

	@Override
	public String toString() {
		String string = "Name = " + Name + "passageRoom= " + passageRoom.toString();
		return string;
	}

}
