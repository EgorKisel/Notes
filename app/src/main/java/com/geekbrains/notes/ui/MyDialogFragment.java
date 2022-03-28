package com.geekbrains.notes.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {

    public static final String QUIT = "QUIT";

    @Override
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Activity activity = requireActivity();
        return new AlertDialog.Builder(activity)
                .setTitle("Exit!")
                .setMessage("Are you sure want to exit?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Toast.makeText(activity, "The application is closed!", Toast.LENGTH_SHORT).show();
                    activity.finish();
                        })

                .setNegativeButton("No", (dialogInterface, i) ->
                        Toast.makeText(activity, "No!", Toast.LENGTH_SHORT).show())
                .setNeutralButton("Cancel", null)
                .create();
    }
}
