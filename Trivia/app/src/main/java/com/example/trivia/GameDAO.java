package com.example.trivia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//This class stores the database queries
@Dao
public interface GameDAO {

    @Query("SELECT * FROM Game ORDER BY id DESC")
    List<Game> getAllGames();

    @Query("SELECT * FROM Game WHERE id= :id")
    Game getGameByID(String id);

    @Insert
    void insertGame(Game game);

    @Query("DELETE FROM Game")
    public void deleteAllGames();


}
