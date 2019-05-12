import java.text.MessageFormat;
import java.util.ArrayList;

public class Game {
    public char[] grid = new char[] {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public int bestMove = 0;

    public Game() {
        char[] newGame = grid;
    }

    public void restart() {
        System.out.println("Press any key to restart, or type 'exit' to exit the program");
//        Program.obj.nextLine();
        if(Program.obj.nextLine().equals("exit")) {
            System.exit(0);
        }
        Program.game = new Game();
        Program.currentPlayer = Program.humanPlayer;
    }

    public void OutputGrid() {
        System.out.println("     |     |     ");
        System.out.println(MessageFormat.format("  {0}  |  {1}  |  {2}  ", grid[0], grid[1], grid[2]));
        System.out.println("_____|_____|_____");
        System.out.println("     |     |     ");
        System.out.println(MessageFormat.format("  {0}  |  {1}  |  {2}  ", grid[3], grid[4], grid[5]));
        System.out.println("_____|_____|_____");
        System.out.println("     |     |     ");
        System.out.println(MessageFormat.format("  {0}  |  {1}  |  {2}  ", grid[6], grid[7], grid[8]));
        System.out.println("     |     |     ");
        System.out.println("\b\b\b");
        System.out.println("-----------------");
    }

    public void Turn(Player currentPlayer) {
        if(currentPlayer.name == "Player") {
            OutputGrid();
            CheckInput(currentPlayer, false);
        } else if(currentPlayer.name == "AI") {
            BestSpot(currentPlayer);
        }
    }

    public void BestSpot(Player AIPlayer) {
        int selectedPosition = miniMax(grid, AIPlayer).position;
        int bestPosition = new String(grid).indexOf(selectedPosition);
        grid[bestPosition] = AIPlayer.icon;
    }

    public Move miniMax(char[] newBoard, Player currentPlayer) {
        ArrayList<Character> availableSpots = emptySpots();
        Move tempMove = new Move();

        if(Winning(Program.humanPlayer)) {
            tempMove.score = -10;
            return tempMove;
        } else if(Winning(Program.AIPlayer)) {
            tempMove.score = 10;
            return tempMove;
        } else if(availableSpots.size() == 0) {
            tempMove.score = 0;
            return tempMove;
        }

        ArrayList<Move> moves = new ArrayList<>();

        for(int i = 0; i < availableSpots.size(); i++) {
            Move saveMove = new Move();
            int found = new String(newBoard).indexOf(availableSpots.get(i));
            saveMove.position = newBoard[found];

            newBoard[found] = currentPlayer.icon;

            if(currentPlayer == Program.AIPlayer) {
                Move result = miniMax(newBoard, Program.humanPlayer);
                saveMove.score = result.score;
            }
            else {
                Move result = miniMax(newBoard, Program.AIPlayer);
                saveMove.score = result.score;
            }

            newBoard[found] = saveMove.position;

            moves.add(saveMove);
        }

        if(currentPlayer == Program.AIPlayer) {
            int bestScore = -10000;
            for(int i = 0; i < moves.size(); i++) {
                if(moves.get(i).score > bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        } else {
            int bestScore = 10000;
            for(int i = 0; i < moves.size(); i++) {
                if(moves.get(i).score < bestScore ) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }

        return moves.get(bestMove);
    }

    public ArrayList emptySpots() {
        ArrayList<Character> list = new ArrayList<>();
        for(int i = 0; i < this.grid.length; i++) {
            if(Character.isDigit(this.grid[i])) {
                list.add(this.grid[i]);
            }
        }

        return list;
    }

    public void CheckInput(Player huPlayer, boolean pass) {
        while(!pass) {
            System.out.println("Choose your field! Or type 'exit' to exit the program");
            int number = 0;

            try {
                String input = Program.obj.nextLine();

                if(input.equals("exit")) {
                    System.exit(0);
                }

                number = Integer.valueOf(input);
            }
            catch (Exception e) {
                System.out.println("Please enter a number!");
                continue;
            }

            if(number > 9 || number < 1) {
                System.out.println("Number must be between 1 and 9");
                continue;
            }

            if(!Character.isDigit(grid[number - 1])) {
                System.out.println("Position already taken");
                continue;
            } else {
                grid[number - 1] = huPlayer.icon;
                pass = true;
            }
        }
    }

    public boolean Winning(Player currentPlayer) {
        char[][] winningCombinations = {
                { grid[0], grid[1], grid[2] },
                { grid[3], grid[4], grid[5] },
                { grid[6], grid[7], grid[8] },
                { grid[0], grid[3], grid[6] },
                { grid[1], grid[4], grid[7] },
                { grid[2], grid[5], grid[8] },
                { grid[0], grid[4], grid[8] },
                { grid[2], grid[4], grid[6] }
        };

        int columns = winningCombinations.length;
        boolean winning = false;

        for (int i = 0; i < columns; i++) {
            if (
                    winningCombinations[i][0] == currentPlayer.icon &&
                    winningCombinations[i][1] == currentPlayer.icon &&
                    winningCombinations[i][2] == currentPlayer.icon
                )
            {
                winning = true;
            }
        }

        return winning;
    }
}
