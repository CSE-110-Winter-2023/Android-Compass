package edu.ucsd.cse110.projects110;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testIsValidDouble() {
        String validDouble = "123.456";
        String invalidDouble = "abc";

        assertTrue(ValidLocations.isValidDouble(validDouble));
        assertFalse(ValidLocations.isValidDouble(invalidDouble));
    }

    @Test
    public void testIsValidLong() {
        String validLong = "100.0\u00B0";
        String invalidLong = "200.0\u00B0";

        assertTrue(ValidLocations.isValidLong(validLong));
        assertFalse(ValidLocations.isValidLong(invalidLong));
    }
    @Test
    public void testIsValidLat() {
        String validLat = "90.0\u00B0";
        String invalidLat = "100.0\u00B0";

        assertTrue(ValidLocations.isValidLat(validLat));
        assertFalse(ValidLocations.isValidLat(invalidLat));
    }

}