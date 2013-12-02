package com.example.wmproject;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.R.menu;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText username;
    private EditText password;
    private static String url_login = "http://plato.cs.virginia.edu/~jy4ny/attendance/users/api_login";
    //private ArrayList<Events> values;
    private String user_name;
    private String pass_word;
    private ArrayAdapter<Events> adapter;
    private JSONParser parser=new JSONParser();
    private boolean logSuccess=false;
    private String user_id="-1";
    
    private List<NameValuePair> parameters=new ArrayList<NameValuePair>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        final Button button = (Button) findViewById(R.id.loginbutton);
        button.setOnClickListener(new View.OnClickListener() {           
            public void onClick(View v) {              

                user_name = username.getText().toString();
                pass_word = password.getText().toString();
                parameters.add(new BasicNameValuePair("username",user_name));
                parameters.add(new BasicNameValuePair("password",pass_word));
                //JSONObject json = jsonParser.makeHttpRequest(url_login,"POST", params);
                new login().execute();
                

            }
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	}
	public void startWeb3(){
		Intent intent = new Intent(this, VoterListActivity.class);
		//intent.putExtra("user_id", user_id);
		startActivity(intent);
	}
	public void startEventsList(){
		Intent intent = new Intent(this, EventListActivity.class);
		intent.putExtra("user_id", user_id);
		startActivity(intent);
	}

	// The definition of our task class
	public class login extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... params) {
			
			try {
				JSONObject jobj = parser.makeHttpRequest(url_login, "POST", parameters);
				//String webJSON = getJSONfromURL(url_login+"?username="+user_name+"&password="+pass_word);
				//Log.d("JSON", webJSON);
				Gson gson = new Gson();
				JSONObject login;
				if (((String)jobj.get("status")).equals("SUCCESS")){
					login =(JSONObject) jobj.get("data");
					user_id=(String)login.get("user_id");
					logSuccess=true;
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
			// tells the adapter that the underlying data has changed and it
			// needs to update the view
			//adapter.notifyDataSetChanged();
			if (logSuccess){
            	logSuccess=false;
            	startEventsList();
            }
		}
	}
	
	

}
