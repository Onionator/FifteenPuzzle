package com.example.fifteenpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SlidePuzzle puzzle;
    private int[][] board;
    private Button[][] buttons = new Button[4][4];
    private int moves = 0;
    private TextView textViewMoveCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        puzzle = new SlidePuzzle();
        board = puzzle.getBoard();
        textViewMoveCounter = findViewById(R.id.counter);


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

//                String buttonId = "button" + i + j;
//                Button button = (Button)findViewById(getResources().getIdentifier(buttonId, "id", getPackageName()));
//                button.setText(Integer.toString(board[i][j]));
//                button.setOnClickListener((View.OnClickListener) this);

                String buttonId = "button" + i + j;
                int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resourceId);
                buttons[i][j].setText(Integer.toString(board[i][j]));
                buttons[i][j].setOnClickListener(this);
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
                    updateBoard();
                    ((Button) v).setText("0");
                    break;
                }
            }
            if (clickedButton[1] > 0) {
                if (board[clickedButton[0]][clickedButton[1] - 1] == 0) {
                    Log.i("The number can move left", "move left");
                    puzzle.moveLeft(board[clickedButton[0]][clickedButton[1]]);
                    updateBoard();
                    ((Button) v).setText("0");
                    break;
                }
            }
            if (clickedButton[1] + 1 < puzzle.boardLength) {
                if (board[clickedButton[0]][clickedButton[1] + 1] == 0) {
                    Log.i("The number can move right", "move right");
                    puzzle.moveRight(board[clickedButton[0]][clickedButton[1]]);
                    updateBoard();
                    ((Button) v).setText("0");
                    break;
                }
            }
            if (clickedButton[0] + 1 < puzzle.boardLength) {
                if (board[clickedButton[0] + 1][clickedButton[1]] == 0) {
                    Log.i("The number can move down", "move down");
                    puzzle.moveDown(board[clickedButton[0]][clickedButton[1]]);
                    updateBoard();
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


}
