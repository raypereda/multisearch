package multisearch;
import java.io.File;
import java.util.LinkedList;
import java.util.List;


/**
 * A class that parses a command-line with various options.
 * It checks for errors and produces a configuration that
 * specifies all inputs required to running a hangman strategy
 * experiment.
 */
public class CommandLineParser {

	public static void main(String[] args) {
		try {
			CommandLineParser commandLineParser = new CommandLineParser();
			RunConfiguration runConfig = commandLineParser.parseArguments(args);
			runConfig.run();
		} catch (CommandLineException e) {
			System.err.println(e.getMessage());
			CommandLineParser.printUsageMessage();
			System.exit(1);					
		}
	}

	/**
	 * @param args command-line arguments
	 * @return a validated run configuration
	 */
	public RunConfiguration parseArguments(String[] args) {
        // the fields for building configuration with default values
		String patternsFilename = null;
		List<String> targetFilenames = new LinkedList<String>();
		boolean nextArgIsPatternsFilename = false;

		if (args.length == 0) {
			// This will trigger printing of the help message with no error message.
			throw new CommandLineException("");
		}
		
		for (String arg : args) {
			if (arg.equals("-p")) {
				nextArgIsPatternsFilename = true;
				continue;
			}

			File f = new File(arg);
			if (!f.isFile()) {
				throw new CommandLineException("File does not exist: " + arg);
			}
			
			if (nextArgIsPatternsFilename) {
				patternsFilename = arg;
				nextArgIsPatternsFilename = false;
			} else {
				targetFilenames.add(arg);
			}	
		}

		if (patternsFilename == null) {
			throw new CommandLineException("Patterns filename must be specified with -f FILENAME.");
		}

		if (targetFilenames.size() == 0) {
			throw new CommandLineException("There must be at least one target filename. None given.");
		}		
		
		return new RunConfiguration(patternsFilename, targetFilenames);
	}
	
	/**
	 * Prints a message to standard output that explains the command-line interface.
	 */
	private static void printUsageMessage() {
		String NL = System.getProperty("line.separator");
		String usageMesage =
		"Usage: java -jar msearch.jar -p PATTERNFILENAME FILENAME1 FILENAME2 ..." + NL +
		"Search for a list of fixed patterns in a list target files." + NL +
		"Example: java -jar multisearch.jar -f patterns.txt newarticle1.txt newsarticle2.txt" + NL + 
		NL +
		"Required:" + NL +
		"  must specify the patterns files with -f" + NL +
		"  must specify at least one target filename" + NL;
		
		System.out.println(usageMesage);
	}
}