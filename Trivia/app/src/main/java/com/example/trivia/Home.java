package com.example.trivia;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Home page - initial fragment shown. Contains all the buttons to navigate.
public class Home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        AppCompatButton playButton = v.findViewById(R.id.play_game);
        AppCompatButton recentButton = v.findViewById(R.id.recent_games);
        AppCompatButton quitButton = v.findViewById(R.id.quit_game);


        //Add to the UI
        Context context = getContext(); //Fetches our context
        if (context == null) return v; //If fragment is not attached - just a fail safe thing.


        //Quit button popup window to confirm
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle("Quit Application");
        builder.setMessage("Are you sure you want to quit the application?");
        builder.setPositiveButton("Confirm",
                (dialog, which) -> {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.finish();
                    System.exit(0);
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> {
        });



        playButton.setOnClickListener(v1 -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.changeFragment(new GameSelectionFragment(), "Select","Home");
        });


        quitButton.setOnClickListener(v12 -> {
            AlertDialog dialog = builder.create();
            dialog.setOnCancelListener(dialog1 -> dialog1.dismiss());
            dialog.show();

        });

        recentButton.setOnClickListener(v13 -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            assert mainActivity != null;
            mainActivity.changeFragment(new FragmentRecentGames(), "Recent","Home");
        });


        return v;
    }
}