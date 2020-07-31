import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum Figure {
    ///T shapes
    T0(new Boolean[][] {{false, true, false}, {true, true, true}}),
    T1(new Boolean[][] {{true, false}, {true, true}, {true, false}}),
    T2(new Boolean[][] {{true, true, true}, {false, true, false}}),
    T3(new Boolean[][] {{false, true}, {true, true}, {false, true}}),

    //square shape
    S(new Boolean[][] {{true, true}, {true, true}}),

    //lines shape
    I0(new Boolean[][] {{true, true, true, true}}),
    I1(new Boolean[][] {{true}, {true},{true}, {true}}),

    //z shapes
    Z00(new Boolean[][] {{false, true, true}, {true, true, false}}),
    Z01(new Boolean[][] {{true, false}, {true, true}, {false, true}}),

    Z10(new Boolean[][] {{true, true, false}, {false, true, true}}),
    Z11(new Boolean[][] {{false, true}, {true, true}, {true, false}}),

    //L shapes
    L01(new Boolean[][] {{true, false, false},{true, true, true}}),
    L02(new Boolean[][] {{true, true},{true, false},{true, false}}),
    L03(new Boolean[][] {{true, true, true}, {false, false, true}}),
    L04(new Boolean[][] {{false, true},{false, true}, {true, true}}),

    L11(new Boolean[][] {{false, false, true},{true, true, true}}),
    L12(new Boolean[][] {{true, false},{true, false}, {true, true}}),
    L13(new Boolean[][] {{true, true, true}, {true, false, false}}),
    L14(new Boolean[][] {{true, true},{false, true},{false, true}});

    public Boolean[][] figure;
    private Figure rotate;

    //number and list of all figures, Figure.getValues can't be used, we need list for random choosing
    private static List<Figure> figures;
    private static int numFigures;

    private Figure(Boolean[][] arr){
        this.figure = arr;
    }

    static {
        //which shape rotates in which
        T0.rotate = T1;
        T1.rotate = T2;
        T2.rotate = T3;
        T3.rotate = T0;

        S.rotate = S;

        I0.rotate = I1;
        I1.rotate = I0;

        Z00.rotate = Z01;
        Z01.rotate = Z00;

        Z10.rotate = Z11;
        Z11.rotate = Z10;

        L01.rotate = L02;
        L02.rotate = L03;
        L03.rotate = L04;
        L04.rotate = L01;

        L11.rotate = L12;
        L12.rotate = L13;
        L13.rotate = L14;
        L14.rotate = L11;

        //initialize list of figures
        numFigures = Figure.values().length;
        figures = new ArrayList<>();
        for (Figure f : Figure.values())
            figures.add(f);
    }

    public Figure rotate(){
        return this.rotate;
    }

    //return string which represent shape with * and blank spaces
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Boolean[] arr : this.figure) {
            for (Boolean b : arr)
                sb.append(b ? "*" : " ");
            sb.append("\n");
        }
        return sb.toString();
    }

    private static Random random= new Random();
    //returns random figure
    public static Figure getRandomFigure(){
        return figures.get(random.nextInt(numFigures-1));
    }
}
