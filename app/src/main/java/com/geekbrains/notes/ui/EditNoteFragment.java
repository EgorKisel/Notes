package com.geekbrains.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Controller;
import com.geekbrains.notes.data.InMemoryRepoImp;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.data.Repo;

public class EditNoteFragment extends Fragment {

    private Note note;
    private int noteId;
    private Repo repo = InMemoryRepoImp.getInstance();
    private EditText title;
    private EditText description;
    public static final String NOTE = "NOTE";

    public static Fragment getInstance(Note note) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(NOTE, note);
        editNoteFragment.setArguments(args);
        return editNoteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        title = view.findViewById(R.id.edit_note_title);
        description = view.findViewById(R.id.edit_note_description);

        init(note);

        Button buttonSave = view.findViewById(R.id.save_button);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                ((Controller) requireActivity()).buttonSavePressed();
            }
        });
    }

    //возвращение отредактированной заметки в NotesListFragment (через перезапись в репо)

    void saveNote() {
        Note editNote = new Note(title.getText().toString(), description.getText().toString());
        editNote.setId(noteId);
        repo.update(editNote);
    }

    private void init(Note note){
        Bundle args = getArguments();
        note = (Note) args.getSerializable(NOTE);
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        noteId = note.getId();
    }


}
