var Kalendarz = function() {
  // Wylicz datę wielkanocy: easter($year) -> {day:$day,month:$month}
  var easter=function(y){var c,n,k,i,j,l,m,d,f=function(a){return Math.floor(a)};c=f(y/100);n=y-19*f(y/19);k=f((c-17)/25);i=c-f(c/4)-f((c-k)/3)+19*n+15;i=i-30*f((i/30));i=i-f(i/28)*(1-f(i/28)*f(29/(i+1))*f((21-n)/11));j=y+f(y/4)+i+2-c+f(c/4);j=j-7*f(j/7);l=i-j;m=3+f((l+40)/44);d=l+28-31*f(m/4);return{d:d,m:m}};
 
  // Sprawdź czy rok jest przestępny: leap(2012) -> true
  var leap=function(y){return((y%4==0)&&(y%100!=0))||y%400==0};
 
  // Wylicz ilość dni dla podanego miesiąca i roku: days(12,2012) -> 29
  var days=function(m,y){var d;if(m==2)d=leap(y)?29:28;else if(m<8)d=30+m%2;else if(m<13)d=30+(m%2==1?0:1);else throw('Bad month');return d};
 
  // Wylicz dzień tygodnia[0(niedziela)-6(sobota)]: day(6,1,2011) -> 4(czwartek)
  var day=function(d,m,y){var D=new Date();D.setFullYear(y);D.setDate(d);D.setMonth(--m);return D.getDay()};
 
  // Polskie dni tygodnia [0(niedziela)-6(sobota)]
  var dniTygodnia = ['Niedziela','Poniedziałek','Wtorek','Środa','Czwartek','Piątek','Sobota'];
 
  // Polskie miesiące [0(styczeń)-12(grudzień)]
  var miesiace = ['Styczeń','Luty','Marzec','Kwiecień','Maj','Czerwiec','Lipiec','Sierpień','Wrzesień','Październik','Listopad','Grudzień'];
 
  // Święta Statyczne (http://pl.wikipedia.org/wiki/Dni_wolne_od_pracy)
  var swietaStatyczne = function(){return[
  {d:1,m:1,o:'Nowy Rok'},
  {d:6,m:1,o:'6 stycznia – Objawienie Pańskie (pot. święto Trzech Króli) obowiązuje od 1 stycznia 2011 roku'},
  {d:1,m:5,o:'Święto Państwowe (ustawa nie nazywa tego dnia \'Świętem Pracy\')'},
  {d:3,m:5,o:'Święto Narodowe Trzeciego Maja (tj. w rocznicę uchwalenia Konstytucji w 1791 r.)'},
  {d:15,m:8,o:'Wniebowzięcie Najświętszej Maryi Panny (to święto liturgiczne wymienia ustawa; data ta jest zbieżna z wprowadzonym w 1992 r. Świętem Wojska Polskiego)'},
  {d:1,m:11,o:'Wszystkich Świętych'},
  {d:11,m:11,o:'Narodowe Święto Niepodległości (tj. w rocznicę odzyskania niepodległości w 1918 r.)'},
  {d:25,m:12,o:'pierwszy dzień Bożego Narodzenia'},
  {d:26,m:12,o:'drugi dzień Bożego Narodzenia'}]};
 
  // Święta Ruchome (http://pl.wikipedia.org/wiki/Dni_wolne_od_pracy)
  var swietaDynamiczne1 = function(y) {
    var r = []; // tablica ze swietami
    var d = new Date(), e = easter(y);
    r.push({d:e.d,m:e.m,o:'pierwszy dzień Wielkanocy (tj. Niedziela Wielkanocna)'});
    d.setFullYear(y);d.setDate(e.d);d.setMonth(e.m-1);
    var et = d.getTime(); // easter timestamp
    d.setTime(et+86400000); // (1 dzień po wielkanocy)
    r.push({d:parseInt(d.getDate()),m:parseInt(d.getMonth())+1,o:'drugi dzień Wielkanocy (tj. Poniedziałek Wielkanocny)'});
    d.setTime(et+4233600000); // (49 dni po wielkanocy)
    r.push({d:parseInt(d.getDate()),m:parseInt(d.getMonth())+1,o:'pierwszy dzień Zielonych Świątek (tj. liturgiczne Zesłanie Ducha Świętego)'});
    d.setTime(et+5184000000); // (60 dni po wielkanocy)
    r.push({d:parseInt(d.getDate()),m:parseInt(d.getMonth())+1,o:'dzień Bożego Ciała (tj. liturgiczna uroczystość Najświętszego Ciała i Krwi Pańskiej)'});
    return r;
  };
  
  //moje
  var swietaDynamiczne = function(y) {
	    var r = []; // tablica ze swietami
	    var d = new Date(), e = easter(y);
	    r.push({d:e.d,m:e.m,o:'pierwszy dzień Wielkanocy (tj. Niedziela Wielkanocna)'});
	    d.setFullYear(y);d.setDate(e.d);d.setMonth(e.m-1);
	    var et = d.getTime(); // easter timestamp
	    d.setTime(et+86400000); // (1 dzień po wielkanocy)
	    r.push({d:parseInt(d.getDate()),m:parseInt(d.getMonth())+1,o:'drugi dzień Wielkanocy (tj. Poniedziałek Wielkanocny)'});
	    d.setTime(et+4233600000); // (49 dni po wielkanocy)
	    r.push({d:parseInt(d.getDate()),m:parseInt(d.getMonth())+1,o:'pierwszy dzień Zielonych Świątek (tj. liturgiczne Zesłanie Ducha Świętego)'});
	    d.setTime(et+5184000000); // (60 dni po wielkanocy)
	    r.push({d:parseInt(d.getDate()),m:parseInt(d.getMonth())+1,o:'dzień Bożego Ciała (tj. liturgiczna uroczystość Najświętszego Ciała i Krwi Pańskiej)'});
	    return r;
	  };
  
 
  // zwraca opis święta w danym dniu lub false
  var czySwieto = function(d,m,y){
    var p = swietaDynamiczne(y);
    for(r in p) {r=p[r];if(r.d==d&&r.m==m)return r.o}
    var p = swietaStatyczne(y);
    for(r in p) {r=p[r];if(r.d==d&&r.m==m)return r.o}
    if(day(d,m,y)==0) return 'Niedziela';
    return false;
  };
 
  // pobierz informacje o miesiącu, bez parametrów pobiera aktualną datę
  this.info = function(m,y){
    // default
    var time = new Date();
    if(!m)m=time.getMonth()+1;
    if(!y)y=time.getFullYear();
    // sprawdź poprawność
    if(m<1 || m>12) throw('Bad month');
    var d = day(1,m,y),id=days(m,y),s=[];
    for(var i=1; i<=id; i++) {
      var sw = czySwieto(i,m,y);
      if(sw !== false && sw != 'Niedziela') s.push({dzien:i,nazwa:sw});
    }
    return{
      rok:y,
      miesiac: {nr:m,nazwa:miesiace[m-1]},
      startTygodnia: {nr:d,nazwa:dniTygodnia[d]},
      iloscDni: id,
      swieta: s
    };
  };
 
  this.dzienPL=function(nr){return dniTygodnia[nr]};
};