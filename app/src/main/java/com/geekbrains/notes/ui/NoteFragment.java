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

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteFragment extends Fragment{
    private Repo repo = InMemoryRepoImp.getInstance();
    private EditText editTitle;
    private EditText editDescription;
    private TextView dateTextView;
    private String date;
    String saveInstanceDate;

    public static final String DATE = "DATE";

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

        dateTextView = view.findViewById(R.id.set_date);

        if (savedInstanceState == null) {
            Date currentDate = new Date();

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            date = dateFormat.format(currentDate);
            dateTextView.setText(date);
        } else {
            saveInstanceDate = savedInstanceState.getString(DATE);
            dateTextView.setText(saveInstanceDate);
        }



        Button buttonSave = view.findViewById(R.id.new_save_button);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                ((Controller) requireActivity()).buttonSavePressed();
                Toast.makeText(getContext(), "The note has been added", Toast.LENGTH_SHORT).show();
            }
        });
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DatePickerListener) requireActivity()).callDatePicker();
            }
        });
    }


    void saveNote()
    {
        Note editNote = new Note(editTitle.getText().toString(), editDescription.getText().toString(),
                dateTextView.getText().toString());
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

    void setDate(String date){
        saveInstanceDate = date;
        dateTextView.setText(saveInstanceDate);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATE, saveInstanceDate);
    }
}
