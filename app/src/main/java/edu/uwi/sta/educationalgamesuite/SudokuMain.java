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
import android.view.View;

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

}
