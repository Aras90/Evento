
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:fb="http://www.facebook.com/2008/fbml">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Eventoo</title>
        
        <link href="http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="styles/style.css" />
        
        <script src="http://connect.facebook.net/pl_PL/all.js"></script>
        <script src="js/fb.js"></script>
    </head>

    <body>
      
        <div id="bg">
            <div id="outer">
		<div id="header">
                    <div id="fejs">
                        <span id="fb-login" style="display: none">
                               
                                <fb:login-button>Zaloguj do fb</fb:login-button>
                               
                                
                            </span>
                            
                            <span id="fb-logout" style="display: none">
                            
                                <!--button onclick="fbLogout();" >Wyloguj sie</button-->
                                <a onclick="fbLogout();">wyloguj sie</a>
                            </span>
                        
                    </div>
                    <div id="logo">
			<h1>
                            <a href="#">Eventoo</a>
                        </h1>
                    </div>
                    <div id="nav">
			<ul>
                            <li class="first active">
				<a href="index.jsp">Home</a>
                            </li>
                            <li>
				<a href="mojeAlbumy.jsp">Moje albumy</a>
                            </li>
                            <li>
				<a href="mojeImprezy.jsp">Moje imprezy</a>
                            </li>
                            <li>
				<a href="poczekalnia.jsp">Poczekalnia</a>
                            </li>
                            <li>
				<a href="kalendarz.jsp">Kalendarz</a>
                            </li>
                            
                            
			</ul>
			<br class="clear" />
                    </div>
		</div>
