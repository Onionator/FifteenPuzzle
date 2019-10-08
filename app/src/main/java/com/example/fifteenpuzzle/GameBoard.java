package com.example.fifteenpuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class GameBoard extends View {

    private Paint tileBorder, numColor;
    private int width, height;

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);

        tileBorder = new Paint();
        tileBorder.setStyle(Paint.Style.STROKE);
        tileBorder.setColor(Color.BLACK);
        tileBorder.setStrokeWidth(3);

        numColor = new Paint();
        numColor.setStyle(Paint.Style.FILL);
        numColor.setColor(Color.BLACK);

        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, context.getResources().getDisplayMetrics());

    }

    @Override
    public void onDraw(Canvas canvas) {
        drawBoard(canvas);
    }

    private void drawBoard(Canvas canvas) {

        int[][] finishedPuzzle = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};

        int tileWidth = (int)(width * .9) / 4;
        int numPosition = (int)(tileWidth / 2);
        Log.i("tileWidth", Integer.toString(tileWidth));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // draw the tiles
                Rect tile = new Rect();
                tile.set((tileWidth * i), (tileWidth * j), (tileWidth * i) + tileWidth, (tileWidth * j) + tileWidth);
                canvas.drawRect(tile, tileBorder);

                // add the numbers
                canvas.drawText(Integer.toString(finishedPuzzle[i][j]), (tileWidth * i) + numPosition, (tileWidth * j) + numPosition, numColor);
            }
        }
    }






    public void touchTile() {
        // if the tile can be moved
            // run the move animation and move it into the empty square
        // else
            // run a puddle animation indicating the tile was touched
    }

    public void swipe_for_map() {
        // when swiped the completed picture will appear
    }


}
