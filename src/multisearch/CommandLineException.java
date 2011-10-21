package multisearch;

/**
 * This exception represents an incorrectly specified on the command-line. 
 * The message gives details on the particular error.
 */
@SuppressWarnings("serial")
public class CommandLineException extends RuntimeException {
	public CommandLineException(String message) { 
		super(message); 
	}
}
