package com.example.trivia;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//Creates a roomDB
@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class GameDatabase extends RoomDatabase {

    public abstract GameDAO gameDAO();

}
