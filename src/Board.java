import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**Class that represents board for the game. */
public class Board {
    private GraphicsContext gc;

    private int width = 200;
    private int height= 400;
    private int step = 20;

    private Color backgroundColor = Color.LIGHTGRAY;
    private Color lineColor = Color.BLACK;
    private boolean hasLines;

    /**Constructor that takes size, background color and weather board should have grid.*/
    public Board(int width, int height, int step, Color backgroundColor, boolean hasLines){
        this.hasLines = true;
        this.width = width;
        this.height = height;
        this.step = step;
        this.backgroundColor = backgroundColor;
        this.lineColor = lineColor;

        Canvas cnv = new Canvas(width, height);
        gc = cnv.getGraphicsContext2D();

        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, width,height);
        gc.setFill(lineColor);
        if (hasLines){
            for (int i=0; i<=height; i+=step)
                gc.strokeLine(0, i, width, i);
            for (int i=0; i<=width; i+=step)
                gc.strokeLine(i, 0,  i, height);
        }
    }

    /**Constructor that makes transparent board.*/
    public Board(int width, int height, int step){
        this(width, height, step, Color.LIGHTGRAY, true);
    }


    /**Restart board, clears all the shapes from the board.*/
    public void restartBoard(){
        gc.setFill(backgroundColor);
        gc.clearRect(0, 0, width,height);
        gc.fillRect(0, 0, width,height);
        gc.setFill(lineColor);
        if (hasLines) {
            for (int i = 0; i <= height; i += step)
                gc.strokeLine(0, i, width, i);
            for (int i = 0; i <= width; i += step)
                gc.strokeLine(i, 0, i, height);
        }
    }

    /**Draws shape that's represented in boolean matrix. Top left corner of shape (which is matrix) is on coordinates (0, start) on the board.*/
    public void drawOnBoard(Boolean[][] board, Color clr, int start){
        gc.setFill(clr);
        int n = board.length;
        int m = board[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                if (board[i][j])
                    gc.fillRect(j*step, i*step + start, step, step);
        }
    }

/**Draw shape on board from the beginning of the board, top left corner is on (0, 0)*/
    public void drawOnBoard(Boolean [][] board, Color clr){
        drawOnBoard(board, clr, 0);
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public Canvas getCanvas(){
        return gc.getCanvas();
    }
}
