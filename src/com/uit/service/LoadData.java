/*
 * 
 */
package com.uit.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoadData extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String para = request.getParameter("key");
		PrintWriter pr = response.getWriter();
		Database db;
		String url_db = request.getSession().getServletContext()
				.getInitParameter("url");
		String username_db = request.getSession().getServletContext()
				.getInitParameter("username");
		String password_db = request.getSession().getServletContext()
				.getInitParameter("password");
		db = new Database(url_db, username_db, password_db);
		// JSONObject obj = new JSONObject();
		// obj.put("result", );
		if (para.equals("select")) {
			try {
				response.setContentType("application/json");
				pr.print(db.select());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (para.equals("updateByLink")) {
			try {
				String url = request.getParameter("val");
				String username = request.getParameter("val1");
				String datenum = request.getParameter("val2");
				db.updateByLink(url, username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (para.equals("selectuser")) {
			try {
				response.setContentType("application/json");
				pr.print(db.selectUser());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (para.equals("selectLog")) {
			try {
				response.setContentType("application/json");
				String key1 = request.getParameter("key1");
				pr.print(db.selectLog(key1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (para.equals("loadsetting")) {
			try {
				response.setContentType("application/json");
				String key1 = request.getParameter("key1");
				pr.print(db.loadSetting(key1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (para.equals("loadmailinfo")) {
			try {
				response.setContentType("application/json");
				String key1 = request.getParameter("key1");
				pr.print(db.loadSettingByUser(key1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (para.equals("loadcontact")) {
			try {
				response.setContentType("application/json");
				pr.print(db.getEmail());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (para.equals("savedata")) {
			try {
				String val1 = request.getParameter("val1");
				String val2 = request.getParameter("val2");
				String val3 = request.getParameter("val3");
				String val4 = request.getParameter("val4");
				String val5 = request.getParameter("val5");
				String val6 = request.getParameter("val6");
				db.saveData(val1, val2, val3, val4, val5);
				db.updateUsers(val6, val1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (para.equals("changepass")) {
			String val1 = request.getParameter("key1");
			String val2 = request.getParameter("key2");
			String val3 = request.getParameter("key3");
			String val4 = request.getParameter("key4");
			if (!val2.equals(val3)) {
				pr.print("Password mới không trùng khớp!");
			} else {
				try {
					if (!val1.equals(db.getPassword(val4))) {
						pr.print("Sai password cũ!");
					} else {
						db.updatePassword(val4, val2);
						pr.print("Done!");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (para.equals("newaccount")) {
			String val1 = request.getParameter("key1");
			String val2 = request.getParameter("key2");
			String val3 = request.getParameter("key3");
			try {
				if (db.checkExistUsername(val1)) {
					pr.print("Tài khoản đã tồn tại.");
				} else {
					db.newAccount(val1, val2, val3);
					pr.print("Done!");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				db.updateStatus(db.select());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}