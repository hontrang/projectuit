$(document).ready(function() {
	$("#submit_form").on("click", function() {
		action();
	});
	$("input[name='username']").keydown(function(event) {
		if (event.which === 13) {
			action();
		}
	});
	$("input[name='password']").keydown(function(event) {
		if (event.which === 13) {
			action();
		}
	});

	editCss();
	function editCss() {
		$("#menu").css("width", "600px");
	}
	function action() {
		var username = $("input[name='username']").val();
		var password = $("input[name='password']").val();
		var remember = false;
		if ($("input[name='remember']").is(":checked") == true) {
			remember = true;
		}
		$.ajax({
			url : "login",
			method : "POST",
			data : {
				key : "login",
				username : username,
				password : $.md5(password),
				remember : remember
			}
		}).done(function(res) {
			if (res == "true") {
				window.location.href = "index.jsp";
			} else {
				var message = "Sai username hoặc password. Làm ơn thử lại.";
				$(".form-4 span").text(message);
			}

		}).fail(function(res) {
			var message = "Không thể kết nối đến server";
			$(".form-4 span").text(message);
		});
	}
});
