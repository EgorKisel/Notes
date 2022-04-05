package com.geekbrains.notes.data;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepoImp2 implements Repo{

    private Gson gson = new Gson();

    private static InMemoryRepoImp2 repo;

    private InMemoryRepoImp2(){
        init();
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

    public static InMemoryRepoImp2 getInstance(){
        if (repo == null){
            repo = new InMemoryRepoImp2();
        }
        return repo;
    }

    private ArrayList<Note> notes = new ArrayList<>();
    private int counter = 0;


    @Override
    public int create(Note note) {
        int id = counter++;
        note.setId(id);
        notes.add(note);
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
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++){
            if (notes.get(i).getId() == id){
                notes.remove(i);
                break;
            }

        }

    }

    @Override
    public List<Note> getAll() {
        return notes;
    }

}
