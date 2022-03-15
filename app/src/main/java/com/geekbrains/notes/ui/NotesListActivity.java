package com.geekbrains.notes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import com.geekbrains.notes.R;

public class NotesListActivity extends AppCompatActivity {

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        manager = getSupportFragmentManager();
        if (savedInstanceState == null){
            manager
                    .beginTransaction()
                    .replace(R.id.list_container, new NotesListFragment())
                    .commit();
        }
    }
}