<%@page import="java.util.List"%>
<%@page import="Evento.model.Picture"%>
<%@page import="Evento.bean.MainClass"%>
   
		<%@ include file="header.jsp" %>
		<div id="main">
                        
                    Moje albumy <br>
                    
                    <%
                        MainClass mc = new MainClass();
                        List<Picture> listaPictures = mc.getUserPicturesData(1);
                        
                        
                       
                    %>
                    
                        ile: <% out.println(listaPictures.size()); %>
                     
                       
                         
                         
                        

                    
                    
		</div>
                <%@ include file="footer.jsp" %>
