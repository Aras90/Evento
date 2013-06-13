<%@taglib uri="/struts-tags" prefix="s" %>
<style>
	#main{padding-bottom: 0}
</style> 

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<script type="text/javascript">

function show(){
	  $("#publicationSection").toggle();
}
</script>

          
<div id="dialog" title="Example"></div>

<div class="title">Moje Eventy</div>    
  	
        	<div class="zdjecia">	

					

                
                <s:iterator value="eventList" status="stat"> 
                        <div class="zdjecie">
                       
	                        <s:set name="Id_Event" value="eventList [# stat.index][0].Id_Event" /> 
	                        <s:set name="Name" value="eventList [# stat.index][0].Name" /> 
	                        <s:set name="pictureLink" value="%{getPictureLink(#Id_Event)}" />
	                     
	                     
	                     
	                        <s:if test="#pictureLink == null">
	                        	 <a href="<s:url action="mojeZdjeciaEvent"><s:param name="id" value="#Id_Event" /><s:param name="name" value="#Name" /> </s:url>"> <div class="folder"></div></a> <br>
	                        </s:if>
	                        <s:else>
	                        	<a href="<s:url action="mojeZdjeciaEvent"><s:param name="id" value="#Id_Event" /><s:param name="name" value="#Name" /> </s:url>"> <img class="album" src="<s:property value="#pictureLink" />"/></a> <br>
	                        </s:else>
	                       
	                        <s:property value="eventList [# stat.index][0].Name" />   <br>
	                        <s:property value="eventList [# stat.index][0].CreatedAt" />  <br>

                        </div>
                  
                </s:iterator>
                
         </div>
   <br style="clear:both">
