package core;
import java.util.ArrayList;

public class SearchResult {
	public boolean hasSolution;
	public double cost;
	public int searchCount;
	public ArrayList<SearchNode> solutionPath;
	public long timeConsumed;
	
	public boolean completed = true;
	
	public SearchResult(boolean hasSolution) {
		this.hasSolution = hasSolution;
	}
	
	
	@Override
	public String toString() {
		return "-- hasSolution: " + hasSolution + "  timeConsumed: " + timeConsumed + "  cost: " + cost + "  searchCount: " + searchCount;
	}
	
}
