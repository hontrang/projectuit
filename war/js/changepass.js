$(document).ready(function() {
	$(".changepassword").on("click", function(e) {
		// Prevents the default action to be
		// triggered.
		e.preventDefault();

		// Triggering bPopup when click event is
		// fired
		$('#changepass_popup').bPopup();
	});
	$("#submitChange").on("click", function() {
		var oldpass = $("#oldpass").val();
		var newpass1 = $("#newpass1").val();
		var newpass2 = $("#newpass2").val();
		var user = $("#option span").html();
		user = user.trim();
		$.post("loaddata", {
			key : "changepass",
			key1 : $.md5(oldpass),
			key2 : $.md5(newpass1),
			key3 : $.md5(newpass2),
			key4 : user
		}, function(response) {
			handleChangePass(response);
		});
	});
	function handleChangePass(response) {
		if (response != "Done!") {
			$("#passDiv2 span").html(response);
		} else {
			alert("Đổi thành công!");
			window.location.href = "logout.jsp";
		}
	}
});