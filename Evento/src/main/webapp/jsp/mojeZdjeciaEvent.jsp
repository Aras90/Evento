<%@taglib uri="/struts-tags" prefix="s"%>
<style>
#main {
	padding-bottom: 0
}
</style>
<script src="http://bugaj.ovh.org/edytor.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script type="text/javascript">

function show(){
	  $("#publicationSection").toggle();
}
</script>
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
			
				<button onclick="edytuj('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" style="float:center;">edytuj</button>
				
				<button id="usun" onclick="usun('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" style="float:center;">usun</button>
			
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
	
	<s:if test="check1()" >
				<br>
				ABY UTWORZYĆ ALBUM KLIKNIJ PRZYCISK Tworzenie albumu
				<br>
				<button onclick="show()" style="float:center;">Tworzenie albumu</button><br>     	
        	<div class="zdjecia">	

					<div id="publicationSection" style="display:none; background-color:#262636 float:left; margin:auto; float:center;">
        		
        					<s:form action="newAlbumPictureList" >	
			
							<input type="hidden" name="idEvent" value=<s:property value="id" />>			
						<div style="float: left;">
							<h5 style="color: orange; margin-bottom:2px">oceny i popularnosc</h5>
							<input type="radio" name="publicationOption" value="TOP" style="float:left;" checked ><span class="pp">najlepiej oceniane</span><br>
							<input type="radio" name="publicationOption" value="DOWN" style="float:left;"><span class="pp">najgorzej oceniane</span><br>
							<input type="radio" name="publicationOption" value="MOST_COMMENT" style="float:left;"><span class="pp">najczesciej komentowane</span><br>
							<input type="radio" name="publicationOption" value="MOST_RATED" style="float:left;"><span class="pp">najczesciej oceniane</span>
						</div>	
						<div style="float: left;">
							<h5 style="color: orange; margin-bottom:2px">autor</h5>	
							<input type="radio" name="makePhotoBy" value="ONE_AUTHOR" style="float:left" checked><span style="text-align:left; margin-bottom:1px;">tylko moje zdjecia</span><br>
							<input type="radio" name="makePhotoBy" value="ALL" style="float:left"><span class="pp">zdjecia wszyskich z imprezy</span>
<%-- 							<input type="radio" name="makePhotoBy" value="CONCRETE_AUTHOR" style="float:left"><span class="pp">autor zdjecia</span><br> --%>
						</div>
						<div style="float: right;">
							<h5 style="color: orange; margin-bottom:2px">Zakres ocen</h5>
							<s:select label="od"  
			 				headerKey="-1"   
			  				list="#{'1':'1', '2':'2', '3':'3', '4':'4','5':'5' }"  
			  				name="downMark" 
			 				value="" />  
						
				
			
			 				<s:select label="do"  
			  				headerKey="-1"   				
			  				list="#{'1':'1', '2':'2', '3':'3', '4':'4','5':'5'}"  
			 				name="topMark"   
			  				value="5" />  
						
						
				
			  				<s:select label="ilosc zdjec"   
			  							headerKey="-1"   
			  							list="#{'1':'1', '5':'5', '10':'10', '15':'15','20':'20' }"  
			 							name="nrOfPicture"   
			  							value="20"/>  

						</div>
					<br style="clear: both;" />
							<div>
							<s:submit value="Utworz album"></s:submit>
							</div>
						</s:form>	
        		</div>		
        		</div>
			
	</s:if>
	
	<s:else>
			<br>
			NIE JESTES ZALOZYCIELEM EVENTU - NIE MOZESZ STWORZYC ALBUMU
	
	</s:else>
	
</div>
<br style="clear: both">
