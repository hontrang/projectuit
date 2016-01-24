//////////////////////////////////////////////
//CÃ¡c hÃ m Ä‘Äƒng nháº­p google plus account
/////////////////////////////////////////////
var profile, email;
var apiKey = 'AIzaSyAxrv9mt6GnLbN6gH2tdM8TDJF6IjLp2UE';
var websiteUrl;
var columns;
var start_time = getToday();
var end_time = getToday();
function getToday() {
	var now = new Date();
	var year = now.getFullYear();
	var month = now.getMonth() + 1;
	if (month < 10)
		month = "0" + month;
	var date = now.getDate();
	if (date < 10) {
		date = "0" + date;
	}
	return year + "-" + month + "-" + date;
}
window.onload = initValue;
function initValue() {
	document.getElementById("stime").value = getToday();
	document.getElementById("etime").value = getToday();
}
/*
 * Triggered when the user accepts the sign in, cancels, or closes the
 * authorization dialog.
 */
function loginFinishedCallback(authResult) {
	if (authResult) {
		if (authResult['error'] == undefined) {
			toggleElement('signin-button');
			// Hide the sign-in button after
			// successfully signing in the user.
			gapi.client.setApiKey(apiKey);
			gapi.client.load('plus', 'v1', loadProfile);
			// Trigger request
			// to get the email
			// address.
			getinfo();
		} else {
			console.log('An error occurred');
		}
	} else {
		console.log('Empty authResult');
		// Something went wrong
	}
}

/**
 * Uses the JavaScript API to request the user's profile, which includes their
 * basic information. When the plus.profile.emails.read scope is requested, the
 * response will also include the user's primary email address and any other
 * email addresses that the user made public.
 */
function loadProfile() {
	var request = gapi.client.plus.people.get({
		'userId' : 'me'
	});
	request.execute(loadProfileCallback);
}

/**
 * Callback for the asynchronous request to the people.get method. The profile
 * and email are set to global variables. Triggers the user's basic profile to
 * display when called.
 */
function loadProfileCallback(obj) {
	profile = obj;
	// Filter the emails object to find the user's primary account, which might
	// not always be the first in the array. The filter() method supports IE9+.
	email = obj['emails'].filter(function(v) {
		return v.type === 'account'; // Filter out the primary email
	})[0].value;
	// get the email from the filtered results, should always be
	// defined.
	displayProfile(profile);
}

/**
 * Display the user's basic profile information from the profile object.
 */
function displayProfile(profile) {
	document.getElementById('name').innerHTML = profile['displayName'];
	document.getElementById('pic').innerHTML = '<img src="'
			+ profile['image']['url'] + '" />';
	document.getElementById('email').innerHTML = email;
	var disbutton = document.getElementById("loginGGAccount");
	disbutton.onclick = disconnect;
	toggleElement('profile');
}

/**
 * Utility function to show or hide elements by their IDs.
 */
function toggleElement(id) {
	var el = document.getElementById(id);
	if (el.getAttribute('class') == 'hide') {
		el.setAttribute('class', 'show');
	} else {
		el.setAttribute('class', 'hide');
	}
}

// ////////////////////////////////////////////
// HÃ m disconnect account google plus
// ////////////////////////////////////////////
function disconnect() {
	$.ajax({
		type : 'GET',
		url : 'https://accounts.google.com/o/oauth2/revoke?token='
				+ gapi.auth.getToken().access_token,
		async : false,
		contentType : 'application/json',
		dataType : 'jsonp',
		success : function(result) {
			toggleElement("signin-button");
			toggleElement('profile');
			alert("Log out google plus account");
		},
		error : function(e) {
			console.log(e);
		}
	});
}

// ///////////////////////////////////////////////////////////////////////
// CÃ¡c hÃ m get thÃ´ng tin cáº§n thiáº¿t cá»§a google analytics
// //////////////////////////////////////////////////////////////////////
function getinfo() {
	gapi.client.load('analytics', 'v3', function() {
		var list = gapi.client.analytics.management.accounts.list();
		list.execute(handleAccounts);
	});
}

function storage(key, value) {
	if (typeof (Storage) !== "undefined") {
		sessionStorage.setItem(key, value);
	} else {
		alert("Sorry! Your browser not support!");
	}
}

function handleAccounts(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {
			// Get the first Google Analytics account
			storage("account", JSON.stringify(results));
			var selc = document.getElementById("analytic_name");
			var str = "<option disabled>Select Account</option>";
			for (var i = 0; i < results.items.length; i++) {
				var tmp = "<option class='extInfo' accountId='"
						+ results.items[i].id + "'>" + results.items[i].name
						+ "</option>";
				str += tmp;
			}
			selc.innerHTML = str;
			queryWebproperties(results.items[0].id);
		} else {
			console.log('No accounts found for this user.');
		}
	} else {
		console.log('There was an error querying accounts: ' + results.message);
	}
}

function queryWebproperties(accountId) {

	// Get a list of all the Web Properties for the account
	gapi.client.analytics.management.webproperties.list({
		'accountId' : accountId
	}).execute(handleWebproperties);
}

