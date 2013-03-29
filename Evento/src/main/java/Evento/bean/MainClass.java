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
}
