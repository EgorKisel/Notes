package com.geekbrains.notes.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.InMemoryRepoImp;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.data.Repo;
import com.geekbrains.notes.recycler.NotesAdapter;

public class NotesListActivity extends AppCompatActivity implements NotesAdapter.onNoteClickListener {

    private RecyclerView list;
    private Repo repo = InMemoryRepoImp.getInstance();
    private NotesAdapter adapter;

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
    }

    public static final int EDIT_NOTE_REQUEST = 66;

    @Override
    public void onNoteClick(Note note) {
        Log.d("happy", note.getDescription());

        Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
        editNoteIntent.putExtra(Note.NOTE, note);
        startActivityForResult(editNoteIntent, EDIT_NOTE_REQUEST);
    }

    // TODO написать функцию обработки возвращаемой заметки - добавить её в
    //  репо и обновить данные в адаптере данными из репо
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}