<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page session="true"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SEO Tool</title>
<link href="css/default.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/changepass.css" rel="stylesheet" type="text/css"
	media="all" />
<script src="js/bpopup.min.js"></script>
<script type="text/javascript" src="js/jquery.base64.js"></script>
<script type="text/javascript" src="js/changepass.js"></script>
</head>
<body>
	<div id="changepass_popup">
		<a class="b-close">x</a>
		<div id="passDiv1">
			<h2>Change Password</h2>
			<ul>
				<li><font color="blue"><span>Username: </span></font><span>
						<%
							out.println(session.getAttribute("userLogin"));
						%>
				</span></li>
				<li><label for="oldpass">Password cũ : </label><input
					id="oldpass" type="password" /></li>
				<li><label for="newpass1">Password mới lần thứ nhất: </label><input
					id="newpass1" type="password" /></li>
				<li><label for="newpass2">Password mới lần thứ 2: </label><input
					id="newpass2" type="password" /></li>
			</ul>
		</div>
		<div id="passDiv2">
			<ul>
				<li><input type="button" value="Xác nhận" id="submitChange" /></li>
				<li><font color="red"><span></span></font></li>
			</ul>
		</div>
</body>
</html>