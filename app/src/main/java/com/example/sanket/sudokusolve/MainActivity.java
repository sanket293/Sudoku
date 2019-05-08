package com.example.sanket.sudokusolve;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {


    int[][] MainBOX = new int[9][9];
    Button[][] Sudokucell = new Button[9][9];
    //    boolean b = false;
    Splash splash = new Splash();
    Background background = new Background();
    FrameLayout Sudokufram, mainfrm;
    float screenwidth = 0, screenheight = 0;
    float sudokuscreenwidth = 0, sudokuscreenheight = 0;
    ImageView drawingImageView;
    Canvas canvas;
    Paint paint;
    int[] cellpoint = new int[2];
    int leftmatgin = 0, topmargin = 0, size = 68;
    int innercellborder = 5, innerboxborder = 10, outerboxborder = 20;
    int a = 0, b = 0;
    int[][] pointjs = new int[9][9];


    int[][] pointx = new int[9][9];
    int[][] pointy = new int[9][9];
    Boolean check = true;
    float x = 0, y = 0;

    View l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getscreenresolution();

        MainBOX = splash.splashgetsudoku();
        initilize();
//        // createsudokuboard();
//        initilizationcanvas();
//        drawLine(0, 0, 720, 1180, 10);// horizontal
//        drawLine(25, 192, 619, 30, 10);// horizontal
    }

    private void initilize() {
        mainfrm = (FrameLayout) findViewById(R.id.mainfrm);

        Sudokufram = (FrameLayout) findViewById(R.id.frmsudoku);
        drawingImageView = (ImageView) findViewById(R.id.iv);
//        l1=(View)findViewById(R.id.l1);
//        mainfrm.setOnTouchListener(this);
        mainfrm.setOnTouchListener(this);

//        if (MainBOX != null) {
//            showfinalboard();
//        } else {
//            Log.e(".", "null");
//        }

        Intent intent = new Intent(this,drawingtest.class);
        startActivity(intent);


    }

    private void drowboxline() {

        int a = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (i == 0) {

//      drawLine(pointa[a], pointa[a+1], pointa[i], pointa[i], outerboxborder);// horizontal


                }
            }
        }


        int[] start = new int[2];
        int[] end = new int[2];
        int strock = outerboxborder;
        // horzontal line
        for (int i = 0; i < 9; i++) {

            for (int j = 0; j < 9; j = j + 8) {

                if (j == 0) {
                    start[0] = pointx[i][j];
                    start[1] = pointy[i][j];
                } else if (j == 8) {
                    end[0] = pointx[i][j];
                    end[1] = pointy[i][j];
                }

                if (i == 0 || i == 3 || i == 6) {
                    strock = innerboxborder;
                } else {
                    strock = innercellborder;
                }


            }
//            Log.e("s " + start[0] + " " + start[1], "e" + end[0] + " " + end[1]);
//            drawLine(start[0], start[1], end[0], end[1], strock);// horizontal
        }


    }

    private float converttodp(float i) {
        float dps = i;
        Log.e(".function", "." + i);
        float pxs = (i * getResources().getDisplayMetrics().density);
        return pxs;
    }


    private void getscreenresolution() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenwidth = size.x;
        screenheight = size.y;

        sudokuscreenheight = screenheight - 50 - 120;
        sudokuscreenwidth = screenwidth;
        //      Log.e("screen w" + screenwidth + " h" + screenheight, " Sudoku w " + sudokuscreenwidth + " h" + sudokuscreenheight);
    }

    private void createsudokuboard() {

        topmargin = outerboxborder + 10+60;
        for (int i = 0; i < 9; i++) {
            if (i == 0) {

            }
            leftmatgin = 0;
            for (int j = 0; j < 9; j++) {

                Button btn = new Button(this);

                if (j == 0) {
                    leftmatgin = outerboxborder + 5;
                }
                if (j == 3 || j == 6) {

                    leftmatgin = leftmatgin + innercellborder;
                }

                btn.setText("" + i);
                btn.setTextSize(12);
                btn.setTextColor(Color.DKGRAY);
//                btn.setBackgroundColor(Color.TRANSPARENT);
//                btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
                if (j == 8) {
                    layoutParams.rightMargin = outerboxborder + 5;
                }
                layoutParams.leftMargin = leftmatgin;
                layoutParams.topMargin = topmargin;
                btn.setLayoutParams(layoutParams);
                Sudokucell[i][j] = btn;


                Sudokufram.addView(Sudokucell[i][j]);
                Sudokucell[i][j].setVisibility(View.INVISIBLE);

//                 filllocation(i, j);

                Sudokucell[i][j].setOnClickListener(cellclick);
                leftmatgin += size + innercellborder;
            }

            if (i == 2 || i == 5) {
                topmargin = topmargin + innerboxborder;
            }
            topmargin += size + innercellborder;


        }
    }


    private View.OnClickListener cellclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean b = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (view == Sudokucell[i][j]) {
                        int[] cellpoint = new int[2];
                        Sudokucell[i][j].getLocationOnScreen(cellpoint);
                        Log.e("X " + cellpoint[0], "Y " + cellpoint[1]);
                        Log.e("getX " + Sudokucell[i][j].getX(), "get Y " + Sudokucell[i][j].getY());
                        Log.e("left X " + Sudokucell[i][j].getLeft(), "get top Y " + Sudokucell[i][j].getTop());
                        b = true;
                    }
                    if (b) {
                        break;
                    }
                }
            }

        }
    };


    private void filllocation(int i, int j) {

        if (i == 0 || i == 8) {
            int[] cellpoint = new int[2];
//            Sudokucell[i][j].getLocationOnScreen(cellpoint);
//
//            pointx[i][j] = cellpoint[0];
//            pointy[i][j] = cellpoint[1];
//
//            pointx[i][j] = (int) Sudokucell[i][j].getX();
//            pointy[i][j] = (int) Sudokucell[i][j].getY();
            pointx[i][j] = (int) Sudokucell[i][j].getLeft();
            pointy[i][j] = (int) Sudokucell[i][j].getTop();

            Log.e("value at . " + i + j + " .", "x" + pointx[i][j] + " Y " + pointy[i][j]);

//            Log.e("value at" + i + j, "a" + a);
            //       Log.e("point x" + pointa[a], "ponit y" + pointa[a + 1]);
            a += 2;
        }
        if (j == 0 || j == 8) {

            if (i >= 1 && i <= 7) {
                int[] cellpoint = new int[2];
//                Sudokucell[i][j].getLocationOnScreen(cellpoint);
//                pointx[i][j] = cellpoint[0];
//                pointy[i][j] = cellpoint[1];

                pointx[i][j] = (int) Sudokucell[i][j].getX();
                pointy[i][j] = (int) Sudokucell[i][j].getY();

                Log.e("value at . " + i + j + " .", "x" + pointx[i][j] + " Y " + pointy[i][j]);
//                Log.e("value at . " + i + j + " ." , "b" + b);
                //           Log.e("point x" + pointa[b], "ponit y" + pointa[b + 1]);
                b += 2;
            }
        }
    }

    public void a(View view) {

        createsudokuboard();
        paint.setStrokeWidth(10);
        drawLine(0, 0, (int) getWindowManager().getDefaultDisplay().getWidth(), (int) getWindowManager().getDefaultDisplay().getHeight(), 10);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Sudokucell[i][j].setVisibility(View.VISIBLE);
                filllocation(i, j);
            }
        }
        drowboxline();
