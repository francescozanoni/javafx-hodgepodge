package sample;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuValueGenerator {

    private Integer[][] matrix;
    private int NUMBER_OF_ROWS = 9;
    private int NUMBER_OF_SECTIONS_PER_LINE = 3;

    private List<Integer> getRowValues(int rowIndex) {
        return Arrays.stream(matrix[rowIndex])
                .filter(n -> n != 0)
                .collect(Collectors.toList());
    }

    private List<Integer> getColumnValues(int columnIndex) {
        List<Integer> list = new ArrayList<>();
        for (Integer[] row : matrix) {
            list.add(row[columnIndex]);
        }
        return list.stream()
                .filter(n -> n != 0)
                .collect(Collectors.toList());
    }

    private List<Integer> getSectionValues(int rowIndex, int columnIndex) {

        // Index of section vertical position.
        int sectionRowIndex = rowIndex / NUMBER_OF_SECTIONS_PER_LINE;

        // Index of section horizontal position.
        int sectionColumnIndex = columnIndex / NUMBER_OF_SECTIONS_PER_LINE;

        // Cells identified by rowIndex/columnIndex.
        int[] sectionFirstCell = {
                sectionRowIndex * NUMBER_OF_SECTIONS_PER_LINE,
                sectionColumnIndex * NUMBER_OF_SECTIONS_PER_LINE
        };
        int[] sectionLastCell = {
                sectionRowIndex * NUMBER_OF_SECTIONS_PER_LINE + NUMBER_OF_SECTIONS_PER_LINE,
                sectionColumnIndex * NUMBER_OF_SECTIONS_PER_LINE + NUMBER_OF_SECTIONS_PER_LINE
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

    public Integer[][] generate() {

        matrix = new Integer[NUMBER_OF_ROWS][NUMBER_OF_ROWS];

        for (Integer[] row : matrix) {
            Arrays.fill(row, 0);
        }

        Random randomizer = new Random();

        // https://www.baeldung.com/java-listing-numbers-within-a-range
        List<Integer> possibleValues = IntStream.rangeClosed(1, NUMBER_OF_ROWS).boxed().collect(Collectors.toList());
        List<Integer> allowedValues;

        for (int rowIndex = 0; rowIndex < matrix.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrix[rowIndex].length; columnIndex++) {

                allowedValues = new ArrayList<>(possibleValues);

                allowedValues.removeAll(getRowValues(rowIndex));
                allowedValues.removeAll(getColumnValues(columnIndex));
                allowedValues.removeAll(getSectionValues(rowIndex, columnIndex));

                // Value generation can cause a situation with no allowed values at all.
                // If it happens, generation is re-started.
                if (allowedValues.size() == 0) {
                    return generate();
                }

                matrix[rowIndex][columnIndex] = allowedValues.get(randomizer.nextInt(allowedValues.size()));

            }
        }

        return matrix;

    }

}
