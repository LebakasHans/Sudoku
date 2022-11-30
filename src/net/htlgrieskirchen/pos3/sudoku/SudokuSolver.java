package net.htlgrieskirchen.pos3.sudoku;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
            int[] columnArray = getColumnArray(i, rawSudoku);
            int[] rowArray = getRowArray(i, rawSudoku);
            Arrays.sort(columnArray);
            Arrays.sort(rowArray);
            if(!Arrays.equals(checkArray, columnArray) || !Arrays.equals(checkArray, rowArray)){
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
        int[][] sudoku = rawSudoku.clone();
        boolean[][][] possibleNumbers = new boolean[9][9][10];



        while(!checkSudoku(sudoku)){
            for (int i = 0; i < sudoku.length; i++) {
                for (int j = 0; j < sudoku[i].length; j++) {
                    if(sudoku[i][j] == 0){
                        int[] column = getColumnArray(j, sudoku);
                        int[] row = getRowArray(i, sudoku);
                        int[] block = getBlockOfSpecificIndex(i, j, sudoku);
                        for (int k = 1; k <= 9; k++) {
                            if(Arrays.stream(column).boxed().toList().contains(k)){
                                possibleNumbers[i][j][k] = false;
                            }else if(Arrays.stream(row).boxed().toList().contains(k)){
                                possibleNumbers[i][j][k] = false;
                            }else if(Arrays.stream(block).boxed().toList().contains(k)){
                                possibleNumbers[i][j][k] = false;
                            }else{
                                possibleNumbers[i][j][k] = true;
                            }
                        }
                    }
                }
            }

            int onlyPossibleNumber;
            for (int i = 0; i < possibleNumbers.length; i++) {
                for (int j = 0; j < possibleNumbers[i].length; j++) {
                    onlyPossibleNumber = getOnlyPossibleNumber(possibleNumbers[i][j]);
                        if(onlyPossibleNumber > 0){
                            sudoku[i][j] = onlyPossibleNumber;
                        }
                }
            }
        }

        return new int[0][0];
    }

    private int[] getBlockOfSpecificIndex(int i, int j, int[][] rawSudoku) {
        switch (i){
            case 0,1,2:
                switch (j){
                    case 0,1,2:
                        return getBlock(0,2, rawSudoku);
                    case 3,4,5:
                        return getBlock(3,2, rawSudoku);
                    case 6,7,8:
                        return getBlock(6,2, rawSudoku);
                }
                break;
            case 3,4,5:
                switch (j){
                    case 0,1,2:
                        return getBlock(0,5, rawSudoku);
                    case 3,4,5:
                        return getBlock(3,5, rawSudoku);
                    case 6,7,8:
                        return getBlock(6,5, rawSudoku);
                }
                break;
            case 6,7,8:
                switch (j){
                    case 0,1,2:
                        return getBlock(0,8, rawSudoku);
                    case 3,4,5:
                        return getBlock(3,8, rawSudoku);
                    case 6,7,8:
                        return getBlock(6,8, rawSudoku);
                }
                break;
        }
        return null;
    }

    /**
    @return -1 if there are more than one possible number and the only possible number if there is only one
     */
    public int getOnlyPossibleNumber(boolean[] possibleNumbers){
        boolean[] allPossibleNumbers = possibleNumbers.clone();
        List<Boolean> allPossibleNumbersList = IntStream.range(0, allPossibleNumbers.length)
                .mapToObj(idx -> allPossibleNumbers[idx])
                .collect(Collectors.toList());

        allPossibleNumbersList.remove(true);
        if(allPossibleNumbersList.contains(true)){
                return -1;
        }

        return IntStream.range(0, possibleNumbers.length)
                .mapToObj(idx -> possibleNumbers[idx])
                .collect(Collectors.toList()).indexOf(true);
    }
    
    @Override
    public int[][] solveSudokuParallel(int[][] rawSudoku) {
        // implement this method
        return new int[0][0]; // delete this line!
    }

    // add helper methods here if necessary
}
