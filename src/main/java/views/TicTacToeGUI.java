package views;

/**
 * Play TicTacToe the computer that can have different AIs to beat you. 
 * Select the Options menus to begin a new game, switch strategies for 
 * the computer player (BOT or AI), and to switch between the two views.
 * 
 * This class represents an event-driven program with a graphical user 
 * interface as a controller between the view and the model. It has 
 * event handlers to mediate between the view and the model.
 * 
 * This controller employs the Observer design pattern that updates two 
 * views every time the state of the tic tac toe game changes:
 * 
 *    1) whenever you make a move by clicking a button or an area of either view
 *    2) whenever the computer AI makes a move
 *    3) whenever there is a win or a tie
 *    
 * You can also select two different strategies to play against from the menus
 * 
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.IntermediateAI;
import model.OurObserver;
import model.RandomAI;
import model.TicTacToeGame;

public class TicTacToeGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private TicTacToeGame theGame;
    private MenuBar menuBar;
    private OurObserver currentView;
    private OurObserver buttonView;
    private OurObserver textAreaView;
    private BorderPane window;
    public static final int width = 254;
    public static final int height = 360;

    private MenuItem newGame;
    private MenuItem randAI;
    private MenuItem interAI;
    private MenuItem button;
    private MenuItem text;

    public void start(Stage stage) {

        stage.setTitle("Tic Tac Toe");
        window = new BorderPane();

        handleMenuBar();
        window.setTop(menuBar);

        Scene scene = new Scene(window, width, height);
        initializeGameForTheFirstTime();

        theGame.addObserver(buttonView);
        theGame.addObserver(textAreaView);

        registerHandlers(stage);
        stage.setScene(scene);
        stage.show();

    }

    private void handleMenuBar() {
        menuBar = new MenuBar();
        Menu options = new Menu("Options");

        newGame = new MenuItem("New Game");

        Menu strategy = new Menu("Strategies");
        randAI = new MenuItem("RandomAI");
        interAI = new MenuItem("IntermediateAI");
        strategy.getItems().addAll(randAI, interAI);

        Menu views = new Menu("Views");
        button = new MenuItem("Button");
        text = new MenuItem("TextArea");
        views.getItems().addAll(button, text);

        options.getItems().addAll(newGame, strategy, views);
        menuBar.getMenus().add(options);

    }

    /**
     * Set the game to the default of an empty board and the random AI.
     */
    public void initializeGameForTheFirstTime() {
        theGame = new TicTacToeGame();
        // This event driven program will always have
        // a computer player who takes the second turn
        textAreaView = new TextAreaView(theGame);
        buttonView = new ButtonView(theGame);
        setViewTo(textAreaView);
        theGame.setComputerPlayerStrategy(new RandomAI());
    }

    private void setViewTo(OurObserver newView) {
        window.setCenter(null);
        currentView = newView;
        window.setCenter((Node) currentView);
    }

    private void registerHandlers(Stage stage) {
        newGame.setOnAction(new menuClicked());
        randAI.setOnAction(new menuClicked());
        interAI.setOnAction(new menuClicked());
        button.setOnAction(new menuClicked());
        text.setOnAction(new menuClicked());
    }

    private class menuClicked implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String text = ((MenuItem) event.getSource()).getText();
            if (text.equals("New Game")) {
                initializeGameForTheFirstTime();

            } else if (text.equals("Button")) {
                setViewTo(buttonView);
                buttonView.update(theGame);

            } else if (text.equals("TextArea")) {
                setViewTo(textAreaView);
                textAreaView.update(theGame);

            } else if (text.equals("RandomAI")) {
                theGame.setComputerPlayerStrategy(new RandomAI());
                theGame.notifyObservers(theGame);
            } else if (text.equals("IntermediateAI")) {
                theGame.setComputerPlayerStrategy(new IntermediateAI());
                theGame.notifyObservers(theGame);
            }
        }

    }

}