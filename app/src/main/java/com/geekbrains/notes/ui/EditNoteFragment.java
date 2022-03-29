package com.geekbrains.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Controller;
import com.geekbrains.notes.data.InMemoryRepoImp;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.data.Repo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class EditNoteFragment extends Fragment implements NavigationBarView.OnItemSelectedListener {

    private Note note;
    private int noteId;
    private Repo repo = InMemoryRepoImp.getInstance();
    private EditText title;
    private EditText description;
    public static final String NOTE = "NOTE";
    private BottomNavigationView bottomNavigationView;


    public static Fragment getInstance(Note note) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(NOTE, note);
        editNoteFragment.setArguments(args);
        return editNoteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bottomNavigationView = view.findViewById(R.id.note_menu);
        bottomNavigationView.setOnItemSelectedListener(this);
        title = view.findViewById(R.id.edit_note_title);
        description = view.findViewById(R.id.edit_note_description);

        init(note);

        Button buttonSave = view.findViewById(R.id.save_button);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                ((Controller) requireActivity()).buttonSavePressed();
                Toast.makeText(getContext(), "The note has been changed", Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.menu_send:
                fragment = new SendFragment();
                break;
            case R.id.menu_add_photo:
                fragment = new AddPhotoFragment();
                break;
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.menu_add);
        MenuItem item1 = menu.findItem(R.id.menu_search);
        MenuItem item2 = menu.findItem(R.id.menu_sort);
        if (item != null){
            item.setVisible(false);
        }
        if (item1 != null){
            item1.setVisible(false);
        }
        if (item2 != null){
            item2.setVisible(false);
        }

    }
}
