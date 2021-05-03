package com.example.trivia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.List;

//A row that shows a summary of a games details.
public class RecentGameListAdaptor extends BaseAdapter {

    private List<Game> gameList;
    private Context context;
    private int layoutToUseForTheRows;


    public RecentGameListAdaptor(Context context, int layout, List<Game> gameList) {
        this.context = context;
        this.gameList = gameList;
        this.layoutToUseForTheRows = layout;
    }


    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public Game getItem(int index) {
        return gameList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(this.layoutToUseForTheRows, parent, false);
        }

        //Gets the game
        Game game = this.getItem(position);
        if (game != null) {
            AppCompatTextView playerName = convertView.findViewById(R.id.row_name);
            AppCompatTextView time = convertView.findViewById(R.id.row_time);

            playerName.setText(game.getPlayerName());
            time.setText(game.getDateTime());
        }

        return convertView;
    }
}
