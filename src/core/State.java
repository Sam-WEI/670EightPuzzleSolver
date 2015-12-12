package core;
import java.util.ArrayList;

/**
 * 
 * State interface from which problem states inherit. Defines a method to check
 * if the current state is a goal, generate successors, and find the cost to
 * come to the current state.
 * 
 */
public interface State {
	boolean isGoal();

	ArrayList<State> genSuccessors();

	double findCost();

	public void printState();

	public boolean equals(State s);
}
