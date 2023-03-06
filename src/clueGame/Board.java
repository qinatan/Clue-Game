package clueGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import clueGame.BoardCell;

public class Board {
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	
	final static int COLS = 24; 
	final static int ROWS = 24;
	private Map<BoardCell, ArrayList<BoardCell>> adjMtx = new HashMap<BoardCell, ArrayList<BoardCell>> ();
	private BoardCell[][] grid = new BoardCell[COLS][ROWS]; 
	private ArrayList<BoardCell> visitedList = new ArrayList<BoardCell> (); 
	private Set<BoardCell> targetsSet = new HashSet<BoardCell> (); 
	private String layoutConfig;
	private String setupConfig;

	// constructor is private to ensure only one can be created
	private Board() {
		// Build grid
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				grid[row][col] = new BoardCell(row, col);
			}
		}
		
		
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		try {
			loadSetupConfig();
		} catch (FileNotFoundException | BadConfigFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			loadLayoutConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setConfigFiles (String string, String string2){
		// TODO tests to see if each file exists

		
	}

	public Room getRoom(char c) {
		// TODO Auto-generated method stub
		Room room = new Room() ; 
		return room;
	}

	public int getNumRows() { 
		return ROWS;
	}

	public int getNumColumns() {
		return COLS;
	}

	public BoardCell getCell(int i, int j) {
		// TODO Auto-generated method stub
		BoardCell BoardCell = grid[i][j];
		return BoardCell;
		
	}

	public Room getRoom(BoardCell cell) {
		// TODO Auto-generated method stub
		return null;
	}

	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		
		
	}

	public void loadLayoutConfig()  throws BadConfigFormatException, FileNotFoundException {
		// Sets numRows and numColumns
		
	}
}
