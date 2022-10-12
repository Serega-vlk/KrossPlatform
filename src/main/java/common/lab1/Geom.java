package common.lab1;

public enum Geom {
  A,
  AB,
  B,
  BC,
  C,
  AC;

  private int count;

  public int getCount(){
    return count;
  }

  public void increment(){
    count++;
  }

  public void decrement(){
    count--;
  }
}
