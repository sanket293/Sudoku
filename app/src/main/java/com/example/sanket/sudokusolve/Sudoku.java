package com.example.sanket.sudokusolve;

import android.util.Log;

public class Sudoku {
    //box size, and game SIZE ==> e.g. size = 3, SIZE = 9
    //game will be the game
    private int size = 3, SIZE;
    private int[][] game;

    public Sudoku(int _size) {
        size = _size;
        SIZE = size * size;
        game = generateGame();
        sudokugame();
    }


    //This will return the game
    private int[][] generateGame() {
        //Set everything to -1 so that it cannot be a value
        int[][] g = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                g[i][j] = -1;
            }
        }

        if (createGame(0, 0, g)) {
            return g;
        }
        return null;
    }


    public int[][] sudokugame() {
        return game;
    }

    //Create the game
    private boolean createGame(int x, int y, int[][] g) {
        //An array of integers
        Rand r = new Rand(SIZE);

        int num;
        boolean b = true;
        //for every random num in r
        for (int NUM = 0; NUM <=  SIZE ; NUM++) {


            num = r.get(NUM);
            if (NUM==SIZE) {
                Log.e("NUM<0", "RESTRAT");
                x = 0;
                y = 0;
                num = r.get(NUM);
            }

//                while (b) {
//                    num = r.get(NUM);
//                    Log.e("num", "." + num);
//                    if (num > -1) {
//                        b = false;
//                    }
//                }
//            }

            Log.e("aaa", "." + num);

            //if num is valid

            if (isValid(x, y, g, num)) {
                //next cell coordinates

                int nx = (x + 1) % SIZE;
                int ny = y;
                if (nx == 0) {
                    ny++;
                }

                //set this cell to num
                g[x][y] = num;

                Log.e(" .   " + x + " " + y + "  " + num, " ." + nx + " " + ny + " " + isValid(x, y, g, num));

                //if the next cell is valid return true
                if (createGame(nx, ny, g)) {
                    return true;
                }


                //otherwise return false
//                else {
                    g[x][y] = -1;
                return false;
//                }

            }
        }
        return true;
    }

    private boolean isValid(int x, int y, int[][] g, int num) {
        boolean b = true;
        //Rows&&Cols
        for (int i = 0; i < SIZE; i++) {
            if (g[i][y] == num || g[x][i] == num) {
                b = false;
                Log.e("r&c", "." + b);

                return b;
            }
        }


//        for(int i=0;i<SIZE;i++){
//            if (g[i][y] == num || g[x][i] == num) {
//                return false;
//            }
//        }


        //Box
        int bx = x - (x % size);
        int by = y - (y % size);
        for (int i = bx; i < bx + size; i++) {
            for (int j = by; j < by + size; j++) {
                if (g[i][j] == num) {
                    b = false;

                    Log.e("Box", "." + b);

                    return b;
                }
            }
        }

        Log.e("other", "." + b);

        return b;
    }


    public class Rand {
        private int rSize;
        private int[] r;

        public Rand(int _size) {
            rSize = _size;

            r = new int[rSize];

            for (int i = 0; i < rSize; i++) r[i] = i;

            for (int i = 0; i < rSize * SIZE; i++) {
                int a = (int) (Math.random() * rSize);
                int b = (int) (Math.random() * rSize);
                int n = r[a];
                r[a] = r[b];
                r[b] = n;
            }
        }

        public int get(int i) {
            if (i >= 0 && i < rSize) return r[i];
            return -1;
        }
    }
}
