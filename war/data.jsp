<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page session="true"%>
<%
	if (session.getAttribute("userLogin") == null) {
		response.sendRedirect("login.jsp");
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SEO Tool</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="css/default.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/result.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
<link href="css/data.css" rel="stylesheet" type="text/css" media="all" />
<script src="js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf8"
	src="js/jquery.dataTables.js"></script>
<script src="js/MD5.js" type="text/javascript"></script>
<script src="js/loaddata.js"></script>
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
					<li><a href="" accesskey="5" class="my-button">CẤU HÌNH
							EMAIL</a><jsp:include page="emailsetting.jsp"></jsp:include></li>
					<li><a href="" accesskey="6" class="changepassword">CHANGE
							PASSWORD</a><jsp:include page="changePass.jsp"></jsp:include></li>
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
	<div id="result">
		<div class="result">
			<div id="option">
				<ul>
					<li><span> <%
 	out.println(session.getAttribute("userLogin"));
 %>
					</span></li>
					<li><a href="logout.jsp">logout</a></li>
<!-- 					<br> -->
<%-- 					<li><a href="" class="changepassword">change pasword</a><jsp:include --%>
<%-- 							page="changePass.jsp"></jsp:include></li> --%>
				</ul>
			</div>
			<table id='table_id' class='display'>
				<thead>
					<tr>
						<th>Date Time</th>
						<th>URL</th>
						<th>TITLE</th>
						<th>META DESCRIPTION</th>
						<th>ALT</th>
						<th>STATUS</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th>Date Time</th>
						<th>URL</th>
						<th>TITLE</th>
						<th>META DESCRIPTION</th>
						<th>ALT</th>
						<th>STATUS</th>
					</tr>
				</tfoot>
			</table>
			<img src="images/bg-loading.gif" class="imageloading" />
		</div>
	</div>
</body>
</html>