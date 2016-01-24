<html>
<head>
<style type="text/css">
html, body {
	margin: 0;
	padding: 0;
}

.hide {
	display: none;
}

.show {
	display: block;
}
</style>
<script type="text/javascript" src="js/googleapi.js"></script>
<script src="https://apis.google.com/js/client:plusone.js"
	type="text/javascript"></script>
</head>
<body>
	<div id="signin-button" class="show">
		<div class="g-signin" data-callback="loginFinishedCallback"
			data-approvalprompt="auto"
			data-clientid="161165347819-4r47atf601d31v2526icdp0spu68f2rv.apps.googleusercontent.com"
			data-scope="https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.profile.emails.read 
                 https://www.googleapis.com/auth/analytics.readonly"
			data-height="standard" data-cookiepolicy="single_host_origin">
		</div>
		<!-- In most cases, you don't want to use approvalprompt=force. Specified
            here to facilitate the demo.-->
	</div>

	<div id="profile" class="hide">
		<div id="account">
			<div id="email"></div>
			<span id="name"></span>
			<button id="loginGGAccount" href="">Logout</button>
		</div>
		<span id="pic"></span>

	</div>
</body>
</html>
