package com.weebly.linzhaoqin.model;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.weebly.linzhaoqin.R;

public class BookAdapter extends ArrayAdapter<Book> {

	private Context context;
	private List<Book> bookList;

	public BookAdapter(Context context, int resource, List<Book> objects){
		super(context, resource, objects);
		this.context = context;
		this.bookList = objects;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_book, parent, false);

		//��������
		Book book = bookList.get(position);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(book.getTitle());
		//��������
		TextView author = (TextView) view.findViewById(R.id.author);
		author.setText(book.getAuthor());
		//������ͳ�������
		TextView publisherAndPubdate = (TextView) view.findViewById(R.id.publisher_and_pubdate);
		publisherAndPubdate.setText(book.getPublisher()+" "+book.getPubdate());
		//����
		TextView rating = (TextView) view.findViewById(R.id.rating);
		if (book.getAverage().matches("����������") || book.getAverage().matches("����10������")) {
			rating.setText(book.getAverage());
		} else {
			rating.setText(book.getAverage()+"��"+"����"+book.getNumRaters()+"�����֣�");
		}
		
		//����ͼ���кţ�
		if (book.getBitmap() != null) {
			ImageView cover = (ImageView) view.findViewById(R.id.cover);
			cover.setImageBitmap(book.getBitmap());
		} else {
			BookAndCover container = new BookAndCover();
			container.book = book;
			container.view = view;
			ImageLoader loader = new ImageLoader();
			loader.execute(container);
		}


		return view;
	}

	class BookAndCover {
		public Book book;
		public View view;
		public Bitmap bitmap;
	}

	public class ImageLoader extends AsyncTask<BookAndCover, Void, BookAndCover>{

		@Override
		protected BookAndCover doInBackground(BookAndCover... params) {
			BookAndCover container = params[0];
//			Book book = container.book;
			try{
				String imageUrl = container.book.getCover();
				InputStream in = (InputStream) new URL(imageUrl).getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(in);
//				container.book.setBitmap(bitmap);
				in.close();
				container.bitmap = bitmap;
				return container;
			} catch (Exception e){
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(BookAndCover result) {

			ImageView cover = (ImageView) result.view.findViewById(R.id.cover);
			cover.setImageBitmap(result.bitmap);
			result.book.setBitmap(result.bitmap);//��ͼƬ���浽book��
		}

	}



}
