<%-- 
    Document   : result
    Created on : Nov 5, 2013, 7:33:09 PM
    Author     : Huy Hon
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page
	import="java.io.*,java.util.*,javax.mail.*"%>
<%@ page import="javax.mail.internet.*,javax.activation.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/result.css" rel="stylesheet" type="text/css" media="all" />
<link href="css/tooltip.css" rel="stylesheet" type="text/css"
	media="all" />
<link href="css/sendmail.css" rel="stylesheet" type="text/css"
	media="all" />
<script src="js/bpopup.min.js"></script>
<script src="js/MD5.js" type="text/javascript"></script>
<script type="text/javascript">
	//<![CDATA[
	function hienlink() {
		document.getElementById('hienlink').style.display = 'inline';
		document.getElementById('anlink').innerHTML = '<a href="javascript:anlink()"><b>Click để ẩn nội dung</b></a></span>';
	}
	function anlink() {
		document.getElementById('hienlink').style.display = 'none';
		document.getElementById('anlink').innerHTML = '<a href="javascript:hienlink()"><b>Nội dung đã được ẩn Click để hiện nội dung</b></a>';
	}
	function hienimage() {
		document.getElementById('hienimage').style.display = 'inline';
		document.getElementById('animage').innerHTML = '<a href="javascript:animage()"><b>Click để ẩn nội dung</b></a></span>';
	}
	function animage() {
		document.getElementById('hienimage').style.display = 'none';
		document.getElementById('animage').innerHTML = '<a href="javascript:hienimage()"><b>Nội dung đã được ẩn Click để hiện nội dung</b></a>';
	}
	function hientukhoa() {
		document.getElementById('hientukhoa').style.display = 'inline';
		document.getElementById('antukhoa').innerHTML = '<a href="javascript:antukhoa()"><b>Click để ẩn nội dung</b></a></span>';
	}
	function antukhoa() {
		document.getElementById('hientukhoa').style.display = 'none';
		document.getElementById('antukhoa').innerHTML = '<a href="javascript:hientukhoa()"><b>Nội dung đã được ẩn Click để hiện nội dung</b></a>';
	}
	//]]>
</script>
<script type="text/javascript">
	//Semicolon (;) to ensure closing of earlier scripting
	// Encapsulation
	// $ is assigned to jQuery
