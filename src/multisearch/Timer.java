package multisearch;

/**
 * Useful for timing the execution of code with minimal amount of clutter.
 */
public class Timer {
  private long duration; // total durations for all the stops of timer
  private long laps;     // the number of laps
  private long start;    // time at last start or restart

  private Timer() {}

  /**
   * @return an active timer
   */
  static public Timer start() {
    Timer t = new Timer();
    t.restart();
    return t;
  }

  /**
   * reset timer to zero, and starts it now
   */
  public void restart() {
    start = System.nanoTime();
  }

  /**
   * @return get number of laps
   */
  public long getLaps() {
    return laps;
  }

/**
   * stops the timer, adds to the duration, increments the "lap" counter
   */
  public void stop() {
    duration += System.nanoTime() - start;
    laps++;
  }

  /**
   * @return total duration of times between starts and stops
   */
  public String getDuration() {
    return formatTime(duration);
  }
  
  /** (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
public String toString() {
    long m = laps > 0 ? duration / laps : 0;
    return formatTime(m);    
  } 
  
  /**
   * If there are 5 or less of a given unit of time, 
   * then use the finer grain unit.  So 3s is expressed as 3000ms.
   * @param time time in nanoseconds
   * @return a string 
   */
  private String formatTime(long time) {
    if (time < 5 * 1000L) {
      return time + "ns";
      
    } if (time < 5 * 1000L * 1000L) {
      return time / 1000L + "us";  // using 'u' as a substitute for the Greek Letter Mu
      
    } else if (time < 5 * 1000L * 1000L * 1000L) {
      return time / 1000L / 1000L + "ms";
      
    } else if (time < 5 * 1000L * 1000L * 1000L * 60L) {
      return time / 1000L / 1000L / 1000L + "s";
      
    } else if (time < 5 * 1000L * 1000L * 1000L * 60L * 60L) {
      return time / 1000L / 1000L / 1000L / 60L + "m";
      
    } else {
      return time / 1000L / 1000L / 1000L / 60L / 60L + "h";
    }
  }
  
  public static void main(String[] args) {
    // example usage for single timing
    Timer t2 = Timer.start();
    someMethodWeWantToTime();
    t2.stop();
    System.out.println("Single Span of Code Time = " + t2);
    
    // example usage for average of several timings
    Timer t = Timer.start();
    for (int i = 0; i < 10; i++) {
      t.restart();
      someMethodWeWantToTime();
      t.stop();
    }
    System.out.println("For mapping loops:");
    System.out.println("Laps = " + t.getLaps());
    System.out.println("Duration = " + t.getDuration());
    System.out.println("Average Time = " + t);
  }
  
  private static void someMethodWeWantToTime() {
    try {
      Thread.sleep(3123);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}

