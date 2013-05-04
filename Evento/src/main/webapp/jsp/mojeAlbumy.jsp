<%@taglib uri="/struts-tags" prefix="s" %>
<style>
	#main{padding-bottom: 0}
</style> 
<div class="title">Moje Albumy</div>    	
        		<div class="zdjecia">
                
                <s:iterator value="albumList" status="stat"> 
                        <div class="zdjecie">
                       
                        <s:set name="Id_Album" value="albumList [# stat.index][0].Id_Album" /> 
                        <s:set name="Name" value="albumList [# stat.index][1].Name" /> 
                        <s:set name="pictureLink" value="%{getPictureLink(#Id_Album)}" />
                     
                     
                     	
                        <s:if test="#pictureLink == null">
                        	 <a href="<s:url action="mojeZdjecia"><s:param name="id" value="#Id_Album" /><s:param name="name" value="#Name" /> </s:url>"> <div class="folder"></div></a> <br>
                        </s:if>
                        <s:else>
                        	<a href="<s:url action="mojeZdjecia"><s:param name="id" value="#Id_Album" /><s:param name="name" value="#Name" /> </s:url>"> <img class="album" src="<s:property value="#pictureLink" />"/></a> <br>
                        </s:else>
                       
                        <s:property value="albumList [# stat.index][1].Name" />   <br>
                        <s:property value="albumList [# stat.index][0].CreatedAt" />  <br>

                        
                     	
                     	
                     	
                     	
                     	
                        
                        
                        </div>
                  
                </s:iterator>
                
                </div>
                <br style="clear:both">