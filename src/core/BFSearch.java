package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFSearch extends SearchAlgorithm {

	public BFSearch(int[] board) {
		super(board);
	}

	public BFSearch(String order) {
		super(order);
	}


	@Override
	protected SearchResult performSearch(String... params) {
		SearchNode root = new SearchNode(new EightPuzzleState(board));
		Queue<SearchNode> queue = new LinkedList<SearchNode>();

		queue.add(root);
		
		int searchCount = 1; // counter for number of iterations
		while (running && !queue.isEmpty()) // while the queue is not empty
		{
			SearchNode tempNode = (SearchNode) queue.poll();

			if (!tempNode.getCurState().isGoal()) // if tempNode is not the goal
													// state
			{
				ArrayList<State> tempSuccessors = tempNode.getCurState().genSuccessors(); // generate tempNode's immediate successors

				/*
				 * Loop through the successors, wrap them in a SearchNode, check if they've already been evaluated, and if not, add them to the queue
				 */
				for (int i = 0; i < tempSuccessors.size(); i++) {
					// second parameter here adds the cost of the new node to the current cost total in the SearchNode
					SearchNode newNode = new SearchNode(tempNode, tempSuccessors.get(i), tempNode.getCost() + tempSuccessors.get(i).findCost(), 0);

					if (!checkRepeats(newNode)) {
						queue.add(newNode);
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
