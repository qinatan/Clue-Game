package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import clueGame.BoardCell;
import clueGame.Room;
import java.io.*;



public class Board {
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();

	private int COLS;
	private int ROWS;
	private Map<BoardCell, ArrayList<BoardCell>> adjMtx = new HashMap<BoardCell, ArrayList<BoardCell>>();
	private BoardCell[][] grid = new BoardCell[COLS][ROWS];
	private ArrayList<BoardCell> visitedList = new ArrayList<BoardCell>();
	private Set<BoardCell> targetsSet = new HashSet<BoardCell>();
	private Map<Character, Room> RoomMap = new HashMap<Character, Room>();
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
		loadSetupConfig();
		loadLayoutConfig();
	}

	public void setConfigFiles(String layoutConfig, String setupConfig) {
		this.layoutConfig = "data/" + layoutConfig;
		this.setupConfig = "data/" + setupConfig;
	}

	public Room getRoom(char c) {
		Room room = RoomMap.get(c);
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

	@SuppressWarnings("resource")
	public void loadSetupConfig() {
		File setupFile = new File(setupConfig);
		try {
			Scanner myReader = new Scanner(setupFile);
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				if (line.contains("//")) {
					continue;
				} else {
					String[] result = line.split(", ");
					Character roomSymbol = result[2].charAt(0);
					Room room = new Room (result[1], roomSymbol);
					RoomMap.put(roomSymbol, room);
					}
				}
			myReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	public void loadLayoutConfig() {
		// reads in file once find numRows, numColumns
		File layoutFile = new File(layoutConfig);
		try {
			Scanner myReader = new Scanner(layoutFile);
			int rows = 0;
			int cols = 0; 
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				String[] result = line.split(",");
				cols = result.length;
				rows++;
			}
			ROWS = rows;
			COLS = cols;
			myReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Reads in file second time to create the board
	}
}
