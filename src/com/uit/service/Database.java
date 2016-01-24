package com.uit.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Database {
	 public static void main(String[] args) throws Exception {
	 Database db = new Database(
	 "jdbc:mysql://127.0.0.1:3306/seo_tool?characterEncoding=utf8",
	 "root", "123456");
	 db.newAccount("admin1", "123", "123");
	 }

	private Connection con = null;
	private Statement statement = null;
	private ResultSet rs = null;
	private String url;
	private String username;
	private String password;

	public Database(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	private void init() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver found");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(url + username + password);
		try {
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Create connection successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static java.sql.Date getCurrentDate() {
		java.util.Date today = new java.util.Date();
		return new java.sql.Date(today.getTime());
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public JSONArray select() throws Exception {
		init();
		JSONArray array = new JSONArray();
		try {
			statement = con.createStatement();
			String query = "select t.* from data t where t.DateNum in (select max(t1.DateNum) from data t1 group by t1.URL)";
			rs = statement.executeQuery(query);
			while (rs.next()) {
				try {
					JSONArray subarray = new JSONArray();
					subarray.add(rs.getDate(2).toString());
					String url = rs.getString(3);
					subarray.add(url);
					String title = "";
					String meta = "";

					DbConnection db = new DbConnection(url, true);
					title = db.getTitle().get("title").toString();
					meta = db.getMetaDesciption().get("meta").toString();
					if (rs.getString(4).equals(title)) {
						subarray.add(rs.getString(4) + ";" + title + ";" + true);
					} else {
						subarray.add(rs.getString(4) + ";" + title + ";"
								+ false);
					}

					if (rs.getString(5).equals(meta)) {
						subarray.add(rs.getString(5) + ";" + meta + ";" + true);
					} else {
						subarray.add(rs.getString(5) + ";" + meta + ";" + false);
					}
					JSONObject image_obj = new JSONObject();
					image_obj = (JSONObject) db.getImagesWithAlt();
					JSONArray image_arr = (JSONArray) image_obj.get("images");
					String image = rs.getString(6);
					String rs_image = "";
					if (!image.equals("")) {
						String image1[] = image.split("<br>");
						for (String img : image1) {
							try {
								String imgs[] = img.split("[|]");
								boolean check = false;
								String currAlt = "";
								for (int i = 0; i < image_arr.size(); i++) {
									String image_arr_full = image_arr.get(i)
											.toString();
									if (image_arr_full.indexOf(imgs[0]) != -1) {
										String image_arr_fulls[] = image_arr_full
												.split(";");
										currAlt = image_arr_fulls[1];
										if (currAlt.equals(imgs[1])) {
											check = true;
										}
									}
								}
								rs_image += imgs[0] + "|" + imgs[1] + "|"
										+ currAlt + "|" + check + "<br>";
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					subarray.add(rs_image);
					subarray.add(rs.getInt(9));
					System.out.println(rs.getDate(2).toString());
					System.out.println(rs.getString(3));
					System.out.println(rs.getString(4));
					System.out.println(rs.getString(5));
					System.out.println(rs.getString(6));
					System.out.println(rs.getInt(9));
					array.add(subarray);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array;

	}

	@SuppressWarnings("unchecked")
	public JSONArray selectUser() throws Exception {
		init();
		JSONArray array = new JSONArray();
		try {
			statement = con.createStatement();
			String query = "select email from email";
			rs = statement.executeQuery(query);
			while (rs.next()) {
				try {
					JSONArray sub = new JSONArray();
					sub.add(rs.getString(1));
					array.add(sub);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				rs.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public JSONArray selectLog(String rows) throws Exception {
		init();
		JSONArray array = new JSONArray();
		try {
			int num = Integer.parseInt(rows);
			// statement = con.createStatement();
			// statement.setMaxRows(1);
			String query = "select * from log order by DateTime desc";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setMaxRows(num);
			rs = prepared.executeQuery(query);
			while (rs.next()) {
				try {
					JSONArray sub = new JSONArray();
					sub.add(rs.getString(1));
					sub.add(rs.getString(2));
					sub.add(rs.getString(3));
					sub.add(rs.getString(4));
					sub.add(rs.getString(5));
					array.add(sub);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				rs.close();
				// statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return array;
	}

	@SuppressWarnings({ "unchecked" })
	public void updateStatus(JSONArray list) throws Exception {
		init();
		JSONArray array1 = new JSONArray();
		JSONArray array2 = new JSONArray();
		JSONArray array3 = new JSONArray();
		JSONArray subarray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			subarray = (JSONArray) list.get(i);
			array1.add(subarray.get(1));
			array2.add(subarray.get(2));
			array3.add(subarray.get(3));
		}
		try {
			for (int i = 0; i < array1.size(); i++) {
				String str2 = array2.get(i).toString();
				String str3 = array3.get(i).toString();
				String titles[] = str2.split(";");
				String title = titles[2];
				String metas[] = str3.split(";");
				String meta = metas[2];
				if (meta.equals("true") && title.equals("true")) {
					String query = "Update data " + "SET Status=1 Where url=?";
					PreparedStatement prepared = con.prepareStatement(query);
					prepared.setString(1, (String) array1.get(i));
					prepared.executeUpdate();
				} else {
					System.out.println("URL hasn't been edited");
				}
				// String meta = (String) db.getMetaDesciption().get("meta");
				// if (array2.get(i).equals(title) &&
				// array3.get(i).equals(meta)) {

				// } else {
				// System.out.println("URL hasn't edited");
				// }
			}
		} finally {
			try {
				con.close();
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void updateByLink(String url, String username) throws Exception {
		init();
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); // 2014/08/06 15:59:48
			String query = "Update data " + "SET Status=? Where url=?";
			PreparedStatement prepared = con.prepareStatement(query);
			int stt = 0;
			String detail = "";
			if (checkSTTbyURL(url) == 0) {
				stt = 1;
				detail = "Overwrite OK to NOT OK";
				System.out.println("Overwrite OK to NOT OK");
			} else {
				detail = "Overwrite NOT OK to OK";
				System.out.println("Overwrite NOT OK to OK");
			}
			System.out.println(stt);
			prepared.setInt(1, stt);
			prepared.setString(2, url);
			prepared.executeUpdate();

			String query1 = "INSERT INTO log(DateTime, Url, Detail, Action, User) VALUES (?,?,?,?,?)";
			prepared = con.prepareStatement(query1);
			prepared.setString(1, dateFormat.format(date));
			prepared.setString(2, url);
			prepared.setString(3, detail);
			prepared.setString(4, "OVERWRITE");
			prepared.setString(5, username);
			prepared.executeUpdate();

		} finally {
			try {
				con.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void updateUsers(String username, String email) throws Exception {
		init();
		try {
			String query = "Update users SET email=? Where username=?";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setString(1, email);
			prepared.setString(2, username);
			prepared.executeUpdate();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void logRank(String baseUrl, String keyword, JSONArray detail)
			throws Exception {
		init();
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String query1 = "INSERT INTO log(DateTime, Url, Detail, Action, User) VALUES (?,?,?,?,?)";
			PreparedStatement prepared;
			String main_detail = "";
			for (int i = 0; i < detail.size(); i++) {
				String data = (String) detail.get(i);
				String[] str1 = data.split(";");
				String url = str1[1];
				String rank = str1[0];
				main_detail += url + "			" + "<font color='blue'>" + "RANK: "
						+ rank + " (KEYWORD: " + keyword + ")" + "</font>";
			}
			prepared = con.prepareStatement(query1);
			prepared.setString(1, dateFormat.format(date));
			prepared.setString(2, baseUrl);
			prepared.setString(3, main_detail);
			prepared.setString(4, "CHECK RANK");
			prepared.setString(5, "anonymous");
			prepared.executeUpdate();

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private int checkSTTbyURL(String url) throws SQLException {
		init();
		int stt = 0;
		try {
			statement = con.createStatement();
			String query = "select * from data where url='" + url + "'";
			System.out.println(query);
			rs = statement.executeQuery(query);
			while (rs.next()) {
				try {
					stt = rs.getInt(9);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.close();
			rs.close();
			statement.close();
		}
		return stt;
	}

	@SuppressWarnings("rawtypes")
	public void create(List list) throws SQLException {
		int rs;
		init();
		try {
			double val1 = Double.parseDouble((String) list.get(0));
			statement = con.createStatement();

			String query = "INSERT INTO data(DateNum, DateTime, URL, Title, Meta, Alt,Status) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setDouble(1, val1);
			prepared.setDate(2, getCurrentDate());
			prepared.setString(3, (String) list.get(2));
			prepared.setString(4, (String) list.get(3));
			prepared.setString(5, (String) list.get(4));
			prepared.setString(6, (String) list.get(5));
			prepared.setInt(7, 0);
			rs = prepared.executeUpdate();
			String query1 = "INSERT INTO log(DateTime, Url, Detail, Action, User) VALUES (?,?,?,?,?)";
			prepared = con.prepareStatement(query1);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date)); // 2014/08/06 15:59:48
			prepared.setString(1, dateFormat.format(date));
			prepared.setString(2, (String) list.get(2));
			String title = "TITLE: " + (String) list.get(3) + "<br>";
			String meta = "META: " + (String) list.get(4) + "<br>";
			String alt = "ALT: " + (String) list.get(5) + "<br>";
			String detail = title + meta + alt;
			prepared.setString(3, detail);
			prepared.setString(4, "CREATE");
			prepared.setString(5, (String) list.get(6));
			prepared.executeUpdate();
			System.out.println("connected successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void logCreate(List list) throws SQLException {
		int rs;
		init();
		try {
			double val1 = Double.parseDouble((String) list.get(0));
			statement = con.createStatement();

			String query = "INSERT INTO data(DateNum, DateTime, URL, Title, Meta, Alt,Status) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setDouble(1, val1);
			prepared.setDate(2, getCurrentDate());
			prepared.setString(3, (String) list.get(2));
			prepared.setString(4, (String) list.get(3));
			prepared.setString(5, (String) list.get(4));
			prepared.setString(6, (String) list.get(5));
			prepared.setInt(7, 0);
			rs = prepared.executeUpdate();
			System.out.println("connected successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void addEmail(String email) throws SQLException {
		init();
		try {
			statement = con.createStatement();
			String query = "Select * from email where email=?";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setString(1, email);
			rs = prepared.executeQuery();
			if (rs.last() != false) {
				// DO NOT THING

			} else {
				// INSERT
				String query1 = "INSERT INTO email VALUES (?,?,?,?,?)";
				PreparedStatement prepared1 = con.prepareStatement(query1);
				prepared1.setInt(1, 2);
				prepared1.setString(2, email);
				prepared1.setString(3, null);
				prepared1.setString(4, null);
				prepared1.setString(5, email);
				prepared1.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeEmail(String email) throws SQLException {
		init();
		try {
			statement = con.createStatement();
			String query;
			// query =
			// "select t.* from data t where t.DateNum in (select max(t1.DateNum) from data t1 group by t1.URL)";
			// rs = statement.executeQuery(query);
			query = "DELETE FROM email WHERE username=?";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setString(1, email);
			prepared.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveData(String user, String pass, String host, String type,
			String contact) throws SQLException {
		init();
		try {
			String setting = pass + ";" + host + ";" + type + ";";
			String query = "select * from email where email=?";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setString(1, user);
			rs = prepared.executeQuery();
			if (rs.last() == false) {
				// INSERT

			} else {
				// UPDATE
				String query1 = "Update email SET setting=?, contact=? where email=?";
				PreparedStatement prepared1 = con.prepareStatement(query1);
				prepared1.setString(1, setting);
				prepared1.setString(2, contact);
				prepared1.setString(3, user);
				prepared1.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public JSONArray loadSettingByUser(String user) throws SQLException {
		init();
		JSONArray array = new JSONArray();
		try {
			String query1 = "SELECT * FROM email WHERE email=(select email from users where username='"
					+ user + "')";
			System.out.println(query1);
			statement = con.createStatement();
			rs = statement.executeQuery(query1);
			while (rs.next()) {
				try {
					JSONArray sub = new JSONArray();
					sub.add(rs.getInt(1));
					sub.add(rs.getString(2));
					sub.add(rs.getString(3));
					sub.add(rs.getString(4));
					sub.add(rs.getString(5));
					array.add(sub);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				rs.close();
				// statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	public JSONArray loadSetting(String email) throws SQLException {
		init();
		JSONArray array = new JSONArray();
		try {
			String query1 = "SELECT * FROM email WHERE email='" + email + "'";
			System.out.println(query1);
			statement = con.createStatement();
			rs = statement.executeQuery(query1);
			while (rs.next()) {
				try {
					JSONArray sub = new JSONArray();
					sub.add(rs.getInt(1));
					sub.add(rs.getString(2));
					sub.add(rs.getString(3));
					sub.add(rs.getString(4));
					sub.add(rs.getString(5));
					array.add(sub);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				rs.close();
				// statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return array;
	}

	public void updatePassword(String username, String password)
			throws SQLException {
		init();
		try {
			String query1 = "Update users SET password=? where username=?";
			PreparedStatement prepared1 = con.prepareStatement(query1);
			prepared1.setString(1, password);
			prepared1.setString(2, username);
			prepared1.executeUpdate();
			System.out.println("Update password done");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				// statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getPassword(String username) throws SQLException {
		init();
		try {
			String query1 = "SELECT password FROM users WHERE username='"
					+ username + "'";
			System.out.println(query1);
			statement = con.createStatement();
			rs = statement.executeQuery(query1);
			String pass = "";
			while (rs.next()) {
				try {
					pass = rs.getString(1);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return pass;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				rs.close();
				// statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public JSONArray getEmail() throws SQLException {
		init();
		JSONArray array = new JSONArray();
		try {
			String query1 = "SELECT username,email FROM users";
			System.out.println(query1);
			statement = con.createStatement();
			rs = statement.executeQuery(query1);
			String pass = "";
			while (rs.next()) {
				try {
					String element = rs.getString(1) + ";" + rs.getString(2);
					array.add(element);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return array;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				rs.close();
				// statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	public boolean checkExistUsername(String username) throws SQLException {
		init();
		try {
			String query = "Select * from users where username=?";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setString(1, username);
			rs = prepared.executeQuery();
			return rs.last();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
			rs.close();
		}
		return false;
	}

	public void newAccount(String username, String password, String email)
			throws SQLException {
		init();
		try {
			String query = "INSERT INTO users(username, password, email) VALUES (?,?,?)";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setString(1, username);
			prepared.setString(2, password);
			prepared.setString(3, email);
			prepared.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
		}
	}

	public boolean loginUser(String username, String password)
			throws SQLException {
		init();
		try {
			String query = "Select * from users where username=?";
			PreparedStatement prepared = con.prepareStatement(query);
			prepared.setString(1, username);
			rs = prepared.executeQuery();
			if (rs.next()) {
				String passwd = rs.getString("password");
				if (passwd.equalsIgnoreCase(password)) {
					return true;
				} else {
					return false;
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
			rs.close();
		}
		return false;
	}

}
