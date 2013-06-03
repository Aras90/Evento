<%@taglib uri="/struts-tags" prefix="s"%>
<style>
#main {
	padding-bottom: 0
}
</style>
<script src="http://bugaj.ovh.org/edytor.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script>
	(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id))
			return;
		js = d.createElement(s);
		js.id = id;
		js.src = "//connect.facebook.net/pl_PL/all.js#xfbml=1&appId=487331951334796";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	$(function() {
		$('.fancybox').fancybox();
	})
	
	function usun(id){
		var r = confirm("Czy jesteś pewien, że chcesz usunąć to zdjęcie?");
		if (r == true) {
		$('#img' + id).remove();
		
		$.post(
				'deletePhoto.action',
				{
					idPicture : id,
				},
				function(data) {				
				},
				'json'
			);
		}
	}
</script>
<div class="title">
	<s:property value="Name" />
</div>
<div class="zdjecia">
	<s:iterator value="picturesList" status="stat">
	
		<div class="zdjecie" id = "img<s:property value="picturesList [# stat.index][0].Id_Picture" />">
			<a class="fancybox" 
				title="<s:property value="picturesList [# stat.index][0].Name" />"
				data-fancybox-group="gallery"
				data-id="<s:property value="picturesList [# stat.index][0].Id_Picture" />"
				data-glos="<s:property value="picturesList [# stat.index][1].Value" />"
				href="<s:property value="picturesList [# stat.index][0].TymczasowyBezposredniLink" />">
				<img rel="foto" id="<s:property value="picturesList [# stat.index][0].Id_Picture" />"
    			src="<s:property value="picturesList [# stat.index][0].TymczasowyBezposredniLink" />" />
    			<br>
			</a>
			
			
			
			<s:property value="picturesList [# stat.index][0].Name" />
			
			
			
		 	<s:if test="check()" >
			
				<input type="button" value="edytuj"
				onclick="edytuj('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" />
			
				<input id="usun" type="button" value="usun"
				onclick="usun('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" />
			
			</s:if>
		
		
		<!--  
				<input type="button" value="edytuj"
				onclick="edytuj('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" />
			
				<input id="usun" type="button" value="usun"
				onclick="usun('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" />
			-->	
				
			<s:set name="Id_Picture" value="picturesList [# stat.index][0].Id_Picture" /> 
			
			<!-- <s:form action="delete">
                    <s:submit type="button" value="Usun"/>
			</s:form>
			-->
			
			<br> Utworzono:
			<s:property value="picturesList [# stat.index][0].CreatedAt" />
			
		</div>

	</s:iterator>
</div>
<br style="clear: both">
