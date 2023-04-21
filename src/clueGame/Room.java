package clueGame;

import java.util.ArrayList;

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
	private int numOccupants; 
	private ArrayList<Player> Occupants; 
	
	public ArrayList<Player> getOccupants() {
		return Occupants;
	}

	public void setOccupants(ArrayList<Player> occupants) {
		Occupants = occupants;
	}

	public int getNumOccupants() {
		return numOccupants;
	}

	public void setNumOccupants(int numOccupants) {
		this.numOccupants = numOccupants;
	}

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
	
	public void updateRoomState() { 
		Occupants = new ArrayList();
		for (Player player: Board.getPlayerList()) {
			if (Boolean.TRUE.equals(player.getCurrCell().isRoom())) {
				if (player.getCurrRoom().getName().equals(Name)) {
					Occupants.add(player);
				}
			}
		}
	}

	@Override
	public String toString() {
		return Name;
	}
	
	
	public boolean isHasSecretPassage() {
		return hasSecretPassage;
	}
}
