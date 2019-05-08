package com.example.sanket.sudokusolve;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Random;

public class Splash extends AppCompatActivity {
    public static int[][] MainBOX = new int[9][9];
    Background background = new Background();
    static Boolean b = true;
    Thread thread = null;

//    private Button[] numbers = new Button[4];
//    LinearLayout fram;
//    int btnheight = 75, btnwidth = 300;

    int difficulty = 0;
    Button easy, meadium, hard, play, score;
    TextView TotalSudoku;
//    DataBaseHelper dataBaseHelper;
//    Sudoku_String_entry sudoku_string_entry;

//    public static final String PREFRENCES = "Myprefrences";
//    public static final String PRF_id = "PRF_id";
//    SharedPreferences sharedPreferences;
//    Boolean sharprf = false;


    public static AsyncTask asyncTask;

    SudokuString sudokuString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initilization();
        showadd();


//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (b) {
//                    try {
//                        sleep(2000);
//                        if (background.getStatus() == AsyncTask.Status.RUNNING) {
////                            Log.e("bs", "running");
//                        }
//                        if (background.getStatus() == AsyncTask.Status.FINISHED) {
////                            Log.e("bs", "finish");
//
//                            MainBOX = background.getsudoku();
//                            splashgetsudoku();
//                            createesudokustring(MainBOX);
//                            Splash.b = false;
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//        //temp stop for testing
//
//        asyncTask = background.execute();
//        thread.start();

        try {
            int totalcol = sudokuString.gettotalcolum();
            TotalSudoku.setText("Total Sudoku :" + totalcol);
        } catch (Exception e) {

        }
    }

    public String createesudokustring(int[][] mainBOX) {
        String ss = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ss = ss + mainBOX[i][j] + ",";
            }
        }
//        Log.e("....", "" + ss);
        return ss;
    }


    private void testingnumbers() {

        String ss = "";

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ss = ss + j + ",";
            }
        }

//        Log.e("....", "" + ss);
        String[] splited = ss.split(",");
        int a = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                MainBOX[i][j] = Integer.parseInt(splited[a]);
                a++;
            }
        }

//
//        for (int i = 0; i < splited.length; i++) {
//
//            try {
//                Log.e("...." + i, "" + splited[i]);
//
//            } catch (Exception e) {
//                Log.e("error", "." + e);
//                break;
//            }
//        }

    }

    private void initilization() {
        easy = (Button) findViewById(R.id.btneasy);
        meadium = (Button) findViewById(R.id.btnmeadium);
        hard = (Button) findViewById(R.id.btnhard);
        play = (Button) findViewById(R.id.btnplay);
        TotalSudoku = (TextView) findViewById(R.id.TotalSudoku);
//        score = (Button) findViewById(R.id.btnscore);

//        dataBaseHelper = DataBaseHelper.getInstance(getApplicationContext());
//        sudoku_string_entry = new Sudoku_String_entry(getApplicationContext());

        sudokuString = new SudokuString(getApplicationContext(), 0);
    }

    private void showadd() {
        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);

    }

    public void easy(View view) {

        easy.setEnabled(false);
        meadium.setEnabled(true);
        hard.setEnabled(true);
        easy.setBackgroundColor(getResources().getColor(R.color.easylight));
        meadium.setBackgroundColor(getResources().getColor(R.color.meadiumdark));
        hard.setBackgroundColor(getResources().getColor(R.color.harddark));
        this.difficulty = 0;
    }

    public void meadium(View view) {
        easy.setEnabled(true);
        meadium.setEnabled(false);
        hard.setEnabled(true);
        easy.setBackgroundColor(getResources().getColor(R.color.easydark));
        meadium.setBackgroundColor(getResources().getColor(R.color.meadiumlight));
        hard.setBackgroundColor(getResources().getColor(R.color.harddark));

        this.difficulty = 1;
    }

    public void hard(View view) {
        easy.setEnabled(true);
        meadium.setEnabled(true);
        hard.setEnabled(false);
        easy.setBackgroundColor(getResources().getColor(R.color.easydark));
        meadium.setBackgroundColor(getResources().getColor(R.color.meadiumdark));
        hard.setBackgroundColor(getResources().getColor(R.color.hardlight));

        difficulty = 2;
    }

    public void play(View view) {
//        if (!b) {
//            Log.e("jsplash", "play");

        Mainboxgenrator();
        //  Intent intent = new Intent(Splash.this, MainActivity.class);
        Intent intent = new Intent(Splash.this, drawingtest.class);
        intent.putExtra("difficulty", difficulty);
        startActivity(intent);
        finish();

//        }
    }


