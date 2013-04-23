<%@taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:fb="http://www.facebook.com/2008/fbml">
  
<body>
<script>
	window.onload = wyloguj;

	FB.init({
	    appId  : '170054506477361',
    	status : true, 
    	cookie : true, 
    	xfbml  : true  
  	});

	function wyloguj() {
		
		FB.getLoginStatus(function(response) {
            if (response.status == 'connected') {
		
       			 FB.logout(function (response) {
        	
        			//window.location = "index.jsp";
        		});
       			$.post("Evento/shutDownSession.action");
            }
		});
    	
    };
</script>

Uzytkownik niezarejestrwany w portalu

</body>
</html>