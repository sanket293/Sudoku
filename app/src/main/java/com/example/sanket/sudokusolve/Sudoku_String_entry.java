package com.example.sanket.sudokusolve;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import static java.lang.Thread.sleep;

/**
 * Created by Sanket on 10/29/2016.
 */

public class Sudoku_String_entry {

    DataBaseHelper dataBaseHelper;
    public static int[][] MainBOX = new int[9][9];
    static Background background = new Background();
    static Boolean b = true;
    static Boolean js = true;
    static Boolean ISFINISH = false;
    static Thread thread = null;
    static Thread thread_one = null;
    Context context;

    public static final String PREFRENCES = "Myprefrences";
    public static final String PRF_id = "PRF_id";
    SharedPreferences sharedPreferences;
    Boolean sharprf = false;
    int id = 1;
    String sudoku_Stringtest = "";
    public static int sudoku_string_genrated = 3; //29

    private AsyncTask asyncTask;

    public Sudoku_String_entry(Context context) {
        this.context = context;


        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (b) {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        );
//        background.execute();
//        thread.start();


//        background.execute();

//        thread.start();

    }

    public void tradhandle() {
        while (true) {
            if (ISFINISH) {
                thread.stop();
                background.execute();
                thread.start();
                ISFINISH = false;
            }
        }
    }

    private void addandstartnewsudoku(String sudoku_String) {
        boolean check = addSudoku_String(sudoku_String);
        if (check) {
            sudoku_Stringtest = sudoku_String;
            Sudoku_String_entry.sudoku_string_genrated++;
            js = false;
            Log.e("Sudoku string genrate", "finish");
            Log.e("Sudoku string genrate", "." + Sudoku_String_entry.sudoku_string_genrated);
            startthread();
        } else {
            try {
                startthread();
            } catch (Exception e) {
            }
        }
    }

    public static void startthread() {
        background.execute();
        thread.start();
    }

    private boolean checkMainboxEmpty(int[][] mainBOX) {
        int a = 0;
        boolean b = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (MainBOX[i][j] == 0) {
                    a++;
                }
            }

        }

        if (a > 9) {  // yes it's empty
            b = true;
            return b;
        } else {
            b = false;
        }

        return b;
    }






    public String createesudokustring(int[][] mainBOX) {
        String ss = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ss = ss + mainBOX[i][j] + ",";
            }
        }
        //Log.e("....", "" + ss);
        return ss;
    }

    public boolean addSudoku_String(String Sudoku_String) {
        String Quary = "insert into Sudoku_List (Sudoku_Id,Sudoku_field,Sudoku_String)values('1','1','" + Sudoku_String + "')";
        boolean bb = false;
        try {
            dataBaseHelper.execute(Quary);
            bb = true;
        } catch (Exception e) {
            Log.e("insert error", "" + e);
            bb = false;
        }
        //    Log.e("ädd sudoku", "." + bb);
        return bb;
    }

    public String getSudoku_String(int id) {
        dataBaseHelper = DataBaseHelper.getInstance(context);

//        List<String> list = new ArrayList<String>();

        String sudoku_string = "";
        String sudoku_quary = "select Sudoku_String from Sudoku_List where id='" + id + "'";

        try {
            Cursor cursor = dataBaseHelper.rawQuery(sudoku_quary);

            if (cursor.moveToFirst()) {
                do {
                    sudoku_string = cursor.getString(0);// 0 means that colum return by cursor
                }
                while (cursor.moveToNext());
            }

            cursor.close();

        } catch (Exception e) {
            Log.e("db error sudoku string","."+e);
            sudoku_string = null;
        }

        return sudoku_string;
    }








    public int totalgenratednum() {
        return sudoku_string_genrated;
    }


    public int getid() {
        sharedPreferences = context.getSharedPreferences(PREFRENCES, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt(PRF_id, id);
        return id;
    }

    public void storeid(int id) {
        sharedPreferences = context.getSharedPreferences(PREFRENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PRF_id, id);
        this.id = id;
        editor.commit();
    }
}
