package sample;

import java.util.*;
import java.util.stream.Collectors;

public class SudokuValueGenerator {

    private Integer[][] matrix;
    private int NUMBER_OF_ROWS = 9;
    private int NUMBER_OF_SECTIONS_ON_LINE = 3;

    public static void main (String[] args) {
        System.out.println(new SudokuValueGenerator().generateAsString());
    }

    public String generateAsString() {
        generateMatrix();
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
        List<Integer> squareValues = getSquareValues(rowNumber, columnNumber);
        return squareValues.stream().anyMatch(n -> n == value);
    }

    private List<Integer> getRowValues(int rowNumber) {
        return Arrays.stream(matrix[rowNumber])
                .filter(n -> n != 0)
                .collect(Collectors.toList());
    }

    private List<Integer> getColumnValues(int columnNumber) {
        List<Integer> list = new ArrayList<>();
        for (Integer[] row : matrix) {
            list.add(row[columnNumber]);
        }
        return list.stream()
                .filter(n -> n != 0)
                .collect(Collectors.toList());
    }

    private List<Integer> getSquareValues(int rowNumber, int columnNumber) {
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
                .filter(n -> n != 0)
                .collect(Collectors.toList());
    }

    private void generateMatrix() {

        matrix = new Integer[NUMBER_OF_ROWS][NUMBER_OF_ROWS];

        for (Integer[] row : matrix) {
            Arrays.fill(row, 0);
        }

        Random r = new Random();

        for (int rowNumber = 0; rowNumber < matrix.length; rowNumber++) {
            for (int columnNumber = 0; columnNumber < matrix[rowNumber].length; columnNumber++) {

                Integer[] allAllowedValues = {1, 2, 3, 4, 5, 6, 7, 8, 9};

                // https://howtodoinjava.com/java/array/intersection-between-arrays/

                HashSet<Integer> set = new HashSet<>(Arrays.asList(allAllowedValues));

                set.removeAll(getRowValues(rowNumber));
                set.removeAll(getColumnValues(columnNumber));
                set.removeAll(getSquareValues(rowNumber, columnNumber));

                int[] allowedValues = set.stream().mapToInt(Integer::intValue).toArray();

                matrix[rowNumber][columnNumber] = allowedValues[r.nextInt(allowedValues.length)];

            }
        }
    }

    private String getIntegerMatrixAsString() {

        StringBuilder incrementalString = new StringBuilder();

        for (int rowNumber = 0; rowNumber < matrix.length; rowNumber++) {
            for (int columnNumber = 0; columnNumber < matrix[rowNumber].length; columnNumber++) {
                incrementalString.append(matrix[rowNumber][columnNumber]).append(" ");
                if (columnNumber == matrix[rowNumber].length - 1 && rowNumber < matrix.length - 1) {
                    incrementalString.append("\n");
                }
            }
        }

        return incrementalString.toString();
    }

}
