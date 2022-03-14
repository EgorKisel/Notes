package com.geekbrains.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.InMemoryRepoImp;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.data.Repo;

public class EditNoteActivity extends AppCompatActivity{

    private int id;
    private Note note;
    Button save;
    EditText title;
    EditText description;
    private Repo repo = InMemoryRepoImp.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note = (Note) getIntent().getSerializableExtra(Note.NOTE);

        // TODO создать внешний вид для редактирования заметки

        setContentView(R.layout.activity_edit_note);
        title = findViewById(R.id.edit_note_title);
        description = findViewById(R.id.edit_note_description);
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        save = findViewById(R.id.button_save);
        save.setOnClickListener(v ->{
            saveNote();
        });

    }


    // TODO написать возвращение отредактированной заметки в NotesListActivity


    public void saveNote() {
        int noteId = note.getId();
        Note editNote = note;

        editNote.setTitle(title.getText().toString());
        editNote.setDescription(description.getText().toString());
        editNote.setId(noteId);
        repo.update(editNote);
        Intent result = new Intent();
        result.putExtra(Note.NOTE, this.note);
        setResult(RESULT_OK, result);
        finish();
    }

}
