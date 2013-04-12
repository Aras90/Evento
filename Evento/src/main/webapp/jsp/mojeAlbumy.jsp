<%@taglib uri="/struts-tags" prefix="s" %>

<html>
 
        <body>
        	
        		
                
                <s:iterator value="albumList" status="stat"> 
                        
                       
                        <s:set name="Id_Album" value="albumList [# stat.index][0].Id_Album" /> 
                        <s:set name="pictureLink" value="%{getPictureLink(#Id_Album)}" />
                     
                     
                     	
                        <s:if test="#pictureLink == null">
                        	 <a href="<s:url action="mojeZdjecia"><s:param name="id" value="#Id_Album" /> </s:url>"> <div class="folder"></div></a> <br>
                        </s:if>
                        <s:else>
                        	<a href="<s:url action="mojeZdjecia"><s:param name="id" value="#Id_Album" /> </s:url>"> <img src="<s:property value="#pictureLink" />"/></a> <br>
                        </s:else>
                       
                        kiedy stworzono:	<s:property value="albumList [# stat.index][0].CreatedAt" />  <br>
                        id:	<s:property value="albumList [# stat.index][0].Id_Album" />  <br>
                        nazwa:		<s:property value="albumList [# stat.index][1].Name" />   <br>
                        
                     	
                     	
                     	
                     	
                     	
                        
                        
                        
                  
                </s:iterator>
                
                
                
                  
        </body>
</html>