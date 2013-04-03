/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.bean;

import java.util.List;
import Evento.model.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Baro
 */
public class MainClass {                 
    private SessionFactory sessionFactory=NewHibernateUtil.getSessionFactory();
    private Session session = sessionFactory.openSession();
    
    
    public List getUserPicturesData(long id){
        Query query = session.createSQLQuery("Select * from picture where Id_User = " + id).addEntity(Picture.class); 
        return query.list();
    }
    
    public List getUserData(){
        Query query = session.createSQLQuery("Select * from user").addEntity(User.class);
        return query.list(); 
    }
    public List getRatingData(){
        Query query = session.createSQLQuery("Select * from rating").addEntity(Rating.class); 
        return query.list();
    }
    public List getPictureData(){
        Query query = session.createSQLQuery("Select * from picture").addEntity(Picture.class); 
        return query.list();
    }
    
    public List getInvitationData(){
        Query query = session.createSQLQuery("Select * from invitation").addEntity(Invitation.class); 
        return query.list();
    }
    
    public List getEventData(){
        Query query = session.createSQLQuery("Select * from event").addEntity(Event.class); 
        return query.list();
    }
    
    public List getCommentData(){
        Query query = session.createSQLQuery("Select * from comment").addEntity(Comment.class); 
        return query.list();
    }
    public List getAlbumData(){
        Query query = session.createSQLQuery("Select * from album").addEntity(Album.class); 
        return query.list();
    }
    
    public List getUserIdHavingEmail(String email){
    	
    	
    	
    	Query query = session.createSQLQuery("Select * from user where Email = '" +"aszymanski18@gmail.com'").addEntity(User.class);
    	
        return query.list();
       
    	
    }
    
    public List getPicturesFromAlbum(long Id_Album, long Id_user, long Id_Event){
    	
    	Query query = session.createSQLQuery("SELECT a.Id_Album, a.CreatedAt from Album a, Event e, User u " +
    									     "WHERE a.Id_Album = "+Id_Album+"ND a.Id_Event = "+Id_Event+" AND e.Id_User = "+Id_user);
    	return query.list();
    }
    
    public List getPicturesFromAlbum(long Id_user){
        Query query = session.createSQLQuery("SELECT * from Album as a, Event as e, User as u WHERE u.Id_User = 1 AND a.Id_Album = e.Id_Album AND a.Id_Event = e.Id_Event AND e.Id_User = u.Id_User")
    	//Query query = session.createSQLQuery("SELECT Id_Album, CreatedAt, Id_Event from Album")
        		.addEntity(Album.class) 
                .addEntity(Event.class)
                .addEntity(User.class)
        		
        			;						
        									
        									
        return query.list();
    }
}
