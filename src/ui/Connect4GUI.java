/** Description: Front-end logic used to simulate board layout. Extends
 * Application class and utilizes core.Connect4Logic and core.Connect4ComputerPlayer
 * classes to provide backend logic of game operation and an automated player.
 * Uses JavaFX to provide GUI layout featuring buttons to select PvE/PvP, columns during play,
 * and TextFields to display player turns or the board contents itself.
 * Also includes methods used to get/set object values and states.
 *
 * @author Stephen Arel
 * @version 1.0 4/7/2024
 */

package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import core.Connect4Logic;
import core.Connect4ComputerPlayer;

/** Class which extends Application to provide functionality as JavaFX app.
 *  Uses various methods to get/set object values and states, inner classes to
 *  handle ActionEvents (clicking buttons). Also includes main method which
 *  launches application.
 *
 */
public class Connect4GUI extends Application {
    /** Boolean variable equal to true if the session is a PvE game, false if PvP. */
    boolean computerPlayer;
    /** Boolean variable to hold state of whether or not either PvE or PvP has been selected. */
    boolean selectionMade;
    /** Boolean variable used to hold state of whether or not the win conditions have been met yet. */
    boolean gameWon;
    /** Integer variable used to store data from user/CPU input to send as parameter for
     * Connect4Logic methods as column integer.
     */
    int columnNum = 0;

    /** Declaration of Connect4Logic object to allow access to class methods and variables. */
    Connect4Logic board = new Connect4Logic();
    /** Declaration of Connect4ComputerPlayer object to allow access to class method makeMove(). */
    Connect4ComputerPlayer CPU = new Connect4ComputerPlayer();

    /** Array containing buttons used to select columns. */
    Button[] buttonArray = new Button[7];
    /** TextField array used to deep copy of values from board.columnArray.
     *  Simulates 7x6 grid pattern of board.
     */
    TextField[][] boardDisplay = new TextField[7][6];
    /** TextField object which displays which player's turn it is. */
    TextField playerTurnLog = new TextField();
    /** Button which, when clicked, makes game session a PvP game. */
    Button playerButton = new Button("Play Against Player");
    /** Button which, when clicked, makes game session a PvE game. */
    Button cpuButton = new Button("Play Against CPU");

    /** Start method which starts Stage object. Initializes different Pane/Button/TextField objects
     * and modifies their properties to provide layout of game. This includes PvE/PvP selection buttons,
     * boardDisplay to display board contents provided in Connect4Logic, and a log at the bottom
     * to display which player's turn it is.
     *
     * @param primaryStage primary stage for the application on which scenes (then panes) are placed.
     */
    public void start(Stage primaryStage) {


        //Create a root pane and place it in a scene
        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane);

        //Create other panes with properties
        rootPane.setMinWidth(400);

        GridPane boardPane = new GridPane();
        boardPane.setPadding(new Insets(20, 20, 20, 20));
        boardPane.setAlignment(Pos.CENTER);
        boardPane.setHgap(5);
        boardPane.setVgap(5);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(8);

