package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Whack-A-Mole 2.0";
    private String advanceScore;
    private TextView advancedScore;
    CountDownTimer readyCountdown;
    CountDownTimer moleTimer;
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */



    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        readyCountdown = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "Ready Countdown! " + l/1000);
                Toast.makeText(getApplicationContext(), "Get ready in "+l/1000 + " seconds!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready Countdown Complete!!");
                Toast.makeText(getApplicationContext(), "Go!",Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                readyCountdown.cancel();
            }
        };
        readyCountdown.start();
    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        moleTimer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "New mole location!");
                setNewMole();
            }

            @Override
            public void onFinish() {
                start();
            }
        }.start();
    }
    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
        R.id.button1,
        R.id.button2,
        R.id.button3,
        R.id.button4,
        R.id.button5,
        R.id.button6,
        R.id.button7,
        R.id.button8,
        R.id.button9,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView advancedScore = findViewById(R.id.advancedScore);
        Intent Score = getIntent();
        advanceScore = Score.getStringExtra("advancedScore");
        advancedScore.setText(advanceScore);
        Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));
        readyTimer();
        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            final Button btn = findViewById(id);
            btn.setText("O");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCheck(btn);
                }
            });
        }

    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(final Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        final TextView advancedScore = (TextView) findViewById(R.id.advancedScore);
        if (checkButton.getText() == "*") {            //if correct button is pressed

            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, advancedScore.getText().toString()); //for checking
            addScore();                             //add 1 to score
            Log.v(TAG, advancedScore.getText().toString());
        }
        else {                                      // if wrong button is pressed
            Log.v(TAG, "Missed, score deducted!");
            Log.v(TAG, advancedScore.getText().toString()); //for checking
            removeScore();                          //remove 1 from score
            Log.v(TAG, advancedScore.getText().toString());
        }
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        for (int i = 0; i<BUTTON_IDS.length; i++){
            Button btn = findViewById(BUTTON_IDS[i]);
            btn.setText("O");
            Button mole = findViewById(BUTTON_IDS[randomLocation]);
            mole.setText("*");
        }

    }
    private void addScore(){
        final TextView advancedScore = (TextView) findViewById(R.id.advancedScore);
        int intScore = Integer.parseInt(advancedScore.getText().toString());    //gets int score from the textview after getting text and converting to string
        intScore += 1;  //adds 1 to score
        String score = Integer.toString(intScore);  //convert back to string
        advancedScore.setText(score);   //sets the printed textview to the new score
    }

    private void removeScore(){
        final TextView advancedScore = (TextView) findViewById(R.id.advancedScore);
        int intScore = Integer.parseInt(advancedScore.getText().toString());    //gets int score from the textview after getting text and converting to string
        if (intScore != 0 ){    //if score is not 0
            intScore-=1;        // score - 1
        }
        else {                  //if score is 0
            intScore=0;         //score will remain 0
        }
        String score = Integer.toString(intScore);  //convert back to string
        advancedScore.setText(score);   //sets the printed textview to the new score
    }
}

