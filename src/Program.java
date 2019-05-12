import java.util.Scanner;

public class Program {
    public static boolean gamePlaying = true;
    public static Player humanPlayer = new Player("Player", 'O');
    public static Player AIPlayer = new Player("AI", 'X');
    public static Game game = new Game();
    public static Player currentPlayer = humanPlayer;
    public static Scanner obj = new Scanner(System.in);

    public static void main(String[] args) {
        while(gamePlaying) {
            game.Turn(currentPlayer);

            if(game.Winning(currentPlayer)) {
                game.OutputGrid();
                System.out.println(currentPlayer.name + " has won the game.");
                game.restart();
                continue;
            } else if(game.emptySpots().size() == 0) {
                System.out.println("Draw");
                game.restart();
                continue;
            }

            currentPlayer = currentPlayer == humanPlayer ? AIPlayer : humanPlayer;
        }
    }
}
