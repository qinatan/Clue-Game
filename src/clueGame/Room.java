package clueGame;

public class Room {

	private String Name;
	private char Symbol;
	
	@Override
	public String toString() {
		return Name;
	}

	public Room(String name, char symbol) {
		super();
		Name = name;
		Symbol = symbol;
	}

	public String getName() {
		// TODO Auto-generated method stub
		
		return Name;
	}

	public BoardCell getLabelCell() {
		// TODO Auto-generated method stub
		return null;
	}

	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return null;
	}

	public char getSymbol() {
		return Symbol;
	}

}
