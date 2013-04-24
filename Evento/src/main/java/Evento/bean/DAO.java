/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.bean;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Evento.model.*;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Baro
 */
public class DAO {                 
	
    private static final SessionFactory sessionFactory= NewHibernateUtil.getSessionFactory();
    
    		//
    private static final ThreadLocal session = new ThreadLocal();
    private static final Logger log = Logger.getAnonymousLogger();
    
    
    
    public DAO(){
    	
    }
    
    
    public static Session getSession(){
    	Session session = (Session) DAO.session.get();
    	if (session == null){
    		session = sessionFactory.openSession();
    		DAO.session.set(session);
    	}
		return session;
    }
    
    protected void begin(){
    	getSession().beginTransaction();
    }
    
    protected void commit(){
    	getSession().getTransaction().commit();
    }
    
    protected void rollback(){
    	try{
    		getSession().beginTransaction().rollback();
    	}catch(HibernateException e){
    		log.log(Level.WARNING,"BLAD rollbacka #1",e);
    	}
    	try{
    		getSession().close();
    	}catch(HibernateException e){
    		log.log(Level.WARNING,"BLAD ZAMKNIECIA #1",e);
    	}
    	DAO.session.set(null);
    }
    public static void close(){
    	getSession().close();
    	DAO.session.set(null);
    }
    
    //obie funkcje(create i update) moznaby bylo chyba wjebac w jedna, bo roznia sie tylko uzyciem "cleana" i uzyciem do zapisu innej funkcji
    //ale  dla przejrzystosci zostawilem tak. Jak chcecie to uzywajcie tylko tej drugiej, tylko wtedy trzeba kontrolwac czy nie udpatuje nie tych rekordoow co chcecie. 
    public User createUser(long id,String email,String password, String desc) throws AdException
	{
		try{
			begin();
			User User = new User(email,password,desc); //tutaj tworzysz sobie tego Usera ktorego chcesz dodac
			getSession().save(User);
			commit();
			return User;
		} catch (HibernateException e){
			rollback();
			throw new AdException("nie udalo sie stworzyc Usera",e);
		}
	}
    
    public User updateUser(String email,String password, String desc) throws AdException
   	{
   		try{
   			begin();
   			User User = new User(email,password,desc); //tutaj tworzysz sobie tego Usera ktorego chcesz zmienic
   			getSession().clear();
   			getSession().saveOrUpdate(User);
   			commit();
   			return User;
   		} catch (HibernateException e){
   			rollback();
   			throw new AdException("nie udalo sie stworzyc Usera",e);
   		}
   	}

    public Picture createPicture(long id,String name,String CreatedAt, String Link,User User, Event Event) throws AdException
   	{
   		try{
   			begin();
   			Picture Picture = new Picture(id,name,CreatedAt,Link,User,Event); //tutaj tworzysz sobie tego Usera ktorego chcesz dodac
   			getSession().save(Picture);
   			commit();
   			return Picture;
   		} catch (HibernateException e){
   			rollback();
   			throw new AdException("nie udalo sie stworzyc Usera",e);
   		}
   	}
    
    public Picture updatePicture(long id,String name,String CreatedAt, String Link,User User, Event Event) throws AdException
   	{
   		try{
   			begin();
   			Picture Picture = new Picture(id,name,CreatedAt,Link,User,Event); //tutaj tworzysz sobie tego Usera ktorego chcesz dodac
   			getSession().clear();
   			getSession().saveOrUpdate(Picture);
   			commit();
   			return Picture;
   		} catch (HibernateException e){
   			rollback();
   			throw new AdException("nie udalo sie stworzyc Usera",e);
   		}
   	}
    public Rating createRating(String createdAt,int value, long idPicture, long idUser) throws AdException
	{
		try{
			begin();
			Rating Rating = new Rating(createdAt, value, (Picture)getSession().get(Picture.class, idPicture), (User)getSession().get(User.class, idUser)); //tutaj tworzysz sobie tego Usera ktorego chcesz dodac
			getSession().save(Rating);
			commit();
			return Rating;
		} catch (HibernateException e){
			rollback();
			throw new AdException("nie udalo sie stworzyc Ratingu",e);
		}
	}
    
