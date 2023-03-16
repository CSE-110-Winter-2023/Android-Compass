package edu.ucsd.cse110.projects110;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class UserListActivity extends AppCompatActivity {
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to be the main layout.
        setContentView(R.layout.activity_user_list);

        var viewModel = setupViewModel();
        var adapter = setupAdapter(viewModel);
        setupViews(viewModel, adapter);
    }

    private UserListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(UserListViewModel.class);
    }

    @NonNull
    private UserAdapter setupAdapter(UserListViewModel viewModel) {
        UserAdapter adapter = new UserAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnNoteDeleteClickListener(user -> onUserDeleteClicked(user, viewModel));
        viewModel.getUsers().observe(this, adapter::setNotes);
        return adapter;
    }
    private void setupViews(UserListViewModel viewModel, UserAdapter adapter) {
        setupRecycler(adapter);
        setupInput(viewModel);
    }


    private void setupInput(UserListViewModel viewModel) {
        var input = (EditText) findViewById(R.id.input_new_note_title);
        input.setOnEditorActionListener((view, actionId, event) -> {
            // If the event isn't "done" or "enter", do nothing.
            if (actionId != EditorInfo.IME_ACTION_DONE) {
                return false;
            }

            // Otherwise, create a new note, persist it...
            var title = input.getText().toString();
            var user = viewModel.getOrCreateUser(title);
             //...wait for the database to finish persisting it...
//            user.observe(this, userEntity -> {
//                // ...stop observing.
//                user.removeObservers(this);
//
//                // ...and launch NoteActivity with it.
//                var intent = NoteActivity.intentFor(this, noteEntity);
//                startActivity(intent);
//            });
            return true;
        });
    }

    public void onUserDeleteClicked(User note, UserListViewModel viewModel) {
        // Delete the note
        Log.d("NotesAdapter", "Deleted note " + note.public_code);
        viewModel.delete(note);
    }

    @SuppressLint("RestrictedApi")
    private void setupRecycler(UserAdapter adapter) {
        // We store the recycler view in a field _only_ because we will want to access it in tests.
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public void onListBackHomeClicked(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}