/** Description: Back-end logic used to simulate function of computer to play against player in
 * Connect4 game. Uses java.util.Random to randomly choose a column to place puck in.
 *
 * @author Stephen Arel
 * @version 1.0 3/29/2023
 *
 */
package core;

import java.util.Arrays;
import java.util.Random;

/** Class which features random number generation using Random class and makeMove() method
 * to randomly choose columns.
 */
public class Connect4ComputerPlayer {

    //------ Variables ------
    /** Integer variable used to hold number of randomly chosen column 0-6. */
    int columnNumber;
    /** Declaration of Random object to allow access to random number generation methods. */
    private Random rand = new Random();

    /** Reference variable pointing to Connect4Logic.columnArray to allow reading of "emptiness"
     * of chosen columns (i.e. if there is a space available on the board to place a puck
     * at some column).
     * */
    String[][] gameBoard = Connect4Logic.columnArray;

    //------ Methods ------

    /** Method using Random rand object to randomly choose a column number in range 0 (inclusive) to
     * 7 (exclusive). while() loop used to check if chosen column has space to place a puck;
     * if not, a new column is chosen.
     *
     * @return columnNumber: used in Connect4TextConsole to assign columnNum (target column)
     *                      randomly assigned number of non-full column.
     *
     */
    public int makeMove() {
        columnNumber = rand.nextInt(7);
        //if randomly chosen column is full, then continue to randomly choose one until that column is not full.
        while (!(gameBoard[columnNumber][5].equals(" "))) {
            columnNumber = rand.nextInt(7);
        }

        System.out.println("The computer is choosing column " + (columnNumber + 1) + "\n");
        return columnNumber;
    }


}