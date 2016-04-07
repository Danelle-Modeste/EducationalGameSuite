package edu.uwi.sta.educationalgamesuite;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaySudokuActivity extends AppCompatActivity {
    protected Sudoku sudoku;
    private GridView grid;
    private int[] drawables;
    private int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_sudoku_acivity);
        sudoku = new Sudoku();
        final int[] gameBoard = sudoku.getConvertedBoard();
        grid = (GridView)findViewById(R.id.sudokuGrid);
        setDrawables();
        difficulty = getIntent().getExtras().getInt("difficulty",1); //0 = easy, 1=med, 2=hard;

        setHeading();

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
                EditText editText;
                if (convertView == null) {
                    imageView = new ImageView(getApplicationContext());
                    imageView.setLayoutParams(new GridView.LayoutParams(55, 55));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setPadding(8, 8, 8, 8);

                    editText = new EditText(getApplicationContext());
                    editText.bringToFront();
                } else {
                    imageView = (ImageView) convertView;
                    editText = new EditText(getApplicationContext());
                    editText.bringToFront();
                }
                int y = Math.abs((int) System.currentTimeMillis());
                Log.d(y + "", y % 4 + "");
                if (y % 4 <= difficulty) {
                    imageView.setImageResource(drawables[9]);//blank tile
                } else {
                    imageView.setImageResource(drawables[gameBoard[position] - 1]);
                    imageView.setClickable(false);
                    imageView.setEnabled(false);
                }

                return imageView;
            }
        });
    }

    private void setHeading() {
        String dif;
        switch (difficulty){
            case 0:
                dif = "Easy";
                break;
            case 1:
                dif ="Medium";
                break;
            default:
                dif="Hard";
        }
        ((TextView)findViewById(R.id.idSudokuHeader)).setText("Sudoku-   "+dif);
    }

    private void setDrawables() {
        drawables = new int[]{R.drawable.one,R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
                                R.drawable.six,R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.blank,
                                R.drawable.one_wrong,R.drawable.two_wrong,R.drawable.three_wrong,R.drawable.four_wrong,
                                R.drawable.five_wrong,R.drawable.six_wrong,R.drawable.seven_wrong,R.drawable.eight_wrong,
                                R.drawable.nine_wrong};
    }
}
