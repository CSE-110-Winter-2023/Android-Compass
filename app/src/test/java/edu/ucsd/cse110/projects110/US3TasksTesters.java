package edu.ucsd.cse110.projects110;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class US3TasksTesters {
    double lat1 = 40.730610;
    double lon1 = -73.935242;
    double lat2 = 51.509865;
    double lon2 = -0.118092;

    @Test
    public void DegreeTester1(){

        float result=DegreeDiff.MockCalculateAngle(lat1, lon1, lat2, lon2);
        assertTrue(result>51.0f);
        assertTrue(result<52.0f);
    }
    @Test
    public void DegreeTester2(){
        lat1 = 33.0;
        lon1 = -117.0;
        lat2 = 42.0;
        lon2 = -71.0;
        float result=DegreeDiff.MockCalculateAngle(lat1, lon1, lat2, lon2);
        assertTrue(result>62.0f);
        assertTrue(result<62.5f);
    }
}