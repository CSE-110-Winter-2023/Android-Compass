package edu.ucsd.cse110.projects110;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface IUserRepository {

    LiveData<User> getLocal(String public_code);

    LiveData<User> getRemote(String public_code);

    LiveData<List<User>> getAllLocal();

    void upsertLocal(User user);

    void deleteLocal(User user);

    boolean existsLocal(String public_code);

    void upsertRemote(User user);
}

