// This pointer class is thanks to YouTube user Sylvain Saurel
// https://www.youtube.com/watch?v=da1uzaj549A&t=67s

public class Point {

  public int x,y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "[" + x + ", " + y + "]";
  }

}
