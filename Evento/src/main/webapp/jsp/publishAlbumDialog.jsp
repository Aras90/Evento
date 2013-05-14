<%@taglib uri="/struts-tags" prefix="s" %>

<html>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<!-- <link rel="stylesheet" href="/resources/demos/style.css" /> -->

<script type="text/javascript">
$(function() {
		$( "#dialog" ).dialog({ width: 550,  });
});
</script>

<script type="text/javascript">

function publish() {
	
	var data1 = {	
			name: $("#titleAlbum").val(),
			description : $("#descriptionAlbum").val(),
	};
	
	
	
	 var album =	FB.api('/me/albums', 'post', data1, function(response){
		    if (!response || response.error) {
// 		        alert('Error occurred' + response.error);
		    } else {
	 	    	addImage(response.id);	    	
// 		        alert('album id: ' + response.id);
		    }
		});
}

var data;
var path;
var licznik=1;

function addImage(id){
	
    var tab = document.getElementById('albumTable');
    var td = tab.getElementsByTagName('td');
    
    for(licznik; licznik <= td.length+1; licznik++ ){
	path = $("#"+licznik).val();
	

	 data = {	
		url :  path,
	},	
	 
	
	FB.api('/'+ id + '/photos', 'post',data , function(response){

	    if (!response || response.error) {
 	       // alert('Error occured');
	    } else {
 	      //  alert('foto ID: ' + response.id);
	    }

	});
	 
}
licznik=1;
//alert("przed dialog close");
$( "#dialog" ).dialog('close');
}
$( "#dialog" ).dialog('close');
</script>

<script type="text/javascript">
function showPicture(){
	 $('#albumPictureSection').show();
}
</script>

<body>
	<div id="dialog" title="Publikacja albumu">
	
	<div id="albumDetailsSection" style="padding-bottom:15px;">
	<table>
		<tr><s:textfield value="wprowadz tytul" id="titleAlbum" label="Tytul albumu"  /></tr>
		<tr><s:textarea id="descriptionAlbum" value="wprowadz opis" cols="40" rows="5" label="Opis albumu" /></tr>
	</table>
	<button id="showImages" value="pokaz zdjecia" onclick="showPicture()">Pokaz zdjecia</button>
	</div>
	
	
	
	<div id="albumPictureSection" style="display:none;">
		<table id="albumTable">
			<s:iterator value="pictureList" status="stat">
			
				<s:set name="Id_Picture" value="pictureList [# stat.index][0].Id_Picture" />
				<s:set name="Link" value="pictureList [# stat.index][0].Link" />
				
				<td  style="padding-left: 10px;"><img width="100" height="150" border="5"  src="<s:property value="Link" />" />
					<input type="hidden" class="unkn"  id="<s:property value="#stat.count" />"  value="<s:property value="Link" />" />
				</td>
			
				<s:if test="%{#stat.modulus(4) == 0}">  
				      <tr></tr>  
				</s:if>  
				
				<s:if test="stat.size<1">  
				<td><p>Brak zdjec.</p></td>
				</s:if>
			
			</s:iterator>
		</table>
	</div>
	
	
	<button name="publishButton" onclick="publish()" value="Publikacja albumu">Publikuj album</button> 	
	
	
	</div>
</body>
</html>
