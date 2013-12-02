package com.example.wmproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.gson.Gson;
 
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class VoterListActivity extends Activity {
	private JSONParser parser=new JSONParser();
	private static String url_list = "http://plato.cs.virginia.edu/~jft4du/cakePHP_VoterPhase5/posts/index.json";
	private List<NameValuePair> parameters=new ArrayList<NameValuePair>();
	TableLayout myTable;
	ArrayList<HashMap<String,String>> issues;
	
	Context currentContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voter_list);
		//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
		currentContext=this;
		myTable = (TableLayout) findViewById(R.id.votetable);
		TableRow tr_head = new TableRow(currentContext);
		
		
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.GRAY);
		tr_head.setLayoutParams(new LayoutParams(
		LayoutParams.FILL_PARENT,
		LayoutParams.WRAP_CONTENT));
		TextView label_date = new TextView(currentContext);
         label_date.setId(20);
         label_date.setText("Voter Issue");
         label_date.setTextColor(Color.WHITE);
         label_date.setPadding(5, 5, 5, 5);
         tr_head.addView(label_date);// add the column to the table row here

        TextView yes_header = new TextView(currentContext);
         yes_header.setId(21);// define id that must be unique
         yes_header.setText("Yes"); // set the text for the header 
         yes_header.setTextColor(Color.WHITE); // set the color
         yes_header.setPadding(5, 5, 5, 5); // set the padding (if required)
         tr_head.addView(yes_header); // add the column to the table row here
         
         TextView no_header = new TextView(currentContext);
         no_header.setId(21);// define id that must be unique
         no_header.setText("No"); // set the text for the header 
         no_header.setTextColor(Color.WHITE); // set the color
         no_header.setPadding(5, 5, 5, 5); // set the padding (if required)
         tr_head.addView(no_header); // add the column to the table row here
        myTable.addView(tr_head);
        new loadVoteList().execute();
        final Button newIssue = (Button) findViewById(R.id.newIssueButton);
        newIssue.setOnClickListener(new View.OnClickListener() {           
            public void onClick(View v) {              

                //parameters.add(new BasicNameValuePair("username",user_id));
                //JSONObject json = jsonParser.makeHttpRequest(url_login,"POST", params);
                startNewIssue();

            }
        });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.voter_list, menu);
		return true;
	}
	public void startNewIssue(){
		Intent intent = new Intent(this, VoteCreateActivity.class);
		startActivity(intent);
	}
	public class loadVoteList extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			
			try {
				JSONObject jobj = parser.makeHttpRequest(url_list, "GET", parameters);
				//String webJSON = getJSONfromURL(url_login+"?username="+user_name+"&password="+pass_word);
				//Log.d("JSON", webJSON);
				issues = new ArrayList<HashMap<String,String>>();
				Iterator<String> itr = jobj.keys();
				
				
				while (itr.hasNext()){
					String key = itr.next();
					HashMap<String,String> oneIssue = new HashMap<String,String>();
					JSONObject currentIssue = jobj.getJSONObject(key);
					oneIssue.put("body", currentIssue.getString("body"));
					oneIssue.put("yes", currentIssue.getString("yes"));
					oneIssue.put("no", currentIssue.getString("no"));
					oneIssue.put("name",currentIssue.getString("name"));
					
					issues.add(oneIssue);
				}
				

				//JsonParser parser = new JsonParser();
				//JsonArray Jarray = parser.parse(webJSON).getAsJsonArray();

				//for (JsonElement obj : Jarray) {
				//	Events evt = gson.fromJson(obj, Events.class);
				//	Log.d("EVENTS", evt.toString());
				//	levt.add(evt);
				//}

			} catch (Exception e) {
				Log.e("Login", "JSONPARSE:" + e.toString());
			}

			//values.clear();
			//values.addAll(lcs);

			return "Done!";
		}

		@Override
		protected void onProgressUpdate(Integer... ints) {

		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			for (HashMap<String,String> issue : issues){
				TableRow tr_head = new TableRow(currentContext);
				
				
				tr_head.setId(10);
				tr_head.setBackgroundColor(Color.WHITE);
				tr_head.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
				TextView label_date = new TextView(currentContext);
		         label_date.setId(20);
		         label_date.setText(issue.get("body"));
		         label_date.setTextColor(Color.BLACK);
		         label_date.setPadding(5, 5, 5, 5);
		         tr_head.addView(label_date);// add the column to the table row here

		        TextView yes_header = new TextView(currentContext);
		         yes_header.setId(21);// define id that must be unique
		         yes_header.setText(issue.get("yes")); // set the text for the header 
		         yes_header.setTextColor(Color.BLACK); // set the color
		         yes_header.setPadding(5, 5, 5, 5); // set the padding (if required)
		         tr_head.addView(yes_header); // add the column to the table row here
		         
		         TextView no_header = new TextView(currentContext);
		         no_header.setId(21);// define id that must be unique
		         no_header.setText(issue.get("no")); // set the text for the header 
		         no_header.setTextColor(Color.BLACK); // set the color
		         no_header.setPadding(5, 5, 5, 5); // set the padding (if required)
		         tr_head.addView(no_header); // add the column to the table row here
		        myTable.addView(tr_head);
			}
//			ArrayList <String> = new ArrayList<String>();
//			for (int i=0; i<evts.size();i++){
//				evtnames.add(evts.get(i).getTitle());
//			}
//			gridView.setAdapter(new ImageAdapter(currentContext, evtnames, evtnames));
			// tells the adapter that the underlying data has changed and it
			// needs to update the view
			//adapter.notifyDataSetChanged();
//			event1 = (TextView) findViewById(R.id.textView1);
//			event2 = (TextView) findViewById(R.id.textView2);
//			event1.setText("Title: " + evts.get(0).getTitle() + ", Description: " + evts.get(0).getDescription() + ", Event ID: " + evts.get(0).getId());
//			event2.setText("Title: " + evts.get(1).getTitle() + ", Description: " + evts.get(1).getDescription() + ", Event ID: " + evts.get(1).getId());
		}
	}
	
}
