package com.geekbrains.notes.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.InMemoryRepoImp;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.data.Repo;
import com.geekbrains.notes.recycler.NoteHolder;
import com.geekbrains.notes.recycler.NotesAdapter;

public class NotesListActivity extends AppCompatActivity implements NotesAdapter.onNoteClickListener {

    private RecyclerView list;
    private Repo repo = InMemoryRepoImp.getInstance();
    private NotesAdapter adapter;
    private TextView title;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        list = findViewById(R.id.list);
        adapter = new NotesAdapter();
        adapter.setOnNoteClickListener(this);
        adapter.setNotes(repo.getAll());
        list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));
        title = findViewById(R.id.note_title);
        description = findViewById(R.id.note_description);
    }

    public static final int EDIT_NOTE_REQUEST = 66;

    @Override
    public void onNoteClick(Note note) {
        Log.d("happy", note.getDescription());
        Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
        editNoteIntent.putExtra(Note.NOTE, note);
        startActivityForResult(editNoteIntent, EDIT_NOTE_REQUEST);
    }

    // Функция обработки возвращаемой заметки - добавляем её в
    //  репо и обновляем данные в адаптере данными из репо
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==EDIT_NOTE_REQUEST && resultCode==RESULT_OK) {
            if (data == null) throw new AssertionError();
            Note note = (Note) data.getSerializableExtra(Note.NOTE);
            repo.update(note);
            adapter.setNotes(repo.getAll());
        }
    }
}