package luxoft.codingchallenge.solution;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ReverseDiagonalIteratorTest {

    @Test
    void givenNullArrayThrows() {
        Exception exception = assertThrows(
                NullPointerException.class,
                () -> new ReverseDiagonalIterator<>(null));
        assertEquals("values must not be null", exception.getMessage());
    }

    @Test
    void givenEmptyArrayDoesNotIterate() {
        var iterator = new ReverseDiagonalIterator<>(new Object[0][0]);
        assertFalse(iterator.hasNext());
    }

    @Test
    void givenEmptyArrayThrowsNSEOnNext() {
        var iterator = new ReverseDiagonalIterator<>(new Object[0][0]);
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void givenSingleElementOnlyReturnsIt() {
        var element = "Luxoft";
        String[][] matrix = { {element} };
        var iterator = new ReverseDiagonalIterator<>(matrix);
        assertTrue(iterator.hasNext());
        assertEquals(iterator.next(), element);
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void givenOnlyColumnIteratesColumn() {
        String[][] elements = {
                {"Humpty"},
                {"Dumpty"}};
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Humpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dumpty", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void givenOnlyRowIteratesRow() {
        String[][] elements = {{"Humpty", "Dumpty"}};
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Humpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dumpty", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void twoByTwo() {
        String[][] elements = {
                {"Humpty", "Dumpty"},
                {"Hunky", "Dory"}};
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Humpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dumpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Hunky", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dory", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void oneNullOneNonNullRow() {
        String[][] elements = {
                null,
                {"Hunky", "Dory"}};
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Hunky", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dory", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void oneNonNullOneNullRow() {
        String[][] elements = {
                {"Humpty", "Dumpty"},
                null};
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Humpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dumpty", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void nullColumnNonNullRow() {
        String[][] elements = {
                {null, "Dumpty"},
                {null, "Dory"}};
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertNull(iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dumpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertNull(iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dory", iterator.next());

        assertFalse(iterator.hasNext());
    }


    @Test
    void oneSize2RowOneSize2Column() {
        String[][] elements = {
                {"Humpty", "Dumpty"},
                {"Hunky"}};
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Humpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dumpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Hunky", iterator.next());

        assertFalse(iterator.hasNext());
    }


    @Test
    void oneSize1RowOneSize2Column() {
        String[][] elements = {
                {"Humpty"},
                {"Hunky"}};
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Humpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Hunky", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void rowsSize3Size2Size1() {
        String[][] elements = {
                {"Humpty", "Dumpty", "Hunky"},
                {"Dory", "Batman"},
                {"Robin"}
        };
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Humpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dumpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dory", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Hunky", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Batman", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Robin", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void rowsSize1Size2Size3() {
        String[][] elements = {
                {"Humpty"},
                {"Dumpty", "Hunky"},
                {"Dory", "Batman", "Robin"}
        };
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Humpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dumpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Hunky", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dory", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Batman", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Robin", iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    void rowsSize1Size3Size2() {
        String[][] elements = {
                {"Humpty"},
                {"Dory", "Batman", "Robin"},
                {"Dumpty", "Hunky"},
        };
        var iterator = new ReverseDiagonalIterator<>(elements);

        assertTrue(iterator.hasNext());
        assertEquals("Humpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dory", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Batman", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Dumpty", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Robin", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals("Hunky", iterator.next());

        assertFalse(iterator.hasNext());
    }

}