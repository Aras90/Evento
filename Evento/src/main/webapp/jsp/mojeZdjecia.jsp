<%@taglib uri="/struts-tags" prefix="s" %>
<script>
   (function(d, s, id) {
			var js, fjs = d.getElementsByTagName(s)[0];
			if (d.getElementById(id)) return;
			js = d.createElement(s); js.id = id;
			js.src = "//connect.facebook.net/pl_PL/all.js#xfbml=1&appId=487331951334796";
			fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	$(function(){
				$('.fancybox').fancybox();
			})
		</script>
	<div class="zdjecia">
		 <s:iterator value="picturesList" status="stat"> 
                       <!--                    
                        <img src="<s:property value="Link" />" /> <br>                      
                        nazwa:	<s:property value="Name" />   <br>
                        data: 	<s:property value="CreatedAt" />   <br>
              			-->
              			
              		<div class="zdjecie">
              			<a class="fancybox" title="<s:property value="picturesList [# stat.index][0].Name" />" data-fancybox-group="gallery" data-id="<s:property value="picturesList [# stat.index][0].Id_Picture" />" data-glos="<s:property value="picturesList [# stat.index][1].Value" />" href="<s:property value="picturesList [# stat.index][0].Link" />">
              				<img rel="foto" src="<s:property value="picturesList [# stat.index][0].Link" />" /> <br> 
              			</a>                     
                        <s:property value="picturesList [# stat.index][0].Name" />   <br>
                        Utworzono: 	<s:property value="picturesList [# stat.index][0].CreatedAt" />   <br>
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
          </div>
          <br style="clear:both">