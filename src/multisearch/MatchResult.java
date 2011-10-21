package multisearch;

/**
 * This class hold the output of successful match.
 */
public class MatchResult {
  public final int indexStart;  // where the match starts
  public final int indexEnd;    // where the match ends
  public final String output;   // the associated output string
  public final String matched;  // the actual text matched
  
  public MatchResult(int indexStart, int indexEnd, String output, String matched) {
    this.indexStart = indexStart;
    this.indexEnd   = indexEnd;
    this.output     = output;
    this.matched    = matched;
  }
}