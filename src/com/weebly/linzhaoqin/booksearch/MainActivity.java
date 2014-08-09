package com.weebly.linzhaoqin.booksearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.weebly.linzhaoqin.R;

public class MainActivity extends Activity {

	//	public final static String URI = "uri";
	TextView output;
	TextView searchBar;
	Button searchButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//启动即检查网络，并弹出提示
		if (isOnline() == false ) {
			Toast.makeText(this, R.string.network_unavailable, Toast.LENGTH_LONG).show();
		}

		searchBar = (TextView) findViewById(R.id.searchBar);

		searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {

			//点击searchButton后
			@Override
			public void onClick(View v) {
				if (searchBar.getText().toString().matches("")) {
					Toast.makeText(MainActivity.this, "你好像什么都没填...", Toast.LENGTH_LONG).show();
				} else if (isOnline() == true) {
					String search = searchBar.getText().toString();
					Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
					intent.putExtra("search", search);
					Log.d("INTENT", "ready to put startActivity");
					startActivity(intent);
				} else {
					Toast.makeText(MainActivity.this, R.string.network_unavailable, Toast.LENGTH_LONG).show();
				}

			}
		});
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	//action bar按钮点击
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return false;
	}

	//检查网络状态
	protected boolean isOnline(){
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

}

