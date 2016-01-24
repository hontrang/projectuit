$(document).ready(
		function() {
			initTable();
			$("#submitTopRows").on('click', function() {
				var rows = $("#toprows").val();
				$.post("loaddata", {
					key : "selectLog",
					key1 : rows
				}).done(function(response) {
					handle(response);
				}).fail(function(xhr, textStatus, errorThrown) {
					alert(errorThrown);
				});
			});
			$('#toprows').keydown(function(event) {
				if (event.which === 13) {
					var rows = $("#toprows").val();
					$.post("loaddata", {
						key : "selectLog",
						key1 : rows
					}).done(function(response) {
						handle(response);
					}).fail(function(xhr, textStatus, errorThrown) {
						alert(errorThrown);
					});
				}
			});
			$.post("loaddata", {
				key : "selectLog",
				key1 : "25"
			}).done(function(response) {
				handle(response);
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
						"sWidth" : "10%"
					}, {
						"sWidth" : "20%"
					}, {
						"sWidth" : "42%"
					}, {
						"sWidth" : "10%"
					}, {
						"sWidth" : "18%"
					} ]
				});
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
				table.columns().eq(0).each(
						function(colIdx) {
							$('input', table.column(colIdx).footer()).on(
									'keyup change',
									function() {
										table.column(colIdx).search(this.value)
												.draw();
									});
						});
				// load(response);
				$("#table_id").show();
				$(".imageloading").hide();
			}
			function initTable() {
				$("#table_id").hide();
			}
		});