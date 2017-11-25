package com.astralbody888.alexanderconner.braintrainer;

import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> answers;
    Button startButton;
    GridLayout gridLayout;
    TextView resultTextView, pointsTextView, sumTextView, timerTextview;
    int locationCorrectAnswer;
    int difficultyChoice =0;
    int score = 0;
    int rounds = 0;

    public void startGame(View view) {
        playAgain();
    }

    public void chooseAnswer(View view) {
        Button btn = (Button) view;
        Log.i("Tag", "Tag picked: " + btn.getTag().toString() + " Answer picked: " + btn.getText().toString());

        if (btn.getText().toString().equals(Integer.toString(answers.get(locationCorrectAnswer)))){
            score++;
            resultTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.LimeGreen));
            resultTextView.setText("Correct");
        } else{
            resultTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.Red));
            resultTextView.setText("Incorrect");
        };
        rounds ++;
        pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(rounds));
        generateQuestion();
    }

    public void generateQuestion(){
        Random rand = new Random();

        int[] difficultyNums = {21, 51, 101};

        int difficulty = difficultyNums[difficultyChoice];

        int a = rand.nextInt(difficulty);
        int b = rand.nextInt(difficulty);

        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));

        locationCorrectAnswer = rand.nextInt(4);

        //clear answers bc if we didnt they would just keep getting added to
        answers.clear();

        int incorrectAnswer;
        int correctAnswer = a + b;
        for (int i = 0; i<gridLayout.getChildCount(); i++)
        {
            if (i==locationCorrectAnswer)
            {
                answers.add(correctAnswer);
            }
            else {
                //add incorrect answer
                incorrectAnswer = rand.nextInt(difficulty * 2);
                while (incorrectAnswer == correctAnswer) {
                    incorrectAnswer = rand.nextInt(difficulty * 2);
                }
                answers.add(incorrectAnswer);
            }
        }

        //Iterate over gridLayout btns and add the answer we defined
        for(int i=0; i<gridLayout.getChildCount(); i++) {
            Button btn = (Button) gridLayout.getChildAt(i);
            // do stuff with child view
            btn.setText(Integer.toString(answers.get(i)));
        }
    }

    public void playAgain(){
        score = 0;
        rounds = 0;
        gridLayout.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);

        timerTextview .setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        generateQuestion();

        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long l) {
                timerTextview.setText(String.valueOf(l/1000) + "s");
            }

            @Override
            public void onFinish() {
                float rating = (float) score / (float) rounds;
                if (rating >= 0.7f )
                {
                    resultTextView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.LimeGreen));
                } else {
                    resultTextView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.Red));
                }
                resultTextView.setText("Your Score:" + Integer.toString(score) + "/" + Integer.toString(rounds));
                startButton.setVisibility(View.VISIBLE);
                gridLayout.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        sumTextView = (TextView) findViewById(R.id.operationTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        timerTextview = (TextView) findViewById(R.id.timerTextView);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        answers = new ArrayList<Integer>();

    }
}
