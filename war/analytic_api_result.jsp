<%-- 
    Document   : analytic_api_results
    Created on : May 5, 2014, 10:26:30 PM
    Author     : Huy Hon
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/analytic_api_result.css" rel="stylesheet" />
<!-- <script type="text/javascript" src="js/googleapi.js"></script> -->
</head>
<body>
	<h1>Information of Google Analytics Account</h1>
	<div id="analytic_result_wap">
		<div id="analytic_accountinfo">
			<label>Account</label> <select id="analytic_name"
				onchange="event_analytic_name();">
				<option disabled>Select Account</option>
			</select> <select id="analytic_property" onchange="event_analytic_property();">
				<option disabled>Select Property</option>
			</select> <select id="analytic_profile" onchange="event_analytic_profile();">
				<option disabled>Select Profile</option>
			</select>
		</div>
		<div id="siteInfo">
			<div id="siteName">
				<label>Site Name:</label> <span></span>
			</div>
		</div>
		<div id="select_time">
			<label>Start Time:</label><input type="date" id="stime"
				onchange="getTime();" /> <label>End Time</label><input type="date"
				id="etime" onchange="getTime();" />
		</div>
		<div id="analytics_info">
			<ul>
				<li id="ga_sessionCount"><label>Số phiên: </label><strong><span></span></strong>
				</li>
				<li id="ga_users"><label>Người dùng: </label><strong><span></span></strong>
				</li>
				<li id="ga_newUsers"><label>Người dùng mới: </label><strong><span></span></strong>
				</li>
				<li id="ga_avgSessionDuration"><label>Thời gian trung
						bình phiên: </label><strong><span></span></strong> giây</li>
				<li id="ga_avgTimeOnSite"><label>Thời gian truy cập
						trung bình: </label><strong><span></span></strong> giây</li>
			</ul>
		</div>
	</div>

	<!-- 	GOOGLE EMBED -->

	<div id="embed-api-auth-container"></div>
	<div id="chart-1-container"></div>
	<div id="chart-2-container"></div>
	<div id="view-selector-container"></div>
	<google-analytics-chart type="area" metrics="ga:sessions"
		dimensions="ga:date" startDate="30daysAgo" endDate="yesterday">
	</google-analytics-chart>

</body>
<script>
	(function(w, d, s, g, js, fs) {
		g = w.gapi || (w.gapi = {});
		g.analytics = {
			q : [],
			ready : function(f) {
				this.q.push(f);
			}
		};
		js = d.createElement(s);
		fs = d.getElementsByTagName(s)[0];
		js.src = 'https://apis.google.com/js/platform.js';
		fs.parentNode.insertBefore(js, fs);
		js.onload = function() {
			g.load('analytics');
		};
	}(window, document, 'script'));
</script>

<script>
	gapi.analytics
			.ready(function() {

				/**
				 * Authorize the user immediately if the user has already granted access.
				 * If no access has been created, render an authorize button inside the
				 * element with the ID "embed-api-auth-container".
				 */
				gapi.analytics.auth
						.authorize({
							container : 'embed-api-auth-container',
							clientid : '161165347819-4r47atf601d31v2526icdp0spu68f2rv.apps.googleusercontent.com',
						});

				/**
				 * Create a new ViewSelector instance to be rendered inside of an
				 * element with the id "view-selector-container".
				 */
				var viewSelector = new gapi.analytics.ViewSelector({
					container : 'view-selector-container'
				});

				// Render the view selector to the page.
				viewSelector.execute();

				/**
				 * Create a new DataChart instance for pageviews over the past 7 days.
				 * It will be rendered inside an element with the id "chart-1-container".
				 */
				var dataChart1 = new gapi.analytics.googleCharts.DataChart({
					query : {
						metrics : 'ga:pageviews',
						dimensions : 'ga:date',
						'start-date' : '7daysAgo',
						'end-date' : 'yesterday'
					},
					chart : {
						container : 'chart-1-container',
						type : 'LINE',
						options : {
							width : '100%'
						}
					}
				});

				/**
				 * Create a new DataChart instance for pageviews over the 7 days prior
				 * to the past 7 days.
				 * It will be rendered inside an element with the id "chart-2-container".
				 */
				var dataChart2 = new gapi.analytics.googleCharts.DataChart({
					query : {
						metrics : 'ga:pageviews',
						dimensions : 'ga:date',
						'start-date' : '15daysAgo',
						'end-date' : '8daysAgo'
					},
					chart : {
						container : 'chart-2-container',
						type : 'LINE',
						options : {
							width : '100%'
						}
					}
				});

				/**
				 * Render both dataCharts on the page whenever a new view is selected.
				 */
				viewSelector.on('change', function(ids) {
					dataChart1.set({
						query : {
							ids : ids
						}
					}).execute();
					dataChart2.set({
						query : {
							ids : ids
						}
					}).execute();
				});

			});
</script>
</html>
