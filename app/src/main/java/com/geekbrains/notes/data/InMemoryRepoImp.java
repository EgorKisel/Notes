package com.geekbrains.notes.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepoImp implements Repo{

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    public static final String KEY_SP = "KEY_SP";

    private static InMemoryRepoImp repo;

    private InMemoryRepoImp(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NotesApplication.getInstance());
        notes = getAll();
//        init();
    }

    private void init() {
        create(new Note("Title 1", "Description 1", "date"));
        create(new Note("Title 2", "Description 2", "date"));
        create(new Note("Title 3", "Description 3", "date"));
        create(new Note("Title 4", "Description 4", "date"));
        create(new Note("Title 5", "Description 5", "date"));
        create(new Note("Title 6", "Description 6", "date"));
        create(new Note("Title 7", "Description 7", "date"));
        create(new Note("Title 8", "Description 8", "date"));
        create(new Note("Title 9", "Description 9", "date"));
        create(new Note("Title 10", "Description 10", "date"));
        create(new Note("Title 11", "Description 11", "date"));
        create(new Note("Title 12", "Description 12", "date"));
        create(new Note("Title 13", "Description 13", "date"));
        create(new Note("Title 14", "Description 14", "date"));
        create(new Note("Title 15", "Description 15", "date"));
    }

    public static InMemoryRepoImp getInstance(){
        if (repo == null){
            repo = new InMemoryRepoImp();
        }
        return repo;
    }

    private List<Note> notes = new ArrayList<>();
    private int counter = 0;


    @Override
    public int create(Note note) {
        int id = counter++;
        note.setId(id);
        notes.add(note);
        String data = gson.toJson(notes);
        sharedPreferences.edit().putString(KEY_SP, data).apply();
        return id;
    }

    @Override
    public Note read(int id) {
        for (int i = 0; i < notes.size(); i++){
            if (notes.get(i).getId() == id)
                return notes.get(i);
        }
        return null;
    }

    @Override
    public void update(Note note) {
        for (int i = 0; i < notes.size(); i++){
            if (notes.get(i).getId() == note.getId()){
                notes.set(i, note);
                String data = gson.toJson(notes);
                sharedPreferences.edit().putString(KEY_SP, data).apply();
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++){
            if (notes.get(i).getId() == id){
                notes.remove(i);
                sharedPreferences.edit().remove(KEY_SP).apply();
                break;
            }

        }

    }

    @Override
    public List<Note> getAll() {
        String data = sharedPreferences.getString(KEY_SP, "{}");
        try {
            notes = gson.fromJson(
                    data, new TypeToken<List<Note>>(){}.getType());
        }
        catch (Exception e){
            Log.d("happy", "Exception: " + e.getMessage());
        }
        if (notes == null)
            notes = new ArrayList<>();
        return notes;
    }

}
