package edu.ucsd.cse110.projects110;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    private static final String PUBLIC_CODE = "abcd1234";
    private static final String LABEL = "test";
    private static final double LATITUDE = 37.1234;
    private static final double LONGITUDE = -122.1234;
    private static final boolean IS_LISTED_PUBLICLY = false;
    private static final long CREATED_AT = 1616879625;
    private static final long UPDATED_AT = 1616879655;

    @Test
    public void testGeneralConstructor() {
        User user = new User(PUBLIC_CODE, LABEL, LATITUDE, LONGITUDE);
        assertNotNull(user);
        assertEquals(PUBLIC_CODE, user.public_code);
        assertEquals(LABEL, user.label);
        assertEquals(LATITUDE, user.latitude, 0.00001);
        assertEquals(LONGITUDE, user.longitude, 0.00001);
        assertEquals(0, user.created_at);
        assertEquals(0, user.updated_at);
    }

    @Test
    public void testDeviceOwnerConstructor() {
        User user = new User(PUBLIC_CODE, LABEL, LATITUDE, LONGITUDE, IS_LISTED_PUBLICLY, CREATED_AT, UPDATED_AT);
        assertNotNull(user);
        assertEquals(PUBLIC_CODE, user.public_code);
        assertEquals(LABEL, user.label);
        assertEquals(LATITUDE, user.latitude, 0.00001);
        assertEquals(LONGITUDE, user.longitude, 0.00001);
        assertEquals(IS_LISTED_PUBLICLY, user.is_listed_publicly);
        assertEquals(CREATED_AT, user.created_at);
        assertEquals(UPDATED_AT, user.updated_at);
    }

    @Test
    public void testToJSON() {
        User user = new User(PUBLIC_CODE, LABEL, LATITUDE, LONGITUDE, IS_LISTED_PUBLICLY, CREATED_AT, UPDATED_AT);
        String json = user.toJSON();
        assertNotNull(json);
        assertTrue(json.contains(PUBLIC_CODE));
        assertTrue(json.contains(LABEL));
        assertTrue(json.contains(String.valueOf(LATITUDE)));
        assertTrue(json.contains(String.valueOf(LONGITUDE)));
        assertTrue(json.contains(String.valueOf(IS_LISTED_PUBLICLY)));
    }
}
