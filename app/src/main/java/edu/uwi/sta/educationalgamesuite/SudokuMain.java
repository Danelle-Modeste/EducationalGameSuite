package edu.uwi.sta.educationalgamesuite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SudokuMain extends AppCompatActivity {
    int difficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void playSudoku(View view){
        startActivity(new Intent(SudokuMain.this,PlaySudokuActivity.class).putExtra("difficulty",difficulty));
    }
    public void viewInstructions(View view){
        startActivity(new Intent(SudokuMain.this,Instructions.class));
    }
    public void viewHighScores(View view){
        startActivity(new Intent(SudokuMain.this,HighScores.class));
    }
    public void setDifficulty(View view){
        new AlertDialog.Builder(this)
                .setTitle("Select Difficulty")
                .setItems(new String[]{"Easy", "Medium", "Hard"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        difficulty=which;
                    }
                }).show();
    }
/*
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
    }*/

}
