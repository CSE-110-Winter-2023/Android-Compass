package edu.ucsd.cse110.projects110;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private UserDatabase db;
    private UserDao userDao;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        userDao = db.getUserDao();
        userRepository = new UserRepository(userDao);
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testGetLocal() throws InterruptedException {
        User user = new User("123", "Alice", 32.7157, -117.1611);
        userRepository.upsertLocal(user);
        LiveData<User> localLiveUser = userRepository.getLocal("123");
        final User[] localUser = new User[1];
        localLiveUser.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                localUser[0] = user;
                assertNotNull(user);
            }
        });

        // wait for onChanged() method to be called
        Thread.sleep(500);

        // assert that the User object retrieved from the LiveData is not null
        assertNotNull(localUser[0]);
        assertEquals(user.public_code, localLiveUser.getValue().public_code);
        assertTrue(user.longitude == localLiveUser.getValue().longitude);
        assertTrue(user.latitude== localLiveUser.getValue().latitude);
        assertEquals(user.label, localLiveUser.getValue().label);

    }

    @Test
    public void testGetRemote() throws InterruptedException {
        User user = new User("0192509125", "Alice", 32.7157, -117.1611);
        userRepository.upsertRemote(user);
        LiveData<User> remoteUser = userRepository.getRemote("0192509125");
        final User[] remoteUserList = new User[1];
        remoteUser.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                remoteUserList[0] = user;
                assertNotNull(user);
            }
        });

        // wait for onChanged() method to be called
        Thread.sleep(1000);

        assertNotNull(remoteUserList[0]);
        assertEquals(user.public_code, remoteUser.getValue().public_code);
        assertTrue(user.longitude == remoteUser.getValue().longitude);
        assertTrue(user.latitude== remoteUser.getValue().latitude);
        assertEquals(user.label, remoteUser.getValue().label);
    }

    @Test
    public void testGetAllLocal() throws InterruptedException {
        User user1 = new User("123", "Alice", 32.7157, -117.1611);
        User user2 = new User("456", "Bob", 32.7174, -117.1624);
        userRepository.upsertLocal(user1);
        userRepository.upsertLocal(user2);
        LiveData<List<User>> localUsers = userRepository.getAllLocal();
        final User[] remoteUserList = new User[2];
        localUsers.observeForever(new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                assertNotNull(users);
                remoteUserList[0] = users.get(0);
                remoteUserList[1] = users.get(1);
            }
        });

        // wait for onChanged() method to be called
        Thread.sleep(500);

        assertNotNull(remoteUserList[0]);
        assertNotNull(remoteUserList[1]);
        assertNotNull(localUsers.getValue());
        assertEquals(2, localUsers.getValue().size());
    }

    @Test
    public void testUpsertLocal() throws InterruptedException {
        User user1 = new User("123", "Alice", 32.7157, -117.1611);
        userRepository.upsertLocal(user1);
        LiveData<User> localUser = userRepository.getLocal("123");
        final User[] remoteUserList = new User[1];
        localUser.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                remoteUserList[0] = user;
                assertNotNull(user);
            }
        });

        // wait for onChanged() method to be called
        Thread.sleep(500);

        assertNotNull(remoteUserList[0]);
        assertNotNull(localUser.getValue());
        assertEquals(user1.public_code, localUser.getValue().public_code);
        assertTrue(user1.longitude == localUser.getValue().longitude);
        assertTrue(user1.latitude== localUser.getValue().latitude);
        assertEquals(user1.label, localUser.getValue().label);
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