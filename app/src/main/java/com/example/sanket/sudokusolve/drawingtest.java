package com.example.sanket.sudokusolve;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class drawingtest extends AppCompatActivity implements View.OnTouchListener {
    float x = 0, y = 0;
    ImageView drawingImageView;
    Canvas canvas;
    Paint paint;
    Splash splash = new Splash();
    private int topmargin = 0;
    private int leftmatgin = 0;
    private int size = 68;
    private int resetnum = 0;
    private int clearnum = 0;
    private Button[][] Sudokucell = new Button[9][9];
    int[][] MainBOX = new int[9][9];
    int[][] emptycell = new int[9][9];
    int[][] filledmainbox = new int[9][9];

    int[][] hintfild = new int[9][9];

    int[][] pointx = new int[9][9];
    int[][] pointy = new int[9][9];
    Button[] numbers = new Button[9];

    FrameLayout fm, frmnum, bottomfrm;
    Button btnpause, btnhint, btnclear, btnreset;
    TextView hinttxt, tvtimer;
    View v;
    int outerboxborder = 15;
    int innerboxborder = 10;
    int innercellborder = 5;
    int a, bbb, c, d;
    int[] tmprandom;
    ArrayList<Integer> rno = new ArrayList<Integer>();
    int[] tmprno = new int[2];
    String[] randomnumstr;
    int fillnum = 0, celli = -1, cellj = -1, celloldi = -1, celloldj = -1, difficulty = 1, emptycellnum = 0, totalcell = 81;

    private int screenwidth = 0, screenheight = 0;
    //    int[] colortext = {Color.rgb(44, 62, 80), Color.rgb(192, 57, 43),
//            Color.rgb(142, 68, 173), Color.rgb(52, 152, 219), Color.rgb(243, 156, 18),
//            Color.rgb(39, 174, 96), Color.rgb(211, 84, 0), Color.rgb(120, 144, 156), Color.rgb(158, 157, 36)};
    int[] colortext = {Color.rgb(14, 133, 62),
            Color.rgb(254, 185, 35),
            Color.rgb(255, 40, 64),
            Color.rgb(74, 35, 90),
            Color.rgb(200, 40, 0),
            Color.rgb(100, 0, 20),
            Color.rgb(23, 32, 42),
            Color.rgb(107, 37, 54),
            Color.rgb(0, 130, 170)
    };
    ArrayList<Integer> colors = new ArrayList<Integer>();

    private int cellsize = 14;
    private int number_size = 12;
    int ratio = 20;   //100>>>20

    int hint = 9;
    //    boolean userput = false;
    int bottom_remaningscreen = 0;

    public int seconds = 00;
    public int minutes = 00;
    public int hours = 00;

    //    Sudoku_String_entry sudoku_string_entry;
    SudokuString sudokuString;

    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawingtest);

        getscreenresolution();
        initilize();

        createsudokuboard();
        createnumberboard();
        emptycellfill(true);
        createemptycell();
        showfinalboard(MainBOX);
//        getviewtree();
        time();
        showadd();
    }

    private void getscreenresolution() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenwidth = size.x;
        screenheight = size.y;
        this.size = (int) ((screenwidth - 100) / 9);

        bottom_remaningscreen = (int) (screenheight - (120 + (9 * this.size) + (6 * innercellborder) + (2 * innerboxborder)));

        cellsize = (int) ((this.size * ratio) / (100));
        number_size = cellsize;

//        Log.e("size " + this.size, "height frm " + screenheight + "    width" + screenwidth);
//        Log.e("cell size " + cellsize, "number size" + number_size);

    }

    private void initilize() {


//        sudoku_string_entry = new Sudoku_String_entry(getApplicationContext());

        sudokuString = new SudokuString(getApplicationContext(), 1);
        MainBOX = splash.splashgetsudoku();

        fm = (FrameLayout) findViewById(R.id.frm);
        fm.setOnTouchListener(this);

        bottomfrm = (FrameLayout) findViewById(R.id.bottomfrm);
//        bottomfrm .setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, bottom_remaningscreen));
        frmnum = (FrameLayout) findViewById(R.id.frmnum);
        drawingImageView = (ImageView) findViewById(R.id.iv);

        hinttxt = (TextView) findViewById(R.id.tvhint);
        tvtimer = (TextView) findViewById(R.id.tvtimer);

        btnclear = (Button) findViewById(R.id.btnclear);
//        btnpause = (Button) findViewById(R.id.btnpause);
        btnreset = (Button) findViewById(R.id.btnreset);

//        Log.e(".bottom fram" + bottom_remaningscreen, "." + ((bottom_remaningscreen) - size + 10));


        Intent intent = getIntent();
        difficulty = intent.getIntExtra("difficulty", 0);
        colortextfn();
//        canvasinit();
    }

    private void colortextfn() {
        colors.clear();
        for (int i = 0; i < colortext.length; i++) {
            colors.add(colortext[i]);
        }
        Collections.shuffle(colors);
    }

    private void time() {
        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {


                        seconds++;

                        if (seconds == 60) {
                            tvtimer.setText(minutes + ":" + seconds);
                            seconds = 0;
                            minutes = minutes + 1;
                        }

                        if (minutes == 60) {
                            tvtimer.setText(hours + ":" + minutes + ":" + seconds);
                            seconds = 0;
                            minutes = 0;
                            hours = hours + 1;
                        }

                        tvtimer.setText(minutes + ":" + seconds);

                    }
                });
            }

        }, 0, 1000);
    }

    private void showadd() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5132757141594041/9087875016");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
        mInterstitialAd.show();

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }


// banner ads
        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("0A9C0FABACDB311BDE4F80D5CDC7356E")
                .build();
        adView.loadAd(adRequest);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("0A9C0FABACDB311BDE4F80D5CDC7356E")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void showfinalboard(int[][] mainBOX) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                stringBuffer.append("\t" + mainBOX[i][j]);
            }
            stringBuffer.append("\n");
        }
//        Log.e(" .", "" + stringBuffer);
    }


    private void createsudokuboard() {
        for (int i = 0; i < 9; i++) {
            if (i == 0) {
                topmargin = 5;
//                topmargin = 120;
            }
            leftmatgin = 0;
            for (int j = 0; j < 9; j++) {

                Button btn = new Button(this);

                if (j == 0) {
                    leftmatgin = outerboxborder + 10;
                }
                if (j == 3 || j == 6) {

//                    leftmatgin = leftmatgin + innercellborder;
                    leftmatgin = leftmatgin + innerboxborder;
                }

                btn.setText("" + MainBOX[i][j]);
                btn.setTextColor(colors.get((MainBOX[i][j]) - 1));
                btn.setTextSize(cellsize);
                btn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                btn.setBackgroundColor(Color.LTGRAY);
                btn.setClickable(false);
//              btn.setBackgroundColor(Color.TRANSPARENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
                if (j == 8) {
                    layoutParams.rightMargin = outerboxborder + 5;
                }
                layoutParams.leftMargin = leftmatgin;
                layoutParams.topMargin = topmargin;
                btn.setLayoutParams(layoutParams);
                Sudokucell[i][j] = btn;


                fm.addView(Sudokucell[i][j]);
//                Sudokucell[i][j].setOnClickListener(cellclick);
                leftmatgin += size + innercellborder;


            }
            if (i == 2 || i == 5) {
                topmargin = topmargin + innerboxborder;
            }

            topmargin += size + innercellborder;

        }
    }

    private void createemptycell() {

//        Random R = new Random();
//        difficulty = R.nextInt(3);


        sethintanddifficuulty();


        tmprandom = new int[emptycellnum];
        randomnumstr = new String[emptycellnum];
//        Log.e(".", ". dfculty" + difficulty);

        for (int i = 0; i < emptycellnum; i++) {
            int[] tmp = getrendomij(i);
            Sudokucell[tmp[0]][tmp[1]].setText("");
            emptycell[tmp[0]][tmp[1]] = -1;
            filledmainbox[tmp[0]][tmp[1]] = -1;
            Sudokucell[tmp[0]][tmp[1]].setOnClickListener(cellclick);
            Sudokucell[tmp[0]][tmp[1]].setClickable(true);
        }
    }

    private void createnumberboard() {
//        int lmargin = 30;
        int lmargin = outerboxborder;
//        int tmargin = innerboxborder + 2;
        int tmargin = 0;
//        int tmargin = innerboxborder;
//        int size = this.size - 2;
        for (int i = 0; i < 9; i++) {
            Button btn = new Button(this);
            btn.setText("" + (i + 1));
            btn.setTextSize(number_size);
            btn.setBackgroundColor(colors.get((i)));
//            Log.e("btn back grounds " + i, " " + colors.get((i)));
            btn.setTextColor(Color.WHITE);
            btn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
            layoutParams.leftMargin = lmargin;
            layoutParams.topMargin = tmargin;
            btn.setLayoutParams(layoutParams);
            numbers[i] = btn;
            frmnum.addView(numbers[i]);
            numbers[i].setOnClickListener(numberclick);
            lmargin += size + innercellborder;
        }
    }

    private void invisiblecells() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (emptycell[i][j] != -1) {
                    Sudokucell[i][j].setText("" + MainBOX[i][j]);
                    Sudokucell[i][j].setTextColor(colors.get((MainBOX[i][j]) - 1));
                }
            }
        }
    }

    private int[] getrendomij(int a) {
        boolean b = true;
        rno.clear();
        while (b) {
            Random r = new Random();
            int random = r.nextInt(9);

            for (int i = 0; i < 9; i++) {
                rno.add(i);
            }
            Collections.shuffle(rno);
            tmprno[0] = rno.get(random);
            Collections.shuffle(rno);
            tmprno[1] = rno.get(random);

            randomnumstr[a] = "" + tmprno[0] + tmprno[1];
            b = false;
            for (int i = 0; i < a; i++) {
                if (!randomnumstr[a].contentEquals(randomnumstr[i])) {
                    b = false;
                } else {
                    b = true;
                    break;
                }
            }
        }

        return tmprno;

    }

    private void emptycellfill(boolean b) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                emptycell[i][j] = MainBOX[i][j];
                filledmainbox[i][j] = 0;
                hintfild[i][j] = 0;
                if (b) {
                    Sudokucell[i][j].setOnClickListener(null);
                    Sudokucell[i][j].setClickable(false);
                }
            }
        }
    }

    private void sethintanddifficuulty() {
        switch (difficulty) {
            case 0:
                emptycellnum = 45;
                hint = 7;
                break;
            case 1:
                emptycellnum = 54;
                hint = 8;
                break;
            case 2:
                emptycellnum = 63;//
                hint = 9;
                break;
        }
        hinttxt.setText("" + hint);
    }


    private void getpoints(boolean bb) {
        boolean b = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!bb) { // click
                    if (v == Sudokucell[i][j]) {
                        setpotion(i, j);
                        points(i, j, bb);
                        b = true;
                    }
                    if (b) {
                        break;
                    }
                }


                if (bb) {
                    if (i == 2) {
                        if (j == 8) {
                            a = (int) Sudokucell[i][0].getX();
                            bbb = (int) Sudokucell[i][0].getY() + (i * size) - (innercellborder);

                            c = size + (int) Sudokucell[i][8].getX();
                            d = (int) Sudokucell[i][8].getY() + (i * size) - (innercellborder);
                            //canvas.drawLine(a, bbb, c, d, paint);
                        }
                    }

                    if (i == 6) {
                        if (j == 8) {
                            a = (int) Sudokucell[i][0].getX();
                            bbb = (int) Sudokucell[i][0].getY() + size + outerboxborder - (innercellborder);
                            c = size + (int) Sudokucell[i][8].getX();
                            d = (int) Sudokucell[i][0].getY() + size + outerboxborder - (innercellborder);

                            //canvas.drawLine(a, bbb, c, d, paint);
//                            Log.e(a + " . " + bbb, c + " .");
                        }
                    }

                    pointx[i][j] = (int) Sudokucell[i][j].getX();
                    pointy[i][j] = (int) Sudokucell[i][j].getY();
//                    points(i, j, bb);
                }
            }
        }


    }

    private void setpotion(int i, int j) {

        celli = i;
        cellj = j;
        Sudokucell[celli][cellj].setBackgroundColor(Color.GRAY); // current cell
        if (celloldi != -1 && celloldj != -1) {
            Sudokucell[celloldi][celloldj].setBackgroundColor(Color.LTGRAY);// prv.selceted
            if (celli == celloldi && cellj == celloldj) {
                Sudokucell[celli][cellj].setBackgroundColor(Color.GRAY); // current cell
            }
        }
        celloldi = celli;
        celloldj = cellj;
    }

    public void fillpotion(int fillnum) {

        if (celli != -1 && cellj != -1) {
            if (Sudokucell[celli][cellj].isClickable()) {
                Sudokucell[celli][cellj].setText("" + fillnum);
                emptycell[celli][cellj] = fillnum;
                Sudokucell[celli][cellj].setTextColor(colors.get((fillnum) - 1));
                Sudokucell[celli][cellj].setTextSize(cellsize);
                Sudokucell[celli][cellj].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//                userput = true;
                checkresult();
            }
        }
    }

    public void clearcell() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (hintfild[i][j] == 1) {
                    Sudokucell[i][j].setClickable(true);
                }
                if (filledmainbox[i][j] == -1) {
                    emptycell[i][j] = -1;
                    Sudokucell[i][j].setText("");
                    hintfild[i][j] = 0;
                } else {
                    Sudokucell[i][j].setText("" + MainBOX[i][j]); // comment
                }
            }
        }
    }

    private void checkresult() {
        int counter = 0;
        boolean result = false;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (emptycell[i][j] == -1) {
                    counter++;

                    //Log.e("." + result, "." + counter);
                    result = false;

//                    Sudokucell[i][j].setText("" + MainBOX[i][j]);
                }
                if (counter == 0) {
                    if (emptycell[i][j] == MainBOX[i][j]) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
        }

        showfinalboard(emptycell);

//        StringBuffer stringBuffer = new StringBuffer();
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                stringBuffer.append("\t" + emptycell[i][j]);
//            }
//            stringBuffer.append("\n");
//        }
//
//        Log.e(" .", "." + stringBuffer);


        if (result) {
            Toast.makeText(getApplicationContext(), " Congratulations...!   \n You win    ", Toast.LENGTH_LONG).show();
//            Log.e("come from ", "come from ");
            startActivity(new Intent(this, Splash.class));
            finish();
        }
    }

    public void reset(View view) {

        if (celloldi != -1 && celloldj != -1) {
            Sudokucell[celloldi][celloldj].setBackgroundColor(Color.LTGRAY);// prv.selceted
        }
        fillnum = 0;
        cellj = -1;
        celli = -1;
        celloldi = -1;
        celloldj = -1;
        difficulty = 1;
        emptycellnum = 0;
//        totalcell = 81;
        seconds = 00;
        minutes = 00;
        hours = 00;

        resetnum++;

        Mainboxgenrator();
        emptycellfill(true);
        createemptycell();
        invisiblecells();


        if (resetnum >= 4) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
            resetnum = 0;
        }

    }

    public void clear(View view) {
        fillnum = 0;
        seconds = 00;
        minutes = 00;
        hours = 00;

        clearcell();

        sethintanddifficuulty();
        clearnum++;
        if (clearnum >= 5) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
            clearnum = 0;
        }


    }

    public void hint(View view) {
        if (celli != -1 && cellj != -1) {
            if (hintfild[celli][cellj] == 0) {
                if (Sudokucell[celli][cellj].isClickable()) {
                    if (hint > 0) {
                        fillpotion(MainBOX[celli][cellj]);
                        Sudokucell[celli][cellj].setClickable(false);
                        hint--;
                        hinttxt.setText("" + hint);
                        hintfild[celli][cellj] = 1;
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "you have no hints ..! ", Toast.LENGTH_LONG);
        }


        if (hint == 5) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
            
        }
    }

    public void pause(View view) {
    }


    private void Mainboxgenrator() {
        Random r = new Random();
        int totalcol = sudokuString.gettotalcolum();

//        int id = r.nextInt(sudoku_string_entry.totalgenratednum());
        int id = r.nextInt(totalcol);
        if (id <= 0) {
            id++;
        }
        if (id > totalcol) {
            id--;
        }

        Log.e(".", "." + id);
        try {
            String sudoku_string = sudokuString.getSudoku_String(id);
            Log.e("sudoku_string", "." + sudoku_string);
            if (sudoku_string != null) {
                fillMainbox(sudoku_string);
            }
        } catch (Exception e) {
            Log.e("e. ", "e.  " + e);
        }
    }


    private void fillMainbox(String sudoku_string) {
        String[] splited = sudoku_string.split(",");
        int a = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    MainBOX[i][j] = Integer.parseInt(splited[a]);
                    a++;
                } catch (Exception e) {
                    Log.e("error fillMainbox", "." + e);
                }
            }
        }
    }


    View.OnClickListener cellclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            v = view;
            getpoints(false);
        }
    };


    View.OnClickListener numberclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (int i = 0; i < 9; i++) {
                if (numbers[i] == view) {
                    fillnum = i + 1;
                    fillpotion(fillnum);
                    break;
                }
            }
        }
    };

    private void points(int i, int j, boolean bb) {
//        Log.e("come from " + bb, "come from " + i + j);
//        Log.e("getX " + Sudokucell[i][j].getX(), "get Y " + Sudokucell[i][j].getY());
    }

    private void getviewtree() {

        fm.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {

                        getpoints(true);

//                        Log.e("" + Sudokucell[0][0].getX(), "" + Sudokucell[0][0].getY());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            fm.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });

    }

    private void canvasinit() {
        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager().getDefaultDisplay().getWidth(),
                (int) getWindowManager().getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bitmap);
        drawingImageView.setImageBitmap(bitmap);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);

//        canvas.drawLine(25,120+topmargin, 619, 120+topmargin, paint);
//        canvas.drawLine(25,193+topmargin, 619, 193+topmargin, paint);
//        canvas.drawLine(25,266+topmargin, 619, 266+topmargin, paint);
//        canvas.drawLine(25,349+topmargin, 619, 349+topmargin, paint);
//        canvas.drawLine(25,422+topmargin, 619, 422+topmargin, paint);
//        canvas.drawLine(25,495+topmargin, 619, 495+topmargin, paint);
//
//        canvas.drawLine(0, 0, (int) getWindowManager().getDefaultDisplay().getWidth(), (int) getWindowManager().getDefaultDisplay().getHeight(), paint);
//
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        x = event.getX();
        y = event.getY();
//        Log.e("." + x, "." + y);
        return true;
    }
}
