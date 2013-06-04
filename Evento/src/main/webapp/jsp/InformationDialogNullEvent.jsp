<%@taglib uri="/struts-tags" prefix="s" %>



<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- <link rel="stylesheet" href="/resources/demos/style.css" /> -->

<script type="text/javascript">
$(function() {
		$( "#errorDialog" ).dialog({ width:350,  });
});


function anuluj(){
	$( "#errorDialog" ).dialog("close");
}
</script>


	<div id="errorDialog" title="Blad tworzenia albumu">
		<p style="color: blue;">Nie uczestniczyles w zadnym wydarzeniu.</p>
		<button onclick="anuluj()" id="Anuluj">Ok</button>
	</div>
	
	

	

	
	





	

	

