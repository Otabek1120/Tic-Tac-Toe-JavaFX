package views;

// @author: Otabek Abduraimov
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.OurObserver;
import model.TicTacToeGame;
import java.lang.Character;

/**
 * Use a Pane to implement this interface so the Pane
 * can show a view of the model and allow user input
 */
public class ButtonView extends BorderPane implements OurObserver {
    private TicTacToeGame theGame;
    private GridPane buttonGPane;
    private Button[][] buttons;
    private Label label;

    public ButtonView(TicTacToeGame game) {
        theGame = game;
        buttons = new Button[3][3];
        initializePanel();
    }

    private void initializePanel() {
        // Button optionBtn = new Button("Options");
        // this.setTop(optionBtn);

        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);

        buttonGPane = new GridPane();
        buttonGPane.maxWidth(200);
        buttonGPane.maxHeight(200);
        buttonGPane.setHgap(2);
        buttonGPane.setVgap(2);
        buttonGPane.setPadding(new Insets(10, 10, 10, 20));
        addButtons(theGame.getTicTacToeBoard());

        label = new Label("Click to make a move");
        label.setPadding(new Insets(5, 5, 5, 5));
        label.setStyle("-fx-font-size: 12pt;" +
                "-fx-font-family: Monospace;" +
                "-fx-text-fill: green;" +
                "-fx-font-weight: bold;");

        vBox.getChildren().addAll(buttonGPane, label);
        this.setCenter(vBox);
        registerHandlers();
    }

    private void addButtons(char[][] gameBoard) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button();
                btn.setText(Character.toString(gameBoard[i][j]));
                btn.setStyle("-fx-font-size: 25pt;" +
                        "-fx-font-family: Monospace;" +
                        "-fx-text-fill: blue;" +
                        "-fx-font-weight: bolder;");
                buttonGPane.add(btn, j, i);
                buttons[i][j] = btn;
            }
        }
    }

    @Override
    public void update(Object observable) {
        System.out.println("update called from the Observable TicTacToeGame");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = buttons[i][j];
                btn.setText(Character.toString(theGame.getTicTacToeBoard()[i][j]));
                if (btn.getText().equals("O") || btn.getText().equals("X")) {
                    btn.setDisable(true);
                }

                String gameState = getGameState();
                if (!gameState.equals("Click to make a move")) {
                    disableButtons();
                }
                label.setText(gameState);
            }
        }
    }

    private String getGameState() {
        String state = "Click to make a move";
        if (theGame.didWin('O')) {
            state = "O wins";
        } else if (theGame.didWin('X')) {
            state = "X wins";
        } else if (theGame.tied()) {
            state = "Tie";
        }
        return state;
    }

    private void disableButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = buttons[row][col];
                btn.setDisable(true);
            }
        }
    }

    private void registerHandlers() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setOnAction(new ButtonClicked());
            }
        }
    }

    private class ButtonClicked implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Button buttonClicked = (Button) event.getSource();
            System.out.println("Button Clicked");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttonClicked == buttons[i][j]) {
                        if (theGame.available(i, j)) {
                            theGame.humanMove(i, j, false);
                            update(theGame);

                        }
                    }
                }
            }

        }

    }

}