$(function(){
	$('#test').click(function() {
			$.post( 'test.action', { test: 1 },function() {
				alert("success");
			});
			return false;
	});
});