package edu.ucsd.cse110.projects110;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public abstract class UserDao {

    @Upsert
    public abstract long upsert(User user);

    @Query("SELECT EXISTS(SELECT 1 FROM locations WHERE public_code = :public_code)")
    public abstract boolean exists(String public_code);

    @Query("SELECT * FROM locations WHERE public_code = :public_code")
    public abstract LiveData<User> get(String public_code);

    @Query("SELECT * FROM locations ORDER BY public_code")
    public abstract LiveData<List<User>> getAll();

    @Delete
    public abstract int delete(User user);
}
