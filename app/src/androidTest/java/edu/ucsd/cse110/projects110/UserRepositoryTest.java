package edu.ucsd.cse110.projects110;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {
    private UserDatabase db;
    private UserDAO userDao;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        userDao = db.getDao();
        userRepository = new UserRepository(userDao);
    }

    @After
    public void tearDown() {
        db.close();
    }



    @Test
    public void testExistsLocal() {
        User user = new User("123", "Alice", 32.7157, -117.1611);
        userRepository.upsertLocal(user);
        boolean exists = userRepository.existsLocal("123");
        assertTrue(exists);
        exists = userRepository.existsLocal("456");
        assertFalse(exists);
    }
}

