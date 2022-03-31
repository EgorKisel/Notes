package com.geekbrains.notes.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.ui.NotesListFragment;

public class NoteHolder extends RecyclerView.ViewHolder {

    private NotesListFragment notesListFragment;
    private TextView title;
    private TextView description;
    private Note note;
    private TextView date;

    public NoteHolder(@NonNull View itemView, NotesAdapter.onNoteClickListener listener, NotesListFragment notesListFragment) {
        super(itemView);
        this.notesListFragment = notesListFragment;
        title = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        date = itemView.findViewById(R.id.note_date);
        registerContextMenu(itemView);
        itemView.setOnClickListener(view -> listener.onNoteClick(note));
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onNoteLongClick(note);
                return false;
            }
        });
    }

    private void registerContextMenu(View itemView) {
        if (notesListFragment != null)
            notesListFragment.registerForContextMenu(itemView);
    }

    void bind (Note note){
        this.note = note;
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        date.setText(note.getDate());
    }
}
