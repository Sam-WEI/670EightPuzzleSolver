package core;

import java.util.ArrayList;
import java.util.Stack;

public class DFSearch extends SearchAlgorithm {

	public DFSearch(int[] board) {
		super(board);
	}

	public DFSearch(String order) {
		super(order);
	}
	
	@Override
	protected SearchResult performSearch(String... params) {
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Stack<SearchNode> stack = new Stack<SearchNode>();

		stack.add(root);

		int searchCount = 1;

		while (running && !stack.isEmpty())
		{
			SearchNode tempNode = (SearchNode) stack.pop();

			if (!tempNode.getCurState().isGoal()) {
				ArrayList<State> tempSuccessors = tempNode.getCurState().genSuccessors();

				for (int i = 0; i < tempSuccessors.size(); i++) {
					SearchNode newNode = new SearchNode(tempNode, tempSuccessors.get(i),
							tempNode.getCost() + tempSuccessors.get(i).findCost(), 0);

					if (!checkRepeats(newNode)) {
						stack.add(newNode);
					}
				}
				searchCount++;
			} else {
				SearchResult r = makeSearchResult(tempNode, searchCount);
				return r;
			}
		}

		if(!running) {// exit loop because user stopped it
			SearchResult r = new SearchResult(true);
			r.completed = false;
			return r;
		} else {
			System.out.println("Error! No solution found!");
			return new SearchResult(false);
		}
	}
}
