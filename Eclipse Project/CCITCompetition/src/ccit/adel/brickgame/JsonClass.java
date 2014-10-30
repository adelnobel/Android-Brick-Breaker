package ccit.adel.brickgame;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;


public class JsonClass {
	
	//HighscoresActivity highScoresAcitivity;
	boolean fetchFailed = false;
	
	public JsonClass()
	{
		//this.highScoresAcitivity = hs;
		//Get the data (see above)
	}
	
	public List<String[]> getScores() throws Exception
	{
		JSONObject json = getJSONfromURL(SendScore.scoresURL);
		JSONArray resultsJSON = json.getJSONArray("results");
		
		List<String[]> resultsList = new ArrayList<String[]>();
		
		for(int x = 0; x < resultsJSON.length(); x++ )
		{
			String[] singleRow = new String[2];
			singleRow[0] = resultsJSON.getJSONObject(x).getString("name");
			singleRow[1] = resultsJSON.getJSONObject(x).getString("time");
			resultsList.add(singleRow);
		}
		
		return resultsList;
		
	}
	
	
	public JSONObject getJSONfromURL(String url) throws Exception
	{
		//initialize
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		//http post
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		//convert response to string
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();

		//try parse the string to a JSON object
	        	jArray = new JSONObject(result);

		return jArray;
	}

}
