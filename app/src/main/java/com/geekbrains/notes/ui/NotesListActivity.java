package com.geekbrains.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_list_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add:
                return toast("Add");
            case R.id.menu_search:
                return toast("Search");
            case R.id.menu_sort:
                return toast("Sort");
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean toast (String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return true;
    }
}