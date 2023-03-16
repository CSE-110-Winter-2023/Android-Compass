package edu.ucsd.cse110.projects110;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static volatile UserDatabase instance = null;

    public abstract UserDao getUserDao();

    public static synchronized UserDatabase provide(Context context) {
        if (instance == null) {
            instance = createInstance(context);
        }
        return instance;
    }

    private static UserDatabase createInstance(Context context) {
        return Room.databaseBuilder(context, UserDatabase.class, "user_app.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
}

