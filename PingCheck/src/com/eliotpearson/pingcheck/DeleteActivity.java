package com.eliotpearson.pingcheck;

import com.eliotpearson.pingcheck.data.PingCheck;
import com.eliotpearson.pingcheck.data.PingCheckDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DeleteActivity extends Activity {
	private PingCheckDao dao;
	private long id;
	private PingCheck pingCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
		
		dao = new PingCheckDao(this);
        dao.open();
		
		Bundle extras = getIntent().getExtras();
		id = extras.getLong("id");

		Log.i("info", "id = " + id);
		
		pingCheck = dao.getPingCheckById(id);
		
		TextView textView1 = (TextView)findViewById(R.id.textView1);
		StringBuffer buffer = new StringBuffer(); 
		
		buffer.append("Are you sure you would like to delete,\"");
		buffer.append(pingCheck.getUrl());
		buffer.append("\"?");
		
		textView1.setText(buffer);
	}
	
	public void onClick(View view) {
    	
    	switch(view.getId()) {
    	case R.id.delete:
    		dao.deletePingCheck(pingCheck);
    		
    		Intent mainIntent = new Intent(this,MainActivity.class);
    		this.startActivity(mainIntent);
    		
    		break;
    	}
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
