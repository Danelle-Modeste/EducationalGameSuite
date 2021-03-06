package edu.uwi.sta.educationalgamesuite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
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
    private int numBlank;
    public static int val=0;//STARTS WITH 1 HAS THE NUMBER FOR ENTRY

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_sudoku_acivity);
        numBlank=0;
        sudoku = new Sudoku();
        timer = new Timer();
        grid = (GridView)findViewById(R.id.sudokuGrid);
        setDrawables();
        difficulty = getIntent().getExtras().getInt("difficulty",1); //0 = easy, 1=med, 2=hard;
        gameBoard= sudoku.getConvertedBoard();
        setHeading();
        populateLinLayout();
        updateTimer();
        setAdapter();
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

    public void setAdapter(){
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
                if (convertView == null) {
                    imageView = new ImageView(getApplicationContext());
                    imageView.setLayoutParams(new GridView.LayoutParams(55, 55));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setPadding(3,3,3,3);

                } else {
                    imageView = (ImageView) convertView;
                }
                int y = Math.abs((int) System.currentTimeMillis());
                Log.d(y + "", y % 4 + "");
                if (y % 4 <= difficulty) {
                    imageView.setImageResource(drawables[9]);//blank tile
                    imageView.setTag(99);
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
        if (imageView.getTag()==null){

        }
        imageView.setImageResource(drawables[PlaySudokuActivity.val]);
        imageView.setTag(val+1);

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
                                R.drawable.nine_wrong,R.drawable.blank_wrong};
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

            AlertDialog.Builder confirm = new AlertDialog.Builder(this);
            confirm.setMessage("Would you like to reset your progress with this puzzle");
            confirm.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    timer = new Timer();
                    GridView gv = (GridView)findViewById(R.id.sudokuGrid);
                    for (int a=0;a<gv.getChildCount();a++){
                        ImageView iv = (ImageView)gv.getChildAt(a);
                        if (iv.getTag()!=null){
                            iv.setImageResource(drawables[9]);
                            iv.setTag(null);
                        }
                    }
                }
            });
            confirm.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            confirm.show();

            return true;
        }

        if (id == R.id.checkSudoku) {
            checkSudoku();
            return true;
        }
        if (id == R.id.submitSudoku) {
            //Toast.makeText(this,"Submit Sudoku Clicked",Toast.LENGTH_SHORT).show();
            submitSudoku();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitSudoku() {
        GridView gv = (GridView)findViewById(R.id.sudokuGrid);
        boolean doCheck = true,filled=false;
        for (int a=0;a<gv.getChildCount();a++) {
            ImageView iv = (ImageView) gv.getChildAt(a);
            if (iv.getTag()!=null){
                if ((int)iv.getTag() == 99){
                    new AlertDialog.Builder(this)
                            .setTitle("BLANK")
                            .setMessage("Fill in all the blanks")
                            .show();
                    doCheck=false;
                    break;
                }
                else{
                    filled=true;
                }
            }
        }
        if (doCheck){
            if (filled){
                if (checkSudoku()){
                    new AlertDialog.Builder(this)
                            .setMessage("Congratulations, you won")
                            .setTitle("Congrats")
                            .show();
                }
            }
            else{
                new AlertDialog.Builder(this)
                        .setTitle("Save Game?")
                        .setMessage("Would you like to save and exit game?")
                        .setPositiveButton("YES",null)
                        .setNegativeButton("NO",null)
                        .show();
            }
        }

    }

    private boolean checkSudoku() {
        GridView gv = (GridView)findViewById(R.id.sudokuGrid);
        boolean x = true;
        for (int a=0;a<gv.getChildCount();a++){
            ImageView iv = (ImageView)gv.getChildAt(a);
            if (iv.getTag()!=null){
                if ((int)iv.getTag()!=gameBoard[a]){
                    if ((int)iv.getTag()<10){
                        iv.setImageResource(drawables[(int)iv.getTag()+9]);
                        x=false;
                    }
                }
            }
        }
        return x;
    }
}
