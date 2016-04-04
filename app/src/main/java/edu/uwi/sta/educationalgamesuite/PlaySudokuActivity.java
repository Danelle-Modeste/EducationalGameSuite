package edu.uwi.sta.educationalgamesuite;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaySudokuActivity extends AppCompatActivity {
    protected Sudoku sudoku;
    private GridView grid;
    private int[] drawables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_sudoku_acivity);
        sudoku = new Sudoku();
        final int[] gameBoard = sudoku.getConvertedBoard();
        grid = (GridView)findViewById(R.id.sudokuGrid);
        setDrawables();
        grid.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return gameBoard.length;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView;
                if (convertView==null){
                    imageView = new ImageView(getApplicationContext());
                    imageView.setLayoutParams(new GridView.LayoutParams(60, 60));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setPadding(8, 8, 8, 8);
                }
                else{
                    imageView = (ImageView)convertView;
                }

                imageView.setImageResource(drawables[gameBoard[position]-1]);
                return imageView;
            }
        });
    }

    private void setDrawables() {
        drawables = new int[]{R.drawable.one,R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
                                R.drawable.six,R.drawable.seven, R.drawable.eight, R.drawable.nine};
    }
}
