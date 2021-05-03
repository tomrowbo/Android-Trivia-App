package com.example.trivia;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

//This class is the fragment for end game summary
public class FragmentGameEnd extends Fragment {
    private ArrayList<Question> questionList;
    private int score;
    private String difficulty;
    private String playerName;


    public FragmentGameEnd(ArrayList<Question> questionList, int score, String difficulty, String playerName) {
        this.questionList = questionList;
        this.score = score;
        this.difficulty = difficulty;
        this.playerName = playerName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_end, container, false);
        ListView lv = v.findViewById(R.id.end_game_list_view);
        Context context = this.getContext();
        EndGameListAdaptor endGameListAdaptor = new EndGameListAdaptor(context,R.layout.custom_score_row,questionList);
        lv.setAdapter(endGameListAdaptor);

        //Setting the game info
        TextView scoreText = v.findViewById(R.id.end_score);
        scoreText.setText("Score: "+score+"/"+questionList.size());
        TextView difficultyText = v.findViewById(R.id.difficulty_text);
        difficultyText.setText("Difficulty: "+difficulty);
        TextView playerNameText = v.findViewById(R.id.player_name_end);
        playerNameText.setText("Player name: "+playerName);


        return v;
    }


}