import java.util.*;

public class State {
	private ArrayList<Change> modRep;
	public State(ArrayList<Change>report) {
		modRep = report;
	}
	public ArrayList<Change> getModificationReport(){
		return this.modRep;
	}
	public void save() {
		getCounter() = getCounter + 1
	}
	public void print() {
		
	}
}
