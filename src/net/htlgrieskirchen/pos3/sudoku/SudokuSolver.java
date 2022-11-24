package net.htlgrieskirchen.pos3.sudoku;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* Please enter here an answer to task four between the tags:
 * <answerTask4>
 *    Hier sollte die Antwort auf die Aufgabe 4 stehen.
 * </answerTask4>
 */
public class SudokuSolver implements ISodukoSolver {
    int[][] inputSudoku;

    public SudokuSolver() {
        inputSudoku = new int[9][9];
    }

    @Override
    public final int[][] readSudoku(File file) {
        int[][] sudoku;
        try(Stream<String> inputLinesStream = Files.lines(file.toPath())){
            sudoku = inputLinesStream
                    .map(item -> item.chars()
                        .filter(i -> (char) i != ';')
                        .map(Character::getNumericValue).toArray())
                    .toArray(int[][]::new);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sudoku;
    }

    @Override
    public boolean checkSudoku(int[][] rawSudoku) {
        int[] checkArray = new int[]{1,2,3,4,5,6,7,8,9};

        int[] sudokuXArray = new int[9];
        int[] sudokuYArray = new int[9];
        for(int i = 0; i < rawSudoku.length;i++){
            for (int j = 0; j < rawSudoku[i].length; j++) {
                sudokuXArray[j] = rawSudoku[i][j];
                sudokuYArray[j] = rawSudoku[j][i];
            }
            Arrays.sort(sudokuXArray);
            Arrays.sort(sudokuYArray);
            if(!Arrays.equals(checkArray, sudokuXArray) || !Arrays.equals(checkArray, sudokuYArray)){
                return false;
            }
        }

        int sudokuXPosEnd = 2;
        int sudokuYPosEnd = 2;
        int sudokuXPosStart = 0;
        int sudokuYPosStart = 0;
        int[] subgrid = new int[9];
        int counter = 0;

        while(sudokuYPosEnd < rawSudoku.length){
            for(int n = 0; n < 3; n++) {
                for (int i = sudokuYPosStart; i <= sudokuYPosEnd; i++) {
                    for (int j = sudokuXPosStart; j <= sudokuXPosEnd; j++) {
                        subgrid[counter] = rawSudoku[i][j];
                        counter++;
                    }
                }
                counter = 0;
                Arrays.sort(subgrid);
                if (!Arrays.equals(checkArray, subgrid)) {
                    return false;
                }
                sudokuXPosStart = sudokuXPosStart + 3;
                sudokuXPosEnd = sudokuXPosEnd + 3;
            }
            sudokuYPosEnd = sudokuYPosEnd+3;
            sudokuYPosStart = sudokuYPosStart+3;
            sudokuXPosStart = 0;
            sudokuXPosEnd = 0;
        }
        return true;
    }

    @Override
    public int[][] solveSudoku(int[][] rawSudoku) {
        // implement this method
        return new int[0][0]; // delete this line!
    }
    
    @Override
    public int[][] solveSudokuParallel(int[][] rawSudoku) {
        // implement this method
        return new int[0][0]; // delete this line!
    }

    // add helper methods here if necessary
}
