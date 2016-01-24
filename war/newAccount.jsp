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
<link href="css/newaccount.css" rel="stylesheet" type="text/css"
	media="all" />
<script src="js/bpopup.min.js"></script>
<script type="text/javascript" src="js/newaccount.js"></script>
</head>
<body>
	<div id="newaccount_popup">
		<a class="b-close">x</a>
		<div id="passDiv1">
			<h2>Sign Up</h2>
			<ul>
				<li><label for="accname">Tên tài khoản : </label><input
					id="accname" type="text" /></li>
				<li><label for="password1">Password: </label><input
					id="password1" type="password" /></li>
				<li><label for="password2">Gõ lại password: </label><input
					id="password2" type="password" /></li>
				<li><label for="email">Email: </label><input id="email"
					type="text" placeholder="example@abc.com" /></li>
			</ul>
		</div>
		<div id="passDiv2">
			<ul>
				<li><input type="button" value="Xác nhận" id="submitSignUp" /></li>
				<li><font color="red"><span></span></font></li>
			</ul>
		</div>
</body>
</html>