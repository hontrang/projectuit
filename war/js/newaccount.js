$(document).ready(function() {
	$(".newaccount").on("click", function(e) {
		// Prevents the default action to be
		// triggered.
		e.preventDefault();

		// Triggering bPopup when click event is
		// fired
		$('#newaccount_popup').bPopup();
	});
	$("#submitSignUp").on("click", function() {
		var accname = $("#accname").val();
		var pass1 = $("#password1").val();
		var pass2 = $("#password2").val();
		var email = $("#email").val();
		if (accname == "") {
			$("#passDiv2 span").html("Tên tài khoản không được để trống.");
		}
		if (pass1 != pass2) {
			$("#passDiv2 span").html("Password không trùng khớp");
		} else {

		}
		$.post("loaddata", {
			key : "newaccount",
			key1 : accname,
			key2 : $.md5(pass1),
			key3 : email
		}, function(response) {
			handleNewAccount(response);
		});
	});
	function handleNewAccount(response) {
		if (response != "Done!") {
			$("#passDiv2 span").html(response);
		} else {
			alert("Tài khoản mới đã được tạo.");
			var bPopup = $("#newaccount_popup").bPopup();
			bPopup.close();
		}
	}
});