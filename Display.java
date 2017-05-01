import java.util.*;

class Display {

  private final int B = 0; int X = 1; int O = 2;
  private char[] charArray = {0,0};

  void printBoard(int[][] theBoard) {

    char[][] newBoard = new char[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if(theBoard[i][j] == X) { newBoard[i][j] = 'X';}
        else if(theBoard[i][j] == O) { newBoard[i][j] = 'O'; }
        else { newBoard[i][j] = ' '; }
      }
    }

    System.out.println("\n" + "    1   2   3");
    System.out.println("a   " + newBoard[0][0] + " | " +
     newBoard[0][1] + " | " + newBoard[0][2]);
    System.out.println("   ---+---+---");
    System.out.println("b   " + newBoard[1][0] + " | " +
     newBoard[1][1] + " | " + newBoard[1][2]);
    System.out.println("   ---+---+---");
    System.out.println("c   " + newBoard[2][0] + " | " +
     newBoard[2][1] + " | " + newBoard[2][2] + "\n");

  }

  Point ask(int currentPlayer, int[][] theBoard, Point userMove) {

    String command = "";
    String text = "";

    while(text != null) {
      System.out.println("\n" + text);
      System.out.println("It is player's " +  currentPlayer + " turn.");
      System.out.println("Please type in a letter and number such as b3.");
      command = System.console().readLine();
      text = this.valid(command, theBoard);
      userMove.x = row(charArray[0]);
      userMove.y = col(charArray[1]);
    }
    return userMove;
  }

  String valid(String command, int[][] theBoard) {

    if(command.length() != 2) {return "Type a letter and a digit";}
    charArray = command.toCharArray();
    if (charArray[0] < 'a' || charArray[0] > 'c') {return "Type a letter a, b or c";}
    if (charArray[1] < '1' || charArray[1] > '3') {return "Type a digit 1, 2 or 3";}
    int row = this.row(charArray[0]), col = this.col(charArray[1]);

    if(theBoard[row][col] != B) {
      return "Choose an empty cell";
    }

    return null;
  }

  int row(char charArray) {
    return charArray - 'a';
  }

  int col(char charArray) {
    return charArray - '1';
  }

  void winner(int player) {
    if(player == X){ System.out.println("Player X is the winner!");}
    if(player == O){ System.out.println("Player Y is the winner!");}
    if(player == B){ System.out.println("It is a draw!");}

  }

  int askBi(int question) {
    String theAnswer = ""; String theNull = "";
    int theResponse = 0;
    while(theNull != null){
      System.out.println("\n" + theNull);
      switch (question) {
        case 1:
        System.out.println("\nWould you like to play against a friend or me (the Computer!)?");
        System.out.println("\nPlease type an Integer:   1. (friend)     2. (Computer)");
        break;
        case 2:
        System.out.println("\n\nSelect turn:\nPlease type an Integer:   1. Computer (X) / 2. User (O) : ?");
        break;
        case 3:
        System.out.println("\n\nWould you like to be X or O.");
        System.out.println("\nPlease type an Integer:   1. (X)     2. (O)");
        break;
      }
      theAnswer = System.console().readLine();
      theNull = validAskBi(theAnswer);
    }
    theResponse = Integer.parseInt(theAnswer);
    return theResponse;
  }

  String validAskBi(String x) {
    if(x.length() != 1) {return "Not valid. Try again.";}
    char[] charArrayy = x.toCharArray();
    if (charArrayy[0] < '1' || charArrayy[0] > '2') {return "Type a digit 1 or 2";}
    return null;
  }

  // Test valid positions.
  void testValid() {
    int[][] theBoard = new int[3][3];
    Board board = new Board();
    board.resetBoard();
    board.getBoard(theBoard);
    assert(valid("a1", theBoard) == null);
    assert(row('a') == 0);
    assert(col('1') == 0);
    assert(valid("b3", theBoard) == null);
    assert(row('b') == 1);
    assert(col('3') == 2);
    assert(valid("c1", theBoard) == null);
    assert(row('c') == 2);
    assert(col('1') == 0);
  }

  // Test invalid positions, including occupied squares
  void testInvalid() {
    int[][] theBoard = new int[3][3];
    Board board = new Board();
    board.resetBoard();
    board.getBoard(theBoard);
    assert(valid("d2", theBoard).compareTo("Type a letter a, b or c") == 0);
    assert(valid("b0", theBoard).compareTo("Type a digit 1, 2 or 3") == 0);
    assert(valid("b4", theBoard).compareTo("Type a digit 1, 2 or 3") == 0);
    assert(valid("2b", theBoard).compareTo("Type a letter a, b or c") == 0);
    assert(valid("b2x", theBoard).compareTo("Type a letter and a digit") == 0);
    assert(valid("b", theBoard).compareTo("Type a letter and a digit") == 0);
    assert(valid("", theBoard).compareTo("Type a letter and a digit") == 0);
    int[][] theBoard2 = {{B,B,B},{B,B,B},{B,X,B}};
    assert(valid("c2", theBoard2).compareTo("Choose an empty cell") == 0);
  }

  }
