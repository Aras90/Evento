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
<button onclick="show()" style="float:left; margin-bottom:5px;">Tworzenie albumu</button><br>     	
        	<div class="zdjecia">	

					<div id="publicationSection" style="display:none; background-color:#262636 float:left">
        		
        					<s:form action="newAlbumPictureList" >	

	
								<label for="choosenEventId" class="label">
							       Wydarzenie
							   </label>
							   
							<select  style="margin-bottom:5px" name="choosenEvent" id="choosenEventId"> 
										<s:iterator value="eventList" status="stat">
							    			<option value="<s:property value="eventList [# stat.index][0].Id_Event" />"><s:property value="eventList [# stat.index][0].Name" />  -  <s:property value="eventList [# stat.index][0].CreatedAt" /></option> 
							 			</s:iterator>
							</select> 	
							
							
							<h5 style="color: orange; margin-bottom:2px">oceny i popularnosc</h5>
							<input type="radio" name="publicationOption" value="TOP" style="float:left;" checked ><span class="pp">najlepiej oceniane</span><br>
							<input type="radio" name="publicationOption" value="DOWN" style="float:left;"><span class="pp">najgorzej oceniane</span><br>
							<input type="radio" name="publicationOption" value="MOST_COMMENT" style="float:left;"><span class="pp">najczesciej komentowane</span><br>
							<input type="radio" name="publicationOption" value="MOST_RATED" style="float:left;"><span class="pp">najczesciej oceniane</span><br>
							
							<h5 style="color: orange; margin-bottom:2px">autor</h5>	
							<input type="radio" name="makePhotoBy" value="ONE_AUTHOR" style="float:left" checked><span style="text-align:left; margin-bottom:1px;">tylko moje zdjecia</span><br>
							<input type="radio" name="makePhotoBy" value="ALL" style="float:left"><span class="pp">zdjecia wszyskich z imprezy</span><br>
<%-- 							<input type="radio" name="makePhotoBy" value="CONCRETE_AUTHOR" style="float:left"><span class="pp">autor zdjecia</span><br> --%>
							
							<h5 style="color: orange; margin-bottom:2px">Zakres ocen</h5>
							<s:select label="od"  
			 				headerKey="-1"   
			  				list="#{'1':'1', '2':'2', '3':'3', '4':'4','5':'5' }"  
			  				name="downMark" 
			 				value="" />  
				
				
			
			 				<s:select label="do"  
			  				headerKey="-1"   				
			  				list="#{'1':'1', '2':'2', '3':'3', '4':'4','5':'5'}"  
			 				name="topMark"   
			  				value="5" />  
						
						
				
			  				<s:select label="ilosc zdjec"   
			  							headerKey="-1"   
			  							list="#{'1':'1', '5':'5', '10':'10', '15':'15','20':'20' }"  
			 							name="nrOfPicture"   
			  							value="20"/>  

				
							<s:submit value="Utworz album"></s:submit>
						</s:form>	
        		</div>		
     

                
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
