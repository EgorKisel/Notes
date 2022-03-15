package com.geekbrains.notes.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
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

    private RecyclerView list;
    private NotesAdapter adapter;
    private Repo repo = InMemoryRepoImp.getInstance();


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
        adapter = new NotesAdapter();
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
    public void refresh() {
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }
}
