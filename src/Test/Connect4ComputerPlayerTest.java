/** JUnit4 test class that creates test objects and checks for the only method in
 * Connect4ComputerPlayer class.
 */
package Test;

import core.Connect4ComputerPlayer;
import core.Connect4Logic;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import static org.junit.Assert.*;

/** Test class made to initialize test objects and run test method for only
 * method, makeMove(), as well as including setUp() and tearDown().
 */
public class Connect4ComputerPlayerTest {

    /** Connect4Logic object used as a "board" for testCPU to make moves on. **/
    Connect4Logic testBoard;
    /** Connect4ComputerPlayer object to test class and call makeMove() method. **/
    Connect4ComputerPlayer testCPU;

    /** Integer variable used to hold integer return of makeMove(). **/
    int testColumnNum = -1;
    /** Boolean variable set to true if random integer falls out of bounds. **/
    boolean boundsTestFailed = false;

    /** Before testing occurs, this method initializes the necessary objects
     * to call methods, as well as prepares testBoard for moves to be made.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        testBoard = new Connect4Logic();
        testBoard.resetBoard();
        testCPU = new Connect4ComputerPlayer();
    }

    /** After testing occurs, sets tested objects reference to null.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        testBoard = null;
        testCPU = null;
    }

    /** Test method that checks that 1000 random moves made do not
     * fall out of bounds (inclusive 0 to inclusive 6). If out of bounds,
     * boundsTestFailed is set to true, the loop breaks, and the test fails.
     */
    @Test
    public void testMakeMove() {
        for (int i = 0; i < 1000; i++) {
            testColumnNum = testCPU.makeMove();
            if (testColumnNum < 0 || testColumnNum > 6) {
                boundsTestFailed = true;
                break;
            }
        }

        assertEquals(false, boundsTestFailed);
    }
}