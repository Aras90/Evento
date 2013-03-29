<%-- 
    Document   : logged
    Created on : 2013-03-18, 21:01:00
    Author     : Aras
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


        
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title> 
    </head>
    <script src="http://connect.facebook.net/pl_PL/all.js"></script>
    <script>
        window.onload = wyswietlImie;

        FB.init({
            appId  : '487331951334796',
            status : true, 
            cookie : true, 
            xfbml  : true  
        });
        function wyswietlImie() {
            FB.getLoginStatus(function(response) {
                if (response.status === 'connected') {
                  // the user is logged in and has authenticated your
                  // app, and response.authResponse supplies
                  // the user's ID, a valid access token, a signed
                  // request, and the time the access token 
                  // and signed request each expire
                  
                  
                  var uid = response.authResponse.userID;
                  var accessToken = response.authResponse.accessToken;
                  
                  FB.api('/me', function(response) {
                       document.getElementById('name').innerHTML = response.name;
                  });
                  
                } else if (response.status === 'not_authorized') {
                  // the user is logged in to Facebook, 
                  // but has not authenticated your app
                } else {
                  // the user isn't logged in to Facebook.
                }
           });      
       };
        
       function fbLogout() {
            FB.logout(function (response) {
                //Do what ever you want here when logged out like reloading the page
                alert('wylogowano');
                 window.location = "http://localhost:8080/Eventoo/index.htm";
            });
       };        


    </script>
    
    
    <body>
        <h1>Zalogowano. Witaj <div id="name"></div> </h1>
        <button id="fb-logout" onclick="fbLogout();"> Wyloguj sie</button>
    </body>
</html>
