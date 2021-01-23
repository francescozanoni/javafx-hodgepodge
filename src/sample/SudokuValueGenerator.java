package sample;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private boolean isValueInSection(int value, int rowNumber, int columnNumber) {
        return getSectionValues(rowNumber, columnNumber)
            .stream()
            .anyMatch(n -> n == value);
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

    private List<Integer> getSectionValues(int rowNumber, int columnNumber) {
        int sectionRowNumber = rowNumber / NUMBER_OF_SECTIONS_ON_LINE;
        int sectionColumnNumber = columnNumber / NUMBER_OF_SECTIONS_ON_LINE;
        int[] sectionFirstCell = {
            sectionRowNumber * NUMBER_OF_SECTIONS_ON_LINE,
            sectionColumnNumber * NUMBER_OF_SECTIONS_ON_LINE
        };
        int[] sectionLastCell = {
            sectionRowNumber * NUMBER_OF_SECTIONS_ON_LINE + NUMBER_OF_SECTIONS_ON_LINE,
            sectionColumnNumber * NUMBER_OF_SECTIONS_ON_LINE + NUMBER_OF_SECTIONS_ON_LINE
        };
        List<Integer> list = new ArrayList<>();
        for (int i = sectionFirstCell[0]; i < sectionLastCell[0]; i++) {
            list.addAll(Arrays.asList(matrix[i]).subList(sectionFirstCell[1], sectionLastCell[1]));
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
        // https://www.baeldung.com/java-listing-numbers-within-a-range
        List<Integer> possibleValues = IntStream.rangeClosed(1, NUMBER_OF_ROWS).boxed().collect(Collectors.toList());
        List<Integer> allowedValues;

        for (int rowNumber = 0; rowNumber < matrix.length; rowNumber++) {
            for (int columnNumber = 0; columnNumber < matrix[rowNumber].length; columnNumber++) {

                allowedValues = new ArrayList<>(possibleValues);

                allowedValues.removeAll(getRowValues(rowNumber));
                allowedValues.removeAll(getColumnValues(columnNumber));
                allowedValues.removeAll(getSectionValues(rowNumber, columnNumber));

                // Value generation can cause a situation with no allowed values at all.
                // If it happens, generation is re-started.
                if (allowedValues.size() == 0) {
                    generateMatrix();
                    return;
                }

                matrix[rowNumber][columnNumber] = allowedValues.get(r.nextInt(allowedValues.size()));
                
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
