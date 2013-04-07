<%@taglib uri="/struts-tags" prefix="s" %>

<html>
 
        <body>
                
                <s:iterator value="albumList" status="stat"> 
                        
                       
                        <s:set name="Id_Album" value="albumList [# stat.index][0].Id_Album" /> 
                        <a href="<s:url action="mojeZdjecia"><s:param name="id" value="#Id_Album" /> </s:url>"> <div class="folder"></div></a> <br>
                        <s:property value="albumList [# stat.index][1].Name" />   <br>
                        Utworzono:	<s:property value="albumList [# stat.index][0].CreatedAt" />  <br>
                        
                        
                     	
                     	
                     	
                     	
                     	
                        
                        
                        
                  
                </s:iterator>
                
                
                  
        </body>
</html>