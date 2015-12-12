package core;
import java.util.ArrayList;
import java.util.Arrays;

public class EightPuzzleState implements State {

	private final int PUZZLE_SIZE = 9;
	private int outOfPlace = 0;

	private int manDist = 0;

	public static final int[] GOAL = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };
//	public static final int[] GOAL = new int[] { 1, 2, 3, 8, 0, 4, 7, 6, 5 };
	private int[] curBoard;

	public EightPuzzleState(int[] board) {
		curBoard = board;
		setHammingDist();
		setManDist();
	}

	/**
	 * How much it costs to come to this state
	 */
	@Override
	public double findCost() {
		return 1;
	}

	private void setHammingDist() {
		for (int i = 0; i < curBoard.length; i++) {
			if (curBoard[i] != GOAL[i]) {
				outOfPlace++;
			}
		}
	}

	private void setManDist() {
		int index = -1;

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				index++;

				int val = (curBoard[index] - 1);

				if (val != -1) {
					int horiz = val % 3;
					int vert = val / 3;

					manDist += Math.abs(vert - (y)) + Math.abs(horiz - (x));
				}
			}
		}
	}

	public int getBlankPile() {
		int holeIndex = -1;

		for (int i = 0; i < PUZZLE_SIZE; i++) {
			if (curBoard[i] == 0) {
				holeIndex = i;
				break;
			}
		}
		return holeIndex;
	}
	
	
	public int getOutOfPlace() {
		return outOfPlace;
	}

	public int getManDist() {
		return manDist;
	}

	private int[] copyBoard(int[] state) {
		int[] ret = new int[PUZZLE_SIZE];
		for (int i = 0; i < PUZZLE_SIZE; i++) {
			ret[i] = state[i];
		}
		return ret;
	}

	/**
	 * Is thought about in terms of NO MORE THAN 4 operations. Can slide tiles
	 * from 4 directions if hole is in middle Two directions if hole is at a
	 * corner three directions if hole is in middle of a row
	 * 
	 * @return an ArrayList containing all of the successors for that state
	 */
	@Override
	public ArrayList<State> genSuccessors() {
		ArrayList<State> successors = new ArrayList<State>();
		int hole = getBlankPile();

		if (hole != 0 && hole != 3 && hole != 6) {
			swapAndStore(hole - 1, hole, successors);
		}

		if (hole != 6 && hole != 7 && hole != 8) {
			swapAndStore(hole + 3, hole, successors);
		}

		if (hole != 0 && hole != 1 && hole != 2) {
			swapAndStore(hole - 3, hole, successors);
		}
		if (hole != 2 && hole != 5 && hole != 8) {
			swapAndStore(hole + 1, hole, successors);
		}

		return successors;
	}

	private void swapAndStore(int d1, int d2, ArrayList<State> s) {
		int[] cpy = copyBoard(curBoard);
		int temp = cpy[d1];
		cpy[d1] = curBoard[d2];
		cpy[d2] = temp;
		s.add((new EightPuzzleState(cpy)));
	}

	@Override
	public boolean isGoal() {
		if (Arrays.equals(curBoard, GOAL)) {
			return true;
		}
		return false;
	}

	@Override
	public void printState() {
		System.out.println(curBoard[0] + " | " + curBoard[1] + " | " + curBoard[2]);
		System.out.println("---------");
		System.out.println(curBoard[3] + " | " + curBoard[4] + " | " + curBoard[5]);
		System.out.println("---------");
		System.out.println(curBoard[6] + " | " + curBoard[7] + " | " + curBoard[8]);

	}

	@Override
	public boolean equals(State s) {
		if (Arrays.equals(curBoard, ((EightPuzzleState) s).getCurBoard())) {
			return true;
		} else
			return false;

	}

	public int[] getCurBoard() {
		return curBoard;
	}

}
