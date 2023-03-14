package edu.ucsd.cse110.projects110;

import android.content.Context;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private volatile static UserDatabase instance = null;

    public abstract UserDAO getDao();

    public synchronized static UserDatabase provide(Context context) {
        if (instance == null) {
            instance = UserDatabase.make(context);
        }
        return instance;
    }

    private static UserDatabase make(Context context) {
        return Room.databaseBuilder(context, UserDatabase.class, "note_app.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @VisibleForTesting
    public static void inject(UserDatabase testDatabase) {
        if (instance != null ) {
            instance.close();
        }
        instance = testDatabase;
    }

}
