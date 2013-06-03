<%@taglib uri="/struts-tags" prefix="s"%>
<html>

<head>

	
</head>

<body>
	
	<script>
	
		var GenerujKalendarz = function(e, m, y) {
			var k = new Kalendarz();
			var info = k.info(m, y), code;
			var events = new Array();
			
			
			var itr = 0;
			'<s:iterator value="userEvents" status="stat">';
				var date = '<s:property value="userEvents [# stat.index][0].CreatedAt" />';
				var year = date.substring(0,4);
				var month = date.substring(5, 7);
				var day = date.substring(8,10);
				var name = '<s:property value="userEvents [# stat.index][0].Name"  />';
				console.log('aaaa'+name);
				var id = '<s:property value="userEvents [# stat.index][0].Id_Event"  />';
				events[itr] = {"h":id,"i":name,"d":[{"d":day,"m":month,"y":year}]};		
				itr++;
			'</s:iterator>';
			
			

			
			//for(var x=0;x<events.length;x++){
				//document.write(x+" "+events[x].i+" "+events[x].d[0].d+" "+events[x].d[0].m+" "+events[x].d[0].y);
				//document.write('<BR>');
			//}
		
			
			
			
			
			// pobierz wydarzenia dla daty
			  this.Event = function(d,m,y) {
			    var r = [];
			    for(var i=0;i<events.length;i++)
			      for(var j=0;j<events[i].d.length;j++){
			        var o=events[i].d[j];
			        
			        if(o.d==d && o.m==m && o.y==y){
			        	r.push(events[i].i);
			        	
			        }
			      }
			    return r;
			  };
			  
			  // pobierz daty dla wydarzenia
			  this.daty = function(n){
			    var r = [];
			    for(var i=0;i<events.length;i++)
			      if(events[i].i == n)r.push(events[i].d);
			    return r;
			  };
			
		
			
			m = info.miesiac.nr; 
			y = info.rok;  
			// pobierz ilosc dni w poprzednevents miesiacu
			var last = {
				y : info.rok,
				m : info.miesiac.nr
			}
			if (last.m == 1) {
				last.y--;
				last.m = 12
			} else
				last.m--;
			lastDay = k.info(last.m, last.y).iloscDni;
			// generuj kod html
			code = '<table class="kalendarz"><tbody><tr><th colspan="7"><span class="miesiac"><span class="btn predMonth">&lt;&lt;</span>' + info.miesiac.nazwa.toLowerCase()
			+ ' <span class="btn nextMonth">&gt;&gt;</span> </span><span class="rok"> <span class="btn predYear">&lt;&lt;</span> '+ info.rok + '<span class="btn nextYear">&gt;&gt;</span></span></th></tr><tr>';
			for ( var i = 1; i < 7; i++)
				code += '<th class="dzien-tygodnia">'
						+ k.dzienPL(i).substr(0, 3).toLowerCase() + '</th>';
			code += '<th class="dzien-tygodnia niedziela">'
					+ k.dzienPL(0).substr(0, 3).toLowerCase()
					+ '</th></tr><tr>';
			for ( var i = 1; i <= 42; i++) {
				var dzien = i
						- (info.startTygodnia.nr == 0 ? 7
								: info.startTygodnia.nr) + 1;
				var title = '';

				

				// pobierz Event
				var tmp_title = '';
				var wydarzenie = false;
				if (dzien > 0 && dzien <= info.iloscDni) {
					var UserEvents = Event(dzien, m, y);
					for ( var j = 0; j < UserEvents.length; j++) {
						tmp_title += UserEvents[j] + (j < UserEvents.length - 1 ? ', ' : '');
						wydarzenie = true;
					}
				}
				if (tmp_title != '')
					title += tmp_title + '\n';
				// buduj dalej
				code += '<td title="'
						+ title
						+ '" class="dzien'
						+ (dzien<1 || dzien>info.iloscDni ? ' nieaktywny'
								: ' aktywny')
						+ (wydarzenie ? ' wydarzenie' : '')
						+ (i % 7 == 0 ? ' niedziela' : '')
						+ '">'
						+ (dzien > 0 ? (dzien <= info.iloscDni ? dzien : dzien
								- info.iloscDni) : lastDay + dzien) + '</td>';
				if (i % 7 == 0 && i < 42)
					code += '</tr><tr>';
			}
			code += '</tr></tbody></table>';
			e.innerHTML = code; // dopisz do elementu
			e.onselectstart = function() {
				return false
			}; // zablokuj mozliwosc zaznaczania tekstu
			// oskryptowanie przyciskow
			e.getElementsByClassName('predMonth')[0].addEventListener('click',
					function(event) {
						GenerujKalendarz(e, last.m, last.y);
					}, false);
			e.getElementsByClassName('nextMonth')[0].addEventListener('click',
					function() {
						var last = {
							y : info.rok,
							m : info.miesiac.nr
						}
						if (last.m == 12) {
							last.y++;
							last.m = 1
						} else
							last.m++;
						GenerujKalendarz(e, last.m, last.y);
					}, false);
			e.getElementsByClassName('predYear')[0].addEventListener('click',
					function() {
						GenerujKalendarz(e, info.miesiac.nr, --info.rok);
					}, false);
			e.getElementsByClassName('nextYear')[0].addEventListener('click',
					function() {
						GenerujKalendarz(e, info.miesiac.nr, ++info.rok);
					}, false);
			
			e.onclick = function(elem){
			    var element = elem.target;
				if(element.className == 'dzien aktywny wydarzenie'){
					
					for(var i=0;i<events.length;i++){
						
						 var a = new String(events[i].i);
						 var a1 = new String(element.title);
						 
					     if(a.trim() === a1.trim()){
					    	 var idAlbumu = events[i].h
					    	 
					    	 var nazwa = a.replace(" ", "%20");
					    	// window.location = "mojeZdjecia.action?id="+idAlbumu+"&name="+nazwa;
					    	 
					     }
					}
					    	
				}
			}
		};
		
		

		addEventListener('DOMContentLoaded', function() {
			GenerujKalendarz(document.getElementById('main'));
		});
	</script>


</body>
</html>
