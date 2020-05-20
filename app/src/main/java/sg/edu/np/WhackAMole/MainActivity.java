package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button buttonLeft;
    private Button buttonMiddle;
    private Button buttonRight;
    private TextView Score;
    private static final String TAG = "Whack-A-Mole";

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Finished Pre-Initialisation!");
        buttonLeft = (Button) findViewById(R.id.Button1);
        buttonMiddle = (Button) findViewById(R.id.Button2);
        buttonRight = (Button) findViewById(R.id.Button3);
        setNewMole();
        doCheck(buttonLeft);
        doCheck(buttonMiddle);
        doCheck(buttonRight);


    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(final Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        final TextView Score = (TextView) findViewById(R.id.Score);
        checkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.v(TAG, checkButton.toString() + "clicked!");
                if (checkButton.getText() == "*")            //if correct button is pressed
                {
                    Log.v(TAG, "Hit, score added!");
                    addScore();                             //add 1 to score
                    Log.v(TAG, "Score is " + Score.getText().toString());
                    setNewMole();
                    if (Integer.parseInt(Score.getText().toString())%10 == 0){
                        Log.v(TAG, "Score is multiple of 10");
                        nextLevelQuery();
                    }
                }
                else {                                      // if wrong button is pressed
                    Log.v(TAG, "Missed, score deducted!");
                    removeScore();                          //remove 1 from score
                    Log.v(TAG, "Score is " + Score.getText().toString());
                    setNewMole();
                }
            }
        });

    }
    private void addScore(){
        final TextView Score = (TextView) findViewById(R.id.Score);
        int intScore = Integer.parseInt(Score.getText().toString());    //gets int score from the textview after getting text and converting to string
        intScore += 1;  //adds 1 to score
        String score = Integer.toString(intScore);  //convert back to string
        Score.setText(score);   //sets the printed textview to the new score
    }

    private void removeScore(){
        final TextView Score = (TextView) findViewById(R.id.Score);
        int intScore = Integer.parseInt(Score.getText().toString());    //gets int score from the textview after getting text and converting to string
        if (intScore != 0 ){    //if score is not 0
            intScore-=1;        // score - 1
        }
        else {                  //if score is 0
            intScore=0;         //score will remain 0
        }
        String score = Integer.toString(intScore);  //convert back to string
        Score.setText(score);   //sets the printed textview to the new score
    }
    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        Log.v(TAG, "Advance option given to user");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to advance to advanced mode?").setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "User accepts");
                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "User declines");
            }
        });
        AlertDialog alert = builder.create();
        alert.setTitle("Warning! Insane Whack-A-Mole Incoming!");
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        TextView score = findViewById(R.id.Score);
        Log.v(TAG, score.getText().toString());
        Intent advancedPage = new Intent(MainActivity.this, Main2Activity.class);
        advancedPage.putExtra("advancedScore", score.getText().toString());
        startActivity(advancedPage);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);

        if (randomLocation == 0){       //sets which button is mole based on random result
            buttonLeft.setText("*");
            buttonMiddle.setText("O");
            buttonRight.setText("O");
        }
        else if (randomLocation==1){    //sets which button is mole based on random result
            buttonLeft.setText("O");
            buttonMiddle.setText("*");
            buttonRight.setText("O");
        }
        else {                          //sets which button is mole based on random result
            buttonLeft.setText("O");
            buttonMiddle.setText("O");
            buttonRight.setText("*");
        }
    }
}