package com.weebly.linzhaoqin.booksearch;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.weebly.linzhaoqin.R;
import com.weebly.linzhaoqin.model.Book;
import com.weebly.linzhaoqin.model.BookAdapter;

public class SearchResultActivity extends ListActivity{

	ProgressBar pb;
	List<Book> bookList;
	String uri;
	String s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);

		Intent intent = getIntent();
		String search = intent.getStringExtra("search");
		Log.d("INTENT", "uri has passed");
		try {
			s = URLEncoder.encode(search, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		uri = "https://api.douban.com/v2/book/search?q="+s+"&fields=title,author,publisher,pubdate,image,rating";
		requestAndDisplayData(uri);
		Log.d("DATA", "requestdata called");

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
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

	//获得数据
	private void requestAndDisplayData(String uri) {
		NetworkTask task = new NetworkTask();
		task.execute(uri);
	}

	//后台连网获取数据
	private class NetworkTask extends AsyncTask<String, String, List<Book>> {

		@Override
		protected void onPreExecute() {
			pb.setVisibility(View.VISIBLE);
		}


		@Override
		protected List<Book> doInBackground(String... params) {
			String content = HttpManagerDouban.getData(params[0]);
			bookList = HttpManagerDouban.bookJSONParser(content);
			
			return bookList;
		}

		@Override
		protected void onPostExecute(List<Book> result) {
			bookList = result;
			//展示数据
			BookAdapter adapter = new BookAdapter(SearchResultActivity.this, R.layout.item_book, bookList);
			setListAdapter(adapter);
			Log.d("DATA", "adapter seted");
			pb.setVisibility(View.INVISIBLE);
		}
	}
	
}