function handleWebproperties(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {

			storage("property", JSON.stringify(results));
			var selc = document.getElementById("analytic_property");
			var str = "<option disabled>Select Property</option>";
			for (var i = 0; i < results.items.length; i++) {
				var tmp = "<option class='extInfo' accountId='"
						+ results.items[i].accountId + "' propertyId='"
						+ results.items[i].id + "'>" + results.items[i].name
						+ "</option>";
				str += tmp;
			}
			selc.innerHTML = str;
			// Query for Views (Profiles)
			queryProfiles(results.items[0].accountId, results.items[0].id);
		} else {
			console.log('No webproperties found for this user.');
		}
	} else {
		console.log('There was an error querying webproperties: '
				+ results.message);
	}
}

function queryProfiles(accountId, webpropertyId) {
	console.log('Querying Views (Profiles).');

	// Get a list of all Views (Profiles) for the first Web Property of the
	// first Account
	gapi.client.analytics.management.profiles.list({
		'accountId' : accountId,
		'webPropertyId' : webpropertyId
	}).execute(handleProfiles);
}

function handleProfiles(results) {
	if (!results.code) {
		if (results && results.items && results.items.length) {
			// Get the first View (Profile) ID
			storage("profile", JSON.stringify(results));
			var selc = document.getElementById("analytic_profile");
			var str = "<option disabled>Select Profile</option>";
			for (var i = 0; i < results.items.length; i++) {
				var tmp = "<option class='extInfo' accountId='"
						+ results.items[i].accountId + "' profilesId='"
						+ results.items[i].id + "'>" + results.items[i].name
						+ "</option>";
				str += tmp;
			}
			// queryGetColumns();
			queryGetdata(results.items[0].id);
			selc.innerHTML = str;
			siteName();

		} else {
			console.log('No views (profiles) found for this user.');
		}
	} else {
		console.log('There was an error querying views (profiles): '
				+ results.message);
	}
}

function queryGetdata(ids) {
	var apiQuery = gapi.client.analytics.data.ga
			.get(
					{
						"ids" : "ga:" + ids,
						"metrics" : "ga:sessions,ga:users,ga:newUsers,ga:avgSessionDuration,ga:avgTimeOnSite",
						"start-date" : start_time,
						"end-date" : end_time,
					}).execute(handleQueryGetdata);
}
function verifyNumber(number) {
	return Math.ceil(number);
}
function handleQueryGetdata(results) {
	if (!results.code) {
		var node = document.getElementById("ga_sessionCount");
		node.childNodes[1].innerHTML = verifyNumber(results.result.rows[0][0]);
		var node = document.getElementById("ga_users");
		node.childNodes[1].innerHTML = verifyNumber(results.result.rows[0][1]);
		var node = document.getElementById("ga_newUsers");
		node.childNodes[1].innerHTML = verifyNumber(results.result.rows[0][2]);
		var node = document.getElementById("ga_avgSessionDuration");
		node.childNodes[1].innerHTML = verifyNumber(results.result.rows[0][3]);
		var node = document.getElementById("ga_avgTimeOnSite");
		node.childNodes[1].innerHTML = verifyNumber(results.result.rows[0][4]);
	} else {
		alert(results.message);
	}
}

function queryGetColumns() {
	var columns = gapi.client.analytics.metadata.columns.list({
		"reportType" : "ga"
	}).execute(handleGetcolumns);
}

function handleGetcolumns(results) {
	if (!results.code) {
		var str = "";
		for (var i = 0; i < results.items.length; i++) {
			str = str + results.items[i].id + "---------------"
					+ results.items[i].attributes.description + "<br />";
		}
		document.getElementById("out").innerHTML = str;
	} else {
		alert(results.message);
	}
}

function getTime() {
	start_time = document.getElementById("stime").value;
	end_time = document.getElementById("etime").value;
	event_analytic_property();
}

// ///////////////////////////////////////////////////////////////////////
// Các hàm xử lý thông tin
// //////////////////////////////////////////////////////////////////////
function event_analytic_name() {
	var account = document.getElementById("analytic_name");
	var accountId = account.options[account.selectedIndex]
			.getAttribute("accountId");
	queryWebproperties(accountId);
}

function siteName() {
	var tmp = document.getElementById("analytic_property");
	var ele = tmp.options[tmp.selectedIndex].text;
	var property = JSON.parse(sessionStorage.getItem("property"));
	for (var i = 0; i < property.items.length; i++) {
		if (property.items[i].name == ele) {
			websiteUrl = property.items[i].websiteUrl;
		}
	}
	var div = document.getElementById("siteName");
	var str = "<a href='" + websiteUrl + "' target='_blank' >" + websiteUrl
			+ "</a>";
	div.getElementsByTagName("span")[0].innerHTML = str;

}

function event_analytic_property() {
	var property = document.getElementById("analytic_property");
	var accountId = property.options[property.selectedIndex]
			.getAttribute("accountId");
	var propertyId = property.options[property.selectedIndex]
			.getAttribute("propertyId");
	queryProfiles(accountId, propertyId);

}