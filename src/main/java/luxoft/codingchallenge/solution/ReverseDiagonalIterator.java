package luxoft.codingchallenge.solution;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ReverseDiagonalIterator<T> implements Iterator<T> {

    private final T[][] values;

    // number of diagonals in a matrix
    private final int diags;

    private int diagStartRow = 0;

    private int diagStartColumn = 0;

    private int diagIndex = 0;

    private final int maxRows;

    private final int maxColumns;

    private int currRow = 0;

    private int currColumn = 0;

    ReverseDiagonalIterator(T[][] values) {
        this.values = Objects.requireNonNull(values, "values must not be null");
        maxRows = this.values.length;
        maxColumns = Arrays.stream(this.values)
                .mapToInt(f -> (f == null) ? 0 : f.length)
                .max()
                .orElse(0);
        this.diags = maxRows + maxColumns - 1;
    }


    @Override
    public boolean hasNext() {
        if (diagIndex >= diags) {
            return false;
        }
        // is currentPoint available
        if (inMatrix(currRow, currColumn)) {
            return true;
        }
        hopToNext();
        return diagIndex < diags;
    }

    private void hopToNext() {
        while (diagIndex < diags) {
            if (inMatrix(currRow, currColumn)) {
                break;
            } else if (!isInBounds(currRow, currColumn)) {
                hopToNextDiagonal();
            } else {
                currRow++;
                currColumn--;
            }
        }
    }

    private void hopToNextDiagonal() {
        diagIndex++;
        if (diagStartColumn <= maxColumns - 1) {
            diagStartColumn++;
        } else {
            diagStartRow++;
        }
        currRow = diagStartRow;
        currColumn = diagStartColumn;
    }

    @Override
    public T next() {
        // defensive against misuse of iterator protocol.
        // we should also defend against concurrent modification but let's not for now.
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        T value = values[currRow][currColumn];
        currRow++;
        currColumn--;
        return value;
    }

    private boolean inMatrix(int i, int j) {
        if (i < maxRows) {
            T[] rows = values[i];
            return (rows != null) && (j >= 0) && (j < rows.length);
        }
        return false;
    }

    private boolean isInBounds(int i, int j) {
        return j >= 0 && i < maxRows;
    }
}

