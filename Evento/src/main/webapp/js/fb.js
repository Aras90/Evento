/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


window.onload = ustawPrzycisk;
            
            FB.init({
               appId  : '487331951334796',
               status : true, 
               cookie : true, 
               xfbml  : true  
             });
             
           FB.Event.subscribe('auth.authResponseChange', function(response) {
                if (response.status === 'connected') {
                     //window.top.location = 'http://localhost:8080/Eventoo/logged.jsp';
                     ustawPrzycisk();
                }
            });
                     
            function ustawPrzycisk() {
                
                
                 FB.getLoginStatus(function(response) {
                        if (response.status === 'connected') {
                          // the user is logged in and has authenticated your
                          // app, and response.authResponse supplies
                          // the user's ID, a valid access token, a signed
                          // request, and the time the access token 
                          // and signed request each expire


                          
                          document.getElementById("fb-login").style.display = "none";
                          var x = document.getElementById("fb-logout");
                             FB.api('/me', function(response) {
                                x.innerHTML =  response.name + " <a onclick=\"fbLogout();\" > wyloguj sie</a>";
                                document.getElementById("email").value = respond.email;
                                var a = document.getElementById("email").value;
                                alert(a);
                            });
                          
                          document.getElementById("fb-logout").style.display = "list-item";
                          
                         
                         
                           
                         
                        } else if (response.status === 'not_authorized') {
                          // the user is logged in to Facebook, 
                          // but has not authenticated your app
                        } else {
                          // the user isn't logged in to Facebook.
                          // alert('y');
                           document.getElementById("fb-logout").style.display = "none";
                           var x = document.getElementById("fb-login").style.display = "inline";
                           
                           
                            
                            
                        }
                   });      
            };
         
            function fbLogout() {
                FB.logout(function (response) {
                    //Do what ever you want here when logged out like reloading the page
                     window.location = "index.jsp";
                });
             
            };   
            
           