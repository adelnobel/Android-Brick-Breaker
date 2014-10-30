package ccit.adel.brickgame;

import android.graphics.Canvas;

public class GameLoopThread extends Thread {
	static final long FPS = 45;
	private GameView view;
	private boolean running = false;
	private boolean paused = false;
	private static long elpasedTime;
	private long threadStartTime;
	private long pausedTime;

	public static long getElpasedTime() {
		return elpasedTime;
	}

	public GameLoopThread(GameView view) {
		this.view = view;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean run) {
		running = run;
	}

	public void setPaused(boolean p) {
		paused = p;
	}

	public boolean isPaused() {
		return paused;
	}

	@Override
	public void run() {
		long ticksPS = 1000 / FPS;
		threadStartTime = System.currentTimeMillis();
		while (running) {
			if (!paused) {
				Canvas c = null;
				try {
					c = view.getHolder().lockCanvas();
					synchronized (view.getHolder()) {
						view.onDraw(c);
					}
				} finally {
					if (c != null) {
						view.getHolder().unlockCanvasAndPost(c);
					}
				}
				elpasedTime = System.currentTimeMillis() - pausedTime
						- threadStartTime;
				try {
					sleep(ticksPS);
				} catch (Exception e) {
				}
			} else {
				// pausedTime += System.currentTimeMillis() -
				try {
					sleep(500);
				} catch (Exception e) {
				}
				;
			}
		}
	}
}