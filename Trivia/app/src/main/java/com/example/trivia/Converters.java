package com.example.trivia;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.Collections;
import java.util.List;

//    This class converts List<Question> to and from a String so it can be stored within RoomDB
public class Converters {

    private static Gson gson = new Gson();


    @TypeConverter
    public static List<Question> stringToQuestionList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Question>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String QuestionListToString(List<Question> someObjects) {
        return gson.toJson(someObjects);
    }
}