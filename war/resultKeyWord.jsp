<%-- 
    Document   : result
    Created on : Nov 5, 2013, 7:33:09 PM
    Author     : Huy Hon
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	//<![CDATA[
	function hienkeyword() {
		document.getElementById('hienkeyword').style.display = 'inline';
		document.getElementById('ankeyword').innerHTML = '<a href="javascript:ankeyword()"><b>Click để ẩn nội dung</b></a></span>';
	}
	function ankeyword() {
		document.getElementById('hienkeyword').style.display = 'none';
		document.getElementById('ankeyword').innerHTML = '<a href="javascript:hienkeyword()"><b>Nội dung đã được ẩn Click để hiện nội dung</b></a>';
	}
	//]]>
</script>
</head>
<body>
	<h2>TỪ KHOÁ TƯƠNG TỰ</h2>
	<br>
	<table id="keyword" align="center" class="same_keywordInfo">
		<tr>
			<td class="col2" id="same_keyword"><img
				src="images/bg-loading.gif" class="imageloading" /></td>
			</td>
		</tr>
	</table>
	<br>
	<h2>
		VỊ TRÍ CỦA TRANG <span id="span_rank"></span> TRONG TOP 100 CỦA
		GOOGLE.
	</h2>
	<br>
	<table id="rank" align="center" class="rank_Info">
		<tr>
			<td class="col2" id="td_rank"><img src="images/bg-loading.gif"
				class="imageloading" /></td>
			</td>
		</tr>
	</table>
	<br>
	<h2 align="left">TÌM TỪ KHOÁ TRONG TẤT CẢ CÁC LINKS CẤP 1</h2>
	<br>
	<div align="center">
		<span id='ankeyword'><a href="javascript:hienkeyword()"><b>Nội dung đã được ẩn Click để hiện nội dung</b></a></span><br>
		<span id='hienkeyword' style='display: none;'>
			<table id="keywordInfo" align="center" class="linkInfo">
				<thead>
					<tr>
						<th><b><font size="6px">Từ Khoá</font></b></th>
						<th><b>Số lần xuất hiện</b></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td class="col2" id="links" colspan="2"><img
							src="images/bg-loading.gif" class="imageloading" /></td>
					</tr>
				</tbody>
			</table> <a href="javascript:ankeyword()"><b>Click
						để ẩn nội dung</b></a><br>
		</span>
	</div>
	<br>

</body>
</html>