<%@taglib uri="/struts-tags" prefix="s" %>
<html>

	<body>
		moje albumy <br>
		data: <s:property value="getDateNow()"/> <br>
		
		<s:iterator value="listaPictures"> 
		
			<s:property value="name"></s:property> <br>
			<img src="<s:property value="link"/>" /> <br>
		
		
		
		</s:iterator>
		
		
		
		
	</body>
</html>