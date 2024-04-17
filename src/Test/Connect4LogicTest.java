/** Description: JUnit4 Test class that creates test objects and comparison objects
 * to test each method in the Connect4Logic class, including those made to make
 * testing easier. This includes placePuck(), checkPucks(), setPuckInput(),
 * and resetBoard().
 *
 * Author: Stephen Arel
 * Version: 1.0 4/16/2024
 */
package Test;

import core.Connect4Logic;
import org.junit.Test;

import static org.junit.Assert.*;

/** Test class made to initialize test objects and run test methods,
 * including setUp() and tearDown().
 */
public class Connect4LogicTest {
    /** Connect4Logic object used to test methods and array functionality as game board. **/
    private Connect4Logic testBoard;
    /** Array of String arrays used to represent "empty" board. **/
    private String[][] emptyBoard = new String[7][6];

    /** Before testing occurs, this method initializes the testBoard object.
     *
     * @throws Exception
     */
    @org.junit.Before
    public void setUp() throws Exception {
        testBoard = new Connect4Logic();
    }

    /** After testing occurs, sets testBoard object reference to null.
     *
     * @throws Exception
     */
    @org.junit.After
    public void tearDown() throws Exception {
        testBoard = null;
    }

    /** Tests 4 cases of puck placement:
     * 1. Place player X puck in empty column.
     * 2. Place player O in empty column.
     * 3. Place one player's puck on top of another's.
     * 4. Try to place puck in full column - nothing should be added,
     *      shown by top puck remaining the same, despite a different
     *      puck being used to place into full column.
     *
     * Also tests that puckInput corresponds to that of the correct player
     *      (X -> "X", O -> "O").
     */
    @org.junit.Test
    public void testPlacePuck() {
        testBoard.placePuck(0, 1);
        assertEquals("X", testBoard.puckInput);
        assertEquals("X", testBoard.columnArray[0][0]);

        testBoard.placePuck(1, 2);
        assertEquals("O", testBoard.puckInput);
        assertEquals("O", testBoard.columnArray[1][0]);
        testBoard.placePuck(1, 1);
        assertEquals("X", testBoard.columnArray[1][1]);

        testBoard.placePuck(0, 2);
        assertEquals("O", testBoard.columnArray[0][1]);

        //fill rest of column 0
        testBoard.placePuck(0, 1);
        testBoard.placePuck(0, 1);
        testBoard.placePuck(0, 1);
        testBoard.placePuck(0, 1);

        //try to place puck in full column
        testBoard.placePuck(0, 2);
        assertEquals("X", testBoard.columnArray[0][5]);

    }

    /** Tests several test cases of how pucks can be checked for four in a row
     *      by asserting that puckCount is 4 (or 0 for last case).
     * 1. 4 in a row vertically.
     * 2. 4 in a row for a different player
     *      (showing that the player doesn't matter for how pucks are checked).
     * 3. 4 in a row diagonall from the bottom left to upper right.
     * 4. 4 in a row diagonally from the bottom right to upper left.
     * 5. 0 in a row when there is not 4 in a row on the board.
     *
     * Also tests that the last row being analyzed is the last row to be placed in,
     *      to show functionality of vertical checks.
     */
    @org.junit.Test
    public void testCheckPucks() {
        //****** Vertical Checks ******
        //place 4 in a row, check 4 in a row for player X
        testBoard.placePuck(0, 1);
        testBoard.placePuck(0, 1);
        testBoard.placePuck(0, 1);
        testBoard.placePuck(0, 1);
        testBoard.checkPucks(0);
        //row should be at 3
        assertEquals(3, testBoard.row);
        //4 in a row should be counted in puckCount
        assertEquals(4, testBoard.puckCount);

        //check that this works for other player
        //set puckInput to O to check for player O pucks
        testBoard.setPuckInput("O");
        //check the same column X placed in
        testBoard.checkPucks(1);
        //row should be set to 3
        assertEquals(3, testBoard.row);
        //0 in a row for player O should be counted
        assertEquals(0, testBoard.puckCount);

        //****** Diagonal checks ******
        //Diagonal check for up-right
        testBoard.resetBoard();
        testBoard.placePuck(0, 1);
        testBoard.placePuck(1, 2);
        testBoard.placePuck(1, 1);
        testBoard.placePuck(2, 2);
        testBoard.placePuck(2, 2);
        testBoard.placePuck(2, 1);
        testBoard.placePuck(3, 2);
        testBoard.placePuck(3, 2);
        testBoard.placePuck(3, 2);
        testBoard.placePuck(3, 1);
        testBoard.setPuckInput("X");
        testBoard.checkPucks(0);
        assertEquals(4, testBoard.puckCount);
        testBoard.resetBoard();

        //Diagonal check for up-left
        testBoard.placePuck(6, 1);
        testBoard.placePuck(5, 2);
        testBoard.placePuck(5, 1);
        testBoard.placePuck(4, 2);
        testBoard.placePuck(4, 2);
        testBoard.placePuck(4, 1);
        testBoard.placePuck(3, 2);
        testBoard.placePuck(3, 2);
        testBoard.placePuck(3, 2);
        testBoard.placePuck(3, 1);
        testBoard.checkPucks(0);
        assertEquals(4, testBoard.puckCount);
        testBoard.resetBoard();

        testBoard.checkPucks(0);
        assertEquals(0, testBoard.puckCount);
    }

    /** Tests setPuckInput() method by using method and checking
     *      puckInput String.
     */
    @Test
    public void testSetPuckInput() {
        testBoard.setPuckInput("test");
        assertEquals("test", testBoard.puckInput);
    }

    /** Tests resetBoard() method by populating board, calling method,
     * then testing for following conditions:
     * 1. Each element on the board is empty/equals " ".
     * 2. playerTurn = 0
     * 3. puckInput = "X"
     * 4. row = 0
     *
     */
    @Test
    public void testResetBoard() {
        //Fill emptyArray with same placeholder values used in Connect4Logic
        for (int i = 0; i < emptyBoard.length; i++) {
            for (int j = 0; j < emptyBoard[i].length; j++) {
                emptyBoard[i][j] = " ";
            }
        }

        testBoard.placePuck(0, 1);
        testBoard.placePuck(0, 1);
        testBoard.placePuck(0, 1);
        testBoard.placePuck(0, 1);

        testBoard.resetBoard();
        //assert that each column is filled with " " as should be
        assertArrayEquals(emptyBoard[0], testBoard.columnArray[0]);
        assertArrayEquals(emptyBoard[1], testBoard.columnArray[1]);
        assertArrayEquals(emptyBoard[2], testBoard.columnArray[2]);
        assertArrayEquals(emptyBoard[3], testBoard.columnArray[3]);
        assertArrayEquals(emptyBoard[4], testBoard.columnArray[4]);
        assertArrayEquals(emptyBoard[5], testBoard.columnArray[5]);
        assertArrayEquals(emptyBoard[6], testBoard.columnArray[6]);

        assertEquals(1, testBoard.playerTurn);
        assertEquals("X", testBoard.puckInput);
        assertEquals(0, testBoard.puckCount);
        assertEquals(0, testBoard.row);

    }
}