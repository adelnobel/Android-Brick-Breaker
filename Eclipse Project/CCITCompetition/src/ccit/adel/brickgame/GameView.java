package ccit.adel.brickgame;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;


public class GameView extends SurfaceView {
	
	private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private List<Brick> bricks = new ArrayList<Brick>();
    private Paddle paddle;
    private List<Ball> ballsList;
    private BallGenerator ballGenerator;
    private Collisions collisions;
    private Sounds sounds;
    private Typeface chops;
    private boolean menuPresent = false;
    private float xUnit;
    private float yUnit;
    private int diffcultyLevel;
    private boolean fireNewBall;
    private Ball ballToBeRemoved = null;
    private float previousX;
    private Paint mPaint;
    private GameActivity gameActivity;
    
    private final int NUM_OF_BRICKS = 56;
    

	public GameView(Context context, int difficulty) 
	{
		super(context);
		this.gameActivity = (GameActivity)context;
		this.diffcultyLevel = difficulty;
		this.setFocusableInTouchMode(true);
		this.setFocusable(true);
		this.requestFocus();
		sounds = new Sounds(context);
		gameLoopThread = new GameLoopThread(this);
		Brick.setDestroyedCount(NUM_OF_BRICKS);
		
		mPaint = new Paint();
	    mPaint.setColor(Color.GREEN);
	    mPaint.setTextSize(20);
	    mPaint.setAntiAlias(true);
	    chops = Typeface.createFromAsset(context.getAssets(), "ThrowMyHandsUpintheAirBold.ttf");
	    mPaint.setTypeface(chops);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

               public void surfaceDestroyed(SurfaceHolder holder) 
               {
            	   	killGameThread();
            	   	gameActivity.finish();
               }

               public void surfaceCreated(SurfaceHolder holder)
               {
            	   initGame();
               }

               public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {}
        	
        });
        
        
	}
	
	protected void onDraw(Canvas canvas)
	{
		
		canvas.drawColor(Color.BLACK);
	    //canvas.drawBitmap(bg, 0, 0 , null);
		
	    if(collisions.isGameFinished()) //If the user wins
	    {
	    	finishGame(); //Win!
	    }
		
		for(Ball ball : ballsList)
		{
		    ball.setPreviousY(ball.getCurrentY()); //Saves the Y position
		    ball.setPreviousX(ball.getCurrentX()); //Saves the X position
		    
		    
		    if(collisions.isDead(ball)) //If the game finishes
		    {
		    	if(ballsList.size() == 1)
		    		gameOver(); //GameOver!
		    	else
		    	{
		    		ballToBeRemoved = ball;
		    	}
		    }
		    
			if(collisions.isTopCollide(ball)) //If the ball collides with the top boundary
			{
				ball.setSpeedY( -ball.getSpeedY() ); //Change Y Direction
				sounds.playSound("bound");
			}
			
			else if(collisions.isSideCollide(ball))
			{
				ball.setSpeedX( -ball.getSpeedX() ); //Change X Direction
				sounds.playSound("bound");
			}
			
			else if(collisions.isPaddleCollide(ball)) //If the ball collides with the paddle
			{
		        ball.setCurrentY(ball.getPreviousY()); //Set current Y to the previous one
		        ball.setCurrentX(ball.getPreviousX()); //Set current X to the previous one
		        
		        collisions.setAngle(Math.PI - ( (ball.getCurrentX() - paddle.getX()) * 
		        collisions.getPixelAngle() + 
		        Collisions.degreeToRadian(collisions.getLowestAngle()) )) ; //Determine the angle
		        
		        ball.setSpeedY( -(Math.sin(collisions.getAngle())) ); //Set Y speed according to the sin of the angle
		        ball.setSpeedX(Math.cos(collisions.getAngle())); //Set X speed according to the cos of the angle
		        sounds.playSound("boing");
			}
			else if(collisions.isBrickCollide(ball)) //If the ball hits a brick
			{
	            ball.setCurrentY(ball.getPreviousY());
	            ball.setCurrentX(ball.getCurrentX());
	                      
	            ball.setSpeedY(-ball.getSpeedY());
	            sounds.playSound("hit");
	            
	            if(ballGenerator.isToGenerateNewBall(NUM_OF_BRICKS - Brick.getDestroyedCount())) //Current Destroyed Brick
	            {
	            	fireNewBall = true;
	            }
	            
			}
			
		    ball.setCurrentY((float) (ball.getCurrentY() + ball.getSpeed() * ball.getSpeedY() * this.yUnit ) ); //Move in Y
		    ball.setCurrentX((float) (ball.getCurrentX() + ball.getSpeed() * ball.getSpeedX() * this.xUnit ) ); //Move in X 
		
		    canvas.drawBitmap(ball.getImg(), ball.getCurrentX(), ball.getCurrentY(), null);
		
		}
		
		if(fireNewBall)
		{
			addNewBall();
			fireNewBall = false;
		}
		
		if(ballToBeRemoved != null)
		{
			ballsList.remove(ballToBeRemoved);
			ballToBeRemoved = null;
		}

	    canvas.drawText("Left: "+String.valueOf(Brick.getDestroyedCount()), 10, 20, mPaint);
	    canvas.drawText("Time: "+String.valueOf((int)GameLoopThread.getElpasedTime()/1000), getWidth() - 100, 20, mPaint);
	    canvas.drawBitmap(paddle.getImage(), paddle.getX(), paddle.getY(), null);
		Brick.drawBricks(canvas, bricks);
		
	}
	
	public void initGame()
	{
		Canvas c = holder.lockCanvas();
		this.xUnit = (float)getWidth() / 300.0F;
		this.yUnit =  (float)getHeight() / 300.0F;
		bricks = Brick.initBricks(this);
		paddle = new Paddle(this);
		ballsList = new ArrayList<Ball>();
		ballsList.add(new Ball(this));
		ballGenerator = new BallGenerator();
		collisions = new Collisions(paddle, bricks, this);
		onDraw(c);
		holder.unlockCanvasAndPost(c);
		gameLoopThread.setRunning(true);
		gameLoopThread.start();
	}
	
	public void addNewBall()
	{
		float[] b = Collisions.getLastDestroyedInfo();
		Ball ball = new Ball(this);
		ball.setCurrentX( ( b[0] + b[2] / 2 ) );
		ball.setCurrentY( ( b[1] + b[3] / 2 ) + b[3] / 5 );
		ball.setSpeedX(-.5);
		ball.setSpeedY(.5);
		
		ballsList.add(ball);
	}
	
	
	public int getDifficultyLevel()
	{
		return this.diffcultyLevel;
	}
	
	protected void killGameThread()
	{
        gameLoopThread.setRunning(false);
	}
	
	private void gameOver()
	{
		killGameThread();
		sounds.playSound("gameover");
		gameActivity.runOnUiThread(new Runnable() {
			
			public void run() {
				gameActivity.showDialog(GameActivity.GAME_OVER);
			}
		});
	}
	
	private void finishGame()
	{
		gameActivity.runOnUiThread(new Runnable() {
			
			public void run() {
				sounds.playSound("win");
				gameActivity.showDialog(GameActivity.WIN_DIALOG);
			}
		});
		killGameThread();
	}
	
	private void pauseGame()
	{
    	menuPresent = true;
    	gameLoopThread.setPaused(true);
		gameActivity.showDialog(GameActivity.PAUSED_DIALOG);
	}
	
	public void resumeGame()
	{
		menuPresent = false;
		gameLoopThread.setPaused(false);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_MENU && !menuPresent && gameLoopThread.isRunning()) 
	    {
	    	pauseGame();
	    	return true; //Don't let the system handle the press; I've handled it!
	    }
	    else if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	if(menuPresent)
	    	{
	    		resumeGame();
	    		return true;
	    	}
	    	else
	    	{
	    		return false;
	    	}
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	
    public boolean onTouchEvent(MotionEvent event) 
    {
    	if(gameLoopThread.isPaused()) //If game is paused 
    		return false;
        int eventaction = event.getAction();
        
        int X = (int)event.getX();
        int Y = (int)event.getY();

        switch (eventaction ) { 

        case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on the paddle
        	previousX = event.getX(); //Get current x of the touch
            break; 

        case MotionEvent.ACTION_MOVE:   // move the paddle
        	//check if the finger is on the paddle
        	if(X >= paddle.getX() && X <= paddle.getX() + paddle.getWidth() && paddle.getY() <= Y )
        	{
        		if(X > previousX)
        		{
        			paddle.moveRight(X - previousX);
        		}
        		else 
        		{
        			paddle.moveLeft(Math.abs(previousX - X));
        		}
        		previousX = X;
        	}
        	break; 

        case MotionEvent.ACTION_UP: 
             break; 
        } 
        return true;
    }
	
	
}
