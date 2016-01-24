$(document)
		.ready(
				function() {
					var json;
					editCss();
					function editCss() {
						$("#menu").css("width", "600px");
					}
					action();
					$("#jsp_result").hide();
					function rel_to_abs(url) {
						if (url.indexOf("http://") === 0
								|| url.indexOf("https://") === 0) {
							return url;
						} else {
							url = "http://" + url;
							return url;
						}
					}
					// $("#jsp_result").hide();
					function action() {
						$('#searchBox').click(function() {
							$("#jsp_result").hide();
						});
						$('#searchBox').keydown(function(event) {
							if (event.which === 13) {
								$("#jsp_result").show("slow");
								ajax();
							}
						});
						$("#searchBox-url").click(function() {
							$("#jsp_result").hide();
						});
						$("#searchBox-url").keydown(function(event) {
							if (event.which === 13) {
								$("#jsp_result").show("slow");
								ajax();
							}
						});
						$('#doSearch-keyword').click(function() {
							$("#jsp_result").show("slow");
							ajax();
						});
					}
					function resetHtml() {
						var loading = "<img src='images/bg-loading.gif' class='imageloading' />";
						$("#same_keyword").html(loading);
						$("#td_rank").html(loading);
						$(".linkInfo").html(loading);
					}
					function ajax() {
						resetHtml();
						var keyword = document.getElementById('searchBox').value;
						var url = $("#searchBox-url").val();
						var key1 = "keywordRecomended";
						var key2 = "checkRanking";
						url = rel_to_abs(url);
						$("#span_rank").html(
								"<font color=red>" + $("#searchBox-url").val()
										+ "</font>");
						$.post("search", {
							val1 : "searchKey",
							val2 : keyword,
							val3 : url
						}).done(function(response) {
							displayResult(response);
						}).fail(function(xhr, textStatus, errorThrown) {
							alert(errorThrown);
						});
						$.post("search", {
							val1 : "keywordRecomended",
							val2 : keyword
						}).done(function(response) {
							displaySameKeyword(response);
						}).fail(function(xhr, textStatus, errorThrown) {
							alert(errorThrown);
						});
						$.post("search", {
							val1 : "checkRanking",
							val2 : keyword,
							val3 : url
						}).done(function(response) {
							displayRanking(response);
						}).fail(function(xhr, textStatus, errorThrown) {
							alert(errorThrown);
						});
					}
					;
					function displayResult(response) {
						var countKeyWord = response.countKeyWord;
						var key0 = countKeyWord[0];
						var key1 = countKeyWord[1];
						var aTag = "";
						for (var i = 0; i < key1.length; i++) {
							var val = key1[i].split(";");
							aTag += "<tr><td class='col2' id='links'><li>["
									+ i
									+ "] <a target='blank' href='"
									+ val[0]
									+ "'>"
									+ val[0]
									+ "</a></li></td><td class='col4' align='center' id='imgLink'>"
									+ val[1] + "</tr>";
						}
						for (var i = 0; i < key0.length; i++) {
							var val = key0[i].split(";");
							aTag += "<tr><td class='col2' id='links'><li>["
									+ i
									+ "] <a target='blank' href='"
									+ val[0]
									+ "'>"
									+ val[0]
									+ "</a></li></td><td class='col4' align='center' id='imgLink'>"
									+ val[1] + "</tr>";
						}
						$(".linkInfo")
								.html(
										"<thead><tr><th><b><font size='6px''>Từ Khoá</font></b></th><th><b>Số lần xuất hiện</b></th></tr></thead><tbody>"
												+ aTag + "</tbody>");
					}
					function displaySameKeyword(response) {
						var data = response;
						var aTag = "";
						for (var i = 0; i < data.length; i++) {
							aTag += "<li>" + data[i] + "</li>";
						}
						$("#same_keyword").html("<ul>" + aTag + "</ul>");
					}
					function displayRanking(response) {
						var data = response;
						var aTag = "";
						for (var i = 0; i < data.length; i++) {
							var str = data[i].split(";");
							aTag += "<ul><li>" + str[1] + "</li><li><b>["
									+ str[0] + "]</b></li></ul><br>";
						}
						if (aTag == "") {
							$("#td_rank").html(
									"<ul align='center'><li><font color='red'>"
											+ "Không tìm thấy"
											+ "</font></li></ul>");
						} else {
							$("#td_rank").html("<ul>" + aTag + "</ul>");
						}
					}
				});