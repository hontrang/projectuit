package com.uit.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class actionServlet
 */
public final class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Database db;
		String url_db = request.getSession().getServletContext()
				.getInitParameter("url");
		String username_db = request.getSession().getServletContext()
				.getInitParameter("username");
		String password_db = request.getSession().getServletContext()
				.getInitParameter("password");
		db = new Database(url_db, username_db, password_db);
		String key = request.getParameter("key");
		if (key.equalsIgnoreCase("login")) {
			response.setContentType("text/plain; charset=UTF-8");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String remember = request.getParameter("remember");
			System.out.println(remember);
			if (remember.equals("true")) {
				Cookie c = new Cookie("cookieName", username);
				c.setMaxAge(24 * 60 * 60);
				response.addCookie(c);
			}
			HttpSession session = null;
			UserInfo objUser = null;
			boolean check = false;
			try {
				check = db.loginUser(username, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().print(check);
			if (check) {
				objUser = new UserInfo();
				objUser.setUsername(username);
				session = request.getSession();
				session.setMaxInactiveInterval(2 * 60 * 60);
				session.setAttribute("userLogin", objUser.getUsername());
				// session.setAttribute("url", db.userUrl);
			}
		}
	}
}
