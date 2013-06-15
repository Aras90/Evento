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
				'deletePhotoFromAlbum.action',
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
			
			<br> Utworzono:
			<s:property value="picturesList [# stat.index][0].CreatedAt" />
			<br>
			<s:if test="check()" >
			
				<button onclick="edytuj('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" style="float:center;">edytuj</button>
				
				<button id="usun" onclick="usun('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" style="float:center;">usun</button>
			
			</s:if>
			
			<br> <input id="checkbox:<s:property value="#stat.index" />"
				type="checkbox"
				name="<s:property value="picturesList [# stat.index][0].TymczasowyBezposredniLink" />"
				onclick="check('<s:property value="#stat.index" />')"
				value="bar" />
		</div>

	</s:iterator>
	
	<br>
	<div class="select_checbox" style="display: inline-block; margin-top: 15px">
		<input id="zaznacz" type="submit" value="Zaznacz wszystkie"
			onclick="checkZAznacz()" />
		<input id="odznacz" type="submit" value="Odznacz wszystkie"
			onclick="checkODznacz()" />
	</div>
	<br>
	<div class="zippp" style="display: inline-block; margin-top: 15px">
		<input id="elo" type="submit" value="Generuj Zipa"
			onclick="checkButtonZIP()" />
		<div id="Loading" style="display: none;">Loading...</div>
		<div id="hidenZip" style="display: none;" class="answer_list">
			<s:url id="fileDownload" namespace="/" action="pobierzZip.action"></s:url>
			<s:a href="%{fileDownload}" onclick="UkryjZ()">Pobierz</s:a>
		</div>
	</div>

	<div class="pdfff" style="display: inline-block; margin-top: 15px">
		<input id="pdf" type="submit" value="Generuj album w PDF"
			onclick="checkButtonPDF()" />
		<div id="Loading1" style="display: none;">Loading...</div>
		<div id="hidenPdf" style="display: none;" class="answer_list">
			<s:url id="DownloadPdf" namespace="/" action="pobierzPdf.action"></s:url>
			<s:a href="%{DownloadPdf}" onclick="UkryjP()">Pobierz</s:a>
		</div>
	</div>

	<script>
		/* DLA ZIPA I PDFA*/
		function checkZAznacz(){
			<s:iterator value="picturesList" status="stat">
			document.getElementById('checkbox:'+<s:property value="#stat.index" />).checked=true;
			</s:iterator>
		};
		function checkODznacz(){
			<s:iterator value="picturesList" status="stat">
			document.getElementById('checkbox:'+<s:property value="#stat.index" />).checked=false;
			</s:iterator>
		};
		function checkButtonZIP() {
			document.getElementById('hidenZip').style.display = "none";
			var invoke = [];
			var namePhoto = [];
			var licz = 0;
			console.log('W button przed petla');
			<s:iterator value="picturesList" status="stat">

			console.log(licz);
			if (document.getElementById('checkbox:'+<s:property value="#stat.index" />).checked) {
				console.log("wchodzi> zip");
				var nameP = "<s:property value="picturesList [# stat.index][0].Name" />";
				namePhoto[licz] = (nameP);
				var link = document
						.getElementById('checkbox:'+<s:property value="#stat.index" />).name;
				invoke[licz] = (link);
				console.log(invoke);
				console.log("licz "+licz);
				licz = licz + 1;

			}

			</s:iterator>
			if (invoke[0] != null) {
				$.ajaxSettings.traditional = true;
				var request1 = $.ajax({
					type : 'POST',
					url : 'testZip.action',
					data : {
						invoke : invoke,
						namePhoto : namePhoto
					}
				});
				$('#Loading').show();
				request1
						.fail(function() {
							$('#Loading').hide();
							document.getElementById('hidenZip').style.display = "block";
						});
				request1
						.done(function() {
							$('#Loading').hide();
							document.getElementById('hidenZip').style.display = "block";
						});

			}else{
				alert("Zaznacz zdjecie");
			}
		};
		function checkButtonPDF() {
			
			var invoke1 = [];
			document.getElementById('hidenPdf').style.display = "none";
			var albumN = new String("<s:property value="Name" />");
			var licz = 0;
			console.log('W button przed petla');
			<s:iterator value="picturesList" status="stat">
			console.log(licz);
			if (document.getElementById('checkbox:'+<s:property value="#stat.index" />).checked) {
				console.log("wchodzi> pdf");

				var link = document
						.getElementById('checkbox:'+<s:property value="#stat.index" />).name;
				invoke1[licz] = link;
				console.log(invoke1);
				licz = licz + 1;

			}

			</s:iterator>
			if (invoke1[0] != null) {
				$.ajaxSettings.traditional = true;
				var request = $.ajax({
					type : 'POST',
					url : 'convertPdf.action',
					data : {
						pdff : invoke1,
						nameAlbum : albumN
					}

				});
				$('#Loading1').show();
				request
						.fail(function() {
							$('#Loading1').hide();
							document.getElementById('hidenPdf').style.display = "block";
						})
				request
						.done(function() {
							$('#Loading1').hide();
							document.getElementById('hidenPdf').style.display = "block";
						})

			}else{
				alert("Zaznacz zdjecie");
			}

		};

		function UkryjZ() {
			document.getElementById('hidenZip').style.display = "none";
		};
		function UkryjP() {
			document.getElementById('hidenPdf').style.display = "none";
		};
		function check(id) {
			console.log("id"+id);
		  if(document.getElementById(id).checked){
			  document.getElementById(id).checked = false;
			  console.log("false"+id);
		  } else if(!document.getElementById(id).checked){
			  document.getElementById(id).checked = true;
			  console.log("true"+id);
		  }
		};
	
	</script>
</div>
<br style="clear: both">
