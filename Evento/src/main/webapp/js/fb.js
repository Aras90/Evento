/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


window.onload = ustawPrzycisk;
            
            FB.init({
               appId  : '170054506477361',
               status : true, 
               cookie : true, 
               xfbml  : true  
             });
            
            
    
            function ustawPrzycisk() {
                
                 FB.getLoginStatus(function(response) {
                        if (response.status == 'connected') {
                   
                          //document.getElementById("fb-login").style.display = "none";
                          var x = document.getElementById("fb-logout");
                             FB.api('/me', function(response) {
                                x.innerHTML =  response.name + " <a onclick=\"fbLogout();\" >Wyloguj sie</a>";
                                //document.getElementById("email").value = response.email;
                                //var a = document.getElementById("email").value;
                                
                          });
                          
                          //document.getElementById("fb-logout").style.display = "list-item";
                            // alert("nieZALOGOWANY1");
                        } else if (response.status === 'not_authorized') {
                        	//alert("nieZALOGOWANY2");
                        } else {
                        	//alert("nieZALOGOWANY3");
                           //document.getElementById("fb-logout").style.display = "none";
                           //var x = document.getElementById("fb-login").style.display = "inline";

                        }
                   });

            };
         
            function fbLogout() {
                FB.logout(function (response) {
                	window.location = "index.jsp";
                });
                $.post("Evento/shutDownSession.action");
            	
            };
            
            function sdLogout() {
            	WL.logout();
                $.post("Evento/shutDownSession.action");
            	
            };

           