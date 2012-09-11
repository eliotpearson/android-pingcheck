package com.eliotpearson.pingcheck;

import com.eliotpearson.pingcheck.data.PingCheckDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CreateActivity extends Activity {
	private PingCheckDao dao;
	private TextView editTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.input);
		
		editTextView = (TextView)findViewById(R.id.edit1);
		
		dao = new PingCheckDao(this);
        dao.open();
        
	}
	
	
	public void onClick(View view) {
    	switch(view.getId()) {
    	case R.id.add:
    		String url = (String) editTextView.getText().toString();
    		
    		dao.createPingCheck(url);
    		
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
