package sample;

import java.util.*;

public class SudokuValueGenerator {

    private Integer[][] matrix;
    private int NUMBER_OF_ROWS = 4;
    private int NUMBER_OF_SECTIONS_ON_LINE = 2;

    public String generateAsString() {
        generateIntegerMatrix();
        return getIntegerMatrixAsString();
    }

    private boolean isValueInRow(int value, int rowNumber) {
        return Arrays.stream(matrix[rowNumber]).anyMatch(n -> n == value);
    }

    private boolean isValueInColumn(int value, int columnNumber) {
        for (Integer[] row : matrix) {
            if (row[columnNumber] == value) {
                return true;
            }
        }
        return false;
    }

    private boolean isValueInSquare(int value, int rowNumber, int columnNumber) {
        int[] squareValues = getSquareValues(rowNumber, columnNumber);
        return Arrays.stream(squareValues).anyMatch(n -> n == value);
    }

    private int[] getSquareValues(int rowNumber, int columnNumber) {
        int squareRowNumber = rowNumber / NUMBER_OF_SECTIONS_ON_LINE;
        int squareColumnNumber = columnNumber / NUMBER_OF_SECTIONS_ON_LINE;
        int[] squareFirstCell = {squareRowNumber * NUMBER_OF_SECTIONS_ON_LINE, squareColumnNumber * 3};
        int[] squareLastCell = {squareRowNumber * NUMBER_OF_SECTIONS_ON_LINE + NUMBER_OF_SECTIONS_ON_LINE, squareColumnNumber * NUMBER_OF_SECTIONS_ON_LINE + NUMBER_OF_SECTIONS_ON_LINE};
        List<Integer> list = new ArrayList<>();
        for (int i = squareFirstCell[0]; i < squareLastCell[0]; i++) {
            list.addAll(Arrays.asList(matrix[i]).subList(squareFirstCell[1], squareLastCell[1]));
        }
        // https://www.techiedelight.com/convert-list-integer-array-int
        return list.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private void generateIntegerMatrix() {

        matrix = new Integer[NUMBER_OF_ROWS][NUMBER_OF_ROWS];

        for (Integer[] integers : matrix) {
            Arrays.fill(integers, 0);
        }

        SplittableRandom r = new SplittableRandom();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int newValue = r.nextInt(1, NUMBER_OF_ROWS + 1);
                while (isValueInRow(newValue, i) ||
                        isValueInColumn(newValue, j) ||
                        isValueInSquare(newValue, i, j)) {
                    newValue = r.nextInt(1, NUMBER_OF_ROWS + 1);
                }
                matrix[i][j] = newValue;
            }
        }
    }

    private String getIntegerMatrixAsString() {

        StringBuilder incrementalString = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                incrementalString.append(matrix[i][j]).append(" ");
                if (j == matrix[i].length - 1 && i < matrix.length - 1) {
                    incrementalString.append("\n");
                }
            }
        }

        return incrementalString.toString();
    }

}
