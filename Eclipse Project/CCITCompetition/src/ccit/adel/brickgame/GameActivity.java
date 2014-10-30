package ccit.adel.brickgame;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class GameActivity extends Activity {
	
	final static int SCORE_DIALOG = 0;
	final static int LOADING_DIALOG = 1;
	final static int ERROR_DIALOG = 2;
	final static int SUCCESS_DIALOG = 3;
	final static int PAUSED_DIALOG = 4;
	final static int WIN_DIALOG = 5;
	final static int GAME_OVER = 6;
	final static int DIFF_LEVEL_DIALOG = 7;
	
	
	private GameView gameView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        showDialog(DIFF_LEVEL_DIALOG);
    }
    
    private void launchGameView(int diffcultyLevel)
    {
        gameView = new GameView(this, diffcultyLevel);
        setContentView(gameView);
    }
    
    private void callScoreThread(String name, int score)
    {
    	showDialog(LOADING_DIALOG);
    	try
    	{
	    	SendScore scObject = new SendScore(this);
	    	scObject.setUrl(name, score);
	    	scObject.execute();
    	}
    	catch(Exception ex)
    	{
    		submitDone(true);
    	}
    }
    
    protected Dialog onCreateDialog(int id)
    {
    	switch(id)
    	{
    	case GAME_OVER:
	    	return new AlertDialog.Builder(this).setTitle("You lose!").setIcon(R.drawable.gameover)
	    	.setMessage("You lost! Game Over :(")
	    	.setNegativeButton("Okay",  new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).setCancelable(false).show();
    	
    	case SCORE_DIALOG:
	    	final EditText input = new EditText(this);
	    	final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	    	imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
	    	
	    	DialogInterface.OnClickListener dialogClickListener = 	new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	String name = input.getText().toString();
	            	input.setText("");
	                switch (which){
	                case DialogInterface.BUTTON_POSITIVE:
	                	imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS,0);
	                    callScoreThread(name, (int)GameLoopThread.getElpasedTime()/1000);
	                    break;
	
	                case DialogInterface.BUTTON_NEGATIVE:
	                    finish();
	                    break;
	                }
	            }
	        };
	    	return new AlertDialog.Builder(this).setPositiveButton("Publish", dialogClickListener).setTitle("Upload your score").setIcon(R.drawable.share)
	        .setNegativeButton("Cancel", dialogClickListener).setCancelable(false).setView(input).show();
    	
    	case LOADING_DIALOG:
    		View b = getLayoutInflater().inflate(R.layout.dialog, null);
    		return new AlertDialog.Builder(this).setMessage("Loading").setView(b).setCancelable(false).show();
    	
    	case ERROR_DIALOG:
	    	return new AlertDialog.Builder(this).setTitle("Error!").setIcon(R.drawable.error)
	    	.setMessage("An error occurred while trying to upload your score, please check your connection")
	    	.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					showDialog(SCORE_DIALOG);
				}
			}).setCancelable(false).show();
	    	
    	case SUCCESS_DIALOG:
	    	return new AlertDialog.Builder(this).setTitle("Success!").setIcon(R.drawable.tick)
	    	.setMessage("Your score was successfully uploaded! :)")
	    	.setNegativeButton("Okay",  new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).setCancelable(false).show();
	    
    	case PAUSED_DIALOG:

    		DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() 
    		{
	            public void onClick(DialogInterface dialog, int which) 
	            { 
	            	gameView.resumeGame();
	            } 
	        }; 
    		
	    	return new AlertDialog.Builder(this).setTitle("Paused").setIcon(R.drawable.tick)
	    	.setNegativeButton("Back", clickListener).show();
	    
    	case WIN_DIALOG:
	    	DialogInterface.OnClickListener finishDialogListener = 	new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                switch (which){
	                case DialogInterface.BUTTON_POSITIVE:
	                    showDialog(SCORE_DIALOG);
	                    break;
	
	                case DialogInterface.BUTTON_NEGATIVE:
	                    //No button clicked
	                	finish();
	                    break;
	                }
	            }
	        };
	    	return new AlertDialog.Builder(this).setPositiveButton("Yes", finishDialogListener).setTitle("You Win!").setIcon(R.drawable.tick)
	        .setMessage("Do you want to upload your score?").setNegativeButton("No", finishDialogListener).setCancelable(false).show();
    	
    	
    	case DIFF_LEVEL_DIALOG:
     	   final CharSequence[] items = {"Easy", "Normal", "Hard"};

     	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
     	   builder.setTitle("Choose Difficulty Level");
     	   builder.setItems(items, new DialogInterface.OnClickListener() {
     	       public void onClick(DialogInterface dialog, int item) {
     	           launchGameView(item);
     	       }
     	   });
     	   
     	   return builder.setCancelable(false).show();
    	}
    	
    	return null;
    }
    
	public void submitDone(boolean error)
	{
		if(error)
			showDialog(ERROR_DIALOG);
		else
			showDialog(SUCCESS_DIALOG);
	}
    
}