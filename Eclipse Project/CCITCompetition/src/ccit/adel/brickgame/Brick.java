package ccit.adel.brickgame;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Brick {
	public static final int gap = 2;
	public static final int xBricks = 8;
	public static final int yBricks = 7;
	public static final int startXOffset = 0;
	public static final int startYOffset = 30;
	private static int destroyedCount = 56;
	
	
	private float x;
	private float y;
	private int height;
	private int width;
	private Bitmap image;
	
	public Brick(float x, float y, int id, GameView view)
	{
		this.x = x;
		this.y = y;
		
    	image = BitmapFactory.decodeResource(view.getResources(), 
    			view.getResources().getIdentifier("bricks_"+(id+1), "drawable",  view.getContext().getPackageName() ));
    	image = Bitmap.createScaledBitmap( image, ( view.getWidth() - 16) / xBricks   , view.getHeight() / 22 , false);
		
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public static List<Brick> initBricks(GameView v)
	{
		List<Brick> bricks = new ArrayList<Brick>();
		int offsetY = Brick.startYOffset;
		int offsetX;
		int current = 0;
		for(int y = 1; y <= Brick.yBricks; y++)
		{
			offsetX = Brick.startXOffset;
			for(int x = 1; x <= Brick.xBricks; x++)
			{
				Brick brick = new Brick(offsetX, offsetY, current, v);
				bricks.add(brick);
				//canvas.drawBitmap(bricks[current].getImage(), offsetX, offsetY, null);
				offsetX += brick.getWidth() + Brick.gap;
				current++;
			}
			offsetY += (v.getHeight() / 22) + Brick.gap;   
		}
		return bricks;
	}
	
	public static void drawBricks(Canvas c, List<Brick> bricks)
	{
		for(Brick brick : bricks)
		{
			c.drawBitmap(brick.getImage(), brick.getX(), brick.getY(), null);
		}
	}
	
	public static void setDestroyedCount(int d)
	{
		destroyedCount = d;
	}
	
	public static int getDestroyedCount()
	{
		return destroyedCount;
	}
	
	public Bitmap getImage()
	{
		return this.image;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
}
