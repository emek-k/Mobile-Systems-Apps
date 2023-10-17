package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX = "current index";
    public static final String KEY_EXTRA_ANSWER = "com.example.quiz.correctAnswer";
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;

    private Button hintButton;
    private TextView questionTextView;
    private static final int REQUEST_CODE_PROMPT = 0;
    private int wynik = 0;

    private boolean answerWasShown;

    private int currentIndex = 0;
    private Question[] questions = new Question[]{
            new Question(R.string.question_1, false),
            new Question(R.string.question_2, true),
            new Question(R.string.question_3, true),
            new Question(R.string.question_4, true),
            new Question(R.string.question_5, false),
            new Question(R.string.question_6, true),
            new Question(R.string.question_7, true),
            new Question(R.string.question_8, true),
            new Question(R.string.question_9, true),
            new Question(R.string.question_10, true)
    };

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;

        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }
        else{
            if(userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                wynik++;
            }
            else
                resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    private void showEndMessage(){
        Toast.makeText(this, "Gratulacje udało Ci się ukończyś Quiz z wynikiem " + wynik + " /10!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test", "onCreate is activated.");

        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            Log.d("test", "restored current index");
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        hintButton = findViewById(R.id.hint_button);

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });
        trueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1);
                if(currentIndex == 10) {
                    showEndMessage();
                    wynik=0;
                }
                currentIndex = currentIndex % questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });
        setNextQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test", "onStart is activated.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test", "onStop is activated.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "onDestroy is activated.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "onPause is activated.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test", "onResume is activated.");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("test", "onSaveInstanceState is activated.");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK)
            return;
        if(requestCode == REQUEST_CODE_PROMPT){
            if(data == null)
                return;
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }
}