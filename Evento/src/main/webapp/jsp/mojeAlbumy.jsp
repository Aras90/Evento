<%@taglib uri="/struts-tags" prefix="s" %>



<html>
	
	<body>
		moje albumy <br>
		data: <s:property value="getDateNow()"/> <br>
	<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.2.js"></script>
	<script>
		$(document).ready(function() { 
			$.ajax({
				type: "POST",
				url: "MojeAlbumyAction.action",
				data: { name: "John", location: "Boston" }
			}).done(function( msg ) {
				alert( "Data Saved: " + msg );
			});
		});
	</script>	
	-->
		
		
		
		
		<s:iterator value="listaPictures"> 
			
			
			<a class="fancybox" title="<s:property value="name"/>" href="<s:property value="link"/>">
				<img src="<s:property value="link"/>" /> <br>
			</a>
		
		
		</s:iterator>
		
		
		
		
	</body>
</html>