package edu.uwi.sta.educationalgamesuite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.awt.font.TextAttribute;

/**
 * Created by kwasi on 4/3/2016.
 */
public class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int[] img;
    private static LayoutInflater inflater;
    public CustomAdapter(MainActivity m, String[] strList, int[] picList){
        context=m;
        result = strList;
        img=picList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        SingleRow sr = new SingleRow();
        View rowView = inflater.inflate(R.layout.game_item,null);
        sr.tv=(TextView) rowView.findViewById(R.id.txtViewTxt);
        sr.iv= (ImageView) rowView.findViewById(R.id.imgViewImg);

        sr.tv.setText(result[position]);
        sr.iv.setImageResource(img[position]);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        Log.d("ITEM CLICKED", "Play sudoku");
                        /*new AlertDialog.Builder(context)
                                .setTitle("Select Difficulty")
                                .setItems(new String[]{"Easy", "Medium", "Hard"}, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        context.startActivity(new Intent(context,PlaySudokuActivity.class).putExtra("difficulty",which));
                                    }
                                }).show();
                        */
                        context.startActivity(new Intent(context,SudokuMain.class));
                        break;
                    default:
                        Toast.makeText(context,"This Game is Currently Unavailable",Toast.LENGTH_LONG).show();
                }
            }
        });
        return rowView;
    }

    public class SingleRow{
        TextView tv;
        ImageView iv;
    }

}
