package com.example.fifteenpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private SlidePuzzle puzzle;
    private int[][] board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        puzzle = new SlidePuzzle();
        board = puzzle.getBoard();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                String buttonId = "button" + i + j;
                Button button = (Button)findViewById(getResources().getIdentifier(buttonId, "id",getPackageName()));
                button.setText(Integer.toString(board[i][j]));
            }
        }

    }


    private void drawNumbers(View view) {


    }
}
