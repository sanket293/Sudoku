package com.example.sanket.sudokusolve;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import static java.lang.Thread.sleep;

public class Main2Activity extends AppCompatActivity {
    public static int[][] MBOX = new int[9][9];
    public static int[][] TMP = new int[9][9];
    Thread thread = null;
    boolean b = true;
    DataBaseHelper dataBaseHelper;
    //    Context context;
    public String sudokuString = "";
    public static int SUDOKU_NUM = 1;
    private   int NUM = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        final Bac bac = new Bac();
        threadch(bac);

        bac.execute();
        thread.start();


    }

    private void threadch(final Bac bac) {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (b) {
                    try {
                        sleep(10000);
                        if (bac.getStatus() == AsyncTask.Status.FINISHED) {
//                            Log.e("bs", "finish");
//                            Log.e("bs" + SUDOKU_NUM, "." + MBOX);
//                            Log.e("bs" + SUDOKU_NUM, "." + TMP);

//                            showfinalboard(MBOX );

                            if (SUDOKU_NUM == 1) {
                                sudokuString = createesudokustring(MBOX);
                                addSudoku_String(sudokuString);
                                Bac bac = new Bac();
                                bac.execute();
                                TMP = bac.getFinish();
                                SUDOKU_NUM++;
                            } else {
                                if (!checkSameornot(TMP)) {
                                    sudokuString = createesudokustring(MBOX);
                                    addSudoku_String(sudokuString);
                                    Bac bac = new Bac();
                                    bac.execute();
                                    TMP = bac.getFinish();
                                    SUDOKU_NUM++;

                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private boolean checkSameornot(int[][] mbox) {
        boolean check = true;
        if (mbox == MBOX) {
//            check = true;
            return true;
        } else {
//            check = false;
            return false;
        }
//        Log.e("chec same or not", "." + check);
//        return check;
    }


    private void showfinalboard(int[][] mainBOX) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                stringBuffer.append("\t" + mainBOX[i][j]);
            }
            stringBuffer.append("\n");
        }
        Log.e(" .", "" + stringBuffer);
    }

    public String createesudokustring(int[][] mainBOX) {
        String ss = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                ss = ss + mainBOX[i][j] + ",";
            }
        }
        Log.e("....", "" + ss);
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
//            Log.e("ädd sudoku", "." + bb);
        return bb;
    }


    public String getSudoku_String(int id) {
        dataBaseHelper = DataBaseHelper.getInstance(this);
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
            Log.e("db error sudoku string", "." + e);
            sudoku_string = null;
        }
        Log.e("sudoku_string", "." + sudoku_string);
        return sudoku_string;
    }

    public void abc(View view) {
        getSudoku_String(NUM );
        NUM++;
    }

    public class Bac extends AsyncTask<Void, Void, int[][]> {
        boolean status = false;   // running>false, running > true;
        private AsyncTask asyncTask;


        @Override
        protected int[][] doInBackground(Void... params) {

            MBOX = Sudokugenrator.sudoGen();

            return MBOX;
        }


        @Override
        protected void onPostExecute(int[][] MBOX) {
            super.onPostExecute(MBOX);

//            setFinish(MBOX);
            getFinish();
        }

        private void setFinish(int[][] MBOX) {
            status = true;
        }

        private int[][] getFinish() {
            return MBOX;
        }


    }


}
