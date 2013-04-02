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
            
            $(document).ready(function() {
            	
	
	   			$('.fancybox').fancybox();
	
	   			/*
	   			 *  Different effects
	   			 */
	
	   			// Change title type, overlay closing speed
	   			$(".fancybox-effects-a").fancybox({
	   				helpers: {
	   					title : {
	   						type : 'outside'
	   					},
	   					overlay : {
	   						speedOut : 0
	   					}
	   				}
	   			});
	
	   			// Disable opening and closing animations, change title type
	   			$(".fancybox-effects-b").fancybox({
	   				openEffect  : 'none',
	   				closeEffect	: 'none',
	
	   				helpers : {
	   					title : {
	   						type : 'over'
	   					}
	   				}
	   			});
	
	   			// Set custom style, close if clicked, change title type and overlay color
	   			$(".fancybox-effects-c").fancybox({
	   				wrapCSS    : 'fancybox-custom',
	   				closeClick : true,
	
	   				openEffect : 'none',
	
	   				helpers : {
	   					title : {
	   						type : 'inside'
	   					},
	   					overlay : {
	   						css : {
	   							'background' : 'rgba(238,238,238,0.85)'
	   						}
	   					}
	   				}
	   			});
	
	   			// Remove padding, set opening and closing animations, close if clicked and disable overlay
	   			$(".fancybox-effects-d").fancybox({
	   				padding: 0,
	
	   				openEffect : 'elastic',
	   				openSpeed  : 150,
	
	   				closeEffect : 'elastic',
	   				closeSpeed  : 150,
	
	   				closeClick : true,
	
	   				helpers : {
	   					overlay : null
	   				}
   			});

   			/*
   			 *  Button helper. Disable animations, hide close button, change title type and content
   			 */

   			$('.fancybox-buttons').fancybox({
   				openEffect  : 'none',
   				closeEffect : 'none',

   				prevEffect : 'none',
   				nextEffect : 'none',

   				closeBtn  : false,

   				helpers : {
   					title : {
   						type : 'inside'
   					},
   					buttons	: {}
   				},

   				afterLoad : function() {
   					this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
   				}
   			});


   			/*
   			 *  Thumbnail helper. Disable animations, hide close button, arrows and slide to next gallery item if clicked
   			 */

   			$('.fancybox-thumbs').fancybox({
   				prevEffect : 'none',
   				nextEffect : 'none',

   				closeBtn  : false,
   				arrows    : false,
   				nextClick : true,

   				helpers : {
   					thumbs : {
   						width  : 50,
   						height : 50
   					}
   				}
   			});

   			/*
   			 *  Media helper. Group items, disable animations, hide arrows, enable media and button helpers.
   			*/
   			$('.fancybox-media')
   				.attr('rel', 'media-gallery')
   				.fancybox({
   					openEffect : 'none',
   					closeEffect : 'none',
   					prevEffect : 'none',
   					nextEffect : 'none',

   					arrows : false,
   					helpers : {
   						media : {},
   						buttons : {}
   					}
   				});

   			/*
   			 *  Open manually
   			 */

   			$("#fancybox-manual-a").click(function() {
   				$.fancybox.open('1_b.jpg');
   			});

   			$("#fancybox-manual-b").click(function() {
   				$.fancybox.open({
   					href : 'iframe.html',
   					type : 'iframe',
   					padding : 5
   				});
   			});

   			$("#fancybox-manual-c").click(function() {
   				$.fancybox.open([
   					{
   						href : '1_b.jpg',
   						title : 'My title'
   					}, {
   						href : '2_b.jpg',
   						title : '2nd title'
   					}, {
   						href : '3_b.jpg'
   					}
   				], {
   					helpers : {
   						thumbs : {
   							width: 75,
   							height: 50
   						}
   					}
   				});
   			});

                
            });