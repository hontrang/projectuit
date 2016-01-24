<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%
	if (session.getAttribute("userLogin") != null) {
		response.sendRedirect("index.jsp");
	}
%>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login to account</title>
<meta name="description" content="Custom Login Form Styling with CSS3" />
<meta name="keywords"
	content="css3, login, form, custom, input, submit, button, html5, placeholder" />
<meta name="author" content="Codrops" />
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="css/login.css" />
<link href="css/default.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/result.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
<script src="js/jquery.js"></script>
<script src="js/MD5.js"></script>
<script src="js/login.js"></script>
<!--[if lte IE 7]><style>.main{display:none;} .support-note .note-ie{display:block;}</style><![endif]-->
<style>
@import url(http://fonts.googleapis.com/css?family=Raleway:400,700);

body {
	background: #7f9b4e url(images/scr01.jpg) no-repeat center top;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	background-size: cover;
}

.container>header h1, .container>header h2 {
	color: #fff;
	text-shadow: 0 1px 1px rgba(0, 0, 0, 0.7);
}
</style>
</head>
<body>
	<div id="header-wrapper">
		<div id="header" class="container">
			<div id="menu" style="background-color: #330033;">
				<ul>
					<li><a href="keyword.jsp" accesskey="2" title="">TỪ KHÓA</a></li>
					<li><a href="index.jsp" accesskey="1" title="">Kiểm tra
							SEO</a></li>
					<li><a href="data.jsp" accesskey="3" title="">CƠ SỞ DỮ
							LIỆU</a></li>
					<li><a href="log.jsp" accesskey="4" title="">XEM LOG</a></li>
				</ul>
			</div>
			<div id="logo">
				<a href="index.jsp"><h1>
						<color ="yellow"/>
						Công Cụ Hỗ Trợ SEO
					</h1></a>
			</div>
		</div>
	</div>
	<div class="container">
		<form class="form-4">
			<h1>Login accout</h1>
			<%
				Cookie[] cookies = request.getCookies(); // request is an instance of type 
				//HttpServletRequest
				boolean foundCookie = false;
				String username = "";
				for (int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					if (c.getName().equals("cookieName")) {
						username = c.getValue();
						foundCookie = true;
					}
				}
			%>
			<p>
				<label for="login">Username or email</label> <input type="text"
					name="username" placeholder="Username or email" required
					value="<%if (foundCookie == true) {
				out.print(username);
			}%>" />
			</p>
			<p>
				<label for="password">Password</label> <input type="password"
					name='password' placeholder="Password" required>
			</p>
			<p>
				<input type="checkbox" name="remember" value="remember">
				Remember username<br>
			</p>
			<p>
				<input type="button" name="submit" value="Login" id="submit_form">
			</p>
			<br />

			<p>
				<span></span>
			<p>
		</form>
		
	</div>
</body>
</html>