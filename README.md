Evento - jak to uruchomi� ?
============================

Co potrzeba? 
-------------------------------

1.W eclipsie trzeba mie� zainstalowane pluginy:   
- m2e - Maven Integration for Eclipse
- Eclipse EGit
- Eclipse IDE for Java EE Developers

2.Ponadto trzeba doda� w pliku hosts w �cie�ce: Windows\System32\drivers\etc wpis:
-127.0.0.1 www.evento.com

3. Server mysql musi mie� ustawiony:
-login: root
-password: sql

4.W mysql workbench trzeba stworzy� schemat o nazwie: eventoo_db

5. W bazie musi by� u�ytkownik z wype�nionym adresem email za pomoc� kt�rego b�dziecie si� logowa� przez fb, skydrive albo dropbox. Schemat naszej bazy: https://docs.google.com/file/d/0B_GeNWigRVmMcWlqa3dEdFktU0k/edit?usp=sharing

Jak logujemy si� na stron� ?
------------------------------------

Po uruchomieniu aplikacji na serwerze wpisujemy w przegl�darce adres: www.evento.com:8080/Evento/

Jak pobra� projekt z githuba?
-----------------------------------
1. W eclipse uruchamiamy perspektywe: Git repository Exploring
2. wybieramy clone projekt i podajemy wszystko co potrzebne tj. https://github.com/Aras90/Evento.git w polu URI oraz potrzebne passy.
3. prze��czamy na perspektywe java ee
4. importujemy istniej�cy projekt mavena
5. podajemy �cie�ke do skolonowanego projektu
6. robimy ppm na projekt -> maven -> update maven project
7. gotowe.
