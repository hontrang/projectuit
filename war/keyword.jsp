<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script src="js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="js/action.js"></script>
<script type="text/javascript" src="js/search-keyword.js"></script>
<script type="text/javascript"
	src="http://s7.addthis.com/js/250/addthis_widget.js#pubid=ra-4e344ae31e7ef1cc"></script>
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
	<div id="three-column" class="container">
		<div class="container">
			<div id="form">
				<h2>Nhập Vào Từ Khoá Cần Tìm:</h2>
				<div class="6u">
					<input id="searchBox" type="text" class="text" />
				</div>
				<h2>Nhập Vào Địa Chỉ Web Cần Tìm:</h2>
				<div class="6u">
					<input id="searchBox-url" type="text" class="text" />
				</div>
				<button class="gbqfba" id="doSearch-keyword" type="submit"
					value="Search" />
				<span id="gbqfsa">Search</span>
				</button>
				&nbsp;
				<button class="gbqfba" onClick="refreshPage()">
					<span id="gbqfsa">Tải Lại Trang</span>
				</button>
			</div>
		</div>
	</div>
	<br />
	<div id="logingg">
		<div class="addthis_toolbox addthis_default_style ">
			<a class="addthis_button_facebook"></a> <a
				class="addthis_button_zingme"></a> <a class="addthis_button_govn"></a>
			<a class="addthis_button_tagvn"></a> <a
				class="addthis_button_twitter"></a> <a
				class="addthis_button_favorites"></a> <a
				class="addthis_button_google"></a> <a
				class="addthis_counter addthis_bubble_style"></a>
		</div>
	</div>
	<div id="result">
		<!-- 		<div id="jsp_analytic_api_result" class="result"> -->
		<%-- 			<jsp:include page="analytic_api_result.jsp"></jsp:include></div> --%>
		<div id="jsp_result" class="result"><jsp:include
				page="resultKeyWord.jsp"></jsp:include></div>
	</div>
</body>
</html>