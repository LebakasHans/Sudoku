package net.htlgrieskirchen.pos3.sudoku;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
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
        boolean isSolved = true;
        Arrays.stream(rawSudoku)
                .map(Arrays::asList)
                .map(item -> item
                        .stream()
                        .map(ints -> ))
                .
        return false; // delete this line!
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
