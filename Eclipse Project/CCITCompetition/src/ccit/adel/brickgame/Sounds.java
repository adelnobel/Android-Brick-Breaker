package ccit.adel.brickgame;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class Sounds {

	private Map<String, MediaPlayer> sounds = new HashMap<String, MediaPlayer>();
	private Context context;
	private MediaPlayer mp;

	public Sounds(Context context) {
		this.context = context;
		loadSounds();

		AudioManager amanager = (AudioManager) this.context
				.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = amanager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
		amanager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);
	}

	public void loadSounds() {
		sounds.put("boing", MediaPlayer.create(context, R.raw.boing));
		sounds.put("hit", MediaPlayer.create(context, R.raw.hit));
		sounds.put("win", MediaPlayer.create(context, R.raw.win));
		sounds.put("bound", MediaPlayer.create(context, R.raw.bound));
		sounds.put("gameover", MediaPlayer.create(context, R.raw.gameover));
		sounds.put("win", MediaPlayer.create(context, R.raw.win));
	}

	public void playSound(String name) {
		mp = (MediaPlayer) sounds.get(name);
		mp.start();
	}

}
