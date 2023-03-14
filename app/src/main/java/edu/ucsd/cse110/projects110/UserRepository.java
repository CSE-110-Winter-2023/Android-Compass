package edu.ucsd.cse110.projects110;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class UserRepository {
    private final UserDAO dao;
    private ScheduledFuture<?> poller; // what could this be for... hmm?

    public UserRepository(UserDAO dao) {
        this.dao = dao;
    }

    // Synced Methods
    // ==============

    /**
     * This is where the magic happens. This method will return a LiveData object that will be
     * updated when the note is updated either locally or remotely on the server. Our activities
     * however will only need to observe this one LiveData object, and don't need to care where
     * it comes from!
     * <p>
     * This method will always prefer the newest version of the note.
     *
     * @param title the title of the note
     * @return a LiveData object that will be updated when the note is updated locally or remotely.
     */
    public LiveData<User> getSynced(String title) {
        MediatorLiveData<User> note = new MediatorLiveData<User>();

        Observer<User> updateFromRemote = theirNote -> {
             User ourNote = note.getValue();
            if (theirNote == null) return; // do nothing
            if (ourNote == null || ourNote.version < theirNote.version) {
                upsertLocal(theirNote, false);
            }
        };

        // If we get a local update, pass it on.
        note.addSource(getLocal(title), note::postValue);
        // If we get a remote update, update the local version (triggering the above observer)
        //note.addSource(getRemote(title), updateFromRemote);

        return note;
    }

    public void upsertSynced(User note) {
        upsertLocal(note);
        //upsertRemote(note);
    }

    // Local Methods
    // =============

    public LiveData<User> getLocal(String title) {
        return dao.get(title);
    }

    public LiveData<List<User>> getAllLocal() {
        return dao.getAll();
    }

    public void upsertLocal(User note, boolean incrementVersion) {
        // We don't want to increment when we sync from the server, just when we save.
        if (incrementVersion) note.version = note.version + 1;
        note.version = note.version + 1;
        dao.upsert(note);
    }

    public void upsertLocal(User note) {
        upsertLocal(note, true);
    }

    public void deleteLocal(User note) {
        dao.delete(note);
    }

    public boolean existsLocal(String UID) {
        return dao.exists(UID);
    }

    // Remote Methods
    // ==============

//    public LiveData<User> getRemote(String title) {
//        UserAPI api = UserAPI.provide();
//        MediatorLiveData<User> returned = new MediatorLiveData<>();
//
//        // Cancel any previous poller if it exists.
//        if (this.poller != null && !this.poller.isCancelled()) {
//            poller.cancel(true);
//        }
//
//        ScheduledExecutorService backgroundThreadExecutor = Executors.newSingleThreadScheduledExecutor();
//        this.poller = backgroundThreadExecutor.scheduleAtFixedRate(() -> {
//            User remoteNote = api.getUser(title);
//            if (remoteNote != null) {
//                returned.postValue(remoteNote);
//                Log.i("hi",returned.toString());
//            }
//        }, 0, 3, TimeUnit.SECONDS);
//
//        return returned;
//    }
//
//    public void upsertRemote(User note) {
//        // TODO: Implement upsertRemote!
//        UserAPI api = UserAPI.provide();
//        api.putUser(note);
//    }
}
