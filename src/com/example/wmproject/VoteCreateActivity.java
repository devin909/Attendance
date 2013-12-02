package com.example.wmproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

public class VoteCreateActivity extends Activity {
	String create_url = "http://plato.cs.virginia.edu/~smh6gb/hw4/question/new?q=";
	private JSONParser parser=new JSONParser();
	private List<NameValuePair> parameters=new ArrayList<NameValuePair>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_create);
		final EditText issue = (EditText) findViewById(R.id.issueText);
		final Button createNew = (Button) findViewById(R.id.issueSubmit);
		
		createNew.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {              
				String theIssue = issue.getText().toString();
				create_url = create_url + theIssue;
				
                //parameters.add(new BasicNameValuePair("username",user_id));
                //JSONObject json = jsonParser.makeHttpRequest(url_login,"POST", params);
                new createNewIssue().execute();

            }
		});
		if (true){
			System.out.println("HEllo");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.vote_create, menu);
		return true;
	}
	
	public void startIssueList (){
		Intent intent = new Intent(this, VoterListActivity.class);
		startActivity(intent);
	}
	public class createNewIssue extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			
			try {
				
				JSONObject jobj = parser.makeHttpRequest(create_url, "POST", parameters);
				
				

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
			startIssueList();
		}
	}	
}