        VBox logBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);

        //Set root pane properties
        rootPane.setTop(buttonBox);
        rootPane.setCenter(boardPane);
        rootPane.setBottom(logBox);

        //------ Player Turn Log ------
        logBox.getChildren().add(playerTurnLog);
        playerTurnLog.setAlignment(Pos.CENTER);
        playerTurnLog.setFont(new Font("Helvetica", 25));

        //------ Buttons ------
        //Button for choosing between PvP or PvE
        buttonBox.getChildren().addAll(cpuButton, playerButton);
        //Setting button properties
        playerButton.setPrefWidth(134);
        cpuButton.setPrefWidth(134);

        //Set event handlers
        cpuButton.setOnAction(new playerSelectHandler());
        playerButton.setOnAction(new playerSelectHandler());


        //Create buttons for selecting columns
        for (int i = 0; i < buttonArray.length; i++)
            buttonArray[i] = new Button(Integer.toString(i+1));


        //Put buttons on board
        for (int i = 0; i < buttonArray.length; i++) {
            boardPane.add(buttonArray[i], i, 0);
            buttonArray[i].setOnAction(new columnSetHandler());

            //Disable buttons until PvP or PvE is selected
            buttonArray[i].setDisable(true);
        }


        //------ Column Display ------
        int flippedJiter;
        //Add Textfields
        for (int i = 0; i < boardDisplay.length; i++)
        {
            for (int j = 0; j < boardDisplay[i].length; j++) {
                boardDisplay[i][j] = new TextField(" ");

                //TextField properties
                boardDisplay[i][j].setEditable(false);
                boardDisplay[i][j].setPrefSize(10, 10);

                //Place on display in correct order relative to JavaFX coorindate system
                flippedJiter = Math.abs(j - 5) + 1;
                boardPane.add(boardDisplay[i][j], i, flippedJiter);
            }
        }


        //Place scene in stage
        primaryStage.setTitle("Connect 4");
        primaryStage.setScene(scene);
        primaryStage.show();

    } //end stage start



    //------ Helper Methods for Event Handling ------

    /** Void method routinely called to deep copy board.columnArray contents
     *  to boardDisplay in GUI.
     *
     * @param boardDisplay array on which contents of columnArray will be deep copied.
     */
    public void refreshBoardDisplay(TextField[][] boardDisplay) {
        //iterate through boardDisplay and copy contents from board object array to display
        for (int i = 0; i < boardDisplay.length; i++) {
            for (int j = 0; j < boardDisplay[i].length; j++) {
                //set each TextField text to board contents
                boardDisplay[i][j].setText(board.columnArray[i][j]);
            }
        }
    }

    /** Void method which disables functionality of column buttons.
     *
     */
    public void enableColumnButtons() {
        for (int i = 0; i < this.buttonArray.length; i++)
            buttonArray[i].setDisable(false);
    }

    /** Method used to retrieve playTurnLog object.
     *
     * @return playerTurnLog for the purpose of modification.
     */
    public TextField getPlayerTurnLog() {
        return this.playerTurnLog;
    }

    /** Method used to retrieve computerPlayer value.
     *
     * @return boolean value of computerPlayer object.
     */
    public boolean getComputerPlayerStatus() {
        return this.computerPlayer;
    }

    /** Void method used to set the boolean value of computerPlayer object.
     *
     * @param parity boolean value to which computerPlayer is set.
     */
    public void setComputerPlayerStatus(boolean parity) {
        this.computerPlayer = parity;
    }

    /** Method used to retrieve playerButton object.
     *
     * @return playerButton object for the purpose of modification.
     */
    public Button getPVPbutton() {
        return this.playerButton;
    }

    /** Method used to retrieve cpuButton object.
     *
     * @return cpuButton object for the purpose of modification.
     */
    public Button getPVEbutton() {
        return this.cpuButton;
    }

    /** Method used to retrieve selectionMade value.
     *
     * @return boolean object of selectionMade object.
     */
    public boolean getSelectionMade() {
        return this.selectionMade;
    }

    /** Void method used to set the boolean value of selectionMade object.
     *
     * @param parity boolean value to which selectionMade is set.
     */
    public void setSelectionMade(boolean parity) {
        this.selectionMade = parity;
    }

    /** Method which iterates through board.columnArray elements and checks top space
     *  to see if it is "empty".
     *
     * @return true if each column is full, false if at least one column has space.
     */
    public boolean getBoardFullStatus() {
        int fullColumns = 0;

        for (int i = 0; i < board.columnArray.length; i++) {
            //if a column does not have an empty space on the top
            if (!board.columnArray[i][5].equals(" "))
                fullColumns++;
        }
        //if all columns are full, return true
        if (fullColumns == 7)
            return true;
        //if some are not full, return false
        else
            return false;
    }

    /** Checks top element of specified column to see if it has room.
     *
     * @param targetColumn column to check for room
     * @return false if the column has room, true if it is full
     */
    public boolean getColumnFullStatus(int targetColumn) {
        //if the target column is not full
        if (board.columnArray[targetColumn][5].equals(" "))
            return false;
        //if the target column is full
        else
            return true;
    }



    //------ Event Handlers ------
    //Event handler for player/computer player selection

    /** Description: Inner class which "hides" one of two buttons pressed (the one which is not clicked).
     * Implements EventHandler<T> class for an ActionEvent to provide event handling functionality.
     */
    class playerSelectHandler implements EventHandler<ActionEvent> {

        /** Void method which handles actionEvent (button clicking).
         * If one of two buttons has not already been clicked, then checks to see which button
         * was clicked. If playerButton is clicked, computerPlayer is set to false, column buttons
         * are enabled, and the cpuButton is disabled/hidden.
         * If the cpuButton is clicked, computerPlayer is set to true, column buttons are enabled,
         * and the playerButton is disabled/hidden.
         *
         * @param actionEvent event which has occured (button is clicked)
         */
        @Override
        public void handle(ActionEvent actionEvent) {
            if (getSelectionMade() == false) {
                //if the PVP button was clicked
                if (((Button)(actionEvent.getSource())).equals(getPVPbutton())) {
                    getPVEbutton().setDisable(true);
                    getPVEbutton().setVisible(false);
                    setComputerPlayerStatus(false);
                    enableColumnButtons();

                    ((Button)(actionEvent.getSource())).setDisable(true);

                    getPlayerTurnLog().setText("Player X's turn.");
                }
                //if the PVE button was clicked
                else {
                    getPVPbutton().setDisable(true);
                    getPVPbutton().setVisible(false);
                    setComputerPlayerStatus(true);

                    ((Button)(actionEvent.getSource())).setDisable(true);

                    enableColumnButtons();
                }
            }
            else
                return;
        }
    }

    /** Description: Inner class which handles gameplay loop everytime an action event (column button)
     * is clicked. Contains handle() method which implements this functionality.
     * Implements EventHandler<T> class for an ActionEvent to provide event handling functionality.
     */
    class columnSetHandler implements EventHandler<ActionEvent> {

        /** Void method which handles gameplay loop functionality on click of button.
         * Gets column number based on which button was pressed (0-6), checks if the game
         * has already been won/drawn and sends a message for a draw game and ceasing functionality
         * thereafter.
         * If neither condition is true, then checks for PvP/PvE game. For PvP, it checks for a full
         * column at the column number specified, places the puck (if not full), checks for
         * four in a row, then refreshes the board display before iterating to the next player's turn.
         * Finally, the playerTurnLog is updated to reflect which player's turn it is.
         * For PvE, it functions similarly, except after the player's turn is handled, another
         * turn based on the CPU.makeMove() method is made. Four in a row is checked again,
         * and if the computer wins, the console outputs such a message and buttons
         * cease functionality. If a player wins, it is specified in the console as well and buttons
         * are disabled again.
         *
         * @param actionEvent event which has occured (button is clicked)
         */
        @Override
        public void handle(ActionEvent actionEvent) {
            //Get column num from source button's text
            int columnNum = Integer.parseInt(((Button) (actionEvent.getSource())).getText()) - 1;

            //if game is already won
            if (gameWon == true)
                return;

            //if the board is full
            else if (getBoardFullStatus() == true) {
                System.out.println("The board is full and nobody wins. Draw!");
                return;
            }

            //if game is not won yet
            else {
                //if PvP
                if (getComputerPlayerStatus() == false) {

                    //if the column is full, do nothing
                    if (getColumnFullStatus(columnNum) == true) {
                        System.out.println("Column " + (columnNum + 1) + " is full");
                        return;
                    }
                    //if the column has room, place puck for player respective to playerTurn
                    else
                        board.placePuck(columnNum, board.playerTurn);

                    //check the board for 4 in a row
                    board.checkPucks(columnNum);

                    //Refresh board display
                    refreshBoardDisplay(boardDisplay);

                    //if a player won
                    if (board.puckCount == 4) {
                        //if player X won
                        if (board.playerTurn % 2 != 0)
                            System.out.println("Player X won the game.");

                        //if player O won
                        else
                            System.out.println("Player O won the game.");

                        //set gameWon condition to true (so buttons don't do anything afterwards)
                        gameWon = true;
                        return;
                    }

                    else if (getBoardFullStatus() == true) {
                        System.out.println("The board is full and nobody wins. Draw!");
                        return;
                    }

                    //if nobody won yet, switch player turns and move on
                    else
                        board.playerTurn++;

                    //if it is player x's turn
                    if (board.playerTurn % 2 != 0)
                        getPlayerTurnLog().setText("Player X's turn.");
                    else
                        getPlayerTurnLog().setText("Player O's turn.");
                }



                //if PvE
                else {
                    //if the column is full, do nothing
                    if (getColumnFullStatus(columnNum) == true) {
                        System.out.println("Column " + (columnNum + 1) + " is full");
                        return;
                    }
                    //if the column has room, place puck for player respective to playerTurn
                    else
                        board.placePuck(columnNum, board.playerTurn);

                    //Refresh board display
                    refreshBoardDisplay(boardDisplay);

                    //check the board for 4 in a row
                    board.checkPucks(columnNum);

                    //check if the player has won
                    if (board.puckCount == 4) {
                        System.out.println("The player has won the game.");
                        gameWon = true;
                        return;
                    }

                    else if (getBoardFullStatus() == true) {
                        System.out.println("The board is full and nobody wins. Draw!");
                        return;
                    }

                    //if nobody has won yet and the board has room
                    else {
                        board.playerTurn++;

                        columnNum = CPU.makeMove();
                        board.placePuck(columnNum, board.playerTurn);

                        //Refresh board display
                        refreshBoardDisplay(boardDisplay);

                        board.checkPucks(columnNum);

                        //check if the computer has won
                        if (board.puckCount == 4) {
                            System.out.println("The computer has won the game.");
                            gameWon = true;
                            return;
                        }

                        else
                            board.playerTurn++;
                    }
                }

            } //end gameWon check
        } //end handle() method

    } //end event handler inner class

    /** Main method which uses Application class to launch JavaFX application.
     *
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
        launch(args);
    }
}


