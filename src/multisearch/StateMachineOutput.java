package multisearch;

/**
 * This hold the info associated with a matching .
 * We arbitrarily allow two items per match: a string & an integer
 */
public class StateMachineOutput {
	public final String outputString; // the arbitrarily associated output string
	public final int inputLength;     // the amount of input consumed on the match
	
	public StateMachineOutput(String outputString, int inputLength) {
		this.outputString = outputString;
		this.inputLength = inputLength;
	}
}
