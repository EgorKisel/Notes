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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Controller;
import com.geekbrains.notes.data.InMemoryRepoImp;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.data.Repo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class NoteFragment extends Fragment{
    private Repo repo = InMemoryRepoImp.getInstance();
    private EditText editTitle;
    private EditText editDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_add_new_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        editTitle = view.findViewById(R.id.new_edit_note_title);
        editDescription = view.findViewById(R.id.new_edit_note_description);

        Button buttonSave = view.findViewById(R.id.new_save_button);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                ((Controller) requireActivity()).buttonSavePressed();
            }
        });
    }

    void saveNote()
    {
        Note editNote = new Note(editTitle.getText().toString(), editDescription.getText().toString());
        if(!(editTitle.getText().toString().equals("") && editDescription.getText().toString().equals("")))
            repo.create(editNote);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.menu_add);
        MenuItem item1 = menu.findItem(R.id.menu_search);
        MenuItem item2 = menu.findItem(R.id.menu_sort);
            item.setVisible(false);
            item1.setVisible(false);
            item2.setVisible(false);


    }
}
