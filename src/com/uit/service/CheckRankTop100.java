package com.uit.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CheckRankTop100 {
	private static Pattern patternDomainName;
	private Matcher matcher;
	private static final String DOMAIN_NAME_PATTERN = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
	static {
		patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
	}

	public static void main(String[] args) {
		CheckRankTop100 check = new CheckRankTop100();
		check.checkRank("tin", "www.24h.com.vn",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
		System.out.println("done");
	}

	private static String removePrefix(String url) {
		url = url.replaceAll("(http://|http://www\\.|www\\.)", "");
		return url;
	}

	@SuppressWarnings("unchecked")
	public JSONArray checkRank(String key, String url, String agent) {
		CheckRankTop100 obj = new CheckRankTop100();
		key = key.replaceAll("\\s+", "+");
		// System.out.println(key);
		JSONArray result = obj.getDataFromGoogle(key, agent);
		JSONArray rs = new JSONArray();
		url = CheckRankTop100.removePrefix(url);
		// System.out.println(url);
		for (int i = 0; i < result.size(); i++) {
			// System.out.println(result.get(i));
			int idex = i + 1;
			String temp = (String) result.get(i);
			if (temp.indexOf(url) != -1) {
				// System.out.println("[" + idex + "]" + temp);
				rs.add(idex + ";" + temp);
			}
		}
		return rs;
	}

	@SuppressWarnings("unchecked")
	private JSONArray getDataFromGoogle(String query, String agent) {

		JSONArray result = new JSONArray();
		String request = "https://www.google.com/search?q=" + query
				+ "&num=100";
		System.out.println("Sending request..." + request);
		JSONArray data = new JSONArray();
		try {
			HTTP h = new HTTP();
			String html = h.sendGet(request);
			// need http protocol, set this as a Google bot agent :)
			Document doc = Jsoup.parse(html);
			Elements rcs = doc.getElementsByClass("rc");
			// System.out.println(rcs.size());
			for (Element rc : rcs) {
				String data_id = rc.attr("data-hveid");
				int index = Integer.parseInt(data_id);
				Elements rs = rc.getElementsByClass("r");
				for (Element r : rs) {
					Elements links = r.select("a[href]");
					for (Element link : links) {
						String temp = link.attr("href");
						data.add(temp);
					}
				}
			}
			// System.out.println(data.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
}