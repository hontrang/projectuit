$(document)
		.ready(
				function() {
					var global_datenum = 0;
					var global_datetime = "";
					var global_url = "";
					var global_title = "";
					var global_meta = "";
					var global_alt = "";
					var global_h1 = "";
					var global_h2 = "";
					$("#sendmail").bind("click", function(e) {
						e.preventDefault();

						// Triggering bPopup when click event is
						// fired
						$('#loading_popup').bPopup();
						var user = $("#option span").html();
						user = user.trim();
						$.post("loaddata", {
							key : "loadmailinfo",
							key1 : user
						}, function(response) {
							sendMail(response);
						});
					});
					function sendMail(response) {
						var rs = response[0];
						var user = rs[1];
						if (rs[2] != "") {
							var ary = rs[2].split(";");
							var pass = ary[0];
							var host = ary[1];
							var type = ary[2];
						}
						var body = getBodyMail();
						var subject = getDateMail();
						var recievers = rs[3];
						$
								.post("mail", {
									key : "sendmail",
									val1 : recievers,
									val2 : body,
									val3 : subject,
									val4 : user,
									val5 : pass,
									val6 : host,
									val7 : type
								})
								.done(function(response) {
									var bPopup = $("#loading_popup").bPopup();
									bPopup.close();
									alert("Email đã được gởi đi.");
									updateToDB();
								})
								.fail(
										function(xhr, textStatus, errorThrown) {
											var bPopup = $("#loading_popup")
													.bPopup();
											bPopup.close();
											alert("Không gởi được. Vui lòng kiểm tra lại cấu hình email!");
										});
					}
					function updateToDB() {
						var address = $("#from").val();
						$.post("mail", {
							key : "updatedata",
							val1 : global_datenum,
							val2 : global_datetime,
							val3 : global_url,
							val4 : global_title,
							val5 : global_meta,
							val6 : global_alt,
							val7 : address
						}, function(response) {
						});
					}
					function rel_to_abs(url) {
						if (url.indexOf("http://") === 0
								|| url.indexOf("https://") === 0) {
							return url;
						} else {
							url = "http://" + url;
							return url;
						}
					}
					function getDateMail() {
						var d = new Date();
						var url = document.getElementById('searchBox').value;
						url = rel_to_abs(url);
						var mili = d.getTime();
						var date = d.getDate();
						var month = d.getMonth() + 1;
						var year = d.getFullYear();
						global_url = url;
						global_datenum = mili;
						global_datetime = year + "/" + month + "/" + date;
						return "[" + mili + "]" + date + "/" + month + "/"
								+ year + "[" + url + "]";
					}
					function getBodyMail() {
						var url = "URL: "
								+ document.getElementById('searchBox').value
								+ "\n";
						var title = "TITLE: " + $("#inputBox-title").val()
								+ "\n";
						var meta = "META: " + $("#inputBox-meta").val() + "\n";
						var fixAlt = "ALT: " + "\n";
						var allList = $(".fixAlt");
						var alt = $(".Images").find(allList);
						global_alt = "";
						for (var i = 0; i < alt.length; i++) {
							if ($(alt[i]).val() != "") {
								var parent = $(alt[i]).parent();
								var link = $(parent).find("#imgLink").html();
								global_alt += link + "|" + $(alt[i]).val()
										+ "<br>";
								fixAlt += link + "  [" + $(alt[i]).val() + "]"
										+ "\n";
							}
						}
						global_title = $("#inputBox-title").val();
						global_meta = $("#inputBox-meta").val();
						return url + title + meta + fixAlt;
					}
				});