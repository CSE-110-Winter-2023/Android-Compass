package edu.ucsd.cse110.projects110;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

/** Data access object for the {@link User} class. */
@Dao
public interface UserDAO {
    /**
     * In the TodoList app, our DAO used the @Insert, @Update to define methods that insert and
     * update items from the database.
     * <p>
     * Here we replace both @Insert and @Update with @Upsert. An @Upsert method will insert a new
     * item into the database if one with the title doesn't already exist, or update an existing
     * item if it does.
     */
    @Upsert
    public abstract long upsert(User user);

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE UID = :UID)")
    public abstract boolean exists(String UID);

    @Query("SELECT * FROM users WHERE UID = :UID")
    public abstract LiveData<User> get(String UID);

    @Query("SELECT * FROM users ORDER BY UID")
    public abstract LiveData<List<User>> getAll();

    @Delete
    public abstract int delete(User user);
}
