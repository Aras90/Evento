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
    
    
    
    
    
    
    
    /*public List getUserPicturesData(long Id_User,long Id_Album){
        Query query =  getSession().createSQLQuery("Select * from picture where Id_User = :Id_User AND Id_Album = :Id_Album")
        		.addEntity(Picture.class);
        query.setParameter("Id_User", Id_User);
        query.setParameter("Id_Album", Id_Album);
        return query.list();
    }*/
    
    
    
    
    
    
    
    
    //obie funkcje(create i update) moznaby bylo chyba wjebac w jedna, bo roznia sie tylko uzyciem "cleana" i uzyciem do zapisu innej funkcji
    //ale  dla przejrzystosci zostawilem tak. Jak chcecie to uzywajcie tylko tej drugiej, tylko wtedy trzeba kontrolwac czy nie udpatuje nie tych rekordoow co chcecie. 
    public User createUser(long id,String email,String password, String desc) throws AdException
	{
		try{
			begin();
			User user = new User(email,password,desc); //tutaj tworzysz sobie tego usera ktorego chcesz dodac
			getSession().save(user);
			commit();
			return user;
		} catch (HibernateException e){
			rollback();
			throw new AdException("nie udalo sie stworzyc usera",e);
		}
	}
    
    public User updateUser(String email,String password, String desc) throws AdException
   	{
   		try{
   			begin();
   			User user = new User(email,password,desc); //tutaj tworzysz sobie tego usera ktorego chcesz zmienic
   			getSession().clear();
   			getSession().saveOrUpdate(user);
   			commit();
   			return user;
   		} catch (HibernateException e){
   			rollback();
   			throw new AdException("nie udalo sie stworzyc usera",e);
   		}
   	}

    public Picture createPicture(long id,String name,String CreatedAt, String Link,User user, Event event) throws AdException
   	{
   		try{
   			begin();
   			Picture picture = new Picture(id,name,CreatedAt,Link,user,event); //tutaj tworzysz sobie tego usera ktorego chcesz dodac
   			getSession().save(picture);
   			commit();
   			return picture;
   		} catch (HibernateException e){
   			rollback();
   			throw new AdException("nie udalo sie stworzyc usera",e);
   		}
   	}
    
    public Picture updatePicture(long id,String name,String CreatedAt, String Link,User user, Event event) throws AdException
   	{
   		try{
   			begin();
   			Picture picture = new Picture(id,name,CreatedAt,Link,user,event); //tutaj tworzysz sobie tego usera ktorego chcesz dodac
   			getSession().clear();
   			getSession().saveOrUpdate(picture);
   			commit();
   			return picture;
   		} catch (HibernateException e){
   			rollback();
   			throw new AdException("nie udalo sie stworzyc usera",e);
   		}
   	}
    public Rating createRating(String createdAt,int value, long idPicture, long idUser) throws AdException
	{
		try{
			begin();
			Rating rating = new Rating(createdAt, value, (Picture)getSession().get(Picture.class, idPicture), (User)getSession().get(User.class, idUser)); //tutaj tworzysz sobie tego usera ktorego chcesz dodac
			getSession().save(rating);
			commit();
			return rating;
		} catch (HibernateException e){
			rollback();
			throw new AdException("nie udalo sie stworzyc ratingu",e);
		}
	}
    
    public Rating updateRating(long id,String createdAt,int value, long idPicture, long idUser) throws AdException
   	{
   		try{
   			begin();
   			Rating rating = new Rating(id, createdAt, value, (Picture)getSession().get(Picture.class, idPicture), (User)getSession().get(User.class, idUser)); //tutaj tworzysz sobie tego usera ktorego chcesz zmienic
   			getSession().clear();
   			getSession().saveOrUpdate(rating);
   			commit();
   			return rating;
   		} catch (HibernateException e){
   			rollback();
   			throw new AdException("nie udalo sie stworzyc usera",e);
   		}
   	}
    
    
    public List getUserPicturesData(long Id_User,long Id_Album){
        Query query = getSession().createSQLQuery("Select * from picture as p left join rating as r ON r.Id_Picture = p.Id_Picture " +
        									"where p.Id_User = :Id_User AND p.Id_Album = :Id_Album")
        		.addEntity(Picture.class)
        		.addEntity(Rating.class);
        query.setParameter("Id_User", Id_User);
        query.setParameter("Id_Album", Id_Album);
        return query.list();
    }
    
    public List getUserPicturesDataWithRate(long Id_User,long Id_Album){
        Query query =  getSession().createSQLQuery("Select * from picture as p left join rating as r ON r.Id_Picture = p.Id_Picture " +
        									"where p.Id_User = :Id_User AND p.Id_Album = :Id_Album")
        		.addEntity(Picture.class)
        		.addEntity(Rating.class);
        query.setParameter("Id_User", Id_User);
        query.setParameter("Id_Album", Id_Album);
        return query.list();
    }
    
    public List getUserData(){
        Query query =  getSession().createSQLQuery("Select * from user").addEntity(User.class);
        return query.list(); 
    }
    public List getRatingData(){
        Query query =  getSession().createSQLQuery("Select * from rating").addEntity(Rating.class); 
        return query.list();
    }
    public List getPictureData(){
        Query query =  getSession().createSQLQuery("Select * from picture").addEntity(Picture.class); 
        return query.list();
    }
    
    public List getInvitationData(){
        Query query = getSession().createSQLQuery("Select * from invitation").addEntity(Invitation.class); 
        return query.list();
    }
    
    public List getEventData(){
        Query query =  getSession().createSQLQuery("Select * from event").addEntity(Event.class); 
        return query.list();
    }
    
    public List getCommentData(){
        Query query =  getSession().createSQLQuery("Select * from comment").addEntity(Comment.class); 
        return query.list();
    }
    public List getAlbumData(){
        Query query =  getSession().createSQLQuery("Select * from album").addEntity(Album.class); 
        return query.list();
    }
    
    public List getUserIdHavingEmail(String email){
    	
    	
    	
    	Query query =  getSession().createSQLQuery("Select * from user where Email = '" +"aszymanski18@gmail.com'").addEntity(User.class);
    	
        return query.list();
       
    	
    }
     
    public List getPicturesFromAlbum(long Id_user){
        Query query =  getSession().createSQLQuery("SELECT * from Album as a, Event as e, User as u WHERE u.Id_User = 1 AND a.Id_Album = e.Id_Album AND a.Id_Event = e.Id_Event AND e.Id_User = u.Id_User")
    	//Query query = session.createSQLQuery("SELECT Id_Album, CreatedAt, Id_Event from Album")
        		.addEntity(Album.class) 
                .addEntity(Event.class)
                .addEntity(User.class)
        		
        			;						
        									
        									
        return query.list();
    }
    
    public List getUserEvents(long Id_User){
        Query query =  getSession().createSQLQuery("Select * FROM event, user WHERE event.Id_User=user.Id_User AND user.Id_User=:Id_User")
        		.addEntity(Event.class)
        		.addEntity(User.class)		              
        		; 
        query.setParameter("Id_User", Id_User);
        return query.list();
    }
    
    public List getAlbumsHavingIdUser(long Id_user){
        Query query =  getSession().createSQLQuery("SELECT * from Album as a, Event as e, User as u " +
        									 "WHERE u.Id_User = 1 AND a.Id_Album = e.Id_Album AND a.Id_Event = e.Id_Event AND e.Id_User = u.Id_User")
    	//Query query = session.createSQLQuery("SELECT Id_Album, CreatedAt, Id_Event from Album")
        		.addEntity(Album.class) 
                .addEntity(Event.class)
                .addEntity(User.class)
                
        		
        			;		
        
       			
        return query.list();
    }
    
    public List getPicturesList(long Id_Album){
    	Query query =  getSession().createSQLQuery("SELECT * from picture where Id_Album = :Id_Album").addEntity(Picture.class);
    	query.setParameter("Id_Album", Id_Album);
    	
    	return query.list();
    }
 
}
