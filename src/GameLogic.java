import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**Class that contains game logic and extends GUI.
 * It has matrices for tom and bottom board. List of colors and figures that are coming up next.*/
public class GameLogic extends GameGUI {
    private Boolean [][] board; // main board matrix
    private Boolean [][] topBoard; // top board matrix
    private Figure fOnTopBoard; // figure on top board
    private Figure savedFigure; // saved figure
    private int a; //right side of figure on top board in matrix

    private int points;

    //list of figures that are coming up next and their colors
    private List<Figure> nextFigure;
    private List<Color> nextColor;

    //all possible colors
    private static Color[] colors = {Color.RED, Color.BLUE, Color.DEEPPINK, Color.YELLOW, Color.ORANGE, Color.GREEN};
    private static int numColors = colors.length;
    private static Random random = new Random();

    private Color colorOfFigure; //color of figure on top board
    private Color colorOfSavedFigure;

    public GameLogic(Scene scene){
        board = new Boolean[20][10];
        topBoard = new Boolean[4][10];
        points = 0;
        for (Boolean[] line : topBoard)
            Arrays.fill(line, false);
        for (Boolean[] line : board)
            Arrays.fill(line, false);

//        makes buttons, but it's not used anymore cause game uses keyboard
        setActions(scene);

        //if player clicks on bottom or top board shape will go down
        super.topBoard.getCanvas().setOnMouseClicked(e-> moveDown());
        super.bottomBoard.getCanvas().setOnMouseClicked(e-> moveDown());

        nextFigure = new LinkedList<>();
        nextColor = new LinkedList<>();
        for (int i = 0; i < 4; i++){
            nextFigure.add(Figure.getRandomFigure());
            nextColor.add(colors[random.nextInt(numColors)]);
        }

        //initialize current shape, color and saved figure
        fOnTopBoard = Figure.getRandomFigure();
        colorOfFigure = colors[random.nextInt(numColors)];
        savedFigure = null;

        //draws shapes on right board
        updateRightBoard();
    }

    @Deprecated
    //used for testing, prints board matrix
    private void printBoard(Boolean [][] board){
        for (Boolean[] arr : board) {
            for (Boolean b : arr)
                System.out.print(b ? "1 " : "0 ");
            System.out.println();
        }
    }

    //takes next color and figure, place them on top board and remove them from list of next shapes, then update board with next shapes
    public void choseNextFigureForTopBoard(){
        colorOfFigure = nextColor.get(0);
        nextColor.remove(0);
        nextColor.add(colors[random.nextInt(numColors)]);

        fOnTopBoard = nextFigure.get(0);
        nextFigure.remove(0);
        nextFigure.add(Figure.getRandomFigure());

        updateRightBoard();
        putOnTopBoard(fOnTopBoard);
    }


    private void putOnTopBoard(Figure f){
        restartTopBoard(); //removes previous shape
        int n = f.figure.length;
        int m = f.figure[0].length;

        //if shape can't fit top board if it's starting on position a, then a moves left just enough so shape can fit
        if(a + m > topBoard[0].length)
            a -= a+m - topBoard[0].length;

        //place in matrix for top board
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                topBoard[i][j+a] = f.figure[i][j];

        super.drawOnTopBoard(topBoard, colorOfFigure); //draws on canvas
    }

    public void restartGame(){
        points = 0;
        restartTopBoard();

        //restart bottom board, used just once
        for (Boolean[] line : board)
            Arrays.fill(line, false);

        savedFigure = null; //there's no saved figures anymore
        super.restartBoard(); //restart canvases on both boards
        updateRightBoard(); //updates right board
        choseNextFigureForTopBoard(); // finds next figure for the board
    }

    //clears shape on top board, it's used multiple times
    private void restartTopBoard(){
        for (Boolean[] line : topBoard)
            Arrays.fill(line, false);
    }

    //setting use of keyboard
    private void setActions(Scene scene){
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT){
                moveLeft();
                super.drawOnTopBoard(topBoard, colorOfFigure);
            }

            if (e.getCode() == KeyCode.RIGHT){
                moveRight();
                super.drawOnTopBoard(topBoard, colorOfFigure);
            }

            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.R){
                fOnTopBoard = fOnTopBoard.rotate();
                putOnTopBoard(fOnTopBoard);
                super.drawOnTopBoard(topBoard, colorOfFigure);
            }

            if (e.getCode() == KeyCode.CONTROL)
                saveFigure();


            if (e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.SPACE)
                moveDown();
        });

        //if actual buttons are used uncomment this
