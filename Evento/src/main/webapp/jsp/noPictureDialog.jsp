<%@taglib uri="/struts-tags" prefix="s" %>



<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- <link rel="stylesheet" href="/resources/demos/style.css" /> -->

<script type="text/javascript">
$(function() {
		$( "#noPictureDialog" ).dialog({ width:350,  });
});


function anuluj(){
	$( "#noPictureDialog" ).dialog("close");
}
</script>


	<div id="noPictureDialog" title="Tworzenie albumu - brak zdjec">
		<p style="color: blue;">Brak zdjec dla wybranego kryterium.</p>
		<button onclick="anuluj()" id="ok" action="mojeImprezy.action">ok</button>
	</div>
	
	

	

	
	





	

	

