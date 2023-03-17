package edu.ucsd.cse110.projects110;

import android.os.FileUriExposedException;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class UserRepository implements IUserRepository{
    private final UserDao dao;
    private ScheduledFuture<?> poller;

    public UserRepository(UserDao dao) {
        this.dao = dao;
    }

    public LiveData<User> getLocal(String public_code) {
        return dao.get(public_code);
    }


    public LiveData<User> getRemote(String public_code){
        UserAPI api = UserAPI.provide();
        MediatorLiveData<User> returned = new MediatorLiveData<>();

        ScheduledExecutorService backgroundThreadExecutor = Executors.newSingleThreadScheduledExecutor();
        this.poller = backgroundThreadExecutor.scheduleAtFixedRate(() -> {
            //noinspection CatchMayIgnoreException
            try{
                User remoteNote = api.getUser(public_code);
                if (remoteNote != null) {
                    returned.postValue(remoteNote);
                    Log.i("hi",returned.toString());
                }

            }
            catch(Exception e) {
            }
        }, 0, 3, TimeUnit.SECONDS);
        return returned;
    }

    public LiveData<List<User>> getAllLocal() {
        return dao.getAll();
    }

    public void upsertLocal(User user) {
        // updated local friends when online data comes in

        dao.upsert(user);
    }

    public void deleteLocal(User user) {
        dao.delete(user);
    }

    public boolean existsLocal(String public_code) {
        return dao.exists(public_code);
    }

    public void upsertRemote(User user) {
        UserAPI api = UserAPI.provide();
        api.putUser(user);
    }
}
