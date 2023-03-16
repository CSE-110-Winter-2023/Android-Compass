package edu.ucsd.cse110.projects110;

import android.util.Log;

import androidx.annotation.AnyThread;
import androidx.annotation.WorkerThread;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserAPI {


        public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

        private volatile static UserAPI instance = null;

        private OkHttpClient client;
        //sets up client
        public UserAPI() {
            this.client = new OkHttpClient();
        }
        //creates API object
        public static UserAPI provide() {
            if (instance == null) {
                instance = new UserAPI();
            }
            return instance;
        }
        //gets user from remote server
        @WorkerThread
        public User getUser(String publicCode) {
            String encodedCode = publicCode.replace(" ", "%20");
            var request = new Request.Builder()
                    .url("https://socialcompass.goto.ucsd.edu/location/" + encodedCode)
                    .method("GET", null)
                    .build();
            try (var response = client.newCall(request).execute()) {
                assert response.body() != null;
                var body = response.body().string();
                Log.i("GET", body);
                User returnedUser = User.fromJSON(body);
                return returnedUser;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        //use this to put our location to server
        public void putUser(User user) {
            var executor = Executors.newSingleThreadExecutor();
            var future = executor.submit(() -> {
                String publicCode = user.public_code.replace(" ", "%20");

                RequestBody body = RequestBody.create(user.toJSON(), JSON);
                Request request = new Request.Builder()
                        .url("https://socialcompass.goto.ucsd.edu/location/" + publicCode)
                        .method("PUT", body)
                        .put(body)
                        .build();
                try (var response = client.newCall(request).execute()) {
                    assert response.body() != null;
                    Log.i("PUT",  response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        //unused
        @AnyThread
        public Future<User> getUserAsync(String publicCode) {
            var executor = Executors.newSingleThreadExecutor();
            var future = executor.submit(() -> getUser(publicCode));

            // We can use future.get(1, SECONDS) to wait for the result.
            return future;
        }



}


