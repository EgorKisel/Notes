package com.geekbrains.notes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Controller;
import com.geekbrains.notes.data.Note;

public class NotesListActivity extends AppCompatActivity implements Controller {

    private FragmentManager manager;
    public static final String DEFAULT_FRAGMENT = "DEFAULT_FRAGMENT";
    public static final String LANDSCAPE_FRAGMENT = "LANDSCAPE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        manager = getSupportFragmentManager();
        if (savedInstanceState == null){
            manager
                    .beginTransaction()
                    .replace(R.id.list_container, new NotesListFragment(), DEFAULT_FRAGMENT)
                    .commit();
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
                manager.getBackStackEntryCount() > 1) {
            manager.popBackStack();
        }
    }

    @Override
    public void openEditNoteFragment(Note note) {
        manager
                .beginTransaction()
                .replace(R.id.list_container, EditNoteFragment.getInstance(note))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void buttonSavePressed() {
        manager.popBackStack();
        if(manager.findFragmentByTag(DEFAULT_FRAGMENT) != null)
            ((NotesListFragment)manager.findFragmentByTag(DEFAULT_FRAGMENT)).refresh();

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE && manager.findFragmentByTag(LANDSCAPE_FRAGMENT) != null)
            manager
                    .beginTransaction()
                    .remove(manager.findFragmentByTag(LANDSCAPE_FRAGMENT))
                    .commit();
    }

    @Override
    public void openEditNoteFragmentLandscape(Note note) {
        manager
                .beginTransaction()
                .replace(R.id.fragment_edit_note_container, EditNoteFragment.getInstance(note), LANDSCAPE_FRAGMENT)
                .commit();
    }
}