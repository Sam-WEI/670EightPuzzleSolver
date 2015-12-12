package core;
import java.util.ArrayList;

public abstract class SearchAlgorithm {
	
	int[] board;
	
	boolean running;
	
	long startTime;
	
	SearchListener listener;
	
	public SearchResult result;
	
	public SearchAlgorithm(int[] board){
		this.board = board;
	}
	
	public SearchAlgorithm(String order){
		this.board = convertToArray(order);
	}
	
	public static int[] convertToArray(String order) {
		int[] r = new int[9];
		for(int i = 0; i < order.length(); i++) {
			r[i] = order.charAt(i) - '0';
		}
		return r;
	}
	
	public void setSearchListener(SearchListener l) {
		this.listener = l;
	}
	
	
	// SearchResult can be returned by either listener or the return value of this method 
	public SearchResult search(String... params) {
		running = true;
		markStartTime();
		
		if(!isSolvable(board)) {
			SearchResult r = new SearchResult(false);
			if(listener != null) {
				listener.searchCompleted(r);
			}
			return r;
		}
		
		SearchResult r = performSearch(params);
		result = r;
		
		if(listener != null) {
			listener.searchCompleted(r);
		}
		return r;
	}
	
	
	protected abstract SearchResult performSearch(String... params);
	
	protected void markStartTime() {
		startTime = System.currentTimeMillis();
	}
	
	protected long markAndGetEndTime() {
		return System.currentTimeMillis() - startTime;
	}
	
	public void stop() {
		running = false;
	}
	
	public SearchResult makeSearchResult(SearchNode tempNode, int searchCount) {
		SearchResult result = new SearchResult(true);
		result.timeConsumed = markAndGetEndTime(); 
		
		result.searchCount = searchCount;
		
		ArrayList<SearchNode> solutionPath = new ArrayList<SearchNode>();

		while (tempNode != null) {
			solutionPath.add(tempNode);
			tempNode = tempNode.getParent();
		}

		result.cost = solutionPath.get(0).getCost();
		result.solutionPath = solutionPath;
		
		return result;
	}
	
	public static boolean isSolvable(int[] board) {
		return getContraryCount(board) % 2 == getContraryCount(EightPuzzleState.GOAL) % 2;
	}
	
	private static int getContraryCount(int[] nums) {
		int num = 0;
		int i, j;
		for (i = 1; i < 9; i++) {
			if (nums[i] == 0) {
				continue;
			}
			for (j = 0; j < i; j++) {
				if (nums[j] != 0 && nums[j] > nums[i]) {
					num++;
				}
			}
		}
		return num;
	}
	
	
	/*
	 * Helper method to check to see if a SearchNode has already been evaluated.
	 */
	protected static boolean checkRepeats(SearchNode n) {
		SearchNode checkNode = n;

		// While n's parent isn't null, check to see if it's equal to the node we're looking for.
		while (n.getParent() != null) {
			if (n.getParent().getCurState().equals(checkNode.getCurState())) {
				return true;
			}
			n = n.getParent();
		}
		return false;
	}
	
	public interface SearchListener {
		void searchCompleted(SearchResult r);
	}
}
