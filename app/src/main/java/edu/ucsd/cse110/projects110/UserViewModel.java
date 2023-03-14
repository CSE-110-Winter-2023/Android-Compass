package edu.ucsd.cse110.projects110;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {
    private LiveData<User> note;
    private final UserRepository repo;

    public UserViewModel(@NonNull Application application) {
        super(application);
        Context context = application.getApplicationContext();
        UserDatabase db = UserDatabase.provide(context);
        UserDAO dao = db.getDao();
        this.repo = new UserRepository(dao);
    }

    public LiveData<User> getUser(String title) {
        // TODO: use getSynced here instead?
        // The returned live data should update whenever there is a change in
        // the database, or when the server returns a newer version of the note.
        // Polling interval: 3s.
        if (note == null) {
            note = repo.getSynced(title);
        }
        return note;
    }

    public void save(User note) {
        // TODO: try to upload the note to the server
        repo.upsertSynced(note);
    }
}
