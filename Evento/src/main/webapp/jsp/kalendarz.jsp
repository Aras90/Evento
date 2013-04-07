<html>
  <head>
    
  </head>
  <body><script type="text/javascript" src="http://localhost:8080/Evento/js/kalendarz/imieniny.js"></script>
    <script type="text/javascript" src="http://localhost:8080/Evento/js/kalendarz/kalendarz.js"></script>
    <script>
 
// sierpien 2010 blad
var GenerujKalendarz = function(e,m,y) {
  var im = new Imieniny();
  var k = new Kalendarz();
  var info = k.info(m,y),code;
  m=info.miesiac.nr; // if undefined
  // pobierz ilosc dni w poprzednim miesiacu
  var last = {y:info.rok,m:info.miesiac.nr}
  if(last.m==1){last.y--;last.m=12}else last.m--;
  lastDay = k.info(last.m,last.y).iloscDni;
  // generuj kod html
  code = '<table class="kalendarz"><tbody><tr><th colspan="7"><span class="miesiac"><span class="btn predMonth">&lt;&lt;</span> <span class="btn nextMonth">&gt;&gt;</span> ' + info.miesiac.nazwa.toLowerCase() + '</span><span class="rok">' + info.rok + ' <span class="btn predYear">&lt;&lt;</span> <span class="btn nextYear">&gt;&gt;</span></span></th></tr><tr>';
  for(var i=1;i<7;i++) code += '<th class="dzien-tygodnia">' + k.dzienPL(i).substr(0,3).toLowerCase() + '</th>';
  code += '<th class="dzien-tygodnia niedziela">' + k.dzienPL(0).substr(0,3).toLowerCase() + '</th></tr><tr>';
  for(var i=1;i<=42;i++){
    var dzien = i-(info.startTygodnia.nr==0 ? 7 : info.startTygodnia.nr)+1;
    var title = '';
    // pobierz swieta
    var swieto = false;
    for(var j=0; j<info.swieta.length; j++) {
      if(info.swieta[j].dzien == dzien) {
        title = 'swieto: ' + info.swieta[j].nazwa + '\n\n';
        swieto = true;
      }
    }
    // pobierz imieniny
    var tmp_title = '';
    if(dzien > 0 && dzien <= info.iloscDni) {
      var imn = im.imieniny(dzien,m);
      for(var j=0; j<imn.length; j++) {
        tmp_title += imn[j] + (j<imn.length-1 ? ', ' : '');
      }
    }
    if(tmp_title != '') title += 'Imieniny: ' + tmp_title + '\n';
    // buduj dalej
    code += '<td title="' + title + '" class="dzien'
      + (dzien<1 || dzien>info.iloscDni ? ' nieaktywny' : ' aktywny')
      + (swieto ? ' swieto' : '')
      + (i%7==0 ? ' niedziela': '') + '">'
      + (dzien>0 ? (dzien<=info.iloscDni ? dzien : dzien-info.iloscDni ) : lastDay + dzien)
    + '</td>';
    if(i%7==0 && i<42) code += '</tr><tr>';
  }
  code += '</tr></tbody></table>';
  e.innerHTML = code; // dopisz do elementu
  e.onselectstart = function(){return false}; // zablokuj mozliwosc zaznaczania tekstu
  // oskryptowanie przyciskow
  e.getElementsByClassName('predMonth')[0].addEventListener('click',function(event){
    GenerujKalendarz(e,last.m,last.y);
  },false);
  e.getElementsByClassName('nextMonth')[0].addEventListener('click',function(){
    var last = {y:info.rok,m:info.miesiac.nr}
    if(last.m==12){last.y++;last.m=1}else last.m++;
    GenerujKalendarz(e,last.m,last.y);
  },false);
  e.getElementsByClassName('predYear')[0].addEventListener('click',function(){
    GenerujKalendarz(e,info.miesiac.nr,--info.rok);
  },false);
  e.getElementsByClassName('nextYear')[0].addEventListener('click',function(){
    GenerujKalendarz(e,info.miesiac.nr,++info.rok);
  },false);
};
 
addEventListener('DOMContentLoaded',function(){
  GenerujKalendarz(document.getElementById('main'));
});
 
    </script>
    <style type="text/css">
 
 
table.kalendarz { font-size: 12px; background-color: #eee; border-radius: 5px; padding: 2px }
table.kalendarz td,table.kalendarz th {
  border: 1px solid #ddd;
  padding: 4px 10px;
  border-radius: 5px;
  text-align: center;
  background-color: white;
}
table.kalendarz .niedziela { color: red; font-weight: bold; background-color: pink }
table.kalendarz .nieaktywny { color: #bbb }
table.kalendarz .niedziela.nieaktywny { color: pink; background-color: #fff }
table.kalendarz .aktywny { cursor: pointer }
table.kalendarz .aktywny:hover { background-color: #eee; }
table.kalendarz .btn {
 background-color: #eee;
  font-size: 10px;
  border: 1px solid #eee;
  padding: 2px 4px;
  cursor: pointer;
  border-radius: 5px;
  color: navy
}
table.kalendarz .btn:hover {
  background-color: #ccc;
}
table.kalendarz .rok { float: right }
table.kalendarz .miesiac { float: left }
table.kalendarz .swieto { color: navy; font-weight: bold; background-color: #ccc }
 
    </style>