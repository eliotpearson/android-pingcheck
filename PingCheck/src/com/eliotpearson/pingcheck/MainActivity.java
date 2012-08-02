package com.eliotpearson.pingcheck;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.eliotpearson.pingcheck.data.PingCheck;
import com.eliotpearson.pingcheck.data.PingCheckDao;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private PingCheckDao dao;
	private ArrayAdapter<PingCheck> adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        dao = new PingCheckDao(this);
        dao.open();
        
        ListView view = (ListView)findViewById(R.id.list);
        
        view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				PingCheck pingCheck = (PingCheck)(parent.getAdapter().getItem(position));
				
				Log.d("onItemClick()", "id = " + pingCheck.getId());
				
				Intent deleteIntent = new Intent(view.getContext(),DeleteActivity.class);
				deleteIntent.putExtra("id", new Long(pingCheck.getId()));
				view.getContext().startActivity(deleteIntent);
			}
		});
        
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(null);
    }
    
    private class DownloadWebPageTask extends AsyncTask<String, Void, List<PingCheck>> {

		@Override
		protected List<PingCheck> doInBackground(String... arg0) {
			
			StringBuffer sb = new StringBuffer();
			
			List<PingCheck> values = dao.getAllPingChecks();
			
			DefaultHttpClient client = new DefaultHttpClient();
			
			for(PingCheck pingCheck : values) {
			
				HttpGet httpGet = new HttpGet(pingCheck.getUrl());
				
				try {
					pingCheck.setStart(System.currentTimeMillis());
					
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();
					
					BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
					String s = "";
					
					while ((s = buffer.readLine()) != null) {
						sb.append(s);
					}
					
					pingCheck.setEnd(System.currentTimeMillis());
					
					Log.d("DowloadWebPageTask", pingCheck.getUrl() + " - " + sb.length() + " byte(s)");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			return values;
		}
		
		protected void onPostExecute(List<PingCheck> result) {
			ListView view = (ListView)findViewById(R.id.list);
			adapter = new ArrayAdapter<PingCheck>
			(view.getContext(), android.R.layout.simple_list_item_1, result);
			
	        view.setAdapter(adapter);
		}
    	
    }
    
	public void onClick(View view) {
    	switch(view.getId()) {
    	case R.id.add:
    		Intent pingCheckIntent = new Intent(this,PingCheckActivity.class);
    		this.startActivity(pingCheckIntent);
    		
    		break;
    	case R.id.reload:
    		Intent intent = getIntent();
    		finish();
    		startActivity(intent);
    		break;
    	}
    	
    	adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void onResume() {
    	dao.open();
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	dao.close();
    	super.onPause();
    }
    
}