    public Rating updateRating(long id,String createdAt,int value, long idPicture, long idUser) throws AdException
   	{
   		try{
   			begin();
   			Rating Rating = new Rating(id, createdAt, value, (Picture)getSession().get(Picture.class, idPicture), (User)getSession().get(User.class, idUser)); //tutaj tworzysz sobie tego Usera ktorego chcesz zmienic
   			getSession().clear();
   			getSession().saveOrUpdate(Rating);
   			commit();
   			return Rating;
   		} catch (HibernateException e){
   			rollback();
   			throw new AdException("nie udalo sie stworzyc Usera",e);
   		}
   	}
    
    
    public List getUserPicturesData(long Id_User,long Id_Album){
        Query query = getSession().createSQLQuery("SELECT * FROM Picture p left join Rating r on p.Id_Picture = r.Id_Picture,Event e, Invitation i " +  
        										"WHERE p.Id_Album = :Id_Album AND (p.Id_User=:Id_User OR (p.Id_Event = e.Id_Event AND e.Id_Event = i.Id_Event AND i.Id_User = :Id_User)) " +
        										"Group by p.Id_Picture")
        		.addEntity(Picture.class)
        		.addEntity(Rating.class)
        		.addEntity(Event.class)
        		.addEntity(Invitation.class);
        query.setParameter("Id_User", Id_User);
        query.setParameter("Id_Album", Id_Album);
        return query.list();
    }
    public List getUserRatingData(long Id_User, long Id_Picture){
    	Query query = getSession().createSQLQuery("SELECT r.* FROM User u, Picture p, Rating r " + 
    											  "WHERE u.Id_User = p.Id_User AND p.Id_Picture = r.Id_Picture AND u.Id_User = " + Id_User + " AND p.Id_Picture = " + Id_Picture)
    			.addEntity(Rating.class);
    	return query.list();
    }
    public List getUserPicturesDataWithRate(long Id_User,long Id_Album){
        Query query =  getSession().createSQLQuery("Select * from Picture as p left join Rating as r ON r.Id_Picture = p.Id_Picture " +
        									"where p.Id_User = :Id_User AND p.Id_Album = :Id_Album")
        		.addEntity(Picture.class)
        		.addEntity(Rating.class);
        query.setParameter("Id_User", Id_User);
        query.setParameter("Id_Album", Id_Album);
        return query.list();
    }
    
    public List getUserData(){
        Query query =  getSession().createSQLQuery("Select * from User").addEntity(User.class);
        return query.list(); 
    }
    public List getRatingData(){
        Query query =  getSession().createSQLQuery("Select * from Rating").addEntity(Rating.class); 
        return query.list();
    }
    public List getPictureData(){
        Query query =  getSession().createSQLQuery("Select * from Picture").addEntity(Picture.class); 
        return query.list();
    }
    
    public List getInvitationData(){
        Query query = getSession().createSQLQuery("Select * from Invitation").addEntity(Invitation.class); 
        return query.list();
    }
    
    public List getEventData(){
        Query query =  getSession().createSQLQuery("Select * from Event").addEntity(Event.class); 
        return query.list();
    }
    
    public List getCommentData(){
        Query query =  getSession().createSQLQuery("Select * from Comment").addEntity(Comment.class); 
        return query.list();
    }
    public List getAlbumData(){
        Query query =  getSession().createSQLQuery("Select * from Album").addEntity(Album.class); 
        return query.list();
    }
    
    public List getUserIdHavingEmail(String email){
    	
    	
    	
    	Query query =  getSession().createSQLQuery("Select * from User where Email = '" +email+"'").addEntity(User.class);
    	
        return query.list();
       
    	
    }
     
    public List getPicturesFromAlbum(long Id_User){
        Query query =  getSession().createSQLQuery("SELECT * from Album as a, Event as e, User as u WHERE u.Id_User = :Id_User AND a.Id_Album = e.Id_Album AND a.Id_Event = e.Id_Event AND e.Id_User = u.Id_User")
    	//Query query = session.createSQLQuery("SELECT Id_Album, CreatedAt, Id_Event from Album")
        		.addEntity(Album.class) 
                .addEntity(Event.class)
                .addEntity(User.class);
        query.setParameter("Id_User", Id_User);
       	
        								
        									
        									
        return query.list();
    }
    
    public List getUserEvents(long Id_User){
        Query query =  getSession().createSQLQuery("Select * FROM Event, User WHERE Event.Id_User=User.Id_User AND User.Id_User=:Id_User")
        		.addEntity(Event.class)
        		.addEntity(User.class)		              
        		; 
        query.setParameter("Id_User", Id_User);
        return query.list();
    }
    
    public List getAlbumsHavingIdUser(long Id_User){
        Query query =  getSession().createSQLQuery("SELECT * from Album as a, Event as e, User as u " +
        									 "WHERE u.Id_User = :Id_User AND a.Id_Album = e.Id_Album AND a.Id_Event = e.Id_Event AND e.Id_User = u.Id_User")
    	//Query query = session.createSQLQuery("SELECT Id_Album, CreatedAt, Id_Event from Album")
        		.addEntity(Album.class) 
                .addEntity(Event.class)
                .addEntity(User.class);
        query.setParameter("Id_User", Id_User);        
        		
        					
        
       			
        return query.list();
    }
    public List getAlbumsHavingIdUserOrInvitation(long Id_User){
        Query query =  getSession().createSQLQuery("SELECT * from Album as a, Event as e, User as u, Invitation as i " +
        									 "WHERE (u.Id_User = " + Id_User + " OR i.Id_User = " + Id_User + ") AND a.Id_Album = e.Id_Album AND a.Id_Event = e.Id_Event AND (e.Id_User = u.Id_User OR e.Id_Event = i.Id_Event) GROUP BY a.Id_Album")
    	//Query query = session.createSQLQuery("SELECT Id_Album, CreatedAt, Id_Event from Album")
        		.addEntity(Album.class) 
                .addEntity(Event.class)
                .addEntity(User.class)
                
        		
        			;		
        
       			
        return query.list();
    }
    
    public List getPicturesList(long Id_Album){
    	Query query =  getSession().createSQLQuery("SELECT * from Picture where Id_Album = :Id_Album").addEntity(Picture.class);
    	query.setParameter("Id_Album", Id_Album);
    	
    	return query.list();
    }
 
}
