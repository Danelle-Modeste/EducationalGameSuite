package edu.uwi.sta.educationalgamesuite;

import android.util.Log;

public class Sudoku {
    private int[][] board;
    private int[]solutions;
    private int[] convertedBoard;
    private final int SUDOKUSIZE = 3;
    
    public Sudoku(){
        genSolutions();
        genBoard();
        printBoard();
        randomise();
        System.out.println("*******************");
        printBoard();
        setConvertedBoard();
    }

    public int[] getConvertedBoard() {
        return convertedBoard;
    }

    private void setConvertedBoard() {
        for (int a=0;a<SUDOKUSIZE*SUDOKUSIZE;a++){
            for (int b=0;b<SUDOKUSIZE*SUDOKUSIZE;b++){
                convertedBoard[(a*9)+b]=board[a][b];
            }
            System.out.print('\n');
        }
    }

    private void randomise() {
        for (int a=0;a<SUDOKUSIZE;a++){ //randomise rows
            int row1= (int) (System.nanoTime()%3), row2=(row1+1)%3;
            int [] tempRow = board[a*SUDOKUSIZE+row1];

            board[a*SUDOKUSIZE+row1] = board[a*SUDOKUSIZE+row2];
            board[a*SUDOKUSIZE+row2]=tempRow;
        }
    }

    private void printBoard() {
        for (int a=0;a<SUDOKUSIZE*SUDOKUSIZE;a++){
            for (int b=0;b<SUDOKUSIZE*SUDOKUSIZE;b++){
                System.out.print(board[a][b]+ " ");
            }
            System.out.print('\n');
        }
    }

    private void genSolutions() {
        solutions = new int[]{  3,2,9,6,5,7,8,4,1,
                                7,4,5,8,3,1,2,9,6,
                                6,1,8,2,4,9,3,7,5,

                                1,9,3,4,6,8,5,2,7,
                                2,7,6,1,9,5,4,8,3,
                                8,5,4,3,7,2,6,1,9,

                                4,3,2,7,1,6,9,5,8,
                                5,8,7,9,2,3,1,6,4,
                                9,6,1,5,8,4,7,3,2};//permutate from the groups to generate board
    }

    private void genBoard() {
        board = new int[9][9];
        convertedBoard = new int[81];
        for (int a=0;a<SUDOKUSIZE*SUDOKUSIZE;a++){
            for (int b=0;b<SUDOKUSIZE*SUDOKUSIZE;b++){
                board[a][b] = getNumber(a,b);
            }
        }
    }

    private int getNumber(int a, int b) {
        return solutions[a*SUDOKUSIZE*SUDOKUSIZE+b];
    }
    public int[][] getBoard(){
        return board;
    }
}
