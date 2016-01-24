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
<!-- <link -->
<!-- 	href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600,700,900" -->
<!-- 	rel="stylesheet" /> -->
<link href="css/default.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="css/fonts.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/data.css" rel="stylesheet" type="text/css" media="all" />
<script src="js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.base64.js"></script>
<script type="text/javascript" src="js/action.js"></script>
<script type="text/javascript" src="js/sendmail.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#slidedown").click(function() {
			$("#slideup").slideToggle('slow');
		});
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#slidedown1").click(function() {
			$("#slideup1").slideToggle('slow');
		});
	});
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#slidedown2").click(function() {
			$("#slideup2").slideToggle('slow');
		});
	});
</script>
<script type="text/javascript" language="javascript">
	function refreshPage() {
		location.reload(true);
	}
</script>
<!-- <script type="text/javascript" -->
<!-- 	src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-4e344ae31e7ef1cc"></script> -->
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
						<color ="yellow" />
						Công Cụ Hỗ Trợ SEO
					</h1></a>
			</div>
		</div>
	</div>
	<div id="three-column" class="container">
		<div class="container">
			<div id="form">
				<h2>Kiểm Tra SEO Cho Trang Web:</h2>
				<div class="6u">
					<%
						if (request.getParameter("url") != null) {
							out.print("<input id='searchBox' type='text' class='text' value='"
									+ request.getParameter("url") + "' />");
						} else {
							out.print("<input id='searchBox' type='text' class='text' value='' />");
						}
					%>

				</div>
				<button class="gbqfba" id="doSearch" type="submit" value="Search" />
				<span id="gbqfsa">Kiểm Tra SEO</span>
				</button>
				&nbsp;
				<button class="gbqfba" onClick="refreshPage()">
					<span id="gbqfsa">Tải Lại Trang</span>
				</button>
			</div>
			<br>
			<div id="option" style="margin-right: 270px;">
				<ul>
					<li><span> <%
 	out.println(session.getAttribute("userLogin"));
 %>
					</span></li>
					<li><a href="logout.jsp">logout</a></li>
					<br>
					<li><a href="" class="newaccount">Sign up</a><jsp:include
							page="newAccount.jsp"></jsp:include></li>
				</ul>
			</div>
		</div>
	</div>
	<br>
	<div id="result">
		<div id="jsp_result" class="result"><jsp:include
				page="result.jsp"></jsp:include></div>
	</div>
</body>
</html>