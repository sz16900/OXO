import java.util.*;

class Oxo {

  private final int B = 0; int X = 1; int O = 2;
  private int[][] theBoard = new int[3][3];
  private Point userMove = new Point(1,1);
  private Board board = new Board();
  private Random RANDOM = new Random();
  private Display display = new Display();
  private Point computerMove = new Point(1,1);

  private void random() {
    Point p = new Point(RANDOM.nextInt(3), RANDOM.nextInt(3));
    board.placeMove(p, X);
    theBoard = board.getBoard(theBoard);
    display.printBoard(theBoard);
  }

  private void didYouWin() {
    if(board.won(X)) { display.winner(X); }
    else if(board.won(O)) { display.winner(O); }
    else { display.winner(B); }
  }

  private void placeSetDisplay(Point move, int player) {
    board.placeMove(move, player);
    theBoard = board.getBoard(theBoard);
    display.printBoard(theBoard);
  }

  private void playHuman(int player) {

    board.resetBoard();
    theBoard = board.getBoard(theBoard);
    display.printBoard(theBoard);
    board.setPlayer(player);
    while(!board.isGameOver()){
      int currentPlayer = board.getPlayer();
      userMove = display.ask(currentPlayer, theBoard, userMove);
      placeSetDisplay(userMove, currentPlayer);
    }
    didYouWin();
  }

  private void playComp() {

    board.resetBoard();
    int choice = display.askBi(2);
    display.printBoard(theBoard);
    // If the computer is first, it picks a random cell
    if(choice == X) { random();}
    while(!board.isGameOver()) {
      userMove = display.ask(O, theBoard, userMove);
      placeSetDisplay(userMove, O);
      if(board.isGameOver()) { break; }
      board.minimax(0, X);
      computerMove = board.getComputerMove();
      placeSetDisplay(computerMove, X);
    }
    didYouWin();
  }

  // Unit Testing
  void test() {

    Board board = new Board();
    board.testResetBoard();
    board.testMove();
    board.testAvailableCells();
    board.testWon();
    Display display = new Display();
    display.testValid();
    display.testInvalid();
    System.out.println("All tests have passed!");
  }

  public static void main (String[] args) {
    boolean testing = false;
    assert(testing = true);
    Oxo program = new Oxo();
    Display mainDisplay = new Display();
    if (testing) program.test();
    else if(!testing) {
      int choice = mainDisplay.askBi(1);
      if (choice == 1) {
        int choiceS = mainDisplay.askBi(3);
        if(choiceS == 1 || choiceS == 2) {program.playHuman(choiceS);}
        else {
          System.err.println("Try to type in an integer (1 or 2) when prompted");
          System.exit(1);
        }
      }
      else if(choice == 2) { program.playComp(); }
    }
    else {
      System.err.println("Use:");
      System.err.println("  java -ea Triangle     for testing or");
      System.err.println("  try to type in an integer when prompted");
      System.exit(1);
    }
  }
}
