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
                                x.innerHTML =  response.name + " <a onclick=\"fbLogout();\" > wyloguj sie</a>";
                                //document.getElementById("email").value = response.email;
                                //var a = document.getElementById("email").value;
                                
                          });
                          
                          //document.getElementById("fb-logout").style.display = "list-item";
 
                        } else if (response.status === 'not_authorized') {

                        } else {

                           //document.getElementById("fb-logout").style.display = "none";
                           //var x = document.getElementById("fb-login").style.display = "inline";

                        }
                   });      
            };
         
            function fbLogout() {
                FB.logout(function (response) {
                    //Do what ever you want here when logged out like reloading the page
                	
                	window.location = "index.jsp";
                });
                $.post("Evento/shutDownSession.action");
            	
            };   
            
           