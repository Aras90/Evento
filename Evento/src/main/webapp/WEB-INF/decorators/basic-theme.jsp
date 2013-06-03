<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@taglib uri="/struts-tags" prefix="s" %>

<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:fb="http://www.facebook.com/2008/fbml"
      xmlns:wl="http://apis.live.net/js/2010">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Evento</title>
        
        <link href="http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="styles/style.css" />
        
        <script src="http://js.live.net/v5.0/wl.js"></script>
        <script src="http://connect.facebook.net/pl_PL/all.js"></script>
        <script src="js/fb.js"></script>
	    <script type="text/javascript" src="js/kalendarz/kalendarz.js"></script>        
        
        
       <link rel="stylesheet" type="text/css" href="js/jRating/jRating.jquery.css" media="screen" />
	   <script type="text/javascript" src="js/jRating/jquery-1.9.1.min.js"></script>
	   <script type="text/javascript" src="js/jRating/jRating.jquery.js"></script>
	   <script type="text/javascript" src="js/jRating/test.js"></script>

	   
		
	   <!-- Add mousewheel plugin (this is optional) -->
	   <script type="text/javascript" src="js/fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
	
		<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="js/fancybox/jquery.fancybox.js?v=2.1.4"></script>
		<link rel="stylesheet" type="text/css" href="js/fancybox/jquery.fancybox.css?v=2.1.4" media="screen" />
	
		<!-- Add Button helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="js/fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
		<script type="text/javascript" src="js/fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
	
		<!-- Add Thumbnail helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="js/fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
		<script type="text/javascript" src="js/fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
	
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="js/fancybox/helpers/jquery.fancybox-media.js?v=1.0.5"></script>
		
	   <script type="text/javascript">
	     $(document).ready(function(){
	      $(".rating").jRating();
		});
		</script>
		
    </head>

    <body>
        <input id="email" style="display: none" value=""> </>
        <div id="bg">
            <div id="outer">
				<div id="header">
                    <div id="fejs">
                    	<s:action namespace="/Evento" name="index" id="Sesja"/>
                    	<s:set name="id" value="#Sesja.getIdUser()"/> 
                    	<s:if test="#id == null || #id == 0">
                        	<span id="fb-login" style="display: inline">                              
                            	<s:form action="loginFB">
									<s:submit type="image" src="styles/images/fb_login.gif" title="Zaloguj przez facebook"/>
								</s:form>     
                        	</span>
                        	<span id="sd-login" style="display: inline">
                        		<s:form action="loginSD">
									<s:submit type="image" src="styles/images/SkyDrive2.jpg" title="Zaloguj przez skydrive"/>
								</s:form>
                       		</span>
                        	<span id="db-login" style="display: inline">
                        		<s:form action="loginDB">
									<s:submit type="image" src="styles/images/dropbox2.png" title="Zaloguj przez dropbox"/>
								</s:form>
                        	</span>
						</s:if>
						<s:else>
							<s:set name="lg" value="#Sesja.getLogin()"/>
							<s:if test="#lg == \"fb\"">
                        		<span id="fb-logout" style="display: list-item">
                            		<a onclick="fbLogout();">wyloguj sie</a>
                        		</span>
                        	</s:if>
                        	<s:if test="#lg == \"sd\"">
                        		<span id="sd-logout" style="display: list-item">       
                            		<s:form action="shutDownSession">
                            			<s:property value="#Sesja.getName()"/>
										<s:submit type="image" src="styles/images/SkyDrive2.jpg" title="Wyloguj z SD"/>
									</s:form>
                        		</span>
                        	</s:if>
                        	<s:if test="#lg == \"db\"">
                        		<span id="db-logout" style="display: inline">       
                            		<s:form action="shutDownSession">
                            			<s:property value="#Sesja.getName()"/>
                            			<s:submit type="image" src="styles/images/dropbox2.png" title="Wyloguj z DB"/>
									</s:form>
                        		</span>
                        	</s:if>
						</s:else>
                    </div>
                    <div id="logo">
						<h1>
                            <a href="/Evento/">Evento</a>
                        </h1>
                    </div>
                    <div id="nav">
						<ul>
                            <li class="first active">
								<a href="index.jsp" action="index">Home</a>
                            </li>
                            <li>
								<a href="mojeEventy.jsp" action="mojeEventy">Moje eventy</a>
                            </li>
                            
                            <li>
								<a href="mojeImprezy.jsp" action="mojeImprezy">Moje imprezy</a>
                            </li>
                           
                            <li>
								<a href="kalendarz.jsp" action="kalendarz">Kalendarz</a>
                            </li>
                            
                            
						</ul>
					<br class="clear" />
                    </div>
				</div>
			<div id="main">
				<decorator:body />
			</div>
		</div>
    
    
   		<div id="copyright">
                 &copy; <a href="mailto:i0b2s1.wat@gmail.com"> I0B2S1 </a>
		</div>
     </div>
        
</body>
</html>