<%@taglib uri="/struts-tags" prefix="s" %>
<style>
	#main{padding-bottom: 0}
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

 <div id="dialog" title="Example"></div>
	
<table>
	<s:set name="Name" value="albumEventList [# stat.index][0].Name" />
	<td>
		<s:form action="publishAlbumAction">	
			Albumy: <select name="choosenEvent" id="choosenEventId"> 
				<s:iterator value="albumEventList" status="stat">
				    <option value="<s:property value="Id_Event" />"><s:property value="Name" /> <-> <s:property value="CreatedAt" /></option> 
				 </s:iterator>
			</select> 
			<s:submit label="Zapisz" value="Publikuj" />
		</s:form>	
	</td>
	
	<td style="padding-left:20px">
		<s:form action="newAlbumPictureList">	
			Wydarzenia: <select name="choosenEvent" id="choosenEventId"> 
				<s:iterator value="eventList" status="stat">
				    <option value="<s:property value="Id_Event" />"><s:property value="Name" /> <-> <s:property value="CreatedAt" /></option> 
				 </s:iterator>
			</select> 
			<s:submit label="Zapisz" value="Utw�rz album" />
		</s:form>
	</td>	
</table>	




<div class="title">Moje Imprezy</div>     	
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