package multisearch;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This class is used build a state machine for matching multiple strings in
 * parallel. This is an algorithmic parallelism, not to be confused with threading.
 * So, with one pass of the input, examining each character only once, we can
 * find matches for several search phrases.<br>
 * References: <br>
 * Aho, Alfred V.; Margaret J. Corasick (June 1975).<br>
 * "Efficient string matching: An aid to bibliographic search".<br> 
 * Communications of the ACM 18 (6): 333-340<br>
 * http://en.wikipedia.org/wiki/Aho-Corasick_algorithm<br>
 */
public class AhoCorasick {

  /**
   * Adds all the character transitions for the state machine.
   * The output is the same as the input string on a match.
   * @param start the initial state of the machine.
   * @param input a string to add to state machine
   */  
  public static void enter(State start, String input) {
    enter(start, input, 0, null);
  }  
    
  /**
   * Adds all the character transitions for the state machine.
   * @param start the initial state of the machine.
   * @param input a string to add to state machine
   * @param output the output string to return on a match
   */  
  public static void enter(State start, String input, String output) {
    enter(start, input, 0, output);
  }  
  
  /**
   * Adds all the character transitions for the state machine.
   * @param start the initial state of the machine.
   * @param input a string to add to state machine
   * @param pos the current position in s, initially 0
   * @param output the output string to return after a match
   */  
  private static void enter(State start, String input, int pos, String output) {
    if (pos >= input.length()) {
      if (output != null)
         start.addOutput(new StateMachineOutput(output, input.length()));
      else 
         start.addOutput(new StateMachineOutput(input, input.length()));
      return;
    }

    char c = input.charAt(pos);
    State next = start.gotoState(c);
    if (next == null) {
      State newState = new State();
      start.putGoto(c, newState);
      enter(newState, input, pos+1, output);
    } else {
      enter(next, input, pos+1, output);
    }
  }

  /**
   * @param root the start state of the the Aho-Corasick state machine
   * @param input the input string to match up
   * @return a list of match position and the matched text
   */
  public static List<MatchResult> match(State root, String input) {
    List<MatchResult> results = new LinkedList<MatchResult>();
    State n = root;
    
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      while (n.gotoState(c) == null) {
        n = n.getFail();
      }
      n = n.gotoState(c);
      if (n.getOutput() != null) {
        for (StateMachineOutput output : n.getOutput()) {
          int  leftOffset = i - output.inputLength  + 1;
          int rightOffset = i + 1;
          results.add(new MatchResult(leftOffset, rightOffset, 
                                      output.outputString,
                                      input.substring(leftOffset, rightOffset)));
        }
      }
    }
    return results;
  }

  /**
   * Sets the fail transitions for the all the states in a breath first 
   * search order, starting at the root.
   * @param root the start state for the state machine
   */
  public static void setFail(State root) {
    root.setFail(null);
    
    Queue<State> queue = new LinkedList<State>();
    for (Map.Entry<Character, State> e : root.getChildren()) {
      queue.add(e.getValue());
      e.getValue().setFail(root);
    }

    while (!queue.isEmpty()) {
      State r = queue.remove();
      for (Map.Entry<Character, State> e : r.getChildren()) {
        queue.add(e.getValue());
        State state = r.getFail();
        while (state.gotoState(e.getKey()) == null) {
          state = state.getFail();
        }
        e.getValue().setFail(state.gotoState(e.getKey()));
        e.getValue().addAllOutput(e.getValue().getFail().getOutput());
      }
    }
  }
  
}