package com.uit.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class HTTP {
	private String cookies = "";

	public String sendGet(String link) throws IOException {
		String result = "";
		URL url = new URL(link);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.addRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0 AppEngine-Google; (+http://code.google.com/appengine; appid: APPID)");
		httpCon.addRequestProperty("Connection", "keep-alive");
		httpCon.addRequestProperty("Accept-Encoding", "*");
		httpCon.addRequestProperty("Accept-Charset", "UTF-8");
		httpCon.addRequestProperty("Accept-Language", "vi-vn;en-us");
		if (cookies != "") {
			httpCon.setRequestProperty("Cookie", cookies);

		}
		System.out.println(cookies);
		httpCon.setDoOutput(true);
		httpCon.setDoInput(true);
		httpCon.setConnectTimeout(60000);
		httpCon.setRequestMethod("GET");
		httpCon.connect();

		System.out.println("Send GET done");
		boolean redirect = false;
		int status = httpCon.getResponseCode();
		System.out.println(status);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				httpCon.getInputStream()));
		String inputLine;

		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			a.append(inputLine);
		in.close();

		result = a.toString();
//		System.out.println(result);
		httpCon.disconnect();
		return result;

	}
}