//        filllocation(i, j);
        //  Log.e("."+Sudokucell[0][0].getTop(),"."+Sudokucell[0][0].getX());


//        final int[] Lcellpointone = new int[2];
//
//        TextView button = (TextView) findViewById(R.id.tv);
//        button.getLocationOnScreen(Lcellpointone);
//        Log.e(".e", "view point x,y (" + Lcellpointone[0] + ", " + Lcellpointone[1] + ")");
//
//        Sudokucell[0][0].getLocationInWindow(cellpoint);
//        Log.e("abbbbbb", "00 " + cellpoint[0] + cellpoint[1]);
//        Sudokucell[8][0].getLocationInWindow(cellpoint);
//        Log.e("abbbbbb", "80 " + cellpoint[0] + cellpoint[1]);
//
//
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                filllocation(i, j);
//
////
////        MainBOX = splash.splashgetsudoku();
////
////        if (MainBOX != null) {
////            showfinalboard();
////        } else {
////            Log.e(".", "null");
////        }
//
//            }
//        }

    }

    private void showfinalboard() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                stringBuffer.append("\t" + MainBOX[i][j]);
            }
            stringBuffer.append("\n");
        }

        Log.e(" .", "." + stringBuffer);

    }


    // draws
    public void drawLine(float startx, float starty, float endx, float endy, int strock) {
        paint.setStrokeWidth(strock);
        canvas.drawLine(startx, starty, endx, endy, paint);

    }

    public void initilizationcanvas() {
//        drawingImageView=new ImageView(this);
//        Sudokufram.addView(drawingImageView);
        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager().getDefaultDisplay().getWidth(),
                (int) getWindowManager().getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bitmap);
        drawingImageView.setImageBitmap(bitmap);


        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        x = event.getX();
        y = event.getY();
        Log.e("." + x, "." + y);
        return true;
    }
}
