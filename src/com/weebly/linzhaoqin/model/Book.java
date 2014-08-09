package com.weebly.linzhaoqin.model;

import java.util.List;

import android.graphics.Bitmap;

public class Book {
	//��Ļ�����Ϣ
	private int id;
	private long ISBN;
	private String title;
	private String author;
	private String publisher;
	private String pubdate;
	private String cover; //����
	private String average;
	private String numRaters;
	private String detailUrl;//����ҳ��URL
	private Bitmap bitmap;
	//��ͼ��ݵ���Ϣ
	
	private String callNum;//�����
	private List<String> localAndHolds;//�ݲص�
	private int holding;//�ܲر�
	private int lending;//�����
	private int remaining;//ʣ����
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public long getISBN() {
		return ISBN;
	}

	public void setISBN(long iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPubdate() {
		return pubdate;
	}

	public void setPubdate(String pubdate) {
		this.pubdate = pubdate;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getNumRaters() {
		return numRaters;
	}

	public void setNumRaters(String numRaters) {
		this.numRaters = numRaters;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public List<String> getLocalAndHolds() {
		return localAndHolds;
	}

	public void setLocalAndHolds(List<String> localAndHolds) {
		this.localAndHolds = localAndHolds;
	}


	public int getHolding() {
		return holding;
	}

	public void setHolding(int holding) {
		this.holding = holding;
	}

	public int getLending() {
		return lending;
	}

	public void setLending(int lending) {
		this.lending = lending;
	}

	public int getRemaining() {
		return remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}


	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	
	
}
