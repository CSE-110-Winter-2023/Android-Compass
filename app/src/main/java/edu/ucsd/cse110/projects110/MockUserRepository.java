package edu.ucsd.cse110.projects110;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockUserRepository implements IUserRepository {
    private final UserDao dao;
    private Map<String, User> localData;
    private HashMap<String, LiveData<User>> fakeServer;

    public MockUserRepository(UserDao dao) {
        this.dao = dao;
        fakeServer = new HashMap<>();
        localData = new HashMap<>();
    }

    @Override
    public LiveData<User> getLocal(String public_code) {
        MutableLiveData<User> liveData = new MutableLiveData<>();
        LiveData<User> user = fakeServer.get(public_code);
        return user;
    }

    @Override
    public LiveData<User> getRemote(String public_code) {
        return fakeServer.get(public_code);
    }

    @Override
    public LiveData<List<User>> getAllLocal() {
        //MutableLiveData<List<User>> liveData = new MutableLiveData<>();
        //List<User> users = new ArrayList<>(fakeServer.values());
        //liveData.setValue(users);
        return null;
    }

    @Override
    public void upsertLocal(User user) {
        localData.put(user.public_code, user);
    }

    @Override
    public void deleteLocal(User user) {
        localData.remove(user.public_code);
    }

    @Override
    public boolean existsLocal(String public_code) {
        return localData.containsKey(public_code);
    }

    @Override
    public void upsertRemote(User user) {
        fakeServer.put(user.public_code, new MutableLiveData<User>(new User(user.public_code, user.label, user.latitude, user.longitude)));
    }
}