//        setButtonAction();
    }

    //if we need to use buttons
    private void setButtonAction(){
        btnLeft.setOnAction(e->{
            moveLeft();
            super.drawOnTopBoard(topBoard, colorOfFigure);
        });
        btnRight.setOnAction(e->{
            moveRight();
            super.drawOnTopBoard(topBoard, colorOfFigure);
        });
        btnRotate.setOnAction(e->{
            fOnTopBoard = fOnTopBoard.rotate();
            putOnTopBoard(fOnTopBoard);
            super.drawOnTopBoard(topBoard, colorOfFigure);
        });
    }

    private void saveFigure(){
        saveBoard.restartBoard();
        //if there's no saved shapes we place current to save
        //if there's already saved shape we switch it with current one
        if(savedFigure == null){
            savedFigure = fOnTopBoard;
            colorOfSavedFigure = colorOfFigure;
            choseNextFigureForTopBoard();
        }
        else{
            Figure tmpf = savedFigure;
            savedFigure = fOnTopBoard;
            fOnTopBoard = tmpf;

            Color tmpc = colorOfSavedFigure;
            colorOfSavedFigure = colorOfFigure;
            colorOfFigure = tmpc;
        }

        //we need to update top board and saveBoard
        saveBoard.drawOnBoard(savedFigure.figure, colorOfSavedFigure,0);
        putOnTopBoard(fOnTopBoard);
    }

    private void moveLeft(){
        int n = topBoard[0].length;
        int m = topBoard.length;
        for (int i = 0; i < m; i++)
            if(topBoard[i][0])
                return; //if there's shape part in the most left column nothing can move
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < m; j++) {
                topBoard[j][i] = topBoard[j][i+1];
            }
        }
        for (int i = 0; i < m; i++)
            topBoard[i][n-1] = false;
        a--;
    }

    private void moveRight(){
        int n = topBoard[0].length;
        int m = topBoard.length;
        for (int i = 0; i < m; i++)
            if(topBoard[i][n-1])
                return; //if there's shape part in the most right column nothing can move
        for (int i = n-1; i > 0 ; i--) {
            for (int j = 0; j < m; j++) {
                topBoard[j][i] = topBoard[j][i-1];
            }
        }
        for (int i = 0; i < m; i++)
            topBoard[i][0] = false;
        a++;
    }

    public void moveDown(){
        int depth = board.length;

        Boolean f[][] = fOnTopBoard.figure;
        int h = f.length;
        int w = f[0].length;

        int b = a+w;
        int i;
        looking:
        for (i=0; i<depth; i++){
            for(int j = a; j<b; j++){
                int k = j-a; //k should go from 0 to b-a=w
                if(f[h-1][k] && board[i][j]) //bottom row of figure matrix is h-1
                    break looking;
//                for Z and T figures we need to check second row too, otherwise it might go through some figures
                if(h>1 && i>=1){
                    if(f[h-2][k] && board[i-1][j]) //bottom row is h-1
                        break looking;
                }
//                h - height of the figure, h greater than two -> it'll be at least 3
//                i - current row of the board
//                for Г figure we need to check third row too, otherwise it goes though some figures
                if(h>2 && i>=2)
                    if(f[h-3][k] && board[i-2][j])
                        break looking;
            }
        }
        updateRightBoard();

        if (h-i>0){ //if figure cant't fit, height of figure is greater that i-number of rows
            gameOver();
            return;}

        i--;
        putOnBottomBoard(i, h,  a, b);
    }

    private void updateRightBoard(){
        rightBoard.restartBoard();
        int n = nextFigure.size();
        for (int i=0; i<n; i++) {
            rightBoard.drawOnBoard(nextFigure.get(i).figure, nextColor.get(i), i*100);
        }
    }

    private void putOnBottomBoard(int bottomLine, int h, int a, int b){
        //in board matrix places figure, figure is placed from bottom line upwards, from a on the left to b on the right
        for (int j = a; j < b; j++)
            for (int k=0; k<h; k++)
                board[bottomLine-k][j] = fOnTopBoard.figure[h-k-1][j-a] | board[bottomLine-k][j];

        super.drawOnBottomBoard(board, colorOfFigure);
        if(checkLines(bottomLine-h+1, bottomLine+1)){
            super.lblPoints.setText(points + " ");
            super.drawOnBottomBoard(board, colorOfFigure);
        }
        choseNextFigureForTopBoard();
    }

    private void ereseLine(int i){
        int h = board.length;
        int w = board[0].length;

        for (int j = i; j > 1; j--) {
            for (int k = 0; k<w; k++)
                board[j][k] = board[j-1][k];
        }
        for (int j=0; j<w; j++)
            board[0][j] = false;
        points += 10;
    }

    private boolean checkLines(int b, int t){
        boolean ret = false; //it returns true if any line should disappear, so board needs update
        int n = board[0].length;

        for (int i = b; i < t; i++) { //check lines from bottom to the top
            boolean shouldErase = true;
            for (int j = 0; j < n; j++)
                if(!board[i][j]){
                    shouldErase = false;
                    break;
                }
            if (shouldErase){
                ereseLine(i);
                ret = true;
            }
        }
        return ret;
    }

    private void gameOver(){
        Label lbl = new Label("You got " + points + " points.");
        lbl.setWrapText(true);

        Stage stage = new Stage();
        stage.setTitle("Game over");

        VBox vbInfo = new VBox(10);
        vbInfo.setPadding(new Insets(10));
        vbInfo.getChildren().addAll(lbl);

        Scene infoScene = new Scene(vbInfo, 300, 150);

        restartGame();

        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(infoScene);
        stage.show();
    }

}