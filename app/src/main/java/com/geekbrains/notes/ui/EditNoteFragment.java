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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Controller;
import com.geekbrains.notes.data.DatePickerListener;
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
    private TextView dateTextView;
    public static final String DATE = "DATE";
    String saveInstanceDate;
    private String date;


    public static EditNoteFragment getInstance(Note note) {
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
        dateTextView = view.findViewById(R.id.set_date);

        if (savedInstanceState == null) {
            dateTextView.setText(date);
        } else {
            date = savedInstanceState.getString(DATE);
            dateTextView.setText(date);
        }

        Button buttonSave = view.findViewById(R.id.save_button);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                ((Controller) requireActivity()).buttonSavePressed();
                Toast.makeText(getContext(), "The note has been changed", Toast.LENGTH_SHORT).show();
            }
        });

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DatePickerListener) requireActivity()).callDatePicker();
            }
        });
        init(note);
    }

    //возвращение отредактированной заметки в NotesListFragment (через перезапись в репо)

    void saveNote() {
        Note editNote = new Note(title.getText().toString(), description.getText().toString(),
                dateTextView.getText().toString());
        editNote.setId(noteId);
        repo.update(editNote);
    }

    private void init(Note note){
        Bundle args = getArguments();
        note = (Note) args.getSerializable(NOTE);
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        dateTextView.setText(note.getDate());
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
    void setDate(String date){
        this.date = date;
        dateTextView.setText(date);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATE, date);
    }
}
