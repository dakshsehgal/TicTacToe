package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView player1;
    private TextView player2;
    private int turnCount, p1Score, p2Score;
    private Button[][] buttons;
    private String[][] fields;
    private Button resetButton;
    private boolean player1turn;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        p1Score = 0;
        p2Score = 0;
        handler = new Handler();
        this.buttons = new Button[3][3];
        this.fields = new String[3][3];
        player1turn = true;
        turnCount = 0;

        player1 = findViewById(R.id.player1score);
        player2 = findViewById(R.id.player2score);
        resetButton = findViewById(R.id.reset);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p1Score = 0;
                p2Score = 0;
                reset();


            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int resID = getResources().getIdentifier("button_" + i + j, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        {

        }
    }

    private void reset() {
        player1.setText("Player 1: " + p1Score);
        player2.setText("Player 2: " + p2Score);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setClickable(true);
            }

        }
        turnCount = 0;
        player1turn = true;
    }

    @Override
    public void onClick(View v) {
        Button button = findViewById(v.getId());
        if (player1turn) {
            button.setText("X");
        } else {
            button.setText("O");
        }

        button.setClickable(false);
        player1turn = !player1turn;
        turnCount++;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = buttons[i][j].getText().toString();

            }
        }

        if (winCheck(fields, "X")) {
            p1Score++;
            Toast.makeText(getApplicationContext(), "Player 1 wins!", Toast.LENGTH_LONG).show();
            onWinPause();



        } else if (winCheck(fields, "O")) {
            p2Score++;
            Toast.makeText(getApplicationContext(), "Player 2 wins!", Toast.LENGTH_LONG).show();
            onWinPause();


        } else if (turnCount == 9) {
            Toast.makeText(getApplicationContext(), "Draw!", Toast.LENGTH_LONG).show();
            onWinPause();

        }

    }

    private boolean winCheck(String[][] fields, String character) {
        for (int i = 0; i < 3; i++) {
            if (fields[i][0].equals(fields[i][1]) && fields[i][0].equals(fields[i][2]) && fields[i][0].equals(character)) {
                return true;
            }

            if (fields[0][i].equals(fields[1][i]) && fields[0][i].equals(fields[2][i]) && fields[0][i].equals(character)) {
                return true;
            }


        }
        if (fields[0][0].equals(fields[1][1]) && fields[0][0].equals(fields[2][2]) && fields[0][0].equals(character)) {
            return true;
        }

        if (fields[2][0].equals(fields[1][1]) && fields[2][0].equals(fields[0][2]) && fields[2][0].equals(character)) {
            return true;
        }
        return false;
    }

    private void onWinPause() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 1000);

    }
}