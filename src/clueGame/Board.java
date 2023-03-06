package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import clueGame.BoardCell;
import clueGame.Room;
import experiment.TestBoardCell;

import java.io.*;

public class Board {
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();

	private int COLS;
	private int ROWS;
	private BoardCell[][] grid = new BoardCell[ROWS][COLS];
	private Map<BoardCell, ArrayList<BoardCell>> adjMtx = new HashMap<BoardCell, ArrayList<BoardCell>>();
	private ArrayList<BoardCell> visitedList = new ArrayList<BoardCell>();
	private Set<BoardCell> targetsSet = new HashSet<BoardCell>();
	private Map<Character, Room> RoomMap = new HashMap<Character, Room>();
	private String layoutConfig;
	private String setupConfig;

	// constructor is private to ensure only one can be created
	private Board() {
		// super();
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

	public void setConfigFiles(String layoutConfig, String setupConfig) {
		this.layoutConfig = "data/" + layoutConfig;
		this.setupConfig = "data/" + setupConfig;
	}

	public Room getRoom(char c) {
		Room room = RoomMap.get(c);
		return room;
	}

	public Room getRoom(BoardCell cell) {
		Character symbol = cell.getCellSymbol();
		Room room = getRoom(symbol);
		return room;
	}

	public int getNumRows() {
		return ROWS;
	}

	public int getNumColumns() {
		return COLS;
	}

	public BoardCell getCell(int row, int col) {
		BoardCell BoardCell = grid[row][col];
		return BoardCell;
	}

	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		File setupFile = new File(setupConfig);
		Scanner myReader = new Scanner(setupFile);
		while (myReader.hasNextLine()) {
			String line = myReader.nextLine();
			// Check for comments
			if (line.contains("//")) {
				continue;
			}
			// If not a comment, split by ", "
			else {
				String[] result = line.split(", ");
				String resultZero = result[0];
				if (!resultZero.equals("Room") && !resultZero.equals("Space")) {
					throw new BadConfigFormatException("Bad setup file found");
				}
				Character roomSymbol = result[2].charAt(0);
				Room room = new Room(result[1], roomSymbol);
				RoomMap.put(roomSymbol, room);
			}
		}
		myReader.close();
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		// reads in file once to find numRows, numColumns
		File layoutFile = new File(layoutConfig);
		Scanner myReader = new Scanner(layoutFile);
		int rows = 0;
		int firstRowCols = 0;
		while (myReader.hasNextLine()) {
			if (firstRowCols == 0) {
				String line = myReader.nextLine();
				String[] result = line.split(",");
				firstRowCols = result.length;
			} else {
				String line = myReader.nextLine();
				String[] result = line.split(",");
				int curRowCols = result.length;
				if (curRowCols != firstRowCols) {
					throw new BadConfigFormatException("Bad Config File found. Inconsistent number of columns.");
				}
			}
			rows++;
		}
		ROWS = rows;
		COLS = firstRowCols;
		myReader.close();

		// Build grid of empty BoardCells
		grid = new BoardCell[ROWS][COLS];
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				grid[row][col] = new BoardCell(row, col);
			}
		}

		// Reads in file second time to create the board
		Scanner myReader2 = new Scanner(layoutFile);
		int row = 0;
		while (myReader2.hasNextLine()) {
			String line = myReader2.nextLine();
			String[] result = line.split(",");
			for (int col = 0; col < result.length; col++) {
				// sets BoardCell symbol for each BoardCell
				grid[row][col].setCellSymbol(result[col]);
				// Checks for bad config file
				if (!RoomMap.containsKey(result[col].charAt(0))) {
					throw new BadConfigFormatException(
							"Letter found in config file that is not a known room: " + result[col].charAt(0));
				}

				// sets cell to "room" if not a walkway or unused square,
				if (result[col] != "X" && result[col] != "W") {
					grid[row][col].setIsRoom(true);
				}

				if (result[col].length() == 2) {
					if (result[col].charAt(0) == 'W') {
						grid[row][col].setIsDoor(true);
						// Set door direction
						grid[row][col].setDoorDirection(result[col].charAt(1));
					} else if (result[col].charAt(1) == '#') {
						grid[row][col].setIsLabel(true);
						// Set this cell to the Room's labelCell
						Room room = RoomMap.get(result[col].charAt(0));
						room.setLabelCell(grid[row][col]);
					} else if (result[col].charAt(1) == '*') {
						grid[row][col].setIsRoomCenterCell(true);
						// Set this cell to the Room's centerCell
						Room room = RoomMap.get(result[col].charAt(0));
						room.setCenterCell(grid[row][col]);
					} else {
						grid[row][col].setSecretPassage(result[col].charAt(1));
					}

				}

			}
			row++;
		}
	}
}
