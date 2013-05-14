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
function showPicture(){
	 $('#albumPictureSection').show();
}
</script>

<body>
	<div id="dialog" title="Tworzenie albumu">
	
	<s:form action="save">
	<input type="hidden" name="eventList" value="<s:property value="choosenEvent" />" >
		<table id="albumTable">
			<s:iterator value="pictureToAlbumList" status="stat">
			<s:set name="Name" value="pictureToAlbumList [# stat.index][0].Name" />
			
			
				<s:set name="Id_Picture" value="pictureToAlbumList [# stat.index][0].Id_Picture" />
				<s:set name="Link" value="pictureToAlbumList [# stat.index][0].Link" />
				<s:set name="TymczasowyBezposredniLink" value="pictureToAlbumList [# stat.index][0].TymczasowyBezposredniLink" />
				
				<td  style="padding-left: 10px;"><img width="100" height="150" border="5"  src="<s:property value="Link" />" />
					<input type="hidden" class="unkn"  id="<s:property value="#stat.count" />"  value="<s:property value="TymczasowyBezposredniLink" />" />
					<input type="checkbox" name="choosenList" value="<s:property value="Id_Picture" />"> - <s:property value="Name" />
				</td>
			
				<s:if test="%{#stat.modulus(2) == 0}">  
				      <tr></tr>  
				</s:if>  
				
<%-- 				<s:if test="stat.size<1">   --%>
<!-- 				<td><p>Brak zdjec.</p></td> -->
<%-- 				</s:if> --%>
			
			</s:iterator>
		</table>
		<s:submit  value="Utwórz album" type="button"/>
	</s:form>
	</div>
	

	
	
</body>
</html>
