$(document)
		.ready(
				function() {
					var defaultLink = window.location.origin
							+ "/project_uit/index.jsp?url=";
					initTable();
					$.post("loaddata", {
						key : "update"
					}).done(function(response) {
						$.post("loaddata", {
							key : "select"
						}).done(function(response) {
							handle(response);
						}).fail(function(xhr, textStatus, errorThrown) {
							alert(errorThrown);
						});
					}).fail(function(xhr, textStatus, errorThrown) {
						alert(errorThrown);
					});
					function handle(response) {
						var data = response;
						var table = $('#table_id').DataTable();
						table.destroy();
						$('#table_id').DataTable({
							"data" : data,
							"bAutoWidth" : false,
							aoColumns : [ {
								"sWidth" : "8%"
							}, {
								"sWidth" : "15%"
							}, {
								"sWidth" : "15%"
							}, {
								"sWidth" : "32%"
							}, {
								"sWidth" : "15%"
							}, {
								"sWidth" : "15%"
							} ]
						});
						load(response);
						$('#table_id tfoot th').each(
								function() {
									var title = $('#table_id thead th').eq(
											$(this).index()).text();
									$(this).html(
											'<input type="text" placeholder="Search '
													+ title + '" />');
								});
						var table = $('#table_id').DataTable();

						// Apply the search
						table
								.columns()
								.eq(0)
								.each(
										function(colIdx) {
											$(
													'input',
													table.column(colIdx)
															.footer())
													.on(
															'keyup change',
															function() {
																table
																		.column(
																				colIdx)
																		.search(
																				this.value)
																		.draw();
															});
										});
						$("#table_id").show();
						$(".imageloading").hide();
					}

					function initTable() {
						$("#table_id").hide();
					}
					function load() {
						$("#table_id tbody tr")
								.each(
										function(i) {
											var sttok = "<img width=32 height=32 src='images/Ok-icon.png' class='iconstatus' />";
											var sttfailed = "<img width=32 height=32 src='images/Close-2-icon.png' class='iconstatus'/>";
											var NA = "<font size='3' color='red'>Không có</font>";
											// Hien thi cot 1
											var url = $(this).find("td").eq(1)
													.html();
											var atag = "<a target='_blank' href='"
													+ defaultLink
													+ url
													+ "' class='linkprocessing'>"
													+ url + "</a>";
											$(this).find("td").eq(1).html(atag);

											// Hien thi cot 2
											var title = $(this).find("td")
													.eq(2).html();
											var titles = title.split(";");
											var titletag = titles[0];
											if (titles[2] == "false") {
												titletag += "<br>"
														+ "<p style='color:red'>"
														+ "(" + titles[1] + ")"
														+ "</p>";
											}
											if (titletag == "") {
												$(this).find("td").eq(2).html(
														NA);
											} else {
												$(this).find("td").eq(2).html(
														titletag);
											}

											// Hien thi cot 3
											var meta = $(this).find("td").eq(3)
													.html();
											var metas = meta.split(";");
											var metatag = metas[0];
											if (metas[2] == "false") {
												metatag += "<br>"
														+ "<p style='color:red'>"
														+ "(" + metas[1] + ")"
														+ "</p>";
											}
											if (metatag == "") {
												$(this).find("td").eq(3).html(
														NA);
											} else {
												$(this).find("td").eq(3).html(
														metatag);
											}
											// Hien thi cot 4
											var img = $(this).find("td").eq(4)
													.html();

											var alts = img.split("<br>");
											var html = "";
											currAlt = "N/A";
											for (var i = 0; i < alts.length; i++) {
												if (alts[i] != "") {
													var str1 = alts[i]
															.split("|");
													// alert(str1[0]);
													var str2 = str1[0]
															.substring(
																	str1[0]
																			.lastIndexOf("/") + 1,
																	str1[0].length);
													if (str1[3] == "false") {
														if (str1[2] != "") {
															currAlt = str1[2];
														}
														html += "<ul class='sublist'><li><font size='3' color='red'><a href='"
																+ str1[0]
																+ "' target='_blank'>"
																+ str1[1]
																+ "</a></font></li><li><font size='3' color='red'>"
																+ currAlt
																+ "</font></li></ul><hr>";
													} else {
														html += "<ul class='sublist'><li><font size='3' color='blue'><a href='"
																+ str1[0]
																+ "' target='_blank'>"
																+ str1[1]
																+ "</a></font></li><li>"
																+ sttok
																+ "</li></ul><hr>";
													}
												}
											}
											if (html == "") {
												$(this).find("td").eq(4).html(
														NA);

											} else {
												$(this)
														.find("td")
														.eq(4)
														.html(
																"<ul>"
																		+ html
																		+ "</ul>");

											}
											// Hien thi cot 5
											var status = $(this).find("td").eq(
													5).html();
											var btnOverwrite = "<input type='button' value='Overwrite' class='overwrite' name='"
													+ url + "'/>";
											if (status == 1) {
												$(this).find("td").eq(5).html(
														sttok + btnOverwrite);
											} else {
												$(this).find("td").eq(5).html(
														sttfailed
																+ btnOverwrite);
											}

										});
					}
					$('#table_id tbody')
							.on(
									'click',
									'input',
									function(event) {
										var name = $(this).attr("name");
										var username = $("#option span").html();
										var d = new Date();
										var mili = d.getTime();
										var result = window
												.confirm("Bạn có muốn ghi đè dữ liệu ?");
										if (result == true) {
											$
													.post("loaddata", {
														key : "updateByLink",
														val : name,
														val1 : username,
														val2 : mili
													})
													.done(
															function(response) {
																$
																		.post(
																				"loaddata",
																				{
																					key : "select"
																				})
																		.done(
																				function(
																						response) {
																					var data = response;
																					table = $(
																							'#table_id')
																							.DataTable();
																					table
																							.destroy();
																					$(
																							'#table_id')
																							.DataTable(
																									{
																										"data" : data
																									});
																					load(response);
																				})
																		.fail(
																				function(
																						xhr,
																						textStatus,
																						errorThrown) {
																					alert(errorThrown);
																				});
															})
													.fail(
															function(xhr,
																	textStatus,
																	errorThrown) {
																alert(errorThrown);
															});
										}
									});
				});