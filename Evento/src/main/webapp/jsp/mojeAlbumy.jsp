<%@taglib uri="/struts-tags" prefix="s" %>
<html>

	<body>
		moje albumy <br>
		ile: <s:property value="getDateNow()"/>
		<img src="<s:property value="getCycki()"/>" />
		
	</body>
</html>