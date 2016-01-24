$(document)
		.ready(
				function() {
					$('.my-button').bind('click', function(e) {
						// Prevents the default action to be
						// triggered.
						e.preventDefault();

						// Triggering bPopup when click event is
						// fired
						$('#element_to_pop_up').bPopup();
						$.post("loaddata", {
							key : "loadcontact"
						}, function(response) {
							$("#from").html(loadcontact(response, false));
							var email = $("#from").val();
							$.post("loaddata", {
								key : "loadsetting",
								key1 : email
							}, function(response) {
								loadsetting(response);
							});
						});

					});
					function loadsetting(response) {
						var rs = response[0];
						var setting = rs[2];
						var contacts = rs[3];
						if (setting != "") {
							var array1 = setting.split(";");
							var password = $.base64('decode', array1[0]);
							$("#password").val(password);
							$("#host").val(array1[1]);
							$("#type").val(array1[2]);
						}
						if (contacts != "") {
							var array2 = contacts.split(";");
							var aTag = "";
							for (var i = 0; i < array2.length; i++) {
								var values = array2[i].split(":");
								var mode = values[0];
								var cont = values[1];
								if (mode == "to") {
									aTag += "<ul class='appended'><li><select class='mode'><option value='1' selected><span>To: </span></option><option value='2'><span>Cc: </span></option><option value='3'><span>Bcc: </span></option></select></li><li><select class='detail'><option>"
											+ cont
											+ "</option></select></li></ul>";
								}
								if (mode == "cc") {
									aTag += "<ul class='appended'><li><select class='mode'><option value='1'><span>To: </span></option><option value='2' selected><span>Cc: </span></option><option value='3'><span>Bcc: </span></option></select></li><li><select class='detail'><option>"
											+ cont
											+ "</option></select></li></ul>";
								}
								if (mode == "bcc") {
									aTag += "<ul class='appended'><li><select class='mode'><option value='1' ><span>To: </span></option><option value='2'><span>Cc: </span></option><option value='3' selected><span>Bcc: </span></option></select></li><li><select class='detail'><option>"
											+ cont
											+ "</option></select></li></ul>";
								}
							}
							// alert(aTag);
							$("#list-contact").html(aTag);
						}
					}
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
					$(".add")
							.bind(
									'click',
									function() {
										$
												.post(
														"loaddata",
														{
															key : "loadcontact"
														},
														function(response) {
															var html = "";
															html += "<ul class='appended'><li><select class='mode'><option value='1'><span>To: </span></option><option value='2'><span>Cc: </span></option><option value='3'><span>Bcc: </span></option></select></li><select class='detail'>";
															html += loadcontact(
																	response,
																	true);
															html += "</select><li></li></ul>";
															$("#list-contact")
																	.append(
																			html);
														});
									});
					$("#savebutton").on("click", function() {
						var userlogin = $("#option span").html().trim();
						var user = $("#from").val();
						var pass = $("#password").val();
						pass = $.base64('encode', pass);
						var recievers = "";
						var host = $("#host").val();
						var type = $("#type").val();
						$("#list-contact ul").each(function() {
							var reciever = "";
							var mode = $(this).find("select").eq(0).val();
							var detail = $(this).find("select").eq(1).val();
							if (mode == 1) {
								reciever += "to:" + detail;
							}
							if (mode == 2) {
								reciever += "cc:" + detail;
							}
							if (mode == 3) {
								reciever += "bcc:" + detail;
							}
							recievers += reciever + ";";
						});
						$.post("loaddata", {
							key : "savedata",
							val1 : user,
							val2 : pass,
							val3 : host,
							val4 : type,
							val5 : recievers,
							val6 : userlogin
						}).done(function(response) {
							alert("Lưu thành công");
						}).fail(function(xhr, textStatus, errorThrown) {
							alert("Không lưu được");
						});
					});
					// $("#addemail").on(
					// "click",
					// function() {
					// var address = prompt("Nhập địa chỉ gmail",
					// "example@gmail.com");
					// if (address != null) {
					// $.post("mail", {
					// key : "addemailaddress",
					// val1 : address
					// }).done(function(response) {
					// alert("Thêm thành công");
					// }).fail(
					// function(xhr, textStatus,
					// errorThrown) {
					// alert("Lỗi");
					// });
					// }
					// });
					$("#remove").on('click', function() {
						var length = $("#list-contact").find("ul").length;
						$("#list-contact").find("ul").eq(length - 1).remove();
					});
				});