package ccit.adel.brickgame;

import java.util.List;

public class Collisions {
	private Paddle paddle;
	private List<Brick> bricks;
	private GameView view;
	private int highestAngle = 165;
	private int lowestAngle = 15;
	private double angle = degreeToRadian(highestAngle - lowestAngle);
	private double pixelAngle;
	private static float lastDestroyedInfo[] = new float[4];

	public Collisions(Paddle p, List<Brick> bricks, GameView v) {
		this.paddle = p;
		this.bricks = bricks;
		this.view = v;
		this.pixelAngle = angle / p.getWidth();
	}

	public static double degreeToRadian(int angle) {
		return Math.PI / 180 * angle;
	}

	public boolean isGameFinished() {
		if (Brick.getDestroyedCount() == 0)
			return true;
		else
			return false;
	}

	public boolean isDead(Ball ball) {
		if (ball.getCurrentY() + ball.getHeight() > paddle.getY()
				+ paddle.getHeight()) {
			return true;
		}
		return false;
	}

	public boolean isTopCollide(Ball ball) {
		if (ball.getCurrentY() <= 0) {
			return true;
		}
		return false;
	}

	public boolean isSideCollide(Ball ball) {
		if (ball.getCurrentX() <= 0
				|| ball.getCurrentX() + ball.getWidth() >= view.getWidth()) {
			return true;
		}
		return false;
	}

	public boolean isPaddleCollide(Ball ball) {
		if ((ball.getCurrentY() + ball.getHeight() >= paddle.getY())
				&& (ball.getCurrentX() >= paddle.getX() && ball.getCurrentX() < paddle
						.getX() + paddle.getWidth())) {
			return true;
		}
		return false;
	}

	public boolean isBrickCollide(Ball ball) {
		for (Brick brick : bricks) {
			// xDistance = Math.abs(ballX - brick.x - brick.width / 2);
			double xDistance = Math.abs(ball.getCurrentX() + ball.getRadius()
					- brick.getX() - brick.getWidth() / 2);
			double yDistance = Math.abs(ball.getCurrentY() + ball.getRadius()
					- brick.getY() - brick.getHeight() / 2);

			double cornerDistance_sq = Math.pow(xDistance - brick.getWidth()
					/ 2, 2)
					+ Math.pow(yDistance - brick.getHeight() / 2, 2);

			if (yDistance > (brick.getHeight() / 2 + ball.getRadius())) {
				continue;
			}

			if (xDistance > (brick.getWidth() / 2 + ball.getRadius())) {
				continue;
			}

			if (xDistance <= brick.getWidth() / 2) {
				brickCollision(brick);
				return true;
			}
			if (yDistance <= brick.getHeight() / 2) {
				brickCollision(brick);
				return true;
			}

			if (cornerDistance_sq <= Math.pow(ball.getRadius(), 2)) {
				brickCollision(brick);
				return true;
			}
		}
		return false;
	}

	public void brickCollision(Brick brick) {
		bricks.remove(brick);
		Brick.setDestroyedCount(Brick.getDestroyedCount() - 1);
		Collisions.lastDestroyedInfo[0] = brick.getX();
		Collisions.lastDestroyedInfo[1] = brick.getY();
		Collisions.lastDestroyedInfo[2] = brick.getWidth();
		Collisions.lastDestroyedInfo[3] = brick.getHeight();
	}

	public static float[] getLastDestroyedInfo() {
		return lastDestroyedInfo;
	}

	public int getHighestAngle() {
		return highestAngle;
	}

	public int getLowestAngle() {
		return lowestAngle;
	}

	public double getAngle() {
		return angle;
	}

	public double getPixelAngle() {
		return pixelAngle;
	}

	public void setHighestAngle(int highestAngle) {
		this.highestAngle = highestAngle;
	}

	public void setLowestAngle(int lowestAngle) {
		this.lowestAngle = lowestAngle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setPixelAngle(double pixelAngle) {
		this.pixelAngle = pixelAngle;
	}

}
