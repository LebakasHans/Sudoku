package net.htlgrieskirchen.pos3.sudoku;


import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        SudokuSolver ss = new SudokuSolver();
        int[][] input = ss.readSudoku(new File("1_sudoku_level1.csv"));
        
        System.out.println(">--- ORIGINAL ---");
        // print the sudoku if you want
        int[][] output = ss.solveSudoku(input);
        System.out.println(">--- SOLUTION ---");
        // print the sudoku if you want
        System.out.println(">----------------");
        System.out.println("SOLVED    = " + ss.checkSudoku(input));
        System.out.println(">----------------");
        System.out.println("Benchmark = " + benchmark(input) + " ns");
    }


    public static long benchmark(int[][] rawSudoku){
        SudokuSolver ss = new SudokuSolver();
        long[] times = new long[10];
        for(int i = 0; i < times.length; i++){
            long startingTime = System.nanoTime();
            ss.readSudoku(new File("1_sudoku_level1.csv"));
            ss.solveSudoku(rawSudoku);
            ss.checkSudoku(rawSudoku);
            times[i] = System.nanoTime() -startingTime;
        }

        return (long) Arrays.stream(times).average().getAsDouble();
    }
}
