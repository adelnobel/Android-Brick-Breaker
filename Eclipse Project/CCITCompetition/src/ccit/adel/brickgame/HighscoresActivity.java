package ccit.adel.brickgame;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class HighscoresActivity extends Activity {
	
	final static int LOADING_DIALOG = 0;
	final static int ERROR_DIALOG = 1;
	
	private Typeface font;
	
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        new GetScores(this).execute();
        setContentView(R.layout.highscores);
        
        font = Typeface.createFromAsset(getAssets(),"ThrowMyHandsUpintheAirBold.ttf");
    	TextView tv = (TextView) findViewById(getResources().getIdentifier("nameHeader", "id", getPackageName()));
    	tv.setTypeface(font);
    	tv = (TextView) findViewById(getResources().getIdentifier("timeHeader", "id", getPackageName()));
    	tv.setTypeface(font);

        showDialog(LOADING_DIALOG);

        //removeDialog(LOADING_DIALOG);
    }
    
    
    public void populateScores(List<String[]> resultsList, boolean failed)
    {
    	removeDialog(LOADING_DIALOG);
    	if(failed)
    	{
    		showDialog(ERROR_DIALOG);
    		return;
    	}
        try
        {
        	int x = 1;
        	for(String[] singleRow : resultsList)
        	{
	        	TextView tv = (TextView) findViewById(getResources().getIdentifier("name"+String.valueOf(x), "id", getPackageName()));
	        	tv.setText(singleRow[0]);
	        	tv.setTypeface(font);
	        	tv = (TextView) findViewById(getResources().getIdentifier("time"+String.valueOf(x), "id", getPackageName()));
	        	tv.setText(singleRow[1]);
	        	tv.setTypeface(font);
	        	x++;
        	}
        }
        catch(Exception ex)
        {    		
        	showDialog(ERROR_DIALOG);
        	return;
		};
    }
    
    
    @Override
    public Dialog onCreateDialog(int id)
    {
    	switch(id)
    	{
    	case LOADING_DIALOG:
    		View b = getLayoutInflater().inflate(R.layout.dialog, null);
    		return new AlertDialog.Builder(this).setMessage("Loading").setView(b).setCancelable(false).show();
    	
    	case ERROR_DIALOG:
	    	return new AlertDialog.Builder(this).setTitle("Error!").setIcon(R.drawable.error)
	    	.setMessage("An error occurred while retrieving scores, please check your connection")
	    	.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).setCancelable(false).show();
    		
    	}
    	return null;
    }

}
