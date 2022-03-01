package com.geekbrains.notes.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Note;

public class EditNoteActivity extends AppCompatActivity {

    private Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = (Note) getIntent().getSerializableExtra(Note.NOTE);

        // TODO создать внешний вид для редактирования заметки

        setContentView(R.layout.activity_edit_note);
    }


    // TODO написать возвращение отредактированной заметки в NotesListActivity

    void saveNote(){
        Intent result = new Intent();
        result.putExtra(Note.NOTE, note);
        setResult(RESULT_OK, result);
        finish();
    }

}
