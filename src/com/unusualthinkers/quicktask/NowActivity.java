package com.unusualthinkers.quicktask;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NowActivity extends Fragment {
	CustomDigitalClock dc;
	TextView date;
	String wr, rankStr;
	private Now data, data1, data2, data3;
	private NowAdapter adapter;
	private ArrayList<Now> Array_Data;

	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_now, container, false);
		Typeface dctf = Typeface.createFromAsset(getActivity().getAssets(),
				"Roboto-Thin.ttf");
		dc = (CustomDigitalClock) v.findViewById(R.id.time);
		date = (TextView) v.findViewById(R.id.date);
		date.setText(android.text.format.DateFormat.format("yyyy년 MM월 dd일",
				System.currentTimeMillis()).toString());
		dc.setTypeface(dctf);
		Array_Data = new ArrayList<Now>();
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		SharedPreferences pref = getActivity().getSharedPreferences("pref",
				Context.MODE_PRIVATE);
		String rankPref = pref.getString("rank", "데이터 업데이트가 필요합니다.");
		String csNewsPref = pref.getString("csNews", "데이터 업데이트가 필요합니다.");
		String jiNewsPref = pref.getString("jiNews", "데이터 업데이트가 필요합니다.");
		String lt = pref.getString("lt", "데이터 업데이트가 필요합니다.");
		data = new Now("네이버 실시간 검색어", rankPref);
		data1 = new Now("조선닷컴 속보", csNewsPref);
		data2 = new Now("중앙일보 주요기사", jiNewsPref);
		data3 = new Now("데이터 업데이트", lt);
		Array_Data.add(data);
		Array_Data.add(data1);
		Array_Data.add(data2);
		Array_Data.add(data3);
		adapter = new NowAdapter(getActivity(), R.layout.layout_text,
				Array_Data);
		final ListView list = (ListView) v.findViewById(R.id.ni);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == 3) {
					Array_Data.clear();
					SharedPreferences pref = getActivity()
							.getSharedPreferences("pref", Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = pref.edit();
					editor.putString("rank", getRank());
					editor.putString("csNews", getcsNews());
					editor.putString("jiNews", getjiNews());
					editor.putString("lt", lasttime());
					editor.commit();
					String rankPref = pref.getString("rank", getRank());
					String csNewsPref = pref.getString("csNews", getcsNews());
					String jiNewsPref = pref.getString("jiNews", getjiNews());
					String lt = pref.getString("lt", lasttime());
					data = new Now("네이버 실시간 검색어", rankPref);
					data1 = new Now("조선닷컴 속보", csNewsPref);
					data2 = new Now("중앙일보 주요기사", jiNewsPref);
					data3 = new Now("데이터 업데이트", lt);
					Array_Data.add(data);
					Array_Data.add(data1);
					Array_Data.add(data2);
					Array_Data.add(data3);
					adapter.notifyDataSetInvalidated();
					Toast.makeText(getActivity(), "데이터를 불러오는 중입니다.",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Uri naver = Uri.parse("http://m.naver.com");
				Uri csNews = Uri.parse("http://m.chosun.com");
				Uri jaNews = Uri.parse("http://mnews.joins.com");
				if (position == 0) {
					Intent naver_i = new Intent(Intent.ACTION_VIEW, naver);
					startActivity(naver_i);
				} else if (position == 1) {
					Intent csNews_i = new Intent(Intent.ACTION_VIEW, csNews);
					startActivity(csNews_i);
				} else if (position == 2) {
					Intent jaNews_i = new Intent(Intent.ACTION_VIEW, jaNews);
					startActivity(jaNews_i);
				}
				return false;
			}
		});
		return v;
	}

	@SuppressLint("SimpleDateFormat")
	private String lasttime() {
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy년MM월dd일 HH:mm:ss");
		String strNow = sdfNow.format(date);
		return strNow;

	}

	private String getRank() {
		String rank = "";
		String rankStr = "";
		String url = "http://www.naver.com/";
		ArrayList<String> al = new ArrayList<String>();
		Source s;
		try {
			s = new Source(new URL(url));
			s.fullSequentialParse();
			List<Element> rootlist = s.getAllElements(HTMLElementName.OPTION);
			for (int i = 0; i < rootlist.size(); i++) {
				Element data = (Element) rootlist.get(i);
				String Find = data.getContent().getTextExtractor().toString();
				al.add(Find);
			}
			rank = "[" + al.get(34) + "]" + "[" + al.get(35) + "]" + "["
					+ al.get(36) + "]" + "[" + al.get(37) + "]" + "["
					+ al.get(38) + "]" + "[" + al.get(39) + "]" + "["
					+ al.get(40) + "]" + "[" + al.get(41) + "]" + "["
					+ al.get(42) + "]" + "[" + al.get(43) + "]";
			rankStr = rank.replace(": ", ":");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			rankStr = "데이터를 불러올 수 없습니다. 네트워크를 확인해주세요. Error : " + e;
			e.printStackTrace();
		} catch (IOException e) {
			rankStr = "데이터를 불러올 수 없습니다. 네트워크를 확인해주세요. Error : " + e;
			e.printStackTrace();
		}
		return rankStr;
	}

	private String getcsNews() {
		String newsStr = "";
		try {
			String html = "";
			String page = "http://www.chosun.com/site/data/rss/rss.xml";
			StringBuilder sb = new StringBuilder();
			try {
				URL url = new URL(page);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				if (urlConnection == null)
					return null;
				urlConnection.setConnectTimeout(10000);
				urlConnection.setUseCaches(false);
				if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStream inputStream = urlConnection.getInputStream();
					InputStreamReader isr = new InputStreamReader(inputStream);

					BufferedReader br = new BufferedReader(isr);
					while (true) {
						String line = br.readLine();
						if (line == null)
							break;
						sb.append(line + "\n");
					}
					br.close();
				}
				html = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

			ByteArrayInputStream bai = new ByteArrayInputStream(html.getBytes());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document parse = builder.parse(bai);
			NodeList datas = parse.getElementsByTagName("item");
			for (int idx = 0; idx < (datas.getLength() - idx); ++idx) {
				String title = "";
				Node node = datas.item(idx);
				int childLength = node.getChildNodes().getLength();
				NodeList childNodes = node.getChildNodes();
				for (int childIdx = 0; childIdx < (childLength); childIdx++) {
					Node childNode = childNodes.item(childIdx);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equals("title")) {
							title = childNode.getFirstChild().getNodeValue();
						}
					}
				}
				newsStr += "[" + title + "]";
			}
		} catch (Exception e) {
			newsStr = "데이터를 불러올 수 없습니다. 네트워크를 확인해주세요. Error : " + e;
			e.printStackTrace();
		}
		return newsStr;
	}

	private String getjiNews() {
		String newsStr = "";
		try {
			String html = "";
			String page = "http://rss.joins.com/joins_homenews_list.xml";
			StringBuilder sb = new StringBuilder();
			try {
				URL url = new URL(page);
				HttpURLConnection urlConnection = (HttpURLConnection) url
						.openConnection();
				if (urlConnection == null)
					return null;
				urlConnection.setConnectTimeout(10000);
				urlConnection.setUseCaches(false);
				if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					InputStream inputStream = urlConnection.getInputStream();
					InputStreamReader isr = new InputStreamReader(inputStream);

					BufferedReader br = new BufferedReader(isr);
					while (true) {
						String line = br.readLine();
						if (line == null)
							break;
						sb.append(line + "\n");
					}
					br.close();
				}
				html = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

			ByteArrayInputStream bai = new ByteArrayInputStream(html.getBytes());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document parse = builder.parse(bai);
			NodeList datas = parse.getElementsByTagName("item");
			for (int idx = 0; idx < (datas.getLength() - idx); ++idx) {
				String title = "";
				Node node = datas.item(idx);
				int childLength = node.getChildNodes().getLength();
				NodeList childNodes = node.getChildNodes();
				for (int childIdx = 0; childIdx < (childLength); childIdx++) {
					Node childNode = childNodes.item(childIdx);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						if (childNode.getNodeName().equals("title")) {
							title = childNode.getFirstChild().getNodeValue();
						}
					}
				}
				newsStr += "[" + title + "]";
			}
		} catch (Exception e) {
			newsStr = "데이터를 불러올 수 없습니다. 네트워크를 확인해주세요. Error : " + e;
			e.printStackTrace();
		}
		return newsStr;
	}
}