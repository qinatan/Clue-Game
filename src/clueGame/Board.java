package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

/**
 * BoardCell
 * @author michaeleack 
 * @author johnOmalley
 * Date: 3/7/23
 * Collaborators: None
 * Sources: None
 */
public class Board {
	/*
	 * variable and methods used for singleton pattern
	 * All variable and method names should use lower camelCase except static finals(consts)
	 * variables should use descriptive names that reveal intent
	 * most of these should be private unless we have a really good reason
	 */
	private static Board theInstance = new Board();
	private int COLS; // TODO: we should clean this up, the variables don't work as finals but should be?
	private int ROWS; // TODO: Same thing
	private BoardCell[][] grid = new BoardCell[ROWS][COLS];
	private Map<BoardCell, ArrayList<BoardCell>> adjMtx = new HashMap<BoardCell, ArrayList<BoardCell>>();
	private ArrayList<BoardCell> visitedList = new ArrayList<BoardCell>();
	private Set<BoardCell> targetsSet = new HashSet<BoardCell>();
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private String layoutConfig;
	private String setupConfig;

	// constructor is private to ensure only one can be created
	private Board() {
		super();
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
			e1.printStackTrace();
		}
		try {
			loadLayoutConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}
	}

	public void setConfigFiles(String layoutConfig, String setupConfig) {
		this.layoutConfig = "data/" + layoutConfig;
		this.setupConfig = "data/" + setupConfig;
	}

	public Room getRoom(char c) {
		Room room = roomMap.get(c);
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
		int lineNum = 0; 
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
					throw new BadConfigFormatException("Error in setup file on line: " + lineNum );
				}
				Character roomSymbol = result[2].charAt(0);
				Room room = new Room(result[1], roomSymbol);
				roomMap.put(roomSymbol, room);
			}
			lineNum++;
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
				if (!roomMap.containsKey(result[col].charAt(0))) {
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
						Room room = roomMap.get(result[col].charAt(0));
						room.setLabelCell(grid[row][col]);
					} else if (result[col].charAt(1) == '*') {
						grid[row][col].setIsRoomCenterCell(true);
						// Set this cell to the Room's centerCell
						Room room = roomMap.get(result[col].charAt(0));
						room.setCenterCell(grid[row][col]);
						
					} else {
						grid[row][col].setSecretPassage(result[col].charAt(1));
					}

				}

			}
			row++;
		}
		 
	}
	
	// Helper functions for setAdjList
	private void addCellRight (BoardCell cell) {
		cell.addAdjacency(grid[cell.getRowNum()][cell.getColumnNum() + 1]);
	}
	
	private void addCellLeft (BoardCell cell) {
		cell.addAdjacency(grid[cell.getRowNum()][cell.getColumnNum() - 1]);
	}
	
	private void addCellAbove (BoardCell cell) {
		cell.addAdjacency(grid[cell.getRowNum() - 1][cell.getColumnNum()]);
	}
	
	private void addCellBelow (BoardCell cell) {
		cell.addAdjacency(grid[cell.getRowNum() + 1][cell.getColumnNum()]);
	}
	
	private void setAdjList() {
		// Traverse grid building adjacency list
		// TODO: Walkways connect to adjacent walkways. ** Need to check if cell is a walkway before adding to adjList
		// TODO: Walkways with doors will also connect to the room center the door points to.
		// TODO: The cell that represents the Room (i.e. connects to walkway) is the cell with a second character of ‘*’ (no other cells in a room should have adjacencies).
		// TODO: Room center cells ONLY connect to 1) door walkways that enter the room and 2) another room center cell if there is a secret passage connecting.
				for (int col = 0; col < COLS; col++) {
					for (int row = 0; row < ROWS; row++) {
						BoardCell currCell = grid[row][col];
						// Check if on top edge
						if (row == 0) {
							// if yes, check if on left edge
							if (col == 0) {
								addCellRight(currCell); 
								addCellBelow(currCell);  
							}
							// check if on right edge
							else if (col == COLS) {
								addCellLeft(currCell);  
								addCellBelow(currCell);  
							}
							// otherwise, the normal top edge case
							else if (col != 3 && col != 0){
								addCellRight(currCell); 
								addCellLeft(currCell);
								addCellBelow(currCell);
							}
						}
						// check if on bottom edge
						else if (row == ROWS) {
							// if yes, check if on left
							if(col == 0) {
								addCellAbove(currCell);
								addCellRight(currCell);
							}
							// check if on right edge
							if(col == ROWS) {
								addCellAbove(currCell); // add cell (3,2) to cell (3, 3)'s adjList
								addCellLeft(currCell); // add cell (2,3) to cell (3,3)'s adjList
							}
							// Else normal bottom edge case
							else if (col != 0) {
								addCellAbove(currCell);
								addCellLeft(currCell);
								addCellRight(currCell);
							}
						}
						
						// Check if on left edge 
						else if (col == 0) {
							addCellAbove(currCell); 
							addCellBelow(currCell);
							addCellRight(currCell);
						}
						
						// Check if on right edge 
						else if (col == COLS) {
							addCellAbove(currCell);
							addCellBelow(currCell);
							addCellLeft(currCell);
						}
						
						// Else add all surrounding cells to adjList
						else {
							addCellAbove(currCell);
							addCellBelow(currCell);
							addCellRight(currCell);
							addCellLeft(currCell);
						}
					}
				}
				
				// Traverse grid populating AdjMatrix
				for (int col = 0; col < COLS; col++) {
					for (int row = 0; row < ROWS; row++) {
						adjMtx.put(grid[row][col], grid[row][col].getAdjList());
					}
				}
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		// TODO Change this method. This is incorrect just to make the Junit test not
		// have errors

		targetsSet.add(getCell(i, j));
		return targetsSet;
	}

	public Set<BoardCell> getTargets() {
		// TODO Change this method. This is incorrect just to make the Junit test not
		// have errors
		int i = 1;
		int j = 1;
		targetsSet.add(getCell(i, j));
		return targetsSet;
	}

	public void calcTargets(BoardCell cell, int i) {
		// TODO Auto-generated method stub

	}

}
