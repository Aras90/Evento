<%@taglib uri="/struts-tags" prefix="s" %>
<html>

	<body>
		 <s:iterator value="picturesList" status="stat"> 
                       <!--                    
                        <img src="<s:property value="Link" />" /> <br>                      
                        nazwa:	<s:property value="Name" />   <br>
                        data: 	<s:property value="CreatedAt" />   <br>
              			-->
              			
              			<img src="<s:property value="picturesList [# stat.index][0].Link" />" /> <br>                      
                        nazwa:	<s:property value="picturesList [# stat.index][0].Name" />   <br>
                        data: 	<s:property value="picturesList [# stat.index][0].CreatedAt" />   <br>
                        
                        
                       	<s:set name="ocena" value="picturesList[#stat.index][1].Value" />
						
                        <s:if test="#ocena == null">
							TUTAJ WSTAWIC GLOWSOWANIE
							<!--
							<s:form name="searchForm" action="ocen" method="post">
    							
								Podaj liczbe od 1-5:<s:textfield id="aaa" value="" />
    							<s:submit value="Ocen" method="doSearch"/>
   								 
							</s:form>
							-->
						</s:if>
						<s:else>
    						rating: <s:property value="#ocena"/> <br>
						</s:else>
                       
                        <div id="fb-root"></div>
						<script>
							(function(d, s, id) {
							  var js, fjs = d.getElementsByTagName(s)[0];
							  if (d.getElementById(id)) return;
							  js = d.createElement(s); js.id = id;
							  js.src = "//connect.facebook.net/pl_PL/all.js#xfbml=1&appId=487331951334796";
							  fjs.parentNode.insertBefore(js, fjs);
							 }(document, 'script', 'facebook-jssdk'));
						</script>
						<div class="fb-comments" data-href="http://localhost:8080/Evento/<s:property value="#stat.index"/>" data-width="470" data-num-posts="10"></div>
						
                </s:iterator>
	</body>
</html>