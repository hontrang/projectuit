/*
 * 
 */
package com.uit.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String baseURL = "http://google.com/searchbyimage?image_url=";
	public String[] links;
	public JSONObject webData = new JSONObject();
	// public JSONArray array = new JSONArray();
	public JSONObject seq;

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("val");
		String key = request.getParameter("key");
		response.setContentType("application/json");
//		String useragent = request.getHeader("User-Agent");
		response.setCharacterEncoding("UTF-8");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("-------------------------- "
				+ dateFormat.format(date) + " -------------------------------"); // 2014/08/06
																					// //
		// 15:59:48
		PrintWriter rs = response.getWriter();
		Recommender rc = new Recommender();
		try {
			if (key.equals("getTitleRecommend")) {
				JSONArray title = rc.getTitleRecommend(url);
				// System.out.println("getTitleRecommend: " + title);
				webData.put("getTitleRecommend", title);
				rs.print(webData);
			} else if (key.equals("getMetaRecommend")) {
				String meta = rc.getMetaRecommend(url);
				System.out.println("getMetaRecommend: " + meta);
				webData.put("getMetaRecommend", meta);
				rs.print(webData);
			} else {
				System.out.println("Get data for website: " + url);
				getWebData(url, key);
				rs.print(webData);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("Clear data");
			webData.clear();
		}

	}

	private JSONObject getWebData(String url, String key) throws Exception {
		DbConnection document = new DbConnection(url, true);
		if (key.equals("title"))
			webData = document.getTitle();
		if (key.equals("meta"))
			webData = document.getMetaDesciption();
		if (key.equals("meta_image"))
			webData = document.getMetaImage();
		if (key.equals("favicon"))
			webData = document.getFavicon();
		if (key.equals("links"))
			webData = document.getLinks(true);
		if (key.equals("H1Tags"))
			webData = document.getH1Tags();
		if (key.equals("H2Tags"))
			webData = document.getH2Tags();
		if (key.equals("images"))
			webData = document.getImages();
		return webData;
	}

}