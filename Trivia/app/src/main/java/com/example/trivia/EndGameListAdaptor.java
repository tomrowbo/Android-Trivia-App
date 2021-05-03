package com.example.trivia;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;

import org.apache.commons.text.StringEscapeUtils;
import java.util.ArrayList;


//This class is a row for each question in the end game summary
public class EndGameListAdaptor extends BaseAdapter {

    private final ArrayList<Question> questionList;
    private Context context;
    private int layoutToUseForTheRows;


    public EndGameListAdaptor(Context context, int layout, ArrayList<Question> questionList) {
        this.context = context;
        this.questionList = questionList;
        this.layoutToUseForTheRows = layout;
    }


    @Override
    public int getCount() {
        return questionList.size();
    }

    @Override
    public Question getItem(int index) {
        return questionList.get(index);
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


        Question question = this.getItem(position);
        if (question != null) {
            //Updating row to current questions info
            AppCompatTextView questionText = convertView.findViewById(R.id.row_question);
            //AppCompatTextView resultText = convertView.findViewById(R.id.row_result);
            AppCompatTextView correctText = convertView.findViewById(R.id.row_correct_answer);
            AppCompatTextView selectedText = convertView.findViewById(R.id.row_selected_answer);
            ImageView icon = convertView.findViewById(R.id.list_image);
            questionText.setText(position + 1 + ": " + StringEscapeUtils.unescapeHtml4(question.getQuestion()));
            String correctAnswer = question.getCorrectAnswer();
            String selectedAnswer = question.getSelectedAnswer();
            //Setting correct and incorrect icons based on users answer
            if (!correctAnswer.equals(selectedAnswer)) {
                icon.setImageResource(R.drawable.ic_baseline_cancel_24);
            } else{
                icon.setImageResource(R.drawable.ic_round_check_circle_24);
            }
            correctText.setText("Correct answer: " + correctAnswer);
            selectedText.setText("You selected: " + selectedAnswer);
        }

        return convertView;
    }
}
