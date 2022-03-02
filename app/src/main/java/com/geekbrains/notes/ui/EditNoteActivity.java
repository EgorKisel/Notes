package com.geekbrains.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Note;

public class EditNoteActivity extends AppCompatActivity implements View.OnClickListener {

    private Note note;
    Button save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = (Note) getIntent().getSerializableExtra(Note.NOTE);

        // TODO создать внешний вид для редактирования заметки

        setContentView(R.layout.activity_edit_note);
        findViewById(R.id.button_save).setOnClickListener(this);

    }


    // TODO написать возвращение отредактированной заметки в NotesListActivity


    void saveNote() {
        Intent result = new Intent();
        result.putExtra(Note.NOTE, note);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void onClick(View view) {
        saveNote();
    }
}
