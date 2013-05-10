Evento - jak to uruchomiæ ?
============================

Co potrzeba? 
-------------------------------

1.W eclipsie trzeba mieæ zainstalowane pluginy:   
- m2e - Maven Integration for Eclipse
- Eclipse EGit
- Eclipse IDE for Java EE Developers

2.Ponadto trzeba dodaæ w pliku hosts w œcie¿ce: Windows\System32\drivers\etc wpis:
-127.0.0.1 www.evento.com

3. Server mysql musi mieæ ustawiony:
-login: root
-password: sql

4.W mysql workbench trzeba stworzyæ schemat o nazwie: eventoo_db

5. W bazie musi byæ u¿ytkownik z wype³nionym adresem email za pomoc¹ którego bêdziecie siê logowaæ przez fb, skydrive albo dropbox. Schemat naszej bazy: https://docs.google.com/file/d/0B_GeNWigRVmMcWlqa3dEdFktU0k/edit?usp=sharing

Jak logujemy siê na stronê ?
------------------------------------

Po uruchomieniu aplikacji na serwerze wpisujemy w przegl¹darce adres: www.evento.com:8080/Evento/

Jak pobraæ projekt z githuba?
-----------------------------------
1. W eclipse uruchamiamy perspektywe: Git repository Exploring
2. wybieramy clone projekt i podajemy wszystko co potrzebne tj. https://github.com/Aras90/Evento.git w polu URI oraz potrzebne passy.
3. prze³¹czamy na perspektywe java ee
4. importujemy istniej¹cy projekt mavena
5. podajemy œcie¿ke do skolonowanego projektu
6. robimy ppm na projekt -> maven -> update maven project
7. gotowe.
