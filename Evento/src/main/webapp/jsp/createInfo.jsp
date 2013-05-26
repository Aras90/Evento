<%@taglib uri="/struts-tags" prefix="s" %>



<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- <link rel="stylesheet" href="/resources/demos/style.css" /> -->

<script type="text/javascript">
$(function() {
		$( "#infoDialog" ).dialog({ width:350,  });
});


function anuluj(){
	$( "#infoDialog" ).dialog("close");
}
</script>


	<div id="infoDialog" title="Tworzenie albumu">
		<p style="color: blue;">Album zostal stworzony.</p>
		<button onclick="anuluj()" id="ok">ok</button>
	</div>
	
	

	

	
	





	

	

