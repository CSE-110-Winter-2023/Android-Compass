package edu.ucsd.cse110.projects110;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {
    private LiveData<List<User>> notes;
    private final UserRepository repo;

    public UserListViewModel(@NonNull Application application) {
        super(application);
        Context context = application.getApplicationContext();
        UserDatabase db = UserDatabase.provide(context);
        UserDAO dao = db.getDao();
        this.repo = new UserRepository(dao);
    }

    /**
     * Load all notes from the database.
     * @return a LiveData object that will be updated when any notes change.
     */
    public LiveData<List<User>> getNotes() {
        if (notes == null) {
            notes = repo.getAllLocal();
        }
        return notes;
    }

    /**
     * Open a note in the database. If the note does not exist, create it.
     * @param title the title of the note
     * @return a LiveData object that will be updated when this note changes.
     */
    public LiveData<User> getOrCreateUser(String title) {
        if (!repo.existsLocal(title)) {
            User note = new User(title,"Sally",23.0,34.0);
            repo.upsertLocal(note, false);
        }

        return repo.getLocal(title);
    }

    public void delete(User note) {
        repo.deleteLocal(note);
    }
}
