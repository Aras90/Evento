<%@taglib uri="/struts-tags" prefix="s"%>
<style>
#main {
	padding-bottom: 0
}
</style>
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
</script>
<div class="title">
	<s:property value="Name" />
</div>
<div class="zdjecia">
	<s:iterator value="picturesList" status="stat">
		<!--                    
                        <img src="<s:property value="Link" />" /> <br>                      
                        nazwa:	<s:property value="Name" />   <br>
                        data: 	<s:property value="CreatedAt" />   <br>
              			-->

		<div class="zdjecie">
			<a class="fancybox"
				title="<s:property value="picturesList [# stat.index][0].Name" />"
				data-fancybox-group="gallery"
				data-id="<s:property value="picturesList [# stat.index][0].Id_Picture" />"
				data-glos="<s:property value="picturesList [# stat.index][1].Value" />"
				href="<s:property value="picturesList [# stat.index][0].Link" />">
				<img rel="foto"
				src="<s:property value="picturesList [# stat.index][0].Link" />" />
				<br>
			</a>
			<s:property value="picturesList [# stat.index][0].Name" />
			<br> Utworzono:
			<s:property value="picturesList [# stat.index][0].CreatedAt" />
			<br> <input id="<s:property value="#stat.index" />"
				type="checkbox"
				name="<s:property value="picturesList [# stat.index][0].Link" />"
				onclick="javascript:check('<s:property value="#stat.index" />');"
				value="bar" />
			<!-- 	<div class="rating" data-average="0" data-id=" <s:property value="picturesList [# stat.index][0].Id_Picture" />" data-glos="<s:property value="picturesList [# stat.index][1].Value" />"></div>
							 
                        
						 <div class="fb-comments" data-href="http://localhost:8080/Evento/<s:property value="#stat.index"/>" data-width="200" data-num-posts="10"></div> -->
		</div>

		<!--<s:set name="ocena" value="picturesList[#stat.index][1].Value" />
						
                        <s:if test="#ocena == null">
							TUTAJ WSTAWIC GLOWSOWANIE
							<div class="exemple">
							 
							   <div class="basic" data-average="12" data-id="1"></div>
							 
							   <div class="basic" data-average="8" data-id="2"></div>
							 
							</div>-->
		<!--
							<s:form name="searchForm" action="ocen" method="post">
    							
								Podaj liczbe od 1-5:<s:textfield id="aaa" value="" />
    							<s:submit value="Ocen" method="doSearch"/>
   								 
							</s:form>
							-->
		<!--</s:if>
						<s:else>
    						rating: <s:property value="#ocena"/> <br>
						</s:else>
                       -->

	</s:iterator>
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
		function checkButtonZIP() {
			var invoke = [];
			var namePhoto = [];
			var licz = 0;
			console.log('W button przed petla');
			<s:iterator value="picturesList" status="stat">

			console.log(licz);
			if (document.getElementById(<s:property value="#stat.index" />).checked) {
				var nameP = "<s:property value="picturesList [# stat.index][0].Name" />";
				namePhoto[licz] = (nameP);
				var link = document
						.getElementById(<s:property value="#stat.index" />).name;
				invoke[licz] = (link);
				console.log(invoke);
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

			}
		};
		function checkButtonPDF() {
			var invoke1 = [];

			var albumN = new String("<s:property value="Name" />");
			var licz = 0;
			console.log('W button przed petla');
			<s:iterator value="picturesList" status="stat">
			console.log(licz);
			if (document.getElementById(<s:property value="#stat.index" />).checked) {

				var link = document
						.getElementById(<s:property value="#stat.index" />).name;
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

			}

		};

		function UkryjZ() {
			document.getElementById('hidenZip').style.display = "none";
		};
		function UkryjP() {
			document.getElementById('hidenPdf').style.display = "none";
		};
	</script>
</div>
<br style="clear: both">