package com.example.sanket.sudokusolve;

import android.util.Log;

import java.util.Random;

/**
 * Created by Sanket on 11/3/2016.
 */
public class getsudoku {

    public getsudoku() {
    }

    private void Mainboxgenrator() {
        Random r = new Random();
//        int id = r.nextInt(sudoku_string_entry.totalgenratednum());
        int id = r.nextInt(19);
        if (id <= 0) {
            id++;
        }
        if (id > 19) {
            id--;
        }

        Log.e(".", "." + id);
        try {
//            String sudoku_string = sudoku_string_entry.getSudoku_String(id);
//            if (sudoku_string != null) {
//                fillMainbox(sudoku_string);
////                splashgetsudoku();// it will final main box 1-9 //
//            }
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
//                    MainBOX[i][j] = Integer.parseInt(splited[a]);
                    a++;
                } catch (Exception e) {
                }
            }
        }
    }

}

