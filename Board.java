import java.util.*;

class Board {

  private final int B = 0; int X = 1; int O = 2;
  private int[][] theArray = new int[3][3];
  private int currentPlayer;
  private Point computerMove;

  void resetBoard() {
    for (int i=0; i<3; i++) {
      for (int j=0; j<3; j++) {
        theArray[i][j] = B;
      }
    }
  }

  void setPlayer(int player) {
    currentPlayer = player;
  }

  int getPlayer() {
    return currentPlayer;
  }

  boolean placeMove(Point point, int player) {
    if(theArray[point.x][point.y] != B) {
      return false;
    }
    theArray[point.x][point.y] = player;
    if(player == X) {
      currentPlayer = O;
    }
    else {
      currentPlayer = X;
    }
    return true;
  }

  int[][] getBoard(int[][] theBoard) {
    return theArray;
  }

  boolean won(int player) {
    if((theArray[0][0] == theArray[1][1] && theArray[0][0] == theArray[2][2] && theArray[0][0] == player) ||
      (theArray[0][2] == theArray[1][1] && theArray[0][2] == theArray[2][0] && theArray[0][2] == player)) {
      return true;
    }
    for (int i = 0; i < 3; i++) {
      if((theArray[i][0] == theArray[i][1] && theArray[i][0] == theArray[i][2] && theArray[i][0] == player) ||
        (theArray[0][i] == theArray[1][i] && theArray[0][i] == theArray[2][i] && theArray[0][i] == player)) {
          return true;
      }
    }
    return false;
  }

  // This getAvailableCells function is thanks to YouTube user Sylvain Saurel
  // https://www.youtube.com/watch?v=da1uzaj549A&t=67s
  private List<Point> getAvailableCells() {
    List<Point> availableCells = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if(theArray[i][j] == B) {
          availableCells.add(new Point(i, j));
        }
      }
    }
    return availableCells;
  }

  // This minimax function is thanks to YouTube user Sylvain Saurel
  // https://www.youtube.com/watch?v=da1uzaj549A&t=67s
  int minimax(int depth, int turn) {
    if(won(X)) { return 1; }
    if(won(O)) { return -1; }

    List<Point> availableCells = getAvailableCells();

    if(availableCells.isEmpty()) {return 0; }

    int min = Integer.MAX_VALUE; int max = Integer.MIN_VALUE;

    for (int i = 0; i < availableCells.size(); i++) {
      Point point = availableCells.get(i);

      if(turn == X) {
        placeMove(point, X);
        int currentScore = minimax(depth + 1, O);
        max = Math.max(currentScore, max);

        if(currentScore >= 0) {
          if(depth == 0) {
            computerMove = point;
          }
        }
        if(currentScore == 1) {
          theArray[point.x][point.y] = B;
          break;
        }
        if(i == availableCells.size() - 1 && max < 0) {
          if(depth == 0) { computerMove = point; }
        }
      }
      else if(turn == O) {
          placeMove(point, O);
          int currentScore = minimax(depth + 1, X);
          min = Math.min(currentScore, min);
          if(min == -1) {
            theArray[point.x][point.y] = B;
            break;
          }
      }
      theArray[point.x][point.y] = B;
    }

    return turn == X ? max : min;
  }

  Point getComputerMove() {
    return computerMove;
  }

  boolean isGameOver() {
    return won(X) || won(O) || getAvailableCells().isEmpty();
  }

  // Checks to see if it has reset correctly
  void testResetBoard() {
    this.resetBoard();
    assert(theArray[0][0] == B);
    assert(theArray[0][1] == B);
    assert(theArray[0][2] == B);
    assert(theArray[1][0] == B);
    assert(theArray[1][1] == B);
    assert(theArray[1][2] == B);
    assert(theArray[2][0] == B);
    assert(theArray[2][1] == B);
    assert(theArray[2][2] == B);
  }

// Tests a valid move
  void testMove() {
    Point point = new Point(1,1);
    int player = X;
    resetBoard();
    setPlayer(player);
    placeMove(point, X);
    assert(theArray[1][1] == X);
    assert(currentPlayer == O);
    point = new Point(1,2);
    placeMove(point, O);
    assert(theArray[1][2] == O);
    assert(currentPlayer == X);
  }

  void testWon() {
    resetBoard();
    theArray = new int[][] {{X,X,X},{B,O,B},{B,O,B}};
    assert(won(X) == true);
    theArray = new int[][] {{B,O,X},{X,X,X},{B,O,B}};
    assert(won(X) == true);
    theArray = new int[][] {{X,O,B},{B,O,B},{X,X,X}};
    assert(won(X) == true);
    theArray = new int[][] {{O,O,O},{X,O,X},{B,X,B}};
    assert(won(O) == true);
  }

  void testAvailableCells() {
    theArray = new int[][] {{X,X,X},{B,O,B},{B,O,B}};
    List<Point> availableCells = getAvailableCells();
    assert(availableCells.size() == 4);
    Point point = availableCells.get(0);
    assert(point.x == 1);
    assert(point.y == 0);
    Point point2 = availableCells.get(1);
    assert(point2.x == 1);
    assert(point2.y == 2);
  }

}
