import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**Class that makes Graphic user interface, GameLogic class extends this class so it's good that some fields are protected.
 * This class make 4 boards. Figures are first placed on top board and later they get on bottom board. Bottom board is main
 * board of the game. Right board shows which shapes are coming up next. Save board shows if some shape is saved for later.*/
public class GameGUI {
    Board topBoard; //board on which shapes go before placing them on main/bottom board
    Board bottomBoard; //main board of the game
    Board rightBoard; //shows which shapes are coming up next
    Board saveBoard; //shows if some shape is saved for later

    private VBox vBoxLeft; //contains top and bottom board
    private VBox vBoxRight; //contains points label, save and right board

    //buttons for playing game, not used anymore
    protected Button btnLeft;
    protected Button btnRotate;
    protected Button btnRight;
    protected Label lblPoints;

    private int width = 200;
    private int height= 400;
    private int step = 20;
    private Color backgorundColor = Color.LIGHTGRAY;

    public GameGUI(){
        vBoxLeft = new VBox(5);
        vBoxLeft.setAlignment(Pos.CENTER);

        bottomBoard = new Board(200, 400, 20);
        topBoard = new Board(width, 4*step, step);
        vBoxLeft.getChildren().addAll(topBoard.getCanvas(), bottomBoard.getCanvas());

        vBoxRight = new VBox(10);
        vBoxRight.setPadding(new Insets(10));
        vBoxRight.setAlignment(Pos.CENTER);
        saveBoard = new Board(80, 80, 20, Color.TRANSPARENT, true);
        rightBoard = new Board(80, 380, 20, Color.TRANSPARENT, true);
        lblPoints = new Label("0");
        vBoxRight.getChildren().addAll(lblPoints, saveBoard.getCanvas(), rightBoard.getCanvas());

        makeControls();
    }

@Deprecated
//makes buttons, it's not needed anymore
// to show buttons uncoment adding to HBox and setButtonAction in setActions in GameLogic class
    private void makeControls(){
        HBox hbControlBtns = new HBox(10);

        btnLeft = new Button("\u25C0");
        btnRotate = new Button("\u21bb");
        btnRight = new Button("\u25b6");
//        hbControlBtns.getChildren().addAll(btnLeft, btnRotate, btnRight);
        hbControlBtns.setAlignment(Pos.CENTER);

        btnLeft.setPrefSize(30, 30);
        btnRight.setPrefSize(30, 30);
        btnRotate.setPrefSize(30, 30);

        btnRotate.setFocusTraversable(false);
        btnRight.setFocusTraversable(false);
        btnLeft.setFocusTraversable(false);

        vBoxLeft.getChildren().add(hbControlBtns);
    }


    //returns hbox that contains all the fields that are necessary for the game
    public HBox getGcBoard() {
        HBox hb = new HBox(10);
        hb.getChildren().addAll(vBoxLeft, vBoxRight);
        hb.setAlignment(Pos.CENTER);
        return hb;
    }

    protected void drawOnTopBoard(Boolean [][] board, Color clr){
        topBoard.restartBoard();
        topBoard.drawOnBoard(board, clr);
    }

    //delete all shapes from top and bottom board
    public void restartBoard(){
        topBoard.restartBoard();
        bottomBoard.restartBoard();
    }

    public void drawOnBottomBoard(Boolean[][] board, Color clr){
        bottomBoard.restartBoard();
        bottomBoard.drawOnBoard(board, clr);
    }
}
