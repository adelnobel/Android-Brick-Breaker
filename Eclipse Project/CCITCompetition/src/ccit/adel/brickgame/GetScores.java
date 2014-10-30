package ccit.adel.brickgame;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public class GetScores extends AsyncTask<Void, Void, Integer> {

	private HighscoresActivity scoresActivity;
	private JsonClass jsonClass;
	private List<String[]> resultsList = new ArrayList<String[]>();
	private boolean failed = false;

	public GetScores(HighscoresActivity sa) {
		this.scoresActivity = sa;
	}

	protected Integer doInBackground(Void... params) {
		try {
			jsonClass = new JsonClass();
			resultsList = jsonClass.getScores();
		} catch (Exception ex) {
			failed = true;
		}
		;

		return 1;

	}

	protected void onPostExecute(Integer result) {
		scoresActivity.populateScores(resultsList, failed);
	}

}
