package edu.ucsd.cse110.projects110;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UserListViewModel extends AndroidViewModel {
    private LiveData<List<User>> users;
    private final UserRepository repo;


    public UserListViewModel(@NonNull Application application) {
        super(application);
        var context = application.getApplicationContext();
        var db = UserDatabase.provide(context);
        var dao = db.getUserDao();
        this.repo = new UserRepository(dao);
    }

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = repo.getAllLocal();
        }
        return users;
    }
    public LiveData<User> getOrCreateUser(String public_code) {
        UserAPI api = UserAPI.provide();
        Executor executor = Executors.newSingleThreadExecutor();
        MediatorLiveData<User> userLiveData = new MediatorLiveData<>();

        executor.execute(() -> {
            User remoteUser = api.getUser(public_code);
            User localUser = repo.getLocal(public_code).getValue();

            if (remoteUser != null) {
                if (localUser == null) {
                    repo.upsertLocal(remoteUser);
                    userLiveData.postValue(remoteUser);
                }else {
                    repo.upsertLocal(remoteUser);
                    userLiveData.postValue(localUser);
                }
            } else {
                userLiveData.postValue(localUser);
            }
        });

        return userLiveData;
    }
    public void delete(User user) {
        repo.deleteLocal(user);
    }
}
