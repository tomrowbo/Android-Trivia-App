package com.example.trivia;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

//This class is the fragment that displays all recent games stored in the database
public class FragmentRecentGames extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recent_games, container, false);
        ListView lv = v.findViewById(R.id.recent_game_list_view);

        //If no games exist display different layout
        lv.setEmptyView(v.findViewById(R.id.empty_list_view));
        Context context = this.getContext();

        //Getting games from DB
        MainActivity mainActivity = (MainActivity) getActivity();
        List<Game> games = mainActivity.getGames();
        Button newGame = v.findViewById(R.id.new_game);

        RecentGameListAdaptor recentGameListAdaptor = new RecentGameListAdaptor(context,R.layout.custom_game_row,games);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            MainActivity mainActivity1 = (MainActivity) getActivity();
            Game game = games.get(position);

            mainActivity1.changeFragment(new FragmentGameEnd((ArrayList<Question>) game.getQuestions(),game.getScore(),game.getDifficulty(),game.getPlayerName()),"End","Recent");
        });

        newGame.setOnClickListener(v1 -> {
            MainActivity mainActivity12 = (MainActivity) getActivity();
            mainActivity12.changeFragment(new GameSelectionFragment(), "Select","Recent");
        });

        lv.setAdapter(recentGameListAdaptor);

        return v;
    }




}