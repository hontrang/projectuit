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
<link href="css/sendmail.css" rel="stylesheet" type="text/css"
	media="all" />
<script src="js/bpopup.min.js"></script>
<script type="text/javascript" src="js/jquery.base64.js"></script>
<script type="text/javascript" src="js/emailsetting.js"></script>
</head>
<body>
	<div id="element_to_pop_up">
		<a class="b-close"> X </a>
		<div id="config">
			<ul>
				<li><span>From: </span></li>
				<li><select id="from" name="from">
						<option></option>
				</select></li>
				<li><span>Password: </span></li>
				<li><input type="password" id="password" value="" /></li>
				<br>
				<br>
				<li><span>HOST: </span></li>
				<li><input type="text" value="smtp.gmail.com" id="host"
					name="host" /></li>
				<li><span>TYPE: </span></li>
				<li><select id="type"><option>465-SSL</option>
						<option>587-TLS</option></select></li>

			</ul>
		</div>
		<div id="recievers">
			<div id="buttonadd">
				<ul>
					<li><input type="button" value="Thêm contact" class="add" /></li>
					<li><input type="button" value="Xóa contact" id="remove" /></li>
				</ul>
			</div>
			<div id="list-contact"></div>
		</div>
		<div id="submit">
			<ul>
				<li><input type="button" value="Save" id="savebutton" /></li>
			</ul>
		</div>
	</div>
</body>
</html>