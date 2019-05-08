package com.example.sanket.sudokusolve;

import android.os.AsyncTask;
import android.util.Log;

public class Background extends AsyncTask {

    //   Background b = new Background();
    int[][] MainBOX = new int[9][9];
    boolean status = false;   // running>false, running > true;
    private AsyncTask asyncTask;
    static Thread thread = null;
    static Boolean b = true;
    static Boolean js = false;

    @Override
    protected Object doInBackground(Object[] objects) {
        genratesudoku();
        return null;
    }

    public void genratesudoku() {

        MainBOX = Sudokugenrator.sudoGen();

    }

    public void abc() {

        if (this.getStatus() == AsyncTask.Status.RUNNING) {
            status = false;
        }

        if (this.getStatus() == AsyncTask.Status.FINISHED) {
            if (isCancelled()) {
                this.execute();
                status = true;
                js = true;
            }
        }
        Log.e("status", " ." + status);
        Log.e("statu s" + isCancelled(), " ." + js);

    }

    public int[][] getsudoku() {
        return MainBOX;
    }


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        js = true;
//        if (this.getStatus() == AsyncTask.Status.FINISHED) {
//            js = true;
            setworkfinish();
//        }

    }

    public boolean setworkfinish() {
        return js;
    }
}
