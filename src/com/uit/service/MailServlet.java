/*
 * 
 */
package com.uit.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

public class MailServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception {
		MailServlet mail = new MailServlet();
		String username = "trang11392@gmail.com";
		String password = "SEBudWl0MTk5Mg==";
		String subject = "testMD5";
		String array = "to:trang11392@gmail.com;";
		String[] receivers = array.split(";");
		String body = "send mail done";
		String host = "smtp.gmail.com";
		String type = "465-SSL";
		mail.sendMail(username, password, subject, receivers, body, host, type);
		try {
			// String clearText = "aaaaaa";
			// String encodedText;
			//
			// // Base64
			// encodedText = new
			// String(Base64.encodeBase64(clearText.getBytes()));
			// System.out.println("Encoded: " + encodedText);
			// System.out.println("Decoded:"
			// + new String(Base64.decodeBase64(encodedText.getBytes())));
			//
			// output :
			// Encoded: SGVsbG8gd29ybGQ=
			// Decoded:Hello world
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<String> val = new ArrayList<String>();
		String key = request.getParameter("key");
		Database db;
		String url_db = request.getSession().getServletContext()
				.getInitParameter("url");
		String username_db = request.getSession().getServletContext()
				.getInitParameter("username");
		String password_db = request.getSession().getServletContext()
				.getInitParameter("password");
		db = new Database(url_db, username_db, password_db);
		if (key.equals("sendmail")) {
			String val1 = request.getParameter("val1");
			String body = request.getParameter("val2");
			String subject = request.getParameter("val3");
			String user = request.getParameter("val4");
			String pass = request.getParameter("val5");
			String host = request.getParameter("val6");
			String type = request.getParameter("val7");
			val1 = val1.substring(0, val1.length() - 1);
			String[] recievers = val1.split(";");
			sendMail(user, pass, subject, recievers, body, host, type);
		} else if (key.equals("addemailaddress")) {
			String val1 = request.getParameter("val1");
			try {
				db.addEmail(val1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (key.equals("removeemailaddress")) {
			String val1 = request.getParameter("val1");
			try {
				db.removeEmail(val1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String datenum = request.getParameter("val1");
			String datetime = request.getParameter("val2");
			String url = request.getParameter("val3");
			String title = request.getParameter("val4");
			String meta = request.getParameter("val5");
			String alt = request.getParameter("val6");
			String username = request.getParameter("val7");
			val.add(datenum);
			val.add(datetime);
			val.add(url);
			val.add(title);
			val.add(meta);
			val.add(alt);
			val.add(username);
			try {
				db.create(val);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private String[] receivers;

	public void sendMail(String username, String password1, String subject,
			String[] receivers, String body, String host, String type) {
		final String mailFrom = username;
		final String password = new String(Base64.decodeBase64(password1
				.getBytes()));
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		String port = "587";
		if (type.equals("465-SSL")) {
			port = "465";
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
		} else {
			props.put("mail.smtp.starttls.enable", "true");
		}
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.sasl.enable", "true");
		props.put("mail.smtp.sasl.mechanisms", "DIGEST-MD5");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mailFrom, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));
			for (int i = 0; i < receivers.length; i++) {
				System.out.println(receivers[i]);
				System.out.println(receivers[i]);
				String[] split = receivers[i].split(":");
				String mode = split[0];
				String mail = split[1];
				if (mode.equals("to")) {
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(mail));
				}
				if (mode.equals("cc")) {
					message.setRecipients(Message.RecipientType.CC,
							InternetAddress.parse(mail));
				}
				if (mode.equals("bcc")) {
					message.setRecipients(Message.RecipientType.BCC,
							InternetAddress.parse(mail));
				}
			}
			message.setSubject(subject);
			MimeBodyPart messagePart = null;
			MimeMultipart multipart = null;
			messagePart = new MimeBodyPart();

			multipart = new MimeMultipart();
			multipart.addBodyPart(messagePart); // adding message part

			// Setting the Email Encoding
			messagePart.setText(body, "utf-8");
			messagePart
					.setHeader("Content-Type", "text/plain; charset='utf-8'");
			messagePart.setHeader("Content-Transfer-Encoding",
					"quoted-printable");

			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}