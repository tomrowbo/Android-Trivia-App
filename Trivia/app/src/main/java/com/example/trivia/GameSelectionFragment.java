package com.example.trivia;

import android.app.AlertDialog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

//This class creates the fragment where you select game options
public class GameSelectionFragment extends Fragment {
    private SeekBar seekBar;
    private TextView questionNumber;
    private RadioGroup difficultySelection;
    private TextInputEditText playerNameBox;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_selection, container, false);
        // Inflate the layout for this fragment

        //Seekbar = Question Number. 10 = Default
        seekBar = v.findViewById(R.id.seekBar);
        seekBar.setProgress(10);
        questionNumber = v.findViewById(R.id.questionNumberText);
        playerNameBox = v.findViewById(R.id.playerName);
        Button createBtn = v.findViewById(R.id.createGameBtn);
        difficultySelection = v.findViewById(R.id.difficultySelection);


        //Updates the textview each time slider is moved
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    int value;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                        this.value = value;
                        questionNumber.setText("Number Of Questions: " + value);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        questionNumber.setText("Number Of Questions: " + value);
                    }
                }
        );

        //Creates a game when button is clicked.
        createBtn.setOnClickListener(v1 -> {
            String playerName = playerNameBox.getEditableText().toString();
            int questions = seekBar.getProgress();
            //If playername is empty - shows popup requiring player to enter.
            if (playerName.equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Player Name Required");
                builder.setMessage("You must enter a player name.")
                        .setCancelable(true)
                        .setPositiveButton("OK", (dialog, id) -> {
                            //do things
                        });
                AlertDialog alert = builder.create();
                alert.setOnCancelListener(dialog1 -> dialog1.dismiss());
                alert.show();
                return;
            }
            //If the user selected 0 questions. I would have a minimum as 1 on Seekbar - but that
            //is only for API 26+
            else if (questions == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Zero Questions Selected");
                builder.setMessage("You must select at least one game.")
                        .setCancelable(true)
                        .setPositiveButton("OK", (dialog, id) -> {
                            //do things
                        });
                AlertDialog alert = builder.create();
                alert.setOnCancelListener(dialog1 -> dialog1.dismiss());
                alert.show();
                return;

            }



            //Gets difficulty from radio buttons
            int selectedID = difficultySelection.getCheckedRadioButtonId();
            String difficulty;
            if (selectedID == R.id.radioEasy){
                difficulty = "Easy";
            } else if (selectedID == R.id.radioMedium) {
                difficulty = "Medium";
            } else if (selectedID == R.id.radioHard){
                difficulty = "Hard";
            } else{
                difficulty = "Any Difficulty";
            }

            //Changing to game fragment and passing data
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.changeFragment(new QuestionHolderFragment(questions,difficulty,playerName), "Game","Select");

        });


        return v;
    }
}