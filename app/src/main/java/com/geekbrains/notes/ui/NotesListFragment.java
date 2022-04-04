package com.geekbrains.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.notes.R;
import com.geekbrains.notes.data.Controller;
import com.geekbrains.notes.data.InMemoryRepoImp;
import com.geekbrains.notes.data.Note;
import com.geekbrains.notes.data.Repo;
import com.geekbrains.notes.recycler.NotesAdapter;

public class NotesListFragment extends Fragment implements NotesAdapter.onNoteClickListener{

    private Note note;
    private RecyclerView list;
    private NotesAdapter adapter;
    private Repo repo = InMemoryRepoImp.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = view.findViewById(R.id.list);
        adapter = new NotesAdapter(this);
        adapter.setOnNoteClickListener(this);
        adapter.setNotes(repo.getAll());
        // Граница между заметками
        list.addItemDecoration(new DividerItemDecoration(list.getContext(), LinearLayoutManager.VERTICAL));

        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));
    }

    @Override
    public void onNoteClick(Note note) {
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            ((Controller) requireActivity()).openEditNoteFragmentLandscape(note);
        } else {
            ((Controller) requireActivity()).openEditNoteFragment(note);
        }
    }

    @Override
    public void onNoteLongClick(Note note) {
        this.note = note;
    }

    public void refresh() {
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.note_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete_note:
                repo.delete(note.getId());
                refresh();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
