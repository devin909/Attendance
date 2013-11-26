package com.example.wmproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.wmproject.EventListActivity.loadEvents;
import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.GridView;

public class TodoActivity extends Activity {
	private JSONParser parser=new JSONParser();
	private List<NameValuePair> parameters=new ArrayList<NameValuePair>();
	private ArrayList<Events> evts= new ArrayList<Events>();
	GridView gridView;
	Context currentContext;
	Button todostart;
	String useful;
	private static String url_list_view1 = "http://devintodolist.appspot.com/todo/";
	private static String url_list_view2 = "/view";
	private static String url_list_add1 = "http://devintodolist.appspot.com/todo/";
	private static String url_list_add2 = "/add";
	String user_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		Intent myIntent= getIntent();
		gridView = (GridView) findViewById(R.id.todolist);
		user_id = myIntent.getStringExtra("user_id");
		currentContext=this;
		new loadTodos().execute();
		//parameters.add(new BasicNameValuePair("user_id",user_id));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}
	private class loadTodos extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			
			try {
				JSONObject jobj = parser.makeHttpRequest(url_list_view1+"1"+url_list_view2, "GET", parameters);
				//String webJSON = getJSONfromURL(url_login+"?username="+user_name+"&password="+pass_word);
				//Log.d("JSON", webJSON);
				Gson gson = new Gson();
				int numEvent;
				JSONObject jsonEvts;
				useful = (String) jobj.get("todoid");
				useful= useful+ " " +(String) jobj.get("title") + " "+ (String) jobj.get("description");
				
//				numEvent = jobj.getInt("eventFound");
//				for (int i = 0; i < numEvent; i++) {
//					jsonEvts = (JSONObject) jobj.get("" + i);
//					Events temp = new Events();
//					temp.setTitle((String) jsonEvts.get("title"));
//					temp.setDescription((String) jsonEvts.get("description"));
//					temp.setId((String) jsonEvts.get("id"));
//					evts.add(temp);
//				}
				

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
			evtnames.add(useful);
//			for (int i=0; i<evts.size();i++){
//				evtnames.add(evts.get(i).getTitle());
//			}
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
