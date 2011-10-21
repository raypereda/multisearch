package multisearch;

import java.util.List;

/**
 * This class provides a simple container for the parsed command-line options
 */
public class RunConfiguration {
	public final String patternsFilename;
	public final List<String> targetFilenames;
	
	public RunConfiguration(String patternsFilename, List<String> targetFilenames) {
		this.patternsFilename = patternsFilename;
		this.targetFilenames = targetFilenames;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RunConfiguration [patternsFilename=" + patternsFilename
				+ ", targetFilenames=" + targetFilenames + "]";
	}

	public void run() {	
		String[] patterns = TextReader.readLines(patternsFilename);
		State matcher = MultiSearch.buildMatcher(patterns);

        for (String targetFilename : targetFilenames) {
        	System.out.println("target file: " + targetFilename);
			String text = TextReader.readWholeFile(targetFilename);		
			List<MatchResult> results = AhoCorasick.match(matcher, text);
	
			for (MatchResult result : results) {
				String NL = System.getProperty("line.separator");
				System.out.format("location: [%6d, %6d] matched: %s" + NL, 
						result.indexStart, result.indexEnd, result.matched);
			}
        }
	}

}