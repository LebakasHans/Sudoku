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

        for(int i = 0; i < rawSudoku.length; i++) {
            if(!Arrays.equals(checkArray, getColumnArray(i, rawSudoku)) || !Arrays.equals(checkArray, getRowArray(i, rawSudoku))){
                return false;
            }
        }

        int[] block;
        for (int j = 2; j < 9; j = j + 3) {
            for (int i = 0; i < 9; i = i + 3) {
                block = getBlock(i, j, rawSudoku);
                Arrays.sort(block);
                if (!Arrays.equals(checkArray, block)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] getBlock(int columnIndexStart, int rowIndexEnd, int[][] rawSudoku){
        int[] block = new int[9];
        int counter = 0;
        for(int i = rowIndexEnd; i < rowIndexEnd + 3; i++){
            for (int j = columnIndexStart; j <= columnIndexStart+2; j++) {
                block[counter] = rawSudoku[i-2][j];
                counter++;
            }
        }
        return block;
    }

    private int[] getRowArray(int rowIndex, int[][] rawSudoku){
        int[] rowArray = new int[9];
        for(int i = 0; i < rawSudoku[rowIndex].length; i++){
            rowArray[i] = rawSudoku[rowIndex][i];
        }
        return rowArray;
    }

    private int[] getColumnArray(int columnIndex, int[][] rawSudoku){
        int[] columnArray = new int[9];
        for(int i = 0; i < rawSudoku[columnIndex].length; i++){
            columnArray[i] = rawSudoku[i][columnIndex];
        }
        return  columnArray;
    }

    @Override
    public int[][] solveSudoku(int[][] rawSudoku) {


        return new int[0][0];
    }
    
    @Override
    public int[][] solveSudokuParallel(int[][] rawSudoku) {
        // implement this method
        return new int[0][0]; // delete this line!
    }

    // add helper methods here if necessary
}
