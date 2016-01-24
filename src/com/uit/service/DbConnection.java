package com.uit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DbConnection {
	public String url;
	public Document doc;
	public JSONObject obj;
	public JSONArray array;

	public DbConnection() {
	}

	/**
	 * Instantiates a new DbConnection.
	 *
	 * @param url
	 *            the url
	 * @param get
	 *            get source not from local
	 * @throws Exception
	 *             the exception
	 */
	public DbConnection(String url, boolean get) throws Exception {
		this.url = url;
		if (get) {
			this.doc = Jsoup.connect(url).timeout(5000).get();
		} else
			this.doc = Jsoup.parse(url, "UTF-8");
	}

	public boolean checkStatus(JSONObject obj) {
		if (obj.isEmpty())
			return false;
		else
			return true;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getTitle() {
		JSONObject obj = new JSONObject();
		obj.put("title", doc.title());
		if (!doc.title().equals("")) {
			String title = doc.title();
			if (title.length() >= 50 && title.length() <= 60) {
				obj.put("checking", "passed");
			} else {
				obj.put("checking", "bad");
			}
		} else {
			obj.put("checking", "failed");
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getMetaDesciption() {
		JSONObject obj = new JSONObject();
		Elements metas = doc.select("meta[name=description]");
		String meta = "";
		for (Element e : metas) {
			meta = e.attr("content");
		}
		obj.put("meta", meta);
		if (!meta.equals("")) {
			if (meta.length() >= 150 && meta.length() <= 160) {
				obj.put("checking", "passed");
			} else {
				obj.put("checking", "bad");
			}
		} else {
			obj.put("checking", "failed");
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getMetaImage() {
		JSONObject obj = new JSONObject();
		Elements metas = doc.select("meta[property=og:image]");
		String meta = "";
		for (Element e : metas) {
			meta = e.attr("content");
		}
		obj.put("meta_image", meta);
		if (!meta.equals("")) {
			obj.put("checking", "passed");
		} else {
			obj.put("checking", "failed");
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getImages() throws Exception {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		JSONArray sub1 = new JSONArray();
		JSONArray sub2 = new JSONArray();
		JSONArray sub3 = new JSONArray();
		Recommender rm = new Recommender();
		Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
		int count = 0;
		for (Element image : images) {
			String alt = image.attr("alt");
			if (image.attr("src").startsWith("http")) {
				if (alt != "") {
					sub3.add(image.attr("src") + ";" + alt + ";"
							+ image.attr("width") + ";" + image.attr("height"));
					continue;
				} else {
					try {
						String rem = rm.getAltRecommend(image.attr("src"));
						alt = rem;
						if (rem.equals("")) {
							// Recommend
							sub1.add(image.attr("src") + ";" + ";"
									+ image.attr("width") + ";"
									+ image.attr("height"));
						} else {
							// Not recommend
							sub2.add(image.attr("src") + ";" + alt + ";"
									+ image.attr("width") + ";"
									+ image.attr("height"));
							count++;
						}

						System.out.println(rem);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				String baseUrl = doc.baseUri();
				String[] url1 = baseUrl.split("//");
				String url2 = url1[1];
				String[] url3 = url2.split("/");
				String domain = url1[0] + "//" + url3[0];
				if (alt != "") {
					sub3.add((domain + image.attr("src")) + ";" + alt + ";"
							+ image.attr("width") + ";" + image.attr("height"));
					continue;
				} else {
					try {
						String rem = rm.getAltRecommend(domain
								+ image.attr("src"));
						alt = rem;
						if (rem.equals("")) {
							// Recommend
							sub1.add((domain + image.attr("src")) + ";" + ";"
									+ image.attr("width") + ";"
									+ image.attr("height"));
						} else {
							// Not recommend
							sub2.add((domain + image.attr("src")) + ";" + alt
									+ ";" + image.attr("width") + ";"
									+ image.attr("height"));
							count++;
						}

						// System.out.println(domain);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		array.add(sub1);
		array.add(sub2);
		array.add(sub3);
		System.out.println("There're " + count
				+ " image(s) have been recommended ");
		obj.put("images", array);
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getImagesWithAlt() {
		JSONObject obj = new JSONObject();
		JSONArray sub3 = new JSONArray();
		Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
		for (Element image : images) {
			String alt = image.attr("alt");
			if (image.attr("src").startsWith("http")) {
				if (alt != "") {
					sub3.add(image.attr("src") + ";" + alt + ";"
							+ image.attr("width") + ";" + image.attr("height"));
					continue;
				}
			} else {
				String Url = doc.baseUri();
				String[] url1 = Url.split("//");
				String url2 = url1[1];
				String[] url3 = url2.split("/");
				String domain = url1[0] + "//" + url3[0];
				if (alt != "") {
					sub3.add((domain + image.attr("src")) + ";" + alt + ";"
							+ image.attr("width") + ";" + image.attr("height"));
					continue;
				}
			}
		}
		obj.put("images", sub3);
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getFavicon() {
		JSONObject obj = new JSONObject();
		Element favis = doc.head().select("link[href~=.*\\.(ico|png)]").first();
		if (favis != null) {
			obj.put("favicon", favis.attr("href"));
		}
		if (checkStatus(obj) == true) {
			obj.put("checking", "passed");
		} else {
			obj.put("checking", "failed");
		}
		return obj;
	}

	@SuppressWarnings({ "unchecked" })
	public JSONObject getLinks(boolean checkStatus) throws Exception {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		JSONArray arrLink = new JSONArray();
		JSONArray arrLinkInt = new JSONArray();
		JSONArray arrLinkExt = new JSONArray();
		String url = "";
		String baseUrl = doc.baseUri();
		Elements links = doc.select("a[href]");
		obj.put("baseurl", doc.baseUri());
		url = baseUrl;
		if (url.indexOf("http://") != -1) {
			url = url.replace("http://", "");
		}
		if (url.indexOf("https://") != -1) {
			url = url.replace("https://", "");
		}
		if (url.indexOf("/") != -1) {
			url = url.substring(0, url.indexOf("/"));
		}
		for (Element link : links) {
			if (!link.attr("abs:href").toString().equals("")) {
				arrLink.add(link.attr("abs:href").toString());
			} else {
				continue;
			}
		}
		// obj.put("links", arrLink);
		for (Object arrlink1 : arrLink) {
			if (arrlink1.toString().indexOf(url) != -1) {
				arrLinkInt.add(arrlink1.toString());
			} else {
				arrLinkExt.add(arrlink1.toString());
			}
		}
		arrLinkInt = formatLinks(arrLinkInt, checkStatus);
		obj.put("LinkInt", arrLinkInt);
		obj.put("LinkExt", arrLinkExt);
		for (Element link : links) {
			if (!link.attr("abs:href").toString().equals("")) {
				array.add(link.attr("abs:href").toString());
			} else {
				continue;
			}
		}
		return obj;
	}

	@SuppressWarnings({ "unchecked" })
	public JSONObject getH1Tags() throws Exception {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		Elements H1TagsAsElementType = doc.select("h1");
		for (Element H1 : H1TagsAsElementType) {
			array.add(H1.text());
		}
		obj.put("H1tags", array);
		if (checkStatus(obj) == true) {
			obj.put("checking", "passed");
		} else {
			obj.put("checking", "failed");
		}
		return obj;
	}

	@SuppressWarnings({ "unchecked" })
	public JSONObject getH2Tags() throws Exception {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		Elements H2TagsAsElementType = doc.select("h2");
		for (Element H2 : H2TagsAsElementType) {
			array.add(H2.text());
		}
		obj.put("H2tags", array);
		if (checkStatus(obj) == true) {
			obj.put("checking", "passed");
		} else {
			obj.put("checking", "failed");
		}
		return obj;
	}

	public String getText() throws Exception, IOException {
		String text = doc.text();
		return text;
		// Pattern p = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
		// String[] oneWordKeysString = p.split(text);
		// //java.util.Arrays.sort(oneWordKeysString);
		// return oneWordKeysString;
	}

	public String getHtml() throws Exception, IOException {

		return doc.html();
		// Pattern p = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
		// String[] oneWordKeysString = p.split(text);
		// //java.util.Arrays.sort(oneWordKeysString);
		// return oneWordKeysString;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JSONArray formatLinks(JSONArray array, boolean checkStatus)
			throws Exception {
		List li = new ArrayList();
		for (int i = 0; i < array.size(); i++) {
			String val = (String) array.get(i);
			li.add(val);
		}
		Collections.sort(li);
		array.clear();
		List li2 = new ArrayList(new HashSet(li));
		Iterator it = li2.iterator();
		while (it.hasNext()) {
			try {
				// System.out.println(it.next());
				String val = (String) it.next();
				String status = "";
				if (checkStatus == true) {
					DbConnection db = new DbConnection(val, true);
					JSONObject obj1 = db.getTitle();
					JSONObject obj2 = db.getMetaDesciption();
					JSONObject obj3 = db.getH1Tags();
					JSONObject obj4 = db.getH2Tags();
					String checkTit = obj1.get("checking").toString();
					String checkMeta = obj2.get("checking").toString();
					String checkH1 = obj3.get("checking").toString();
					String checkH2 = obj4.get("checking").toString();
					// System.out.println(checkTit + " " + checkMeta + " "
					// + checkH1 + " " + checkH2);
					if ((checkTit.equals("passed"))
							&& (checkMeta.equals("passed"))
							&& checkH1.equals("passed")
							&& checkH2.equals("passed")) {
						status = "passed";
					} else if (checkTit.equals("failed")
							|| checkMeta.equals("failed")
							|| checkH1.equals("failed")
							|| checkH2.equals("failed")) {
						status = "failed";
					} else {
						status = "bad";
					}
				}
				array.add(val + ";" + status);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return array;
	}

	public String checkAltImageByLink(String url, String img) {
		Elements images = doc.select("img[src='" + img + "']");
		String alt = null;
		for (Element image : images) {
			alt = image.attr("alt");
		}
		return alt;
	}

}
