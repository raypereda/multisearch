package multisearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class MultiSearch {

	public static List<MatchResult> matchOnWordBoundaries(State root, String s) {
		List<MatchResult> results = AhoCorasick.match(root, s);

		List<MatchResult> filteredResults = new ArrayList<MatchResult>();

		for (MatchResult result : results) {
			int left = result.indexStart;
			// check the left side of the match, to see if we're
			// either out at boundary and butted up against more letters
			if (((left - 1) > 0) && Character.isLetter(s.charAt(left - 1)))
				continue;

			int right = result.indexEnd;
			// check the left side of the match, to see if we're
			// either out at boundary and butted up against more letters
			if ((right <= s.length()-1) && Character.isLetter(s.charAt(right)))
				continue;

			filteredResults.add(result);
			// System.out.format("[%d,%d] matched: %s%n", nextLeftCharIndex+1,
			// nextRightCharIndex-1, matchedString);
		}
		return filteredResults;
	}

	/**
	 * Creates a copy of the input list with duplicates removed.
	 * A duplicate is defined as having an output value we seen before in the list.
	 * @param results list of result with possible redundant result outputs
	 * @return a deduped result list
	 */
	public static List<MatchResult> unique(List<MatchResult> results) {
		Set<String> alreadySeen = new HashSet<String>();
		List<MatchResult> uniqueResults = new ArrayList<MatchResult>();
		for (MatchResult result : results) {
			if (!alreadySeen.contains(result.output)) {
				alreadySeen.add(result.output);
				uniqueResults.add(result);
			}
		}
		return uniqueResults;
	}


	/**
	 * use Unicode whitespace
	 * 
	 * @param input
	 *          any String
	 * @return a copy of the input all runs of whitespace characters converted to
	 *         a single blank.
	 */
	public static String normalizeWhitespace(String input) {
		return whitespaceRuns.matcher(input).replaceAll(" ").trim();
	}

	private static String whitespaceCharacters = 
			"(" +
					"\\p{javaWhitespace}" +
					"|[\\u0009-\\u000D]" +   
					"|\\u0020" +         
					"|\\u0085" +          
					"|\\u00A0" +          
					"|\\u1680" +
					"|\\u180E" +    
					"|[\\u2000-\\u200A]" + 
					"|\\u2028" + 
					"|\\u2029" + 
					"|\\u202F" +
					"|\\u205F" +          
					"|\\u3000" +
					")+";

	private static Pattern whitespaceRuns = Pattern.compile("("+whitespaceCharacters+")+");  

	public static String punctuationToSpace(String input) {
		return punctuationPattern.matcher(input).replaceAll(" ");    
	}

	private static String punctuationCharacters = 
			"-|!|“|#|\\$|%|&|'|\\(|\\)|\\*|\\+|,|\\.|/|:|\\;|<|=|>|\\?|@|\\[|\\\\|\\]|_|`|\\{|\\||\\}|~"
			+ "|[\u2000-\u206F]|[\"]";  
	private static Pattern punctuationPattern = Pattern.compile(punctuationCharacters);

	public static String normalize(String input) {
		return whitespaceOrPunctuationRuns.matcher(input).replaceAll(" ").trim();  
	}

	private static String whitespaceOrPunctuation = 
			whitespaceCharacters + "|" + punctuationCharacters;
	private static Pattern whitespaceOrPunctuationRuns = Pattern.compile("("+whitespaceOrPunctuation+")+");

	/**
	 * @param patterns a list of strings to be search
	 * @return a matcher for all the patterns
	 */
	public static State buildMatcher(String[] patterns) {
		State root = new State();	  
		State r = root;

		for (String pattern : patterns) {
			AhoCorasick.enter(r, pattern);
		}
		AhoCorasick.setFail(r);
		return r;
	}

}
