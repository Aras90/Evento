<%@ include file="header.jsp" %>

<%@page import="Evento.model.Comment"%>
<%@page import="Evento.model.Picture"%>
<%@page import="Evento.model.Rating"%>
<%@page import="Evento.model.User"%>
<%@page import="java.util.List"%>
<%@page import="Evento.bean.MainClass"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
//MainClass mc = new MainClass();
//List<User> listaUsers = mc.getUserData();
//List<Rating> listaRatings = mc.getRatingData();
//List<Picture> listaPictures = mc.getPictureData();
//List<Picture> listaPictures2 = mc.getUserPicturesData(1);
//List<Comment> listaComments = mc.getCommentData();

%>


		<div id="main">
                    <div style="font-size: 20px; color: white; margin-bottom:20px;">Dobra teraz dodawanie do bazy powinno smigac dobrze, dodalem wam przyklad wyciagniecia z bazy zdjec wykonanych tylko przez uzytkownika numer 1.
                        wprowadzanie dancyh do bazy(kolejnosc)
                        1user
                        2event
                        3picture
                        4reszta
                        <div style="text-align: right">Zwolej</div></div>
                        
                       
                        
                        
                        <h1>Eventoo</h1>
                        <a href="http://facebook.com">zaloguj</a>
                        <h2 style="font-size: 100px; text-align: center">DO ROBOTY!</h2>

                       

                                         
                        <div id="fb-root">
                            <!-- <button id="fb-login">Zaloguj się do mojej aplikacji!</button>  -->
                        </div>
                         
         
		</div>
                <%@ include file="footer.jsp" %>
