<%@taglib uri="/struts-tags" prefix="s" %>

<html>
 
        <body>
                poczekalnia
                <s:iterator value="albumList" status="stat"> 
                        <div class="folder"></div>
                        treeee 	<s:property value="albumList [# stat.index][0].CreatedAt" />  <br>
                        nazwa		<s:property value="albumList [# stat.index][1].Name" />   <br>
                        			<s:property value="albumList [# stat.index][2]" />
                        		
                </s:iterator>
        </body>
</html>