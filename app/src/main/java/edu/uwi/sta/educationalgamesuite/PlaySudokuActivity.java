package edu.uwi.sta.educationalgamesuite;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlaySudokuActivity extends AppCompatActivity {
    protected Sudoku sudoku;
    private GridView grid;
    private GridView panel;
    private int[] drawables;
    private int difficulty;
    private int[] gameBoard;
    private Timer timer;
    private String dif;
    public static int val=0;//STARTS WITH 1 HAS THE NUMBER FOR ENTRY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_sudoku_acivity);

        sudoku = new Sudoku();
        timer = new Timer();
        grid = (GridView)findViewById(R.id.sudokuGrid);
        setDrawables();
        difficulty = getIntent().getExtras().getInt("difficulty",1); //0 = easy, 1=med, 2=hard;
        gameBoard= sudoku.getConvertedBoard();
        setHeading();
        populateLinLayout();
        updateTimer();

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
                final ImageView imageView;
                EditText editText;
                if (convertView == null) {
                    imageView = new ImageView(getApplicationContext());
                    imageView.setLayoutParams(new GridView.LayoutParams(55, 55));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setPadding(3,3,3,3);

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
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("CLICK","Click detected");
                            setImage(imageView);
                        }
                    });
                } else {
                    imageView.setImageResource(drawables[gameBoard[position] - 1]);
                    imageView.setClickable(false);
                    imageView.setEnabled(false);
                }
                return imageView;
            }
        });
    }

    private void updateTimer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()){
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            ((TextView)findViewById(R.id.idSudokuHeader)).setText("Sudoku - "+dif+"    "+timer.toString());

                        }
                    });
                    try{
                        Thread.sleep(500);
                    }
                    catch(InterruptedException e){
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void populateLinLayout() {
        panel = (GridView) findViewById(R.id.gridViewPanel);
        panel.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 9;
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
                final ImageView imageView;
                if (convertView == null) {
                    imageView = new ImageView(getApplicationContext());
                    imageView.setLayoutParams(new GridView.LayoutParams(55, 55));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setPadding(8, 8, 8, 8);
                } else {
                    imageView = (ImageView) convertView;
                }
                imageView.setTag(position);
                imageView.setImageResource(drawables[position]);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPosition(imageView);
                    }
                });
                imageView.setClickable(true);
                return imageView;
            }
        });
    }

    private void getPosition(ImageView imageView) {
        Log.d("TAG CLICKED",imageView.getTag().toString());
        PlaySudokuActivity.val=(Integer)imageView.getTag();
    }

    private void setImage(ImageView imageView){
        imageView.setImageResource(drawables[PlaySudokuActivity.val]);
    }
    private int getInput(){
        return 1;
    }

    private void setHeading() {
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
    }

    private void setDrawables() {
        drawables = new int[]{R.drawable.one,R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
                                R.drawable.six,R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.blank,
                                R.drawable.one_wrong,R.drawable.two_wrong,R.drawable.three_wrong,R.drawable.four_wrong,
                                R.drawable.five_wrong,R.drawable.six_wrong,R.drawable.seven_wrong,R.drawable.eight_wrong,
                                R.drawable.nine_wrong};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.resetSudoku) {
            Toast.makeText(this,"Reset Sudoku Clicked",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.checkSudoku) {
            Toast.makeText(this,"Check Sudoku Clicked",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.submitSudoku) {
            Toast.makeText(this,"Submit Sudoku Clicked",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
