package com.geekbrains.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Controller;
import com.geekbrains.notes.data.DatePickerListener;
import com.geekbrains.notes.data.Note;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class NotesListActivity extends AppCompatActivity implements Controller, DatePickerListener {


    private FragmentManager manager;
    public static final String DEFAULT_FRAGMENT = "DEFAULT_FRAGMENT";
    public static final String LANDSCAPE_FRAGMENT = "LANDSCAPE_FRAGMENT";
    public static final String EDIT_NOTE = "EDIT_NOTE";
    public static final String ADD_NOTE = "ADD_NOTE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.drawer_main);


        manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            manager
                    .beginTransaction()
                    .replace(R.id.list_container, new NotesListFragment(), DEFAULT_FRAGMENT)
                    .commit();
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &&
                manager.getBackStackEntryCount() > 1) {
            manager.popBackStack();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Создаем боковое навигационное меню
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.burger_open, R.string.burger_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.nav_about): {
                        openAboutFragment();
                        drawerLayout.close();
                        return true;
                    }
                    case (R.id.nav_settings): {
                        openSettingsFragment();
                        drawerLayout.close();
                        return true;
                    }
                }
                return false;
            }
        });
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
        if (manager.findFragmentByTag(DEFAULT_FRAGMENT) != null)
            ((NotesListFragment) manager.findFragmentByTag(DEFAULT_FRAGMENT)).refresh();

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
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_add:
                manager
                        .beginTransaction()
                        .replace(R.id.list_container, new NoteFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.menu_search:
                fragment = new SearchFragment();
                break;
            case R.id.menu_sort:
                fragment = new SortFragment();
                break;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_container, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

    public boolean toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return true;
    }


    private void openSettingsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.list_container, new SettingsFragment()).commit();
    }

    private void openAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.list_container, new AboutFragment()).commit();
    }


    @Override
    public void onBackPressed() {
        if (manager.getBackStackEntryCount() == 0)
            new MyDialogFragment().show(getSupportFragmentManager(), MyDialogFragment.QUIT);
        else {
            super.onBackPressed();
        }
    }
    @Override
    public void callDatePicker() {
        new DatePickerFragment().show(getSupportFragmentManager(), DatePickerFragment.DATE_PICKER);
    }

    @Override
    public void sendDatePicker(String date) {
        if (manager.findFragmentByTag(ADD_NOTE) != null) {
            EditNoteFragment addNoteFragment = (EditNoteFragment) manager.findFragmentByTag(ADD_NOTE);
            addNoteFragment.setDate(date);
        }
        if (manager.findFragmentByTag(EDIT_NOTE) != null) {
            NoteFragment noteFragment = (NoteFragment) manager.findFragmentByTag(EDIT_NOTE);
            noteFragment.setDate(date);
        }
    }
}