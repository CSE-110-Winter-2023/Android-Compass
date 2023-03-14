package edu.ucsd.cse110.projects110;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users = Collections.emptyList();
    private Consumer<User> onUserClicked;
    private Consumer<User> onUserDeleteClicked;


    public void setOnNoteClickListener(Consumer<User> onNoteClicked) {
        this.onUserClicked = onNoteClicked;
    }

    public void setOnNoteDeleteClickListener(Consumer<User> onNoteDeleteClicked) {
        this.onUserDeleteClicked = onNoteDeleteClicked;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View itemView;
        public final TextView nameView;
        public final TextView previewView;
        public final View deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            // Populate the text views...
            this.nameView = itemView.findViewById(R.id.note_item_title);
            this.previewView = itemView.findViewById(R.id.note_item_preview);
            this.deleteButton = itemView.findViewById(R.id.note_item_delete);
        }
        public void bind(User user) {
            nameView.setText(user.name);
            previewView.setText(user.UID);
            itemView.setOnClickListener(v -> onUserClicked.accept(user));
            deleteButton.setOnClickListener(v -> onUserDeleteClicked.accept(user));
        }
    }

    public void setUsers(List<User> notes) {
        this.users= notes;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User note = this.users.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }


    public String getUserId(int position) {
        // We don't actually have a unique int/long ID on the Note object, so instead
        // we generate a unique ID based on the title. It is possible that two notes
        // could have different titles but the same hash code, but it is beyond unlikely.
        return this.users.get(position).UID;
    }

}
