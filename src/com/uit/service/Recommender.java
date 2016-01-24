package com.uit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Recommender {

	/** Define variable */
	// public JSONArray array = new JSONArray();
	private JSONObject seq;

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public JSONArray getTitleRecommend(String url) throws Exception {
		DbConnection db = new DbConnection(url, true);
		seq = new JSONObject();
		String text = db.getText();
		text = text.toLowerCase();
		String[] var = text.split("\\s+");
		// Pattern p = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
		// var = p.split(text);
		List li = new ArrayList();
		List li1 = new ArrayList();
		int in = 0;
		for (String subString : var) {
			// System.out.println(subString);
			Matcher p1 = Pattern.compile("^\\W+|\\W+$",
					Pattern.UNICODE_CHARACTER_CLASS).matcher(subString);
			while (p1.find()) {
				subString = p1.replaceAll("");
			}
			li.add(subString);
		}
		for (int i = 0; i < li.size(); i++) {
			if (li.get(i).equals("")) {
				continue;
			} else {
				li1.add(li.get(i));
			}
		}
		String[] var1 = new String[li1.size()];
		System.out.println("size " + li1.size());
		for (int i = 0; i < li1.size(); i++) {
			var1[i] = li1.get(i).toString();
			// System.out.println(li1.get(i).toString());
		}
		for (int i = 0; i < var1.length - 1; i++) {
			var1[i] = var1[i] + " " + var1[i + 1];
			// System.out.println(var1[i]);
		}
		JSONArray list = new JSONArray();
		Arrays.sort(var1);
		// for (String string : var) {
		// list.add(string);
		// }
		summary(var1);
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		int i = 0;
		System.out.println(seq.size());
		while (seq.size() != 0) {
			if (seq.containsKey(i)) {
				array.add(i, seq.get(i));
				// System.out.println(i);
				seq.remove(i);
			} else {
				array.add(i, "");
			}
			i++;
		}
		;
		// seq.put("titleRecommended", getTitleRecommened(arrangeJSON(seq)));
		// seq.put("metaRecommended", getMetaRecommened(arrangeJSON(seq)));
		// String title = "";
		// int index = array.size() - 1;
		// while ((title.length() < 65 || title.length() > 80) && (index > 0)) {
		// if (array.get(index) != "") {
		// JSONParser parser = new JSONParser();
		// Object obj = parser.parse(array.get(index).toString());
		// JSONArray array_temp = (JSONArray) obj;
		// for (i = 0; i < array_temp.size(); i++) {
		// StringBuilder build = new StringBuilder(title);
		// build.append(array_temp.get(i) + ", ");
		// title = build.toString();
		// if (title.length() < 65 || title.length() > 80)
		// break;
		// }
		// }
		// index--;
		// }
		// title = title.substring(0, title.length() - 2);
		return array;

	}

	@SuppressWarnings("unchecked")
	public String getMetaRecommend(String url) throws Exception {
		DbConnection db = new DbConnection(url, true);
		seq = new JSONObject();
		String text = db.getText();
		String[] var = null;
		Pattern p = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
		var = p.split(text);
		for (int i = 0; i < var.length - 1; i++) {
			var[i] = var[i] + " " + var[i + 1];
		}
		Arrays.sort(var);
		// for (String string : var) {
		// list.add(string);
		// }
		summary(var);
		JSONArray array = new JSONArray();
		int i = 0;

		while (seq.size() != 0) {
			if (seq.containsKey(i)) {
				array.add(i, seq.get(i));
				seq.remove(i);
			} else {
				array.add(i, "");
			}
			i++;
		}
		;
		String meta = "";
		int index = array.size() - 1;
		while ((meta.length() < 140 || meta.length() > 160) && (index > 0)) {
			if (array.get(index) != "") {
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(array.get(index).toString());
				JSONArray array_temp = (JSONArray) obj;
				for (i = 0; i < array_temp.size(); i++) {
					StringBuilder build = new StringBuilder(meta);
					build.append(array_temp.get(i) + ", ");
					meta = build.toString();
					if (meta.length() < 140 || meta.length() > 160)
						break;
				}
			}
			index--;
		}
		meta = meta.substring(0, meta.length() - 2);
		return meta;

	}

	@SuppressWarnings({ "unchecked", "unused" })
	private JSONArray arrangeJSON(JSONObject obj)
			throws IndexOutOfBoundsException {
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		int i = 0;

		while (obj.size() != 0) {
			if (obj.containsKey(i)) {
				array.add(i, obj.get(i));
				obj.remove(i);
			} else {
				array.add(i, "");
			}
			i++;
		}
		;
		// System.out.println(array);
		return array;
	}

	@SuppressWarnings("unchecked")
	private void summary(String[] s) throws Exception {
		int count = 0;
		String head = s[0];
		for (String vari : s) {
			// System.out.println(vari);
			if (vari.equals(head)) {
				count++;
			} else {
				JSONArray array = new JSONArray();
				if (!seq.containsKey(count)) {
					array.add(head);
				} else {
					array = (JSONArray) seq.get(count);
					array.add(head);
				}
				// System.out.println(count + "--" +
				// array.get(array.size()-1).toString());
				seq.put(count, array);
				count = 1;
				head = vari;
			}
		}
	}

	public String getAltRecommend(String url) throws Exception {
		String baseURL = "https://www.google.com/searchbyimage?image_url=";
		String baseClassName = "_gUb";
		HTTP q = new HTTP();
		String image_link = baseURL + url;
		// q.writeFile("test.txt", image_link);
		System.out.println(image_link);
		String html = q.sendGet(image_link);
		Document doc = Jsoup.parse(html);
//		System.out.println(doc.html());
		// Document doc = Jsoup.parse(html);
		Elements str = doc.getElementsByClass(baseClassName);
		String alt = str.text();
		return alt;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getKeyWord(String string) throws IOException {
		JSONArray array = new JSONArray();
		string = string.replaceAll("\\s+", "+");
		System.out.println(string);
		HTTP q = new HTTP();
		String query = "https://www.google.com/search?ie=utf-8&oe=utf-8&q="
				+ string;
		String html = q.sendGet(query);
		// System.out.println(html);
		Document doc = Jsoup.parse(html);
		Elements str = doc.getElementsByClass("_e4b");
		for (Element ele1 : str) {
			Elements subele1 = ele1.getElementsByTag("a");
			for (Element ele2 : subele1) {
				String val = ele2.html();
				array.add(val);
				System.out.println(val);
			}
		}
		return array;
	}

	public static void main(String[] args) throws Exception {
		Recommender rm = new Recommender();
		String url = "http://static.bongdaplus.vn/Assets/Media/2015/01/14/42/yeu.jpg";
		System.out.println(rm.getAltRecommend(url));
	}
}