</script>
</head>
<body>
	<h2 align="left">1. THÔNG TIN VỀ SEO HIỆN TẠI CỦA TRANG WEB</h2>
	<br>
	<table>
		<tr>
			<td><h2>Result for:</h2></td>
			<td><a href="#" id="urlInput"></a></td>
			<td align="center"><a class="tooltip" target="_blank" href="#"><img
					width=32px height=32px src="images/no_image.jpg" id="favicon" /><span>Logo
						của trang Web</span></a></td>
			<td class="col4" align="center" id="imgLogo"></td>
		</tr>
		<tr>
			<td class="col1"><h2>Title</h2></td>
			<td class="col2" id="title">
				<li></li>
			</td>
			<td class="col3" id="charTit" align="center"></td>
			<td class="col4" align="center" id="imgTit"></td>
		</tr>
		<tr>
			<td class="col1"><h2>Meta Description</h2></td>
			<td class="col2" id="meta"><li></li>
				<li></li></td>
			<td class="col3" id="charMeta" align="center"></td>
			<td class="col4" align="center" id="imgMeta"></td>
		</tr>
		<tr>
			<td class="col1"><h2>Meta Image</h2></td>
			<td class="col2" id="meta_image"></td>
			<td class="col3" id="charMeta_image" align="center"><font
				color="blue">Hiển thị ảnh khi chia sẻ Facebook</font></td>
			<td class="col4" align="center" id="imgMeta_image"></td>
		</tr>
	</table>
	<br>
	<h2 align="left">2. H1 Tags</h2>
	<table align="center" class="H1Info">
		<tr>
			<td class="col3" id="charH1" align="center">Kiểm tra H1:</td>
			<td class="col4" align="center" id="imgH1"></td>
		</tr>
	</table>
	<h2 align="left">3. H2 Tags</h2>
	<table align="center" class="H2Info">
		<tr>
			<td class="col3" id="charH2" align="center">Kiểm tra H2:</td>
			<td class="col4" align="center" id="imgH2"></td>
		</tr>
	</table>
	<h2 align="left">4. KIỂM TRA SEO CỦA TẤT CẢ CÁC LINKS CẤP 1</h2>
	<br>
	<div align="center">
		<a></a> <span id='anlink'><a href="javascript:hienlink()"><b>Nội
					dung đã được ẩn Click để hiện nội dung</b></a></span><br> <span id='hienlink'
			style='display: none;'>
			<table id="link" align="center" class="linkInfo">
				<tr>
					<td class="col2" id="links"><img src="images/bg-loading.gif"
						class="imageloading" /></td>
				</tr>
			</table> <br> <a href="javascript:anlink()"><b>Click để ẩn nội
					dung</b></a><br>
		</span>
	</div>
	<br>
	<h2 align="left">5. ĐỀ XUẤT NỘI DUNG CHO THẺ ALT CỦA HÌNH ẢNH</h2>
	<br>
	<div align="center">
		<span id='animage'><a href="javascript:hienimage()"><b>Nội
					dung đã được ẩn Click để hiện nội dung</b></a></span><br> <span
			id='hienimage' style='display: none;'>
			<table id="image" align="center" class="Images" align="center">
				<tr>
					<td class="col2" align="center" id="Images"><img
						src="images/bg-loading.gif" class="imageloading" /></td>
				</tr>
			</table> <br> <a href="javascript:animage()"><b>Click để ẩn nội
					dung</b></a><br>
		</span>
	</div>
	<br>
	<h2 align="left">6. THÔNG TIN VỀ TỪ KHÓA CỦA TRANG WEB</h2>
	<div class="container">
		<div id="form">
			<p>
				<b>Tạo Title:</b><font color="blue">(Từ 50-60 ký tự)</font>
			</p>
			<ul>
				<li>
					<div class="6u">
						<textarea rows="1" cols="60" id="inputBox-title"></textarea>
					</div>
				</li>
				<li><span id="key-count-title"
					style="color: blue; font-size: 2.5em"></span></li>
				<!-- 					<li><div id="key-image"></div></li> -->
			</ul>
			<p>
				<b>Tạo Meta Description:</b><font color="blue">(Từ 150-160 ký
					tự)</font>
			</p>
			<ul>
				<li>
					<div class="6u">
						<textarea rows="1" cols="60" id="inputBox-meta"></textarea>
					</div>
				</li>
				<li><span id="key-count-meta"
					style="color: blue; font-size: 2.5em"></span></li>
				<!-- 					<li><div id="key-image"></div></li> -->
			</ul>
		</div>
		<div id="mail-result">
			<input type="button" value="SEND EMAIL" id="sendmail" />
			<div id="loading_popup">
				<a class="b-close">x<a /> <span><img
						src="images/bg-loading.gif" class="imageloading" /></span>
			</div>
		</div>
	</div>

	<div align="center">
		<a></a> <span id='antukhoa'><a href="javascript:hientukhoa()"><b>Nội
					dung đã được ẩn Click để hiện nội dung</b></a></span><br> <span
			id='hientukhoa' style='display: none;'>
			<table id="keyword" align="center" class="keywordInfo">
				<thead>
					<tr>
						<th><b><font size="5px">Từ Khoá</font></b></th>
						<th><b>Số lần xuất hiện</b></th>
					</tr>
				</thead>
				<tbody></tbody>
			</table> <br> <a href="javascript:antukhoa()"><b>Click để ẩn nội
					dung</b></a><br>
		</span>
	</div>
	<br>
</body>
</html>