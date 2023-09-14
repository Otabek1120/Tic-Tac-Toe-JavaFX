package views;

// @author: Otabek Abduraimov
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.OurObserver;
import model.TicTacToeGame;

public class TextAreaView extends BorderPane implements OurObserver {

    private TicTacToeGame theGame;
    private Button moveBtn;
    private TextField rowTField;
    private TextField colTField;
    private TextArea outputTArea;

    public TextAreaView(TicTacToeGame theModel) {
        theGame = theModel;
        initializePanel();
    }

    private void initializePanel() {
        VBox vBox = new VBox();

        GridPane gPane = new GridPane();
        Label labelR = new Label("Row");
        rowTField = new TextField();
        rowTField.setMaxWidth(80);

        Label labelC = new Label("Column");
        colTField = new TextField();
        colTField.setMaxWidth(80);
        moveBtn = new Button("Make Move"); // set on action

        gPane.setHgap(10);
        gPane.setVgap(10);

        gPane.add(labelR, 1, 1);
        gPane.add(rowTField, 2, 1);
        gPane.add(labelC, 1, 2);
        gPane.add(colTField, 2, 2);
        gPane.add(moveBtn, 2, 3);

        // Text Area
        outputTArea = new TextArea();
        outputTArea.setText(theGame.toString());
        outputTArea.setEditable(false);
        outputTArea.setStyle("-fx-font-size: 28pt;" +
                "-fx-font-family: Monospace; " +
                "-fx-text-fill: blue;" +
                "-fx-font-weight: bold;");

        vBox.getChildren().addAll(gPane, outputTArea);
        this.setCenter(vBox);

        registerHandlers();
    }

    // This method is called by Observable's notifyObservers()
    @Override
    public void update(Object observable) {
        outputTArea.setText(theGame.toString());

        // checking for a win or a tie
        if (theGame.didWin('X')) {
            moveBtn.setText("X wins");
            moveBtn.setDisable(true);
        } else if (theGame.didWin('O')) {
            moveBtn.setText("O wins");
            moveBtn.setDisable(true);
        } else if (theGame.tied()) {
            moveBtn.setText("Tie");
            moveBtn.setDisable(true);
        }

        // clear textFields when move is valid
        rowTField.clear();
        colTField.clear();
    }

    private void registerHandlers() {
        moveBtn.setOnAction(new ButtonMoved());
    }

    private class ButtonMoved implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            moveBtn.setText("Make Move");

            String rowVal = rowTField.getText().trim();
            String colVal = colTField.getText().trim();

            try {
                // catches non-integer input
                int row = Integer.parseInt(rowVal);
                int col = Integer.parseInt(colVal);

                // catches out-of-range point
                if (!theGame.available(row, col) || row > 2 || col > 2) {
                    throw new Exception();
                }
                theGame.humanMove(row, col, false);
                update(theGame);

            } catch (Exception e) {
                // TODO: handle exception
                moveBtn.setText("Invalid choice");
                update(theGame);
            }
        }
    }
}