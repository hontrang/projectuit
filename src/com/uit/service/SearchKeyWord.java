/*
 * 
 */
package com.uit.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SearchKeyWord extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JSONObject obj = new JSONObject();
	JSONArray array = new JSONArray();

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String key = request.getParameter("val1");
		PrintWriter rs = response.getWriter();
		Database db;
		String url_db = request.getSession().getServletContext()
				.getInitParameter("url");
		String username_db = request.getSession().getServletContext()
				.getInitParameter("username");
		String password_db = request.getSession().getServletContext()
				.getInitParameter("password");
		db = new Database(url_db, username_db, password_db);
		if (key.equals("keywordRecomended")) {
			String keyword = request.getParameter("val2");
			JSONObject obj = new JSONObject();
			JSONArray array_keyword = new JSONArray();
			Recommender rm = new Recommender();
			array_keyword = rm.getKeyWord(keyword);
			// obj.put("keywordRecomended", array_keyword);
			rs.print(array_keyword);
		} else if (key.equals("checkRanking")) {
			String keyword = request.getParameter("val2");
			keyword = keyword.toLowerCase();
			String url = request.getParameter("val3");
			String useragent = request.getHeader("User-Agent");
			CheckRankTop100 check = new CheckRankTop100();
			JSONArray res = check.checkRank(keyword, url, useragent);
			try {
				db.logRank(url, keyword, res);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("res: " + res.toString());
			rs.print(res);
		} else if (key.equals("searchKey")) {
			String keyword = request.getParameter("val2");
			String url = request.getParameter("val3");
			keyword = keyword.toLowerCase();
			try {
				DbConnection dbc = new DbConnection(url, true);
				JSONArray array = new JSONArray();
				JSONArray array1 = new JSONArray();
				JSONArray array2 = new JSONArray();
				JSONObject obj = new JSONObject();
				obj = dbc.getLinks(false);
				JSONArray arrayLinks = (JSONArray) obj.get("LinkInt");
				// array.add(findKeyWord(url, keyword));
				for (int i = 0; i < arrayLinks.size(); i++) {
					String arrayVal = (String) arrayLinks.get(i);
					String[] val = arrayVal.split(";");
					try {
						String str = findKeyWord(val[0], keyword);
						// System.out.println(str);
						if (str.indexOf(";0") != -1) {
							array1.add(str);
						} else {
							array2.add(str);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				array.add(array1);
				array.add(array2);
				obj.put("countKeyWord", array);
				rs.print(obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String findKeyWord(String url, String keyword) throws Exception {
		DbConnection db = new DbConnection(url, true);
		// JSONObject title_obj = db.getTitle();
		// JSONObject meta_obj = db.getMetaDesciption();
		// String title = title_obj.get("title").toString().toLowerCase();
		// String meta = meta_obj.get("meta").toString().toLowerCase();
		// int count_title = 0;
		Pattern pattern = Pattern.compile(keyword);
		// Matcher matcher_title = pattern.matcher(title);
		// while (matcher_title.find()) {
		// count_title++;
		// }
		// int count_meta = 0;
		// Matcher matcher_meta = pattern.matcher(meta);
		// while (matcher_meta.find()) {
		// count_meta++;
		// }
		int count = 0;
		// if (count_title != 0 || count_meta != 0) {
		//
		// }
		String html = db.getText();
		html = html.toLowerCase();
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			count++;
		}
		return url + ";" + count;
	}
}