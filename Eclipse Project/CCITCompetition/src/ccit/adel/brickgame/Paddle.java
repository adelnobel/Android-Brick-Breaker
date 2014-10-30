package ccit.adel.brickgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Paddle {
	private float x;
	private float y;
	private int width;
	private int height;
	private float speed = 5;
	private Bitmap img;
	private GameView view;
	
	public Paddle(GameView v)
	{
		this.view = v;
		this.img = BitmapFactory.decodeResource(this.view.getResources() ,
				this.view.getContext().getResources().getIdentifier("paddle", "drawable", this.view.getContext().getPackageName()));
		this.img = Bitmap.createScaledBitmap(this.img, this.view.getWidth() / 3, this.view.getHeight() / 25 , false);
		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
		this.x = (this.view.getWidth() / 2) - (this.width / 2) - 5;
		this.y = this.view.getHeight() -  this.view.getHeight() / 12;
	}
	
	public void moveRight(float move)
	{
	    if(this.x + this.width + move < view.getWidth())
	        this.x += move;
	    else
	    	this.x = view.getWidth() - this.width;
	}
	
	public void moveLeft(float move)
	{
	    if(this.x - move > 0)
	        this.x -= move;
	    else
	    	this.x = 0;
	}
	
	public Bitmap getImage()
	{
		return this.img;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}

}
