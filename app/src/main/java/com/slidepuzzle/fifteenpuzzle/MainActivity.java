package com.slidepuzzle.fifteenpuzzle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SlidePuzzle puzzle;
    public int[][] board;
    private Button[][] buttons = new Button[4][4];
    private int moves = 0;
    private TextView textViewMoveCounter;
    private MainActivity game;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);

        puzzle = new SlidePuzzle();
        board = puzzle.getBoard();
        textViewMoveCounter = findViewById(R.id.counter);
        puzzle.setMainActivity(this);


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String buttonId = "button" + i + j;
                int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resourceId);
                buttons[i][j].setText(Integer.toString(board[i][j]));
                buttons[i][j].setOnClickListener(this);
                if (buttons[i][j].getText().toString().equals("0")) {
                    buttons[i][j].setVisibility(Button.INVISIBLE);
                } else {
                    buttons[i][j].setVisibility(Button.VISIBLE);
                }
            }
        }


    }



    @Override
    public void onClick(View v) {
        Log.i("v's number is", ((Button) v).getText().toString());

        int[] clickedButton = puzzle.find(Integer.valueOf(((Button) v).getText().toString()));

        boolean checkMoves = true;
        while (checkMoves) {
            if (clickedButton[0] > 0) {
                if (board[clickedButton[0] - 1][clickedButton[1]] == 0) {
                    Log.i("The number can move up", "move up");
                    puzzle.moveUp(board[clickedButton[0]][clickedButton[1]]);
                    ((Button) v).setText("0");
                    break;
                }
            }
            if (clickedButton[1] > 0) {
                if (board[clickedButton[0]][clickedButton[1] - 1] == 0) {
                    Log.i("The number can move left", "move left");
                    puzzle.moveLeft(board[clickedButton[0]][clickedButton[1]]);
                    ((Button) v).setText("0");
                    break;
                }
            }
            if (clickedButton[1] + 1 < puzzle.boardLength) {
                if (board[clickedButton[0]][clickedButton[1] + 1] == 0) {
                    Log.i("The number can move right", "move right");
                    puzzle.moveRight(board[clickedButton[0]][clickedButton[1]]);
                    ((Button) v).setText("0");
                    break;
                }
            }
            if (clickedButton[0] + 1 < puzzle.boardLength) {
                if (board[clickedButton[0] + 1][clickedButton[1]] == 0) {
                    Log.i("The number can move down", "move down");
                    puzzle.moveDown(board[clickedButton[0]][clickedButton[1]]);
                    ((Button) v).setText("0");
                    break;
                }
            }

            Log.i("The number cannot move", "Try again");
            checkMoves = false;
        }

    }

    public void updateBoard() {
        board = puzzle.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String buttonId = "button" + i + j;
                int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resourceId);
                buttons[i][j].setText(Integer.toString(board[i][j]));
                buttons[i][j].setOnClickListener(this);
                if (buttons[i][j].getText().toString().equals("0")) {
                    buttons[i][j].setVisibility(Button.INVISIBLE);
                } else {
                    buttons[i][j].setVisibility(Button.VISIBLE);
                }
            }
        }
        // update move counter
        moves++;
        textViewMoveCounter.setText(String.valueOf(moves));

        if (gameOver()) {
            // if the game is finished
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBoard(int[][] currentBoard) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String buttonId = "button" + i + j;
                int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resourceId);
                buttons[i][j].setText(Integer.toString(currentBoard[i][j]));
                buttons[i][j].setOnClickListener(this);
                if (buttons[i][j].getText().toString().equals("0")) {
                    buttons[i][j].setVisibility(Button.INVISIBLE);
                } else {
                    buttons[i][j].setVisibility(Button.VISIBLE);
                }
            }
        }
        // update move counter
        moves++;
        textViewMoveCounter.setText(String.valueOf(moves));

        if (gameOver()) {
            // if the game is finished
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean gameOver() {
        for (int i = 0; i < puzzle.boardLength; i++) {
            if (!(Arrays.equals(puzzle.board[i], puzzle.finishedPuzzle[i]))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.solveForMe):
                Toast.makeText(this, "Solving the puzzle... the correct way.", Toast.LENGTH_SHORT).show();
                puzzle.solveSlidePuzzle();
                return true;
            case (R.id.resetBoard):
                Toast.makeText(this, "Resetting board", Toast.LENGTH_SHORT).show();
                resetBoard();
                return true;
        }
        return false;
    }

    public void resetBoard() {
        puzzle = new SlidePuzzle();
        board = puzzle.getBoard();
        puzzle.setMainActivity(this);
        moves = 0;
        textViewMoveCounter.setText(String.valueOf(moves));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String buttonId = "button" + i + j;
                int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resourceId);
                buttons[i][j].setText(Integer.toString(board[i][j]));
                buttons[i][j].setOnClickListener(this);
                if (buttons[i][j].getText().toString().equals("0")) {
                    buttons[i][j].setVisibility(Button.INVISIBLE);
                } else {
                    buttons[i][j].setVisibility(Button.VISIBLE);
                }
            }
        }
    }

}
