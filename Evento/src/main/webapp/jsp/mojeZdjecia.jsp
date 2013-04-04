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
						</s:if>
						<s:else>
    						rating: <s:property value="#ocena"/> <br>
						</s:else>
                       
                        
						
						
                </s:iterator>
	</body>
</html>