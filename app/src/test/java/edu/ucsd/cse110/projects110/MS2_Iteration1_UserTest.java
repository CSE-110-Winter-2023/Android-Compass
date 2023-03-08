package edu.ucsd.cse110.projects110;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
@RunWith(JUnit4.class)
public class MS2_Iteration1_UserTest {

    @Test
    public void testConstructorAndGetters() {
        String uid = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6";
        String name = "John Doe";
        double longitude = -117.1611;
        double latitude = 32.7157;

        User user = new User(uid, name, latitude, longitude);

        assertEquals(uid, user.UID);
        assertEquals(name, user.name);
        assertEquals(longitude, user.Long, 0.1);
        assertEquals(latitude, user.Lat, 0.1);
        assertEquals(0, user.version);
    }

    @Test
    public void testSetters() {
        User user = new User("f81d4fae-7dec-11d0-a765-00a0c91e6bf6", "Jane Doe", 32.7157, -117.1611);

        double newLongitude = -122.4194;
        double newLatitude = 37.7749;

        user.Long = newLongitude;
        user.Lat = newLatitude;

        assertEquals(newLongitude, user.Long, 0.1);
        assertEquals(newLatitude, user.Lat, 0.1);
    }

    @Test
    public void testFromJSONAndToJSON() {
        String json = "{\"UID\":\"f81d4fae-7dec-11d0-a765-00a0c91e6bf6\",\"Name\":\"John Doe\",\"Lat\":32.7157,\"Long\":-117.1611,\"version\":0}";
        User expectedUser = new User("f81d4fae-7dec-11d0-a765-00a0c91e6bf6", "John Doe", 32.7157, -117.1611);

        User actualUser = User.fromJSON(json);

        assertNotNull(actualUser);
        assertEquals(expectedUser.UID, actualUser.UID);
        assertEquals(expectedUser.name, actualUser.name);
        assertEquals(expectedUser.Long, actualUser.Long, 0.1);
        assertEquals(expectedUser.Lat, actualUser.Lat, 0.1);
        assertEquals(expectedUser.version, actualUser.version);

        String expectedJSON = "{\"UID\":\"f81d4fae-7dec-11d0-a765-00a0c91e6bf6\",\"Name\":\"John Doe\",\"Lat\":32.7157,\"Long\":-117.1611,\"version\":0}";
        String actualJSON = expectedUser.toJSON();

        assertNotNull(actualJSON);
        assertEquals(expectedJSON, actualJSON);
    }
}

