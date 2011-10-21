package multisearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a single state in the Aho-Corasick state machine.
 * Each state has three properties:
 * <ol>
 * <li>a set of out going transitions to other states given a character</li>
 * <li>a fail transition for non-specified characters
 * <li>an output string associated with the state
 * </ol> 
 */
public class State {
	
  private Map<Character, State> children;
  private State fail;
  private ArrayList<StateMachineOutput> outputList;

  public State() {
    children = new HashMap<Character, State>(1);
    fail = this;
  }

  /**
   * Provides a state machine transition from the this current state.
   * @param c given this input character
   * @return the new state is this.
   */
  public State gotoState(char c) {
    State s = children.get(c);
    if (s == null && fail == null)
      s = this;
    return s;
  }

  /**
   * Inserts this out transition for this state.
   * @param c given this input character
   * @param n transition to this output state
   */
  public void putGoto(char c, State n) {
    children.put(c, n);
  }

  /**
   * @return the next transition if the machine jams on a character.
   */
  public State getFail() {
    return fail;
  }

  /**
   * @param n set the out transition if the machine jams on character.
   */
  public void setFail(State n) {
    fail = n;
  }

  /**
   * @return set of out transitions.
   */
  public Set<Map.Entry<Character, State>> getChildren() {
    return children.entrySet();
  }

  /**
   * Adds a string to the output set.
   * @param pair a string to add to the set of output strings
   */
  public void addOutput(StateMachineOutput output) {
    if (output == null) return;
    
    if (outputList == null) {
      outputList = new ArrayList<StateMachineOutput>(1);
    }
    outputList.add(output);
  }


  /**
   * Adds a set of Strings to the output sets.
   * @param pair a string to add to the set of output strings
   */
  public void addAllOutput(Collection<StateMachineOutput> c) {
    if (c == null) return;
    for (StateMachineOutput output : c) {
      addOutput(output);
    }    
  }  
  
  /**
   * Returns the set of output strings, or matched strings.
   * @return the set of output strings
   */
  public Collection<StateMachineOutput> getOutput() {
    if (fail == null)
      return null;
    return outputList;
  }

  /**
   * Provides a string representation.
   * @return a string representation of the state machine.
   */
  @Override
public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("id: " + System.identityHashCode(this) + "\n");
    s.append("fail: " + System.identityHashCode(fail) + "\n");
    s.append("out: " + outputList + "\n");
    s.append("[");
    for (Map.Entry<Character, State> e : getChildren()) {
      s.append(e.getKey() + "->");
      s.append(e.getValue().toString());
    }
    
    s.append("]\n");
    return s.toString();
  }

}