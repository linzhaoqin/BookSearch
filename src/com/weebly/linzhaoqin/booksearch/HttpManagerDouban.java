package com.weebly.linzhaoqin.booksearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.weebly.linzhaoqin.model.Book;

public class HttpManagerDouban {

	public static String getData(String uri) {
		
		BufferedReader reader = null;
		
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			StringBuilder sb = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}
	
	public static List<Book> bookJSONParser(String content) {
		
		final String TAG_TITLE = "title";
		final String TAG_AUTHOR = "author";
		final String TAG_PUBLISHER = "publisher";
		final String TAG_PUBDATE = "pubdate";
		final String TAG_RATING = "rating";
		final String TAG_IMAGE = "image";
		final String TAG_AVERAGE = "average";
		final String TAG_NUMRATERS = "numRaters";
		
		try{
			List<Book> bookList = new ArrayList<>();
			JSONObject mainObj = new JSONObject(content);
			JSONArray bookAr = mainObj.getJSONArray("books");
			for (int i = 0; i < bookAr.length(); i++) {
				JSONObject bookObj = bookAr.getJSONObject(i);
				Book book = new Book();
				//书名
				book.setTitle(bookObj.getString(TAG_TITLE));
				//作者
				JSONArray authorAr = bookObj.getJSONArray(TAG_AUTHOR);
				String author = "";
				for (int j = 0; j < authorAr.length(); j++) {
					StringBuilder sb = new StringBuilder();
					author = sb.append(authorAr.get(j)).append(", ").toString();
				}
				if (author.matches("")) {
					book.setAuthor("暂无作者信息");
				} else {
					author = author.substring(0,author.length()-2);//删掉最后的空格和逗号
					book.setAuthor(author);
				}
				//出版社
				if (bookObj.getString(TAG_PUBLISHER).matches("")) {
					book.setPublisher("暂无出版社信息");
				} else {
					book.setPublisher(bookObj.getString(TAG_PUBLISHER));
				}
				//出版日期
				book.setPubdate(bookObj.getString(TAG_PUBDATE));
				//封面图（中号）
				book.setCover(bookObj.getString(TAG_IMAGE));
				//评分
				JSONObject rateObj = bookObj.getJSONObject(TAG_RATING);
				if (rateObj.getString(TAG_AVERAGE).matches("0.0") && rateObj.getString(TAG_NUMRATERS).matches("0")) {
					book.setAverage("暂无人评分");//处理无人评分的情况
				} else if (Integer.parseInt(rateObj.getString(TAG_NUMRATERS)) < 10) {
					book.setAverage("少于10人评分");//少于10人评分时，豆瓣不返回评分值
				} else {
					book.setAverage(rateObj.getString(TAG_AVERAGE));
					book.setNumRaters(rateObj.getString(TAG_NUMRATERS));
				}
				
				bookList.add(book);
			}
			return bookList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
