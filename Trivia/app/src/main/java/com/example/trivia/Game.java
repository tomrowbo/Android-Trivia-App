package com.example.trivia;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;


//This class stores the Game entity for the DB.
@Entity
public class Game {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="player_name")
    private String playerName;

    @ColumnInfo(name="difficulty")
    private String difficulty;

    @ColumnInfo(name="score")
    private int score;

    @ColumnInfo(name="questions")
    @TypeConverters(Converters.class)
    private List<Question> questions;


    @ColumnInfo(name="date_time")
    private String dateTime;

    public Game(String playerName, String difficulty, int score, List<Question> questions, String dateTime) {
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.score = score;
        this.questions = questions;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }


    public String getDifficulty() {
        return difficulty;
    }


    public int getScore() {
        return score;
    }


    public List<Question> getQuestions() {
        return questions;
    }


    public String getDateTime() {
        return dateTime;
    }

}
