package edu.ucsd.cse110.projects110;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class UserListActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        UserListViewModel viewModel = setupViewModel();
        UserAdapter adapter = setupAdapter(viewModel);
        setupViews(viewModel, adapter);

    }
    private UserListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(UserListViewModel.class);
    }

    @NonNull
    private UserAdapter setupAdapter(UserListViewModel viewModel) {
        UserAdapter adapter = new UserAdapter();
        adapter.setHasStableIds(true);
        adapter.setOnNoteDeleteClickListener(note -> onNoteDeleteClicked(note, viewModel));
        viewModel.getNotes().observe(this, adapter::setUsers);
        return adapter;
    }

    private void setupViews(UserListViewModel viewModel, UserAdapter adapter) {
        setupRecycler(adapter);
        setupInput(viewModel);
    }

//    private void setupToolbar() {
//        Toolbar toolbar = findViewById(R.id.toolbar_main);
//        setSupportActionBar(toolbar);
//    }

    // Override the @VisibleForTesting annotation to allow access from this (and only this) method.
    @SuppressLint("RestrictedApi")
    private void setupRecycler(UserAdapter adapter) {
        // We store the recycler view in a field _only_ because we will want to access it in tests.
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void setupInput(UserListViewModel viewModel) {
        EditText input = (EditText) findViewById(R.id.input_new_note_title);
        input.setOnEditorActionListener((view, actionId, event) -> {
            // If the event isn't "done" or "enter", do nothing.
            if (actionId != EditorInfo.IME_ACTION_DONE) {
                return false;
            }

            // Otherwise, create a new note, persist it...
            String title = input.getText().toString();
            LiveData<User> note = viewModel.getOrCreateUser(title);


            return true;
        });
    }

    /* Mediation Logic */



    public void onNoteDeleteClicked(User note, UserListViewModel viewModel) {
        // Delete the note
        Log.d("UserAdapter", "Deleted note " + note.UID);
        viewModel.delete(note);
    }
}