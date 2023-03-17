package edu.ucsd.cse110.projects110;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TimeUnitTest {
    @Test
    public void testFormatMillisToHM() {
        // Test case 1: 0 milliseconds
        assertEquals("0m", TimeUnitConvert.formatMillisToHM(0));

        // Test case 2: 34 minutes in milliseconds
        assertEquals("34m", TimeUnitConvert.formatMillisToHM(34 * 60 * 1000));

        // Test case 3: 60 minutes in milliseconds (1 hour)
        assertEquals("1h", TimeUnitConvert.formatMillisToHM(60 * 60 * 1000));

        // Test case 4: 90 minutes in milliseconds (1 hour and 30 minutes)
        assertEquals("1h", TimeUnitConvert.formatMillisToHM(90 * 60 * 1000));

        // Test case 5: 120 minutes in milliseconds (2 hours)
        assertEquals("2h", TimeUnitConvert.formatMillisToHM(120 * 60 * 1000));
    }
}


