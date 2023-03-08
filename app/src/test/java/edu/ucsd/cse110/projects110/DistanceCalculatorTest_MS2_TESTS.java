package edu.ucsd.cse110.projects110;
import org.junit.Test;
import static org.junit.Assert.*;

public class DistanceCalculatorTest_MS2_TESTS {

    @Test
    public void testDistance() {
        double lat1 = 32.64;
        double lon1 = -117.0842;
        double lat2 = 32.7157;
        double lon2 = -117.1611;
        double expectedDistance1 = 6.8;
        double lat3 =41.8781;
        double lon3 =-83.63;
        double expectedDistance2 = 1930;
        double actualDistance = DistanceCalculator.distance(lat1, lon1, lat2, lon2);
        double actualDistanceCS = DistanceCalculator.distance(lat1, lon1, lat3, lon3);
        assertEquals(expectedDistance2, actualDistanceCS, 5.0);
        assertEquals(expectedDistance1, actualDistance, 0.1); // Allow for a 0.1 mile error
    }

    @Test
    public void testCircleDecider() {
        double distance1 = 0.5;
        double distance2 = 1.0;
        double distance3 = 100.0;
        double distance4 = 1000.0;
        int expectedCircle1 = 1;
        int expectedCircle2 = 2;
        int expectedCircle3 = 3;
        int expectedCircle4 = 4;
        int actualCircle1 = DistanceCalculator.circleDecider(distance1);
        int actualCircle2 = DistanceCalculator.circleDecider(distance2);
        int actualCircle3 = DistanceCalculator.circleDecider(distance3);
        int actualCircle4 = DistanceCalculator.circleDecider(distance4);
        assertEquals(expectedCircle1, actualCircle1);
        assertEquals(expectedCircle2, actualCircle2);
        assertEquals(expectedCircle3, actualCircle3);
        assertEquals(expectedCircle4, actualCircle4);
    }
}