//    public void score(View view) {
//
//        Toast.makeText(getApplicationContext(),"This Feature Is Come Soon..!.",Toast.LENGTH_LONG).show();
//
////        Mainboxgenrator();
////        Intent intent = new Intent(Splash.this, drawingtest.class);
////        intent.putExtra("difficulty", difficulty);
////        startActivity(intent);
//    }
//
//    public void genrate(View view) {
//        Toast.makeText(getApplicationContext(),"This Feature Is Come Soon..!.",Toast.LENGTH_LONG).show();
//
///*
//        try {
//            if (background.setworkfinish()) {
//                Splash.b = true;
//                asyncTask = background.execute();
//                thread.start();
//            } else {
//                Log.e("btn  genrate", "." + background.setworkfinish());
//            }
//        } catch (Exception e) {
//        }
//*/
//    }

    private void Mainboxgenrator() {
        Random r = new Random();
//        int totalcol =sudokuString.gettotalcolum();
        int totalcol = 5;
        int id = r.nextInt(totalcol);
        if (id <= 0) {
            id++;
        }
        if (id > totalcol) {
            id--;
        }

//        Log.e(".", "." + id);
        try {
            String sudoku_string = sudokuString.getSudoku_String(id);
//            Log.e("ssss", "..." + sudoku_string);
//            Log.e("sudoku_string","."+sudoku_string);


            if (sudoku_string != null) {
                fillMainbox(sudoku_string);
                splashgetsudoku();// it will final main box 1-9 //
            }
        } catch (Exception e) {
//            Log.e("e. ", "e.  " + e);
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
                }
            }
        }
    }

    public int[][] splashgetsudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (MainBOX[i][j] == 0) {
                    MainBOX[i][j] = 9;
                }
            }
        }
        return MainBOX;
    }


}


//    private void setsplashsudoku(int[][] mainBOX) {
//
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                MainBOX[i][j] = mainBOX[i][j];
//            }
//        }
//        splashgetsudoku();
//    }


///////////////////////////////////////////////////////////////////////

///// in the level wise use one by on id not random

////        int id = sudoku_string_entry.getid();// it will come from shared prf
//    Log.e(".", "." + id);
//    try {
//        String sudoku_string = sudoku_string_entry.getSudoku_String(id);
//        Log.e("ssss", "..." + sudoku_string);
//        if (sudoku_string != null) {
//            fillMainbox(sudoku_string);
//            splashgetsudoku();// it will final main box 1-9 //
//        }
//    } catch (Exception e) {
//        Log.e("e. ", "e.  " + e);
//    }

//        sudoku_string_entry.storeid(id + 1);
//        sudoku_string_entry.addSudoku_String("," + id);

//        Log.e(".", "stored  " + (id + 1));


//    private int getid() {
//        sharedPreferences = getSharedPreferences(PREFRENCES, MODE_PRIVATE);
//            sharprf = true;
//            susername = sharedPreferences.getString(PRF_UNAME, "");
//        return 1;
//    }
//
//    private void storeid(int id) {
//        sharedPreferences = getSharedPreferences(PREFRENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(PRF_id, id);
//        editor.commit();
//    }


//////////////////////////////////////////////////////////////////////////////////

// ad string to database logic
//
//    public boolean addSudoku_String(String Quary) {
//
//        boolean b = false;
//        try {
//            dataBaseHelper.execute(Quary);
//            b = true;
//        } catch (Exception e) {
//            b = false;
//        }
//        return b;
//    }
//
//    public String getSudoku_String(int id) {
//
//        List<String> list = new ArrayList<String>();
//
//        String sudoku_string = "";
//        String sudoku_quary = "select Sudoku_String from Sudoku_List where id='" + id + "'";
//
//        try {
//            Cursor cursor = dataBaseHelper.rawQuery(sudoku_quary);
//
//            if (cursor.moveToFirst()) {
//                do {
//                    sudoku_string = cursor.getString(0);// 0 means that colum return by cursor
//                }
//                while (cursor.moveToNext());
//            }
//
//            cursor.close();
//
//        } catch (Exception e) {
//            sudoku_string = null;
//        }
//        return sudoku_string;
//    }

