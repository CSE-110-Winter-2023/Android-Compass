//package edu.ucsd.cse110.projects110;
//
//import android.util.Log;
//
//import androidx.annotation.AnyThread;
//import androidx.annotation.WorkerThread;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class UserAPI {
//    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
//
//    private volatile static UserAPI instance = null;
//
//    private OkHttpClient client;
//
//    public UserAPI() {
//        this.client = new OkHttpClient();
//    }
//
//    public static UserAPI provide() {
//        if (instance == null) {
//            instance = new UserAPI();
//        }
//        return instance;
//    }
//
//    /**
//     * An example of sending a GET request to the server.
//     *
//     * The /echo/{msg} endpoint always just returns {"message": msg}.
//     *
//     * This method should can be called on a background thread (Android
//     * disallows network requests on the main thread).
//     */
//    @WorkerThread
//    public String echo(String msg) {
//        // URLs cannot contain spaces, so we replace them with %20.
//        String encodedMsg = msg.replace(" ", "%20");
//
//        Request request = new Request.Builder()
//                .url("https://sharednotes.goto.ucsd.edu/echo/" + encodedMsg)
//                .method("GET", null)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            assert response.body() != null;
//            String body = response.body().string();
//            Log.i("ECHO", body);
//            return body;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @WorkerThread
//    public User getUser(String UID){
//
//        // URLs cannot contain spaces, so we replace them with %20.
//        String encodedMsg = UID.replace(" ", "%20");
//
//        Request request = new Request.Builder()
//                .url("https://socialcompass.goto.ucsd.edu/docs/" + encodedMsg)
//                .method("GET", null)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            assert response.body() != null;
//            String body = response.body().string();
//            Log.i("GET", body);
//            User returedNote = User.fromJSON(body);
//            return returedNote;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public void putUser (User note){
//         ExecutorService executor = Executors.newSingleThreadExecutor();
//        Future<?> future = executor.submit(() -> {
//            //URLs cannot contain spaces, so we replace them with %20.
//            String title = note.UID.replace(" ", "%20");
//
//            RequestBody body = RequestBody.create(note.toJSON(), JSON);
//            Request request = new Request.Builder()
//                    .url("https://socialcompass.goto.ucsd.edu/docs/" + title)
//                    .method("PUT", body)
//                    .put(body)
//                    .build();
//            try (Response response = client.newCall(request).execute()) {
//                assert response.body() != null;
//                Log.i("PUT",  response.body().string());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    @AnyThread
//    public Future<String> echoAsync(String msg) {
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        Future future = executor.submit(() -> echo(msg));
//
//        // We can use future.get(1, SECONDS) to wait for the result.
//        return future;
//    }
//}
