package edu.ucsd.cse110.projects110;

import static org.junit.Assert.assertEquals;

import android.view.View;

import androidx.core.util.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class UserAdapterTest {

    private UserAdapter userAdapter;
    private List<User> testUsers;

    @Mock
    private Consumer<User> onUserClicked;

    @Mock
    private Consumer<User> onUserDeleteClicked;

    @Before
    public void setup() {
        // Create some test users
        testUsers = new ArrayList<>();
        testUsers.add(new User("123", "Alice", 32.7157, -117.1611));
        testUsers.add(new User("456", "Bob", 37.7749, -122.4194));

        // Create the adapter and set the click listeners
        userAdapter = new UserAdapter();
        userAdapter.setOnNoteClickListener(onUserClicked);
        userAdapter.setOnNoteDeleteClickListener(onUserDeleteClicked);
    }

    @Test
    public void testSetUsers() {
        // Set the test users in the adapter
        userAdapter.setUsers(testUsers);

        // Check that the adapter contains the test users
        assertEquals(testUsers.size(), userAdapter.getItemCount());
        for (int i = 0; i < testUsers.size(); i++) {
            assertEquals(testUsers.get(i), userAdapter.users.get(i));
        }
    }
}

