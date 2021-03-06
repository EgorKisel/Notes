package com.geekbrains.notes.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.ui.NotesListFragment;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteHolder> {

    private List<Note> notes = new ArrayList<>();

    private NotesListFragment notesListFragment;
    private int noteHolderPosition;

    public NotesAdapter(Fragment fragment) {
        this.notesListFragment = (NotesListFragment) fragment;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view, listener, notesListFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public interface onNoteClickListener{
        void onNoteClick(Note note);
        void onNoteLongClick(Note note);
    }
    private onNoteClickListener listener;

    public void setOnNoteClickListener (onNoteClickListener listener){
        this.listener = listener;
    }

    public void create(List<Note> notes, int pos){
        this.notes = notes;
        notifyItemInserted(pos);
    }
}
