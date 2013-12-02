package com.example.wmproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.wmproject.MainActivity.login;
import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class EventListActivity extends Activity {
	private String user_id="-1";
	private JSONParser parser=new JSONParser();
	private List<NameValuePair> parameters=new ArrayList<NameValuePair>();
	private static String url_list = "http://plato.cs.virginia.edu/~jy4ny/attendance/events/api_list";
	private ArrayList<Events> evts= new ArrayList<Events>();
	GridView gridView;
	Context currentContext;
	Button todostart;
	Button voteliststart;
	//private TextView event1;
	//private TextView event2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent myIntent= getIntent();
		setContentView(R.layout.activity_event_list);
		gridView = (GridView) findViewById(R.id.attendance_list);
		//gridView.setAdapter(new ImageAdapter(this, gigdog.getStationNames(), gigdog.getStationImageURLs()));
		user_id = myIntent.getStringExtra("user_id");
		//TextView text = (TextView) findViewById(R.id.hello);
		//text.setText(user_id);
		currentContext=this;
		todostart= (Button)this.findViewById(R.id.todostart);
		
		parameters.add(new BasicNameValuePair("user_id",user_id));
		new loadEvents().execute();
		todostart.setOnClickListener(new View.OnClickListener() {           
            public void onClick(View v) {              

                //parameters.add(new BasicNameValuePair("username",user_id));
                //JSONObject json = jsonParser.makeHttpRequest(url_login,"POST", params);
                startTodo();

            }
        });
		voteliststart = (Button) this.findViewById(R.id.voteliststart);
		voteliststart.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {              

                //parameters.add(new BasicNameValuePair("username",user_id));
                //JSONObject json = jsonParser.makeHttpRequest(url_login,"POST", params);
                startVoteList();

            }
		});
		
		
	}
	public void startTodo(){
		Intent intent = new Intent(this, TodoActivity.class);
		intent.putExtra("user_id", user_id);
		startActivity(intent);
	}
	public void startVoteList(){
		Intent intent = new Intent(this, VoterListActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}
	public class loadEvents extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			
			try {
				JSONObject jobj = parser.makeHttpRequest(url_list, "GET", parameters);
				//String webJSON = getJSONfromURL(url_login+"?username="+user_name+"&password="+pass_word);
				//Log.d("JSON", webJSON);
				Gson gson = new Gson();
				int numEvent;
				JSONObject jsonEvts;
				numEvent = jobj.getInt("eventFound");
				for (int i = 0; i < numEvent; i++) {
					jsonEvts = (JSONObject) jobj.get("" + i);
					Events temp = new Events();
					temp.setTitle((String) jsonEvts.get("title"));
					temp.setDescription((String) jsonEvts.get("description"));
					temp.setId((String) jsonEvts.get("id"));
					evts.add(temp);
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

		@Override
		protected void onPostExecute(String result) {
			ArrayList <String> evtnames= new ArrayList<String>();
			for (int i=0; i<evts.size();i++){
				evtnames.add(evts.get(i).getTitle());
			}
			gridView.setAdapter(new ImageAdapter(currentContext, evtnames, evtnames));
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
