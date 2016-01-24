$(document)
		.ready(
				function() {
					var json;
					action();
					$("#jsp_result").hide();
					var defaultLink = window.location.origin
							+ "/project_uit/index.jsp?url=";
					var valueURL = document.getElementById('searchBox').value;
					if (valueURL != "") {
						$("#jsp_result").show("slow");
						ajax();
					}
					$.post("loaddata", {
						key : "loadcontact"
					}, function(response) {
						$("#from").html(loadcontact(response, false));
					});
					function loadcontact(response, loadall) {
						var user = $("#option span").html();
						user = user.trim();
						var html = "";
						if (loadall == true) {
							for (var i = 0; i < response.length; i++) {
								var strs = response[i].split(";");
								var username = strs[0];
								var email = strs[1];
								var html1 = "<option ";
								if (username == user) {
									html1 += "selected";
								}
								html1 += ">" + email + "</option>"
								html += html1;
							}
						} else {
							for (var i = 0; i < response.length; i++) {
								var strs = response[i].split(";");
								var username = strs[0];
								var email = strs[1];
								if (username == user) {
									html += "<option selected>" + email
											+ "</option>"
								}
							}
						}
						return html;
					}
					$("#inputBox-title")
							.keyup(
									function() {
										var key = document
												.getElementById('inputBox-title').value;
										$("#key-count-title").html(key.length);

									});
					$("#inputBox-meta").keyup(
							function() {
								var key = document
										.getElementById('inputBox-meta').value;
								$("#key-count-meta").html(key.length);

							});
					function rel_to_abs(url) {
						if (url.indexOf("http://") === 0
								|| url.indexOf("https://") === 0) {
							return url;
						} else {
							url = "http://" + url;
							return url;
						}
					}
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
						$('#doSearch').click(function() {
							$("#jsp_result").show("slow");
							ajax();
						});
					}
					function resetHtml() {
						var loading = "<img src='images/bg-loading.gif' class='imageloading' />";
						$(".linkInfo").html(loading);
						$(".Images").html(loading);
					}
					function ajax() {
						resetHtml();
						var temp = document.getElementById('searchBox').value;
						var url = rel_to_abs(temp);
						$("#urlInput").html(url);
						$("#charUrl").html(
								" (" + url.length.toString() + " kí tự)");
						$.post("project", {
							val : url,
							key : "title"
						}, function(response) {

							displayTitle(response);
						});
						$.post("project", {
							val : url,
							key : "meta"
						}, function(response) {
							displayMeta(response);
						});
						$.post("project", {
							val : url,
							key : "meta_image"
						}, function(response) {
							displayMetaImage(response);
						});
						$.post("project", {
							val : url,
							key : "favicon"
						}, function(response) {
							displayFavicon(response);
						});
						$.post("project", {
							val : url,
							key : "links"
						}, function(response) {
							displayLinks(response);
						});
						$.post("project", {
							val : url,
							key : "images"
						}, function(response) {
							displayImages(response);
						});
						$.post("project", {
							val : url,
							key : "getTitleRecommend"
						}).done(function(response) {
							displayTitleRecomend(response);
						});
						// $.post("project", {
						// val : url,
						// key : "getMetaRecommend"
						// }).done(function(response) {
						// displayMetaRecomend(response);
						// }).fail(function(xhr, textStatus, errorThrown) {
						// alert(errorThrown);
						// });
						$.post("project", {
							val : url,
							key : "H1Tags"
						}).done(function(response) {
							displayH1tags(response);
						});
						$.post("project", {
							val : url,
							key : "H2Tags"
						}).done(function(response) {
							displayH2tags(response);
						});
					}
					;
					function displayTitle(response) {
						var jsonResult = response;
						$("#charTit").html(
								"(" + jsonResult.title.length + " kí tự)");
						if (jsonResult.checking == "passed") {
							var show = "Số ký tự đã tối ưu (50-60 ký tự)";
							$("#imgTit")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Ok-icon.png'/><span>"
													+ show + "</span></a>");
							$("#title").html(jsonResult.title);
						} else if (jsonResult.checking == "bad") {
							var show = "Chưa tối ưu, Tốt nhất 50-60 ký tự";
							$("#imgTit")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/infomation.png'/><span>"
													+ show + "</span></a>");
							$("#title").html(jsonResult.title);
						} else {
							var show = "Không Có";
							$("#imgTit")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Close-2-icon.png'/><span>"
													+ show + "</span></a>");
							$("#title").html("<font color='red>KHÔNG CÓ");
						}
					}
					;
					function displayMeta(response) {
						var jsonResult = response;
						$("#charMeta").html(
								"(" + jsonResult.meta.length + " kí tự)");
						if (jsonResult.checking == "passed") {
							var show = "Số ký tự đã tối ưu (150-160 ký tự)";
							$("#imgMeta")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Ok-icon.png'/><span>"
													+ show + "</span></a>");
							$("#meta").html(jsonResult.meta);
						} else if (jsonResult.checking == "bad") {
							var show = "Chưa tối ưu, Tốt nhất 150-160 ký tự";
							$("#imgMeta")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/infomation.png'/><span>"
													+ show + "</span></a>");
							$("#meta").html(jsonResult.meta);
						} else {
							var show = "Không Có";
							$("#imgMeta")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Close-2-icon.png'/><span>"
													+ show + "</span></a>");
							$("#meta").html("<font color='red'>KHÔNG CÓ");
						}
					}
					;
					function displayMetaImage(response) {
						var jsonResult = response;
						if (jsonResult.checking == "passed") {
							var show = "Đã có";
							$("#imgMeta_image")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Ok-icon.png'/><span>"
													+ show + "</span></a>");
							$("#meta_image").html(jsonResult.meta_image);
						} else {
							var show = "Không Có";
							$("#imgMeta_image")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Close-2-icon.png'/><span>"
													+ show + "</span></a>");
							$("#meta_image").html(
									"<font color='red'>KHÔNG CÓ</font>");

						}
					}
					;
					function displayFavicon(response) {
						var jsonResult = response;
						$("#favicon").attr("src", jsonResult.favicon);
						if (jsonResult.checking == "passed") {
							var show = "Đã Có Logo";
							$("#imgLogo")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Ok-icon.png'/><span>"
													+ show + "</span></a>");
						} else {
							var show = "Không Có Logo";
							$("#imgLogo")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Close-2-icon.png'/><span>"
													+ show + "</span></a>");
						}
					}
					;
					function displayLinks(response) {
						var LinkInt = response.LinkInt;
						var LinkExt = response.LinkExt;
						var size = response.totalLink;
						var baseUrl = response.baseurl;
						var aTag = "";
						// aTag += "<h1 style='color:blue'>Link internal of "+
						// baseUrl +"</h1>";
						for (var i = 0; i < LinkInt.length; i++) {
							var val = LinkInt[i].split(";");
							var img = "images/Close-2-icon.png";
							var show = "Thiếu Thẻ Title hoặc Meta Description";
							if (val[1] == "passed") {
								img = "images/Ok-icon.png";
								show = "Đủ Thẻ Title và Meta Description, Đạt Chuẩn";
							}
							if (val[1] == "bad") {
								img = "images/infomation.png";
								show = "Số Kí Tự Thẻ Title hoặc Meta Description Chưa Tối Ưu";
							}
							aTag += "<tr><td class='col2' id='links'><li>["
									+ i
									+ "] <a target='_blank' href='"
									+ defaultLink
									+ val[0]
									+ "' class='linkprocessing'>"
									+ val[0]
									+ "</a></li></td><td class='col4' align='center' id='imgLink'><a class='tooltip' target='_blank' href='"
									+ "http://localhost:10156/project_uit/index.jsp?url="
									+ val[0]
									+ "'><img class='statusLink' width=32px height=32px src='"
									+ img + "' /><span>" + show
									+ "</span></a></td></tr>";
						}
						// aTag += "<h1 style='color:blue'>Link external of "+
						// baseUrl +"</h1>";
						// for (var i = 0; i < LinkExt.length; i++) {
						// aTag += "<li>[" + i + "] <a target='blank' href='"
						// + LinkExt[i] + "'>" + LinkExt[i] + "</a></li>";
						// }
						$(".linkInfo").html(aTag);
					}
					;
					function displayImages(response) {
						var array = response.images;
						var array0 = array[0];
						var array1 = array[1];
						var array2 = array[2];
						var aTag = "";
						for (var i = 0; i < array1.length; i++) {
							var val = array1[i].split(";");

							aTag += "<tr><td><a target='_blank' href='"
									+ val[0]
									+ "'><img src='"
									+ val[0]
									+ "' alt='"
									+ val[1]
									+ "' width='240px' height='240px' ></a><br/></td><td><font color='blue'>Từ khoá thẻ Alt:</font> "
									+ val[1]
									+ "<font color='blue'>"
									+ " (Được đề xuất)"
									+ "</font><br /><label><font color='blue'>Sửa từ khoá Alt:</font></lable><input type='text' class='fixAlt' value='"
									+ val[1]
									+ "' /><br/><a target='_blank' href='"
									+ val[0]
									+ "'>"
									+ "<font color='blue'>Url hình ảnh:</font><p id='imgLink'>"
									+ val[0] + "</p>" + "</a>" + "</td></tr>";
						}
						for (var i = 0; i < array0.length; i++) {
							var val = array0[i].split(";");
							aTag += "<tr><td><a target='_blank' href='"
									+ val[0]
									+ "'><img src='"
									+ val[0]
									+ "' alt='"
									+ val[1]
									+ "' width='240px' height='240px' ></a><br/></td><td><font color='blue'>Từ khoá thẻ Alt:</font> "
									+ val[1]
									+ "<font color='red'>"
									+ " (Không có nội dung)"
									+ "</font><br /><label><font color='blue'>Sửa từ khoá Alt:</font></lable><input type='text' class='fixAlt'/><br/><a target='_blank' href='"
									+ val[0]
									+ "'>"
									+ "<font color='blue'>Url hình ảnh:</font><p id='imgLink'>"
									+ val[0] + "</p>" + "</a>" + "</td></tr>";
						}
						for (var i = 0; i < array2.length; i++) {
							var val = array2[i].split(";");
							aTag += "<tr><td><a target='_blank' href='"
									+ val[0]
									+ "'><img src='"
									+ val[0]
									+ "' alt='"
									+ val[1]
									+ "' width='240px' height='240px' ></a><br/></td><td><font color='blue'>Từ khoá thẻ Alt:</font> "
									+ val[1]
									+ "<font color='blue'>"
									+ " (Hiện tại)"
									+ "</font><br /><label><font color='blue'>Sửa từ khoá Alt:</font></lable><input type='text' class='fixAlt'/><br/><a target='_blank' href='"
									+ val[0]
									+ "'>"
									+ "<font color='blue'>Url hình ảnh:</font><p id='imgLink'>"
									+ val[0] + "</p>" + "</a>" + "</td></tr>";
						}
						$(".Images").html(aTag);
					}
					;
					function displayTitleRecomend(response) {
						var keyword = response.getTitleRecommend;
						// alert(keyword.length);
						var aTag = "";
						for (var i = keyword.length - 1; i >= 0; i--) {
							var subArray = keyword[i];
							if (subArray.length == 0) {

							} else {
								var subTag = "";
								for (var j = 0; j < subArray.length; j++) {
									subTag += subArray[j] + ", ";
								}
								subTag = subTag.substring(0, subTag.length - 2);
								aTag += "<tr><td>"
										+ subTag
										+ "</td><td class='col4' align='center'>"
										+ i + "</td></tr>";
							}
						}
						$(".keywordInfo tbody").html(aTag);
					}
					;
					// function displayMetaRecomend(response) {
					// $("#meta li")
					// .eq(1)
					// .append(
					// "<br /><p><font color='blue'>"
					// + "Từ khoá nên có trong Meta Description: "
					// + "</font>"
					// + "<font color='red'>"
					// + response.getMetaRecommend
					// + "</font></p>");
					// }
					// ;
					function displayH1tags(response) {
						var jsonResult = response;
						$("#H1tags").append(jsonResult.H1tags);
						if (response.checking == "passed") {
							var show = "Đã Có thẻ H1";
							$("#imgH1")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Ok-icon.png'/><span>"
													+ show + "</span></a>");
						} else {
							var show = "Không Có thẻ H1";
							$("#imgH1")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Close-2-icon.png'/><span>"
													+ show + "</span></a>");
						}

					}
					function displayH2tags(response) {
						var jsonResult = response;
						$("#H2tags").append(jsonResult.H2tags) + ", ";
						if (response.checking == "passed") {
							var show = "Đã Có thẻ H2";
							$("#imgH2")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Ok-icon.png'/><span>"
													+ show + "</span></a>");
						} else {
							var show = "Không Có thẻ H2";
							$("#imgH2")
									.html(
											"<a class='tooltip' target='_blank' href='#'><img width=32px height=32px src='images/Close-2-icon.png'/><span>"
													+ show + "</span></a>");
						}

					}
				});