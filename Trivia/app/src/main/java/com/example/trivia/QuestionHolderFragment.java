package com.example.trivia;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;


//This fragment is for the game. This will show the loading page, question page or failed page depending
//on connection
public class QuestionHolderFragment extends Fragment {

    private LinearLayoutCompat questionLoadingContainer;

    private LinearLayoutCompat questionFailedContainer;

    private RelativeLayout questionContainer;
    private RequestQueue requestQueue;
    private TextView questionText;
    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    private int index = 0;
    private int score = 0;
    private String url;
    private int questionNumber;
    private String difficulty;
    private String playerName;
    private ArrayList<Question> questionList;
    private Question question;
    private ProgressBar progressBar;
    private TextView questionNumberText;

    public QuestionHolderFragment(int questionNumber,String difficulty, String playerName) {
        this.questionNumber = questionNumber;
        this.difficulty = difficulty;
        this.playerName = playerName;

        //Creating API URL based on users game selections.
        url = "https://opentdb.com/api.php?amount=";
        url += questionNumber;
        if (!difficulty.equals("Any Difficulty")){
            url += "&difficulty="+difficulty.toLowerCase();
        }
        url += "&type=multiple";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_holder, container, false);

        //Getting fragment elements
        ContentLoadingProgressBar questionLoading = v.findViewById(R.id.progress);
        questionLoadingContainer = v.findViewById(R.id.question_loading_container);

        questionContainer = v.findViewById(R.id.question_container);

        questionText = v.findViewById(R.id.fragment_question);
        answerA = v.findViewById(R.id.optionA);
        answerB = v.findViewById(R.id.optionB);
        answerC = v.findViewById(R.id.optionC);
        answerD = v.findViewById(R.id.optionD);

        questionFailedContainer = v.findViewById(R.id.question_fetch_failed_container);
        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setMax(questionNumber);

        questionNumberText = v.findViewById(R.id.question_number);

        //Adding events to buttons
        answerA.setOnClickListener(v1 -> {
            disableQuestions();
            if (question.getCorrectAnswer().contentEquals(answerA.getText())) {
                answerA.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                Log.d("ANSWER", "correct");
                score++;
            } else {
                answerA.setBackgroundColor(getResources().getColor(R.color.colorRed));
                Log.d("ANSWER", "Incorrect");
            }
            question.setSelectedAnswer((String) answerA.getText());
            questionList.set(index,question);

            answerA.setEnabled(false);
            new CountDownTimer(2000, 2000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    answerA.setBackgroundResource(android.R.drawable.btn_default);
                    nextQuestion();
                }

            }.start();

        });
        answerB.setOnClickListener(v1 -> {
            disableQuestions();
            if (question.getCorrectAnswer().contentEquals(answerB.getText())) {
                answerB.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                Log.d("ANSWER", "correct");
                score++;
            } else {
                answerB.setBackgroundColor(getResources().getColor(R.color.colorRed));
                Log.d("ANSWER", "Incorrect");
            }
            question.setSelectedAnswer((String) answerB.getText());
            questionList.set(index,question);
            new CountDownTimer(2000, 2000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    answerB.setBackgroundResource(android.R.drawable.btn_default);
                    nextQuestion();
                }

            }.start();
        });
        answerC.setOnClickListener(v1 -> {
            disableQuestions();
            if (question.getCorrectAnswer().contentEquals(answerC.getText())) {
                answerC.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                Log.d("ANSWER", "correct");
                score++;
            } else {
                answerC.setBackgroundColor(getResources().getColor(R.color.colorRed));
                Log.d("ANSWER", "Incorrect");
            }
            question.setSelectedAnswer((String) answerC.getText());
            questionList.set(index,question);
            new CountDownTimer(2000, 2000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    answerC.setBackgroundResource(android.R.drawable.btn_default);
                    nextQuestion();
                }

            }.start();
        });
        answerD.setOnClickListener(v1 -> {
            disableQuestions();
            if (question.getCorrectAnswer().contentEquals(answerD.getText())) {
                answerD.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                Log.d("ANSWER", "correct");
                score++;
            } else {
                answerD.setBackgroundColor(getResources().getColor(R.color.colorRed));
                Log.d("ANSWER", "Incorrect");
            }
            question.setSelectedAnswer((String) answerD.getText());
            questionList.set(index,question);
            new CountDownTimer(2000, 2000) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    answerD.setBackgroundResource(android.R.drawable.btn_default);
                    nextQuestion();
                }

            }.start();
        });

        //Sets the loading UI until questions have been fetched from API
        setLoadingUi();
        fetchQuestions();


        return v;
    }

    //Shows loading UI hides other containers.
    private void setLoadingUi() {
        this.questionContainer.setVisibility(View.GONE);
        this.questionFailedContainer.setVisibility(View.GONE);
        this.questionLoadingContainer.setVisibility(View.VISIBLE);
    }

    //Shows failed UI hides other containers. Occurs when error happens ie. no wifi
    private void setFailedUI(){
        this.questionLoadingContainer.setVisibility(View.GONE);
        this.questionContainer.setVisibility(View.GONE);
        this.questionFailedContainer.setVisibility(View.VISIBLE);
    }

    //Shows main UI once JSON has loaded from API
    private void setFetchedUI(){
        this.questionLoadingContainer.setVisibility(View.GONE);
        this.questionContainer.setVisibility(View.VISIBLE);
    }

    private void fetchQuestions(){
        Context context = this.getContext();
        if (context == null){
            handleQuestionFail();
            return;
        }

        requestQueue = Volley.newRequestQueue(context);
        getQuestionsJSON();


    }

    //Sends request to Open Questions DB for questions.
    private void getQuestionsJSON() {
        //on Error, do this
        //on (Success) response, do this
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, //The type of request, e.g., GET, POST, DELETE, PUT, etc.
                url, //defined above
                null,
                response -> {
                    try {
                        Log.d("RESPONSE", response.toString(2)); //prints the response to LogCat
                        JSONArray questions = response.getJSONArray("results"); //Gets all questions
                        //Converting to question objects from JSON.
                        questionList = convertToQuestions(questions);

                        //Sets the UI for first question.
                        updateQuestion();
                        question = questionList.get(index);
                        questionText.setText(question.getQuestion()); //Sets question text
                        ArrayList<String> answers = question.getShuffledAnswers();
                        answerA.setText(answers.get(0));
                        answerB.setText(answers.get(1));
                        answerC.setText(answers.get(2));
                        answerD.setText(answers.get(3));
                        //Show UI now everything is set
                        setFetchedUI();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    if (error.getMessage() != null)
                        Log.d("ERROR", error.getMessage()); //prints the error message to LogCat
                        handleQuestionFail();

                }
        );
        requestQueue.add(jsonObjectRequest);

    }

    private ArrayList<Question> convertToQuestions(JSONArray questions) {
        ArrayList<Question> questionList = new ArrayList<>();
        //For each question in the JSON extract the data and turn into a question object.
        for (int i = 0; i < questions.length(); i++) {
            try {
                JSONObject questionJSON = questions.getJSONObject(i);
                String questionText = StringEscapeUtils.unescapeHtml4(questionJSON.getString("question"));
                JSONArray incorrectAnswersJSON = questionJSON.getJSONArray("incorrect_answers"); //Gets all incorrect answers
                ArrayList<String> incorrectAnswers = new ArrayList<>();
                for (int j = 0; j < incorrectAnswersJSON.length(); j++) {
                    //Adds each incorrect answer to list of answers
                    incorrectAnswers.add(StringEscapeUtils.unescapeHtml4(incorrectAnswersJSON.getString(j)));
                }
                String correctAnswer = StringEscapeUtils.unescapeHtml4(questionJSON.getString("correct_answer"));
                Question question = new Question(questionText,correctAnswer,incorrectAnswers);
                questionList.add(question);


            } catch (JSONException e) {
                e.printStackTrace();
                handleQuestionFail();
            }

        }
        return questionList;


    }

    //Sets the UI for an updated index.
    public void updateQuestion() {
        questionNumberText.setText("Question "+(index+1));
        question = questionList.get(index);
        questionText.setText(question.getQuestion()); //Sets question text
        ArrayList<String> answers = question.getShuffledAnswers();
        answerA.setText(answers.get(0));
        answerB.setText(answers.get(1));
        answerC.setText(answers.get(2));
        answerD.setText(answers.get(3));

    }

    private void handleQuestionFail(){
        setFailedUI();
    }

    //Disables questions to stop spamming of answer in one question
    public void disableQuestions(){
        answerA.setEnabled(false);
        answerB.setEnabled(false);
        answerC.setEnabled(false);
        answerD.setEnabled(false);
    }

    //Enabling questions
    public void enableQuestions(){
        answerA.setEnabled(true);
        answerB.setEnabled(true);
        answerC.setEnabled(true);
        answerD.setEnabled(true);
    }

    //Moving on to the next question
    public void nextQuestion(){
        index++;
        progressBar.setProgress(index);
        enableQuestions();
        if(index!=questionNumber){
            updateQuestion();
        } else{
            //Game over: Creates end game screen
            MainActivity mainActivity = (MainActivity) getActivity();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String dateTime = dateFormat.format(cal.getTime());
            Game playedGame = new Game(playerName,difficulty,score,questionList,dateTime);
            mainActivity.insertGame(playedGame);
            mainActivity.changeFragment(new FragmentGameEnd(questionList,score,difficulty,playerName),"End","Game");
        }
    }


}