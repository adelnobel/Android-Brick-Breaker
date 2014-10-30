package ccit.adel.brickgame;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class CCITCompetitionActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // Hiding title
		setContentView(R.layout.main);

		initAnimations(); // Initialize Animations

		ImageView imageView = (ImageView) findViewById(R.id.play);
		imageView.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				// showDialog(SCORE_DIALOG);
				Intent game = new Intent(CCITCompetitionActivity.this,
						GameActivity.class);
				CCITCompetitionActivity.this.startActivity(game);
			}
		});

		ImageView imageView2 = (ImageView) findViewById(R.id.high);
		imageView2.setOnClickListener(new ImageView.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent highscores = new Intent(CCITCompetitionActivity.this,
						HighscoresActivity.class);
				CCITCompetitionActivity.this.startActivity(highscores);
			}
		});

	}

	public void onResume() {
		super.onResume();
		initAnimations();
	}

	public void onStop() {
		super.onStop();
		stopAnimations();
	}

	private void initAnimations() {
		ArrayList<String> adel = new ArrayList<String>();
		adel.add("logo");
		adel.add("adel");
		adel.add("play");
		adel.add("high");
		for (String img : adel) {
			ImageView logoIV = (ImageView) findViewById(getResources()
					.getIdentifier(img, "id", this.getPackageName()));
			Animation logoMoveAnimation = AnimationUtils.loadAnimation(
					this,
					getResources().getIdentifier(img, "anim",
							this.getPackageName()));
			logoIV.startAnimation(logoMoveAnimation);
		}
	}

	private void stopAnimations() {
		ArrayList<String> adel = new ArrayList<String>();
		adel.add("logo");
		adel.add("adel");
		adel.add("play");
		adel.add("high");
		for (String img : adel) {
			ImageView logoIV = (ImageView) findViewById(getResources()
					.getIdentifier(img, "id", this.getPackageName()));
			logoIV.clearAnimation();
		}
	}

}