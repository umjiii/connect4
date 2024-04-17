/** Description: Backend logic simulating Connect 4 board game. Includes methods that simulate placing a "puck" (or coin, gamepiece, etc.)
 * represented by X or Os for each player respectively, a method for analyzing the board to find pucks placed 4-in-a-row (the condition to win
 * the game) in all relevant directions (downwards, horizontally, diagonally). Board is simulated using a 7x6 array of arrays, and data is
 * placed at index 0 of each column (or index 0 of one of 7 inner arrays). Win condition, four in a row, is kept track of using puckCount variable.
 *
 *
 * @author Stephen Arel
 * @version 1.0 3/22/2023
*/
package core;

import java.util.Arrays;

/** Class which features essential methods and variables to create base function of game. */
public class Connect4Logic {

    //------ Variables ------
    /** Integer variable used to keep track of "win condition" of puckCount == 4. */
    public int puckCount;

    /** Integer variable used to keep track of rows in which "pucks" are placed. */
    public int row;

    /** Integer variable used to keep track of which of two player's turn it is. Odd numbers represent Player X's turn
     * while even numbers represent Player O.
     */
    public static int playerTurn = 1;

    /** Array of arrays used to simulate 7x6 grid pattern of board: 7 columns, 6 rows. */
    //7x6 array of arrays; 7 columns, 6 rows
    public static String[][] columnArray = new String[7][6];

    /** String variable alternately equal to "X" or "O" used to simulate the insertion of "pucks" in columns. */
    public String puckInput;

    //------ Constructor ------

    /** Default constructor for class object. Fills columnArray with String " " in each inner element. */
    public Connect4Logic() {

        //fill array with spaces
        for (int i = 0; i < columnArray.length; i++) {
            for (int j = 0; j < columnArray[i].length; j++) {
                columnArray[i][j] = " ";
            }
        }
    }


    //------ Methods ------

    /** Void method which resets row variable to 0 and uses puckInput String to place into an inner array element
     * to simulate placing of "pucks" falling to bottom of column. Places X or O based on playerTurn modulus function.
     *
     * @param column Variable stating which inner array is used to fill with puckInput String.
     * @param playerTurn Variable dictating which player's "puck", represented by puckInput = "X" or "O", is placed in inner arrays.
     */
    public void placePuck(int column, int playerTurn) {

        //reset row "cursor"
        row = 0;

        //If playerTurn is odd, then it will be Player X's turn. Xs will be used for pucks
        if (playerTurn % 2 != 0)
            puckInput = "X";
            //If playerTurn is even, then it will be Player O's turn. Os will be used for pucks
        else
            puckInput = "O";

        //if column is full, do nothing
        if (columnArray[column][5] != " ")
            return;

        //starting from bottom, while row is not empty, "point" to the next row above
        while (columnArray[column][row] != " " && row < 6) {
            row++;
        }

        //enter "X" or "O" in spot
        columnArray[column][row] = puckInput;

    }

    /** Method used to check for "four in a row" win-condition of game. Iterates through inner arrays with appropriate bounds
     * to find String inputs of same type (X's or O's) consecutively four times vertically, horizontally, or diagonally.
     * Diagonal checks are used to check both right to left and left to right, both from "bottom" (index 0) to "top" (index 5)
     * of inner arrays.
     *
     * @param targetColumn Variable used in vertical-check as starting point from which to count down from (index of highest element in inner array
     *                    to 0 or until non-matching String is found).
     */
    public void checkPucks(int targetColumn) {
        //check vertically for 4 in a row
        //if it is possible for 3 more pucks to be below
        if (row >= 3) {

            //this doesn't count the placed puck, so 1 is added to account for the most recent puck placed
            puckCount = 1;

            for (int i = row - 1; i >= 0; i--) {
                //check each puck below. if it matches, add to the count
                if (columnArray[targetColumn][i] == puckInput && puckCount < 4) {
                    puckCount++;
                }

                //if 4 in a row, stop searching
                else if (puckCount == 4)
                    break;

                    //if puck count is not 4 and there are no more matches, reset the count and move on
                else {
                    puckCount = 0;
                    break;
                }
            }
        }

        //check horizontally for 4 in a row
        //if 4 in a row haven't been found yet
        if (puckCount != 4) {
            //go through each column to check the same row
            for (int i = 0; i < columnArray.length; i++) {
                if (columnArray[i][row] == puckInput && puckCount < 4) {
                    puckCount++;
                }

                //if 4 in a row, stop searching
                else if (puckCount == 4)
                    break;

                    //if puck count is not 4 and there are no matches, reset the count and move on
                else {
                    puckCount = 0;
                    break;
                }
            }
        }

        //check diagonally, up and to the right
        if (puckCount != 4) {

            //check each diagonal-line
            for (int i = 0; i <= 3; i++) {
                for (int j = 0; j < 3; j++) {

                    //if this element and the 3 diagonally up-right to it are equal to the last puck placed, set puckCount to 4
                    if (columnArray[i][j] == puckInput && puckCount < 4) {
                        if (columnArray[i + 1][j + 1] == puckInput && columnArray[i + 2][j + 2] == puckInput && columnArray[i + 3][j + 3] == puckInput)
                            puckCount = 4;
                    }

                    //if 4 in a row, stop searching
                    else if (puckCount == 4) {
                        break;
                    }

                    //if puck count is not 4 and there are no matches, reset the count and check next line
                    else {
                        puckCount = 0;
                        break;
                    }
                }
            }
        }

        //check diagonally, up and to the left
        if (puckCount != 4) {

            //check each diagonal-line
            //iterate from bottom right of board
            for (int i = (columnArray.length - 1); i >= 3; i--) {
                for (int j = 0; j < 3; j++) {

                    //if this element and the 3 diagonally up-right to it are equal to the last puck placed, set puckCount to 4
                    if (columnArray[i][j] == puckInput && puckCount < 4) {
                        if (columnArray[i - 1][j + 1] == puckInput && columnArray[i - 2][j + 2] == puckInput && columnArray[i - 3][j + 3] == puckInput)
                            puckCount = 4;
                    }

                    //if 4 in a row, stop searching
                    else if (puckCount == 4) {
                        break;
                    }

                    //if puck count is not 4 and there are no matches, reset the count and check next line
                    else {
                        puckCount = 0;
                        break;
                    }
                }
            }
        }
    }

    /** Method used for testing to set puckInput variable to specified String value.
     *
     * @param input specified String value to set puckInput to.
     */
    public void setPuckInput(String input) {
        puckInput = input;
    }

    /** Method used for testing to set board slots to " " and set each variable to a default value. */
    public void resetBoard() {
        for (int i = 0; i < columnArray.length; i++) {
            for (int j = 0; j < columnArray[i].length; j++) {
                columnArray[i][j] = " ";
            }
        }
        playerTurn = 1;
        puckInput = "X";
        puckCount = 0;
        row = 0;
    }
}