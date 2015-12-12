package core;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class AStarSearch extends SearchAlgorithm{
	
	
	
	public AStarSearch(int[] board) {
		super(board);
	}

	public AStarSearch(String order) {
		super(order);
	}

	@Override
	public SearchResult performSearch(String... params) {
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Queue<SearchNode> q = new LinkedList<SearchNode>();
		q.add(root);

		int searchCount = 1; 

		while (running && !q.isEmpty()) // while the queue is not empty and user doesn't click stop
		{
			SearchNode tempNode = (SearchNode) q.poll();

			if (!tempNode.getCurState().isGoal()) {
				ArrayList<State> tempSuccessors = tempNode.getCurState().genSuccessors();
				ArrayList<SearchNode> nodeSuccessors = new ArrayList<SearchNode>();

				for (int i = 0; i < tempSuccessors.size(); i++) {
					SearchNode checkedNode;
					if (params[0].equals("h")) {
						/*
						 * Create a new SearchNode, with tempNode as the parent,
						 * tempNode's cost + the new cost (1) for this state,
						 * and the Out of Place h(n) value
						 */
						checkedNode = new SearchNode(tempNode, tempSuccessors.get(i),
								tempNode.getCost() + tempSuccessors.get(i).findCost(),
								((EightPuzzleState) tempSuccessors.get(i)).getOutOfPlace());
					} else {
						checkedNode = new SearchNode(tempNode, tempSuccessors.get(i),
								tempNode.getCost() + tempSuccessors.get(i).findCost(),
								((EightPuzzleState) tempSuccessors.get(i)).getManDist());
					}

					if (!checkRepeats(checkedNode)) {
						nodeSuccessors.add(checkedNode);
					}
				}

				if (nodeSuccessors.size() == 0)
					continue;

				SearchNode lowestNode = nodeSuccessors.get(0);

				for (int i = 0; i < nodeSuccessors.size(); i++) {
					if (lowestNode.getFCost() > nodeSuccessors.get(i).getFCost()) {
						lowestNode = nodeSuccessors.get(i);
					}
				}

				int lowestValue = (int) lowestNode.getFCost();

				for (int i = 0; i < nodeSuccessors.size(); i++) {
					if (nodeSuccessors.get(i).getFCost() == lowestValue) {
						q.add(nodeSuccessors.get(i));
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
