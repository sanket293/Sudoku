package com.example.sanket.sudokusolve;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanket on 10/28/2016.
 */
public class SudokuEntry {

    public static final String DATABASE_NAME = "Sudokugame";
    public static final int DATABASE_VIRSON = 1;


    DataBaseHelper dataBaseHelper;
    Context context;


    public SudokuEntry(Context context) {

        String Sudukugame = null;
        dataBaseHelper.getDataBaseHelper(context, DATABASE_NAME);
        this.context = context;
        Log.e("context", "is" + context.toString());
        String Quary = "insert into Sudoku_List (Id, Sudoku_Id, Sudoku_Field, Sudoku_String) values('1','1','1','" + "abc" + "');";
        Log.e("i quary", "." + Quary);
//        addSudoku_String(Quary);

        Quary = "select * From Sudoku_List";
        String result_str = getSudoku_String(Quary);
        Log.e("select quary", "." + Quary);
        Log.e("select quary ", "REsult" + result_str);


    }


//    public void SudokuEntry(Context context) {
//
//        String Sudukugame = null;
//        dataBaseHelper.getDataBaseHelper(context, DATABASE_NAME);
//        this.context = context;
//        String Quary = "insert into Sudoku_List (Id, Sudoku_Id, Sudoku_Field, Sudoku_String) values('1','1','1','" + "abc" + "');";
//        Log.e("i quary", "." + Quary);
//        addSudoku_String(Quary);
//
//        Quary = "select * From Sudoku_List";
//        String result_str = getSudoku_String(Quary);
//        Log.e("select quary", "." + Quary);
//        Log.e("select quary ", "REsult" + result_str);
//
//    }

    public boolean addSudoku_String(String Quary) {

        boolean b = false;
        try {
            dataBaseHelper.execute(Quary);
            b = true;
        } catch (Exception e) {
            b = false;
        }
        return b;
    }

    public String getSudoku_String(String Quary) {

        List<String> list = new ArrayList<String>();

        String sudoku_string = "";


        try {
            Cursor cursor = dataBaseHelper.rawQuery(Quary);

            if (cursor.moveToFirst()) {
                do {
//                        list.add(cursor.getString(1));
                    sudoku_string = cursor.getString(0);// 0 means that colum return by cursor
                }
                while (cursor.moveToNext());
            }

            cursor.close();

        } catch (Exception e) {
            sudoku_string = "ERROR";
        }
        return sudoku_string;

    }


    public static class DataBaseHelper extends SQLiteOpenHelper {

        private static SQLiteDatabase sqliteDb;
        private static DataBaseHelper dataBaseHelper;

        private Context context;
        static Cursor cursor = null;


        public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, null, DATABASE_VIRSON);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public SQLiteDatabase getDatabase() {
            return sqliteDb;
        }

        private static void initialize(Context context, String databaseName) {
            if (dataBaseHelper == null) {

                if (!checkDatabase(context, databaseName)) {

                    try {
                        copyDataBase(context, databaseName);
                    } catch (IOException e) {

                        System.out.println(databaseName
                                + " does not exists ");
                    }
                }

                dataBaseHelper = new DataBaseHelper(context, databaseName, null,
                        DATABASE_VIRSON);
                sqliteDb = dataBaseHelper.getWritableDatabase();

                System.out.println("instance of  " + databaseName + " created ");
            }
        }


        public static final DataBaseHelper getDataBaseHelper(Context context,
                                                             String databaseName) {
            initialize(context, databaseName);
            return dataBaseHelper;
        }

        private static void copyDataBase(Context aContext, String databaseName)
                throws IOException {

            InputStream myInput = aContext.getAssets().open(databaseName);

            String outFileName = getDatabasePath(aContext, databaseName);

            File f = new File("/data/data/" + aContext.getPackageName()
                    + "/databases/");
            if (!f.exists()) {
                f.mkdir();
            }

            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();

            System.out.println(databaseName + " copied");
        }

        public static boolean checkDatabase(Context aContext, String databaseName) {
            SQLiteDatabase checkDB = null;

            try {
                String myPath = getDatabasePath(aContext, databaseName);

                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READONLY);

                checkDB.close();
            } catch (SQLiteException e) {

                System.out.println(databaseName + " does not exists");
            }

            return checkDB != null ? true : false;
        }

        private static String getDatabasePath(Context aContext, String databaseName) {
            String path = "/data/data/" + aContext.getPackageName() + "/databases/"
                    + databaseName;
            Log.e("PAth ", "is " + path);
            return path;
        }

        public static Cursor rawQuery(String query) {
            try {
                if (sqliteDb.isOpen()) {
                    sqliteDb.close();
                }
                sqliteDb = dataBaseHelper.getWritableDatabase();

                cursor = null;
                cursor = sqliteDb.rawQuery(query, null);
            } catch (Exception e) {
                System.out.println("DB ERROR  " + e.getMessage());
                e.printStackTrace();
            }
            return cursor;
        }

        public static void execute(String query) {
            try {
                if (sqliteDb.isOpen()) {
                    sqliteDb.close();
                }
                sqliteDb = dataBaseHelper.getWritableDatabase();
                sqliteDb.execSQL(query);
            } catch (Exception e) {
                System.out.println("DB ERROR  " + e.getMessage());
                e.printStackTrace();
            }
        }


    }
}
