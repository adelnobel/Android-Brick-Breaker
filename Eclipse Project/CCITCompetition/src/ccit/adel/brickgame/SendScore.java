package ccit.adel.brickgame;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class SendScore extends AsyncTask<Void, Void, Integer> {

	private boolean fetchFailed = false;
	public static String scoresURL = "http://shabmasry.com/ccitgame/scores.php";
	private String url = "http://shabmasry.com/ccitgame/";
	private String name;
	private String score;
	private GameActivity mainActivity;
	private EncryptScore e;

	public SendScore(GameActivity a) {
		this.mainActivity = a;
	}

	public void setUrl(String name, int score) throws Exception {
		e = new EncryptScore();
		String sc = e.encrypt(String.valueOf(score));
		this.score = sc;
		this.name = name.replaceAll("[^\\x{0600}-\\x{06FF}a-zA-Z0-9 ]", "");
	}

	public void uploadScore() throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("name", java.net.URLEncoder
				.encode(name, "UTF-8")));
		nameValuePairs.add(new BasicNameValuePair("score", score));
		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		httpclient.execute(httpPost);
		// HttpResponse response = httpclient.execute(httpPost);
		/**
		 * HttpEntity en = response.getEntity(); InputStreamReader b = new
		 * InputStreamReader(en.getContent()); BufferedReader c = new
		 * BufferedReader(b); String line = ""; while( (line = c.readLine()) !=
		 * null ) { //Log.d("HTTP", line); }
		 **/
	}

	protected Integer doInBackground(Void... params) {
		try {
			uploadScore();
		} catch (Exception e) {
			fetchFailed = true;
		}
		;
		return 1;
	}

	protected void onPostExecute(Integer result) {
		mainActivity.removeDialog(GameActivity.LOADING_DIALOG);
		if (fetchFailed)
			mainActivity.submitDone(true);
		else
			mainActivity.submitDone(false);
	}

}
