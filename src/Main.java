import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**Class that makes gameLogic object. Contains start button which starts and restart game. Instruction button shows instructions*/
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    GameLogic gameLogic = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vbField = new VBox(10);
        vbField.setPadding(new Insets(10));


        Scene scene = new Scene(vbField, 500, 600);
        gameLogic = new GameLogic(scene);


        Button btnStart = new Button("START");
        btnStart.setFocusTraversable(false);
        Button btnInstructions = new Button("?");
        btnInstructions.prefWidth(btnInstructions.getHeight());
        btnInstructions.setFocusTraversable(false);

        HBox hbBtns = new HBox(10);
        hbBtns.setAlignment(Pos.CENTER);
        hbBtns.getChildren().addAll(btnStart, btnInstructions);

        vbField.getChildren().addAll(hbBtns, gameLogic.getGcBoard());
        gameLogic.choseNextFigureForTopBoard();
        vbField.setAlignment(Pos.CENTER);


        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris");
        primaryStage.show();

        btnStart.setOnAction(e-> {
            gameLogic.restartGame();
        });



        VBox instructionsRoot = new VBox(10);
        instructionsRoot.setAlignment(Pos.CENTER);
        instructionsRoot.setPadding(new Insets(10));

        Label lblInstructions = new Label("Use left and right arrow key to move shapes on top board left and right.\n" +
                "To rotate shape press up arrow or R key.\n" +
                "To move shape to bottom board press down key, enter or space, you can also click on bottom or top board.\n" +
                "If you want to save shape for later press ctrl. You can save only one shape for later, and that way you're" +
                " exchanging it with current shape. Saved shape is in the square on the right\n" +
                "On the right board are shapes that are coming next on top board (first comes one on top).\n" +
                "Good luck :)");
        lblInstructions.setWrapText(true);

        instructionsRoot.getChildren().addAll(lblInstructions);
        Scene instructionScene = new Scene(instructionsRoot, 400, 200);

        Stage stage = new Stage();
        stage.setTitle("Instructions");
        stage.initOwner(primaryStage);
        stage.setScene(instructionScene);

        btnInstructions.setOnAction(e -> {
            stage.show();
        });
    }
}