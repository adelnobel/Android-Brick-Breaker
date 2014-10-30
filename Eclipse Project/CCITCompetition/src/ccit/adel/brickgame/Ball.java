package ccit.adel.brickgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Ball {
	
	private float currentX;
	private float currentY;
	private float previousX = 0;
	private float previousY = 0;
	private int width;
	private int height;
	private double radius;
	private double speed = 5.5;
	private double speedX = 0.0;
	private double speedY = 1.0;
	private GameView gameView;
	private Bitmap img;
	private static final int EASY = 0;
	private static final int NORMAL = 1;
	private static final int HARD = 2;
	
	public Ball(GameView v)
	{
		this.gameView = v;
		this.img = BitmapFactory.decodeResource(v.getContext().getResources(),
				v.getContext().getResources().getIdentifier("ball", "drawable", v.getContext().getPackageName()));
		this.img = Bitmap.createScaledBitmap(this.img, v.getWidth() / 25, v.getWidth() / 25 , false);
		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
		this.radius = this.width / 2;
		this.currentX = v.getWidth() / 2;
		this.currentY = v.getHeight() / 2;
		this.setSpeed();
	}
	

	public float getCurrentX() {
		return currentX;
	}

	public float getCurrentY() {
		return currentY;
	}

	public float getPreviousX() {
		return previousX;
	}

	public float getPreviousY() {
		return previousY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getRadius() {
		return radius;
	}

	public double getSpeed() 
	{
		return speed;
	}

	public double getSpeedX() {
		return speedX;
	}

	public double getSpeedY() {
		return speedY;
	}

	public Bitmap getImg() {
		return img;
	}

	public void setCurrentX(float currentX) {
		this.currentX = currentX;
	}

	public void setCurrentY(float currentY) {
		this.currentY = currentY;
	}

	public void setPreviousX(float previousX) {
		this.previousX = previousX;
	}

	public void setPreviousY(float previousY) {
		this.previousY = previousY;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	private void setSpeed() 
	{
		switch(gameView.getDifficultyLevel())
		{
			case EASY:
				speed *= 0.75;
			case NORMAL:
				break;
			case HARD:
				speed *= 1.5;
		}
	}

	public void setSpeedX(double speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

}