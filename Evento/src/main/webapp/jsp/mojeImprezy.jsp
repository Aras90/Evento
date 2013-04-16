<%@taglib uri="/struts-tags" prefix="s" %>

<html>
 
        <body>
        	
        		<div class="zdjecia">
                aa
                <s:iterator value="albumList" status="stat"> 
                        <div class="zdjecie">
                       
                        <s:set name="Id_Album" value="albumList [# stat.index][0].Id_Album" /> 
                        <s:set name="pictureLink" value="%{getPictureLink(#Id_Album)}" />
                     
                     
                     	
                        <s:if test="#pictureLink == null">
                        	 <a href="<s:url action="mojeZdjecia"><s:param name="id" value="#Id_Album" /> </s:url>"> <div class="folder"></div></a> <br>
                        </s:if>
                        <s:else>
                        	<a href="<s:url action="mojeZdjecia"><s:param name="id" value="#Id_Album" /> </s:url>"> <img class="album" src="<s:property value="#pictureLink" />"/></a> <br>
                        </s:else>
                       
                        <s:property value="albumList [# stat.index][1].Name" />   <br>
                        <s:property value="albumList [# stat.index][0].CreatedAt" />  <br>

                        
                     	
                     	
                     	
                     	
                     	
                        
                        
                        </div>
                  
                </s:iterator>
                
                </div>
                <br style="clear:both">
                  
        </body>
</html>