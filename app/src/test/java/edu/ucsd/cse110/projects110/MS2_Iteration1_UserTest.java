package edu.ucsd.cse110.projects110;

import org.junit.Test;
import static org.junit.Assert.*;

public class MS2_Iteration1_UserTest {

    @Test
    public void testConstructorAndGetters() {
        String name = "John Doe";
        double longitude = -117.1611;
        double latitude = 32.7157;

        User user = new User(name, longitude, latitude);

        assertEquals(name, user.getName());
        assertEquals(longitude, user.getUserLong(), 0.1);
        assertEquals(latitude, user.getUserLat(), 0.1);
        assertNotNull(user.getUID());
    }

    @Test
    public void testSetters() {
        User user = new User("Jane Doe", -117.1611, 32.7157);

        double newLongitude = -122.4194;
        double newLatitude = 37.7749;

        user.setUserLong(newLongitude);
        user.setUserLat(newLatitude);

        assertEquals(newLongitude, user.getUserLong(), 0.1);
        assertEquals(newLatitude, user.getUserLat(), 0.1);
    }

    @Test
    public void testToString() {
        User user = new User("John Doe", -117.1611, 32.7157);
        String expectedString = "User{UID='" + user.getUID() + "', Name='John Doe', userLong=-117.1611, userLat=32.7157}";

        assertEquals(expectedString, user.toString());
    }
}

