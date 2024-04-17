/** Description: Front-end logic used to simulate board layout. Extends Connect4Logic
 * to "implement" backend logic of game operation. Features methods to display board and its
 * "contents", receive user input to forward into logic, and a method to implement the other
 * UI methods as well as perform gameplay loop between players.
 *
 * @author Stephen Arel
 * @version 2.5 4/7/2024
*/

package ui;

import core.Connect4Logic;
import core.Connect4ComputerPlayer;
import ui.Connect4GUI;
import javafx.application.Application;

import java.util.Arrays;
import java.util.Scanner;

/** Class which extends Connect4Logic to bring core functionality of game and combine them with
 *  methods and variables that allow user functionality to the game.
 */
public class Connect4TextConsole extends Connect4Logic {

    //------ Board Object ------
    /** Declaration of Connect4Logic object to allow access to class methods and variables. */
    static Connect4Logic board = new Connect4Logic();

    //------ Computer Player Object ------
    /** Declaration of Connect4ComputerPlayer object to allow access to class method makeMove(). */
    static Connect4ComputerPlayer CPU = new Connect4ComputerPlayer();

    //------ Variables ------
    /** Integer variable used to store data from user input to send as parameter for
     * Connect4Logic methods as column integer.
     */
    static int columnNum;

    /** Initialization of Scanner object to allow user-input functionality. */
    static Scanner input = new Scanner(System.in);

    /**String variable referring to player playing against computer if 'P', 'C' if playing another player. **/
    public static String computerPlayer;


    /** Main method which uses gameLoop() method to bring core/UI functionality to console.
     *
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
        gameLoop();
    }



    //------ Methods ------

    /** Void method which iterates through Connect4Logic's columnArray and
     * print out text-representation of Connect4 "board". Displays Xs and Os
     * as "placed" by user.
     */
    public static void displayContents() {
        int printingRow = 5;

        while (printingRow >= 0) {
            System.out.print("|");

            for (int i = 0; i < board.columnArray.length; i++)
                System.out.print(" " + columnArray[i][printingRow] + " |");
            System.out.print("\n");

            printingRow--;
        }
    }

    /** Void method which prompts user for console input to decide whether to play against
     * another player or against the computer. This decision is kept as a String in the
     * computerPlayer variable. Avoids IllegalArgumentExceptions by validating input, ignoring case
     * of letters.
     *
     */
    public static void gamemodeSelection() {
        System.out.println("Begin game. Enter 'P' if you want to play against another player; enter 'C' to play against computer.");

        computerPlayer = input.nextLine();

        boolean pass = false;

        while (!(computerPlayer.equalsIgnoreCase("P")) && !(computerPlayer.equalsIgnoreCase("C"))) {
            System.out.println("Invalid input. Enter Enter 'P' if you want to play against another player; enter 'C' to play against computer.");
            System.out.println("Computer player: " + computerPlayer);
            computerPlayer = input.nextLine();
        }
    }

    /** Void method which dictates player's turn using playerTurn variable. Prompts user to play against computer or another player.
     * Then prompts user in console for user input and minor instruction. Changes name of user if
     * it is a player vs computer game, rather than differentiating between PlayerX and PlayerO.
     *
     * @param playerTurn Variable used to get playerTurn variable from
     *                   Connect4Logic and dictate oddness/evenness of
     *                   integer and change name of player in result between
     *                   X and O.
     */
    public static void getPlayerInput(int playerTurn){
            /** String method variable used to store name of player based on
             * playerTurn variable.
             */
            String playerName;

            //if playing against the computer
            if (computerPlayer.equalsIgnoreCase("C"))
                playerName = "Player";
            //if playing against another player
            else if (playerTurn % 2 != 0)
                playerName = "PlayerX";
            else
                playerName = "PlayerO";

            System.out.println(playerName + "-your turn. Choose a column from 1-7.");
            //subtracting 1 to match array structure of 0-6
            columnNum = input.nextInt() - 1;
            while ((columnNum) < 0 || columnNum > 6) {
                System.out.println("Input must be an integer 1-7. Please reenter.");
                columnNum = input.nextInt() - 1;
            }

            while (columnArray[columnNum][5] == "X" || columnArray[columnNum][5] == "O") {
                System.out.println("This column is full. Please choose another column 1-7.");
                columnNum = input.nextInt() - 1;
            }
    }

    /** Method that iterates through board.columnArray to return
     * how many columns are full. Used to check for a draw (board is full, nobody wins).
     *
     * @return nonEmptyColumns integer number of columns that are full have have no space left.
     */
    public static int gameDraw() {
        //integer variable dictating how many columns are full.
        int nonEmptyColumns = 0;

        for (int i = 0; i < columnArray.length; i++) {
            //if the currently iterated column is full, increment nonEmptyColumns
            if (columnArray[i][5] != " ")
                nonEmptyColumns++;
        }

        return nonEmptyColumns;
    }

    /** Prompts console user to choose between using console or GUI to play game
     * Returns true if user chooses to play on GUI based on String input.
     *
     * @return boolean true if user inputs GUI to play on GUI, false if they choose console.
     * @throws IllegalArgumentException if user provides invalid input.
     */
    public static boolean guiSelect() throws IllegalArgumentException {
        String selectGUI = "";
        System.out.println("Would you like to play using console or GUI?" +
                "\n Print \"console\" or \"GUI\".");
        selectGUI = input.nextLine();

        if (selectGUI.equalsIgnoreCase("GUI")) {
            return true;
        }
        else if (selectGUI.equalsIgnoreCase("console"))
            return false;
        else
            throw new IllegalArgumentException("Invalid input was provided.");

    }

    /** Method implementing methods from Connect4Logic, Connect4ComputerPlayer, and Connect4TextConsole
     * classes to bring core and UI functionality together. First checks returning boolean for
     * guiSelect() to choose between GUI or console interface for user.
     * Then runs gamemodeSelection() to decide if it is a user is playing against a player
     * or computer. Features a loop which increments playerTurn variable (to vary between odd and even)
     * and stops loop when win condition is found (puckCount == 4). At beginning of turn (loop),
     * conditions dictate if it is a player vs player game (and requires input regardless), or PvC game,
     * which requires input if it is the player's turn or the computer makes a move if it is not the
     * player's turn.
     *
     */
    public static void gameLoop() {
        int endDraw = 0;
        if (guiSelect() == true) {
            System.out.println("\nSwitching to GUI interface.\n");
            Application.launch(Connect4GUI.class);

            return;
        }

        gamemodeSelection();

        //while win/draw conditions are not met
        while (board.puckCount != 4 && endDraw != 7) {

            //if it is a PvP game, or it is against the computer and it is the player's turn.
            if (computerPlayer.equalsIgnoreCase("P") || playerTurn % 2 != 0)
                getPlayerInput(board.playerTurn);
            //if it is not a PvP game and it is not the player's turn
            else
                columnNum = CPU.makeMove();
            System.out.println("");
            board.placePuck(columnNum, board.playerTurn);
            displayContents();
            board.checkPucks(columnNum);
            if (board.puckCount != 4)
                board.playerTurn++;

            //check for draw
            endDraw = gameDraw();
        }

        if (endDraw == 7)
            System.out.println("Draw/tie game. Nobody wins.");
        else if (board.playerTurn % 2 != 0 && computerPlayer.equalsIgnoreCase("P"))
            System.out.println("Player X won the game.");
        else if (computerPlayer.equalsIgnoreCase("P"))
            System.out.println("Player O won the game.");
        else if (computerPlayer.equalsIgnoreCase("C")) {
            if (board.playerTurn % 2 != 0)
                System.out.println("Player X won the game.");
            else
                System.out.println("Computer won the game.");
        }
    }

} //End class