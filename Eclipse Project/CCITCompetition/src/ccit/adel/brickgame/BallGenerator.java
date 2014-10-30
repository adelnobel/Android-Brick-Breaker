package ccit.adel.brickgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import android.util.Log;

public class BallGenerator {
	private int numOfBallsToGenerate;
	private List<Integer> ballSpawnerBrick;
	private Random generator;

	public BallGenerator() {
		generator = new Random();
		numOfBallsToGenerate = generator.nextInt(4) + 1; // Between 1-4
		initBallSpawnerList();
	}

	private void initBallSpawnerList() {
		ballSpawnerBrick = new ArrayList<Integer>();
		ballSpawnerBrick.add(2);
		ballSpawnerBrick.add(10);
		for (int x = 1; x <= numOfBallsToGenerate; x++) {
			ballSpawnerBrick.add(generator.nextInt(55) + 1); // Between 1 and 56
		}

		/**
		 * String ballsPlaces = ""; for(int x : ballSpawnerBrick) { ballsPlaces
		 * += ", " + x; } Log.d("adel", "ballsPlaces: " + ballsPlaces);
		 * Log.d("adel", "numOfBalls: " + String.valueOf(numOfBallsToGenerate));
		 **/
	}

	public boolean isToGenerateNewBall(int brickNum) {
		if (ballSpawnerBrick.contains(brickNum)) {
			return true;
		}
		return false;
	}

}
