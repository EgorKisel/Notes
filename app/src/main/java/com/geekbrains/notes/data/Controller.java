package com.geekbrains.notes.data;

public interface Controller {
    void openEditNoteFragment(Note note);
    void openEditNoteFragmentLandscape(Note note);
    void buttonSavePressed();
}
