/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.bean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;



import Evento.model.*;
import Evento.skydrive.SkydriveCallBack;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;

import com.opensymphony.xwork2.ActionContext;

/**
 *
 * @author Baro
 */
public class DAO implements SessionAware {                 
	
    private static final SessionFactory sessionFactory= NewHibernateUtil.getSessionFactory();
    
    		//
    private static final ThreadLocal session = new ThreadLocal();
    private static final Logger log = Logger.getAnonymousLogger();
    private Map<String, Object> sessionAware;
    
    
    
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
    
    /*public Picture updatePicture(long id,String name,String CreatedAt, String Link,User User, Event Event) throws AdException
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
   	}*/
    public Picture updatePicture(long id,String name,String CreatedAt, String Link, String tymczasowyBezposredniLink, Event Event, Album Album, User User) throws AdException
   	{
   		try{
   			begin();

   			Picture Picture = new Picture(id,name,CreatedAt,Link, tymczasowyBezposredniLink, Event, Album, User); //tutaj tworzysz sobie tego Usera ktorego chcesz dodac
   			getSession().clear();
   			getSession().saveOrUpdate(Picture);
   			commit();
   			return Picture;
   		} catch (HibernateException e){
   			rollback();
   			throw new AdException("nie udalo sie stworzyc Usera",e);
   		}
   	}
    
    public Comment createComment(String Description, Timestamp createdAt, long idPicture, long idUser ) throws AdException
	{
		try{
			begin();
			Comment Comment = new Comment(Description, createdAt, (Picture)getSession().get(Picture.class, idPicture), (User)getSession().get(User.class, idUser)); //tutaj tworzysz sobie tego Usera ktorego chcesz dodac
			getSession().save(Comment);
			commit();
			return Comment;
		} catch (HibernateException e){
			rollback();
			throw new AdException("nie udalo sie stworzyc Komentarza",e);
		}
	}
    
    public Rating createRating(Timestamp createdAt,int value, long idPicture, long idUser) throws AdException
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
    
    public Rating updateRating(long id,Timestamp createdAt,int value, long idPicture, long idUser) throws AdException
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
    
    public List getComments(int Id_Picture ){
    	Query query =  getSession().createSQLQuery("select * from Comment c, Picture p, User u WHERE c.Id_Picture = p.Id_Picture AND c.Id_User = u.Id_User AND c.Id_Picture = :Id_Picture  ")
    			.addEntity(Comment.class)
    			.addEntity(Picture.class)
    			.addEntity(User.class);
	   	query.setParameter("Id_Picture", Id_Picture);	   	
	   	return query.list();
  
   }
    
    public List getUserPicturesData(long Id_User,long Id_Album){
        Query query = getSession().createSQLQuery("select * FROM Picture p LEFT JOIN Rating r ON p.Id_Picture = r.Id_Picture and r.Id_User= :Id_User , User u LEFT JOIN Invitation i ON u.Id_User = i.Id_User,Event e WHERE p.Id_Album = :Id_Album GROUP BY p.Id_Picture")
        		.addEntity(Picture.class)
        		.addEntity(Rating.class)
        		.addEntity(Event.class)
        		.addEntity(Invitation.class);
        query.setParameter("Id_User", Id_User);
        query.setParameter("Id_Album", Id_Album);
        
        wypelnijTymczasowyBezposredniLink2(query);
        
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
    	
    	
    	
    	Query query =  getSession().createSQLQuery("Select * from User where CloudLogin = '" +email+"'").addEntity(User.class);
    	
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
        Query query =  getSession().createSQLQuery("SELECT * from Album as a, User as u, Event as e left join Invitation as i on e.Id_Event = i.Id_Event " +
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
    	
    	wypelnijTymczasowyBezposredniLink(query);
    	
    	return query.list();
    }
    
    

    
    
    
    // ###################################### - dao  do tworzenia albumu - skwaro ################################################
    
    public List<Picture> getPicturesListWithoutAlbum(String Email, long Id_Album){
    	Query query =  getSession().createSQLQuery("SELECT * from Picture p, User u where p.Id_Album=:Id_Album and u.Email=:Email ");
    	query.setParameter("Id_Album", Id_Album);
    	query.setParameter("Email", Email);
    	 List picture = query.list();
    	 for(int i=0; i<picture.size(); i++){
    		 Picture pic = (Picture) picture.get(i);
    	 }
    	
    	return query.list();
    }
    
    public List getPicturesListWithRatingWithoutAlbum(String Email, long Id_Album){
    	Query query =  getSession().createSQLQuery("SELECT * from Picture p, User u where p.Id_Album=:Id_Album and u.Email=:Email ");
    	query.setParameter("Id_Album", Id_Album);
    	query.setParameter("Email", Email);
    	return query.list();
    }
    
    public List<Event> getEventDataById(long iD_EVENT){
    	Query query =  getSession().createSQLQuery("SELECT * from Event e  where e.Id_Event=:Id_Event").addEntity(Event.class);
    	query.setParameter("Id_Event", iD_EVENT);
//    	query.setParameter("Id_User", id);
    	
    	return query.list();
    }
    
    public void createNewAlbum(List choosenList, Event event, long User_Id){
    	Album album = new Album();
    	Date d = new Date();
    	
    	
    	
    	System.out.println("data: " + d);
    	String data ="06-06-2013";
    	try{
    		SimpleDateFormat simpleDateHere = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println( simpleDateHere.format(new Date()) );
            data = simpleDateHere.format(new Date());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	album.setCreatedAt(data);
    	album.setId_Event(event);
    	Transaction transaction = null;
    	
    
	    	Session session = getSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(album);
			
	    	
			long Id_Album = getNewAlbumId(session);
		    long Id_Event = event.getId_Event();
		 	
	
	        String eventUpdateHql = "update Event e set e.Id_Album = :Id_Album where e.Id_Event = :Id_Event";
			int updatedEvent = session.createQuery(eventUpdateHql)
					.setLong("Id_Album", Id_Album)
					.setLong("Id_Event", Id_Event).executeUpdate();
	     
			
	    	for(int i=0; i < choosenList.size(); i++){
	    		System.err.println("ITERUJE????? +0 " + i);
	    		String Id_Picture = (String) choosenList.get(i);
	    		System.out.println(Id_Picture);
	    		String hqlUpdate = "update Picture c set c.Id_Album = :Id_Album where c.Id_Picture = :Id_Picture";
	    		int updatedEntities = session.createQuery(hqlUpdate)
	    				.setString("Id_Picture", Id_Picture)
	    				.setLong("Id_Album", Id_Album).executeUpdate();
	    	}
	    	
	    transaction.commit();
	    System.out.println("Commit");
    	
    	
    	
    }
 
    
    public Long getNewAlbumId(Session session){
    	System.out.println("Utworzenie albumu");
    	long Id_Album;
    	String HQL_QUERY = "select max(Id_Album) from Album a";
        Query query = session.createQuery(HQL_QUERY);
        if(query.list().get(0) != null){
        	Id_Album = (Long) query.list().get(0);
        }else{
        	Id_Album =1;
        }
        	return Id_Album;       
    }
    
    
    public List<Event> getEventDataWhichHaveAlbum(long id){
    	Query query =  getSession().createSQLQuery("SELECT * from Event e, User u where e.Id_Album is not null and u.Id_User=:Id_User and u.Id_User=e.Id_User").addEntity(Event.class);
    	query.setParameter("Id_User", id);
    	return query.list();
    }
    
    
    public List<Picture> getPictureToPublish(Long id,long Id_Album){
    	Query query =  getSession().createSQLQuery("SELECT * from Picture p, User u, Event e where p.Id_Album is not null and e.Id_Album=p.Id_Album and p.Id_Album=:Id_Album").addEntity(Picture.class);
//    	query.setParameter("Id_User", id);
    	query.setParameter("Id_Album", Id_Album);
    	return query.list();
    }
   
    
    public List<Event> getEventListWithoutAlbum(long id){
    	Query query =  getSession().createSQLQuery("select distinct(e.Id_Event), e.CreatedAt, e.EditedAt,e.Name,e.Id_User, e.Id_Album" +
    			" from Event e left join Invitation i on e.Id_Event = i.Id_Event" +
    			" where ((i.Id_Event=e.Id_Event and i.Id_User=:Id_User) or e.Id_User=:Id_UserInvent ) and e.Id_Album is null ").addEntity(Event.class);
    	query.setParameter("Id_User", id);
    	query.setParameter("Id_UserInvent", id);
    	
 //   	System.err.println("SASDASDASDASAS:ID:"+id +",    size:"+(query.list()).size());
    	
    return query.list();
    	
    }
    
    
    public List<Picture> getPictureToNewAlbum(Long Id_User, int Id_Event){
    	Query query =  getSession().createSQLQuery("select * from Picture p, Event e where p.Id_Event=e.Id_Event and p.Id_Event=:Id_Event and p.Id_Album is NULL")
    			.addEntity(Picture.class);
	   	query.setParameter("Id_Event", Id_Event);
	   	wypelnijTymczasowyBezposredniLink(query);
	   	
	   	return query.list();
  
   }
    
    public List<Picture> getPictureToNewAlbumByTopRating(List idUserList, Long Id_Event, Integer quantityOfPicture){
    	System.out.println("Wybrany event= " + Id_Event);
    	Query query =  getSession().createSQLQuery("select * from Picture p LEFT JOIN Rating r on(p.Id_Picture=r.Id_Picture), Event e, User u" +
    	" where p.Id_Event=e.Id_Event and p.Id_Event=:Id_Event " +
    	" and p.Id_Album is NULL and p.Id_User in (:idUserList) "+
		" GROUP BY p.Id_Picture HAVING avg(r.Value) between 1 and 5 or avg(r.Value) is null order by avg(r.Value) desc").addEntity(Picture.class);
	   	query.setParameter("Id_Event", Id_Event);
	   	query.setParameterList("idUserList", idUserList);
		query.setMaxResults(quantityOfPicture);
	   	wypelnijTymczasowyBezposredniLink(query);
	   	return query.list();
   }
    
    public List<Picture> getPictureToNewAlbumByWorstRating(List idUserList, Long Id_Event, Integer quantityOfPicture){
    	System.out.println("Wybrany event= " + Id_Event);
    	Query query =  getSession().createSQLQuery("select * from Picture p, Event e, Rating r, User u where p.Id_Event=e.Id_Event and p.Id_Event=:Id_Event and r.Id_Picture=p.Id_Picture and p.Id_Album is NULL and p.Id_User in (:idUserList) "+
		" GROUP BY r.Id_Picture HAVING avg(r.Value) between 1 and 5 or avg(r.Value) is null order by avg(r.Value) asc").addEntity(Picture.class);
	   	query.setParameter("Id_Event", Id_Event);
	   	query.setParameterList("idUserList", idUserList);
		query.setMaxResults(quantityOfPicture);
	   	wypelnijTymczasowyBezposredniLink(query);
	   	return query.list();
   }

    
    public List<Picture> getPictureToNewAlbumByRangeRating(List idUserList, Long Id_Event, long quantityOfPicture, String minMark, String maxMark){
    	System.out.println("Wybrany event= " + Id_Event);
    	System.out.println("Zakres w DAAO: " + minMark + " gorny: " + maxMark);
    	Query query =  getSession().createSQLQuery("select * from Picture p, Event e, Rating r where p.Id_Event=e.Id_Event and p.Id_Event=:Id_Event and r.Id_Picture=p.Id_Picture and p.Id_Album is NULL and p.Id_User in (:idUserList)"+
		" GROUP BY r.Id_Picture HAVING avg(r.Value) between :minMark and :maxMark or or avg(r.Value) is null order by avg(r.Value) asc").addEntity(Picture.class);
	   	query.setParameter("Id_Event", Id_Event);
	   	query.setParameter("minMark", minMark);
	   	query.setParameter("maxMark", maxMark);
	   	query.setParameter("idUserList", idUserList);
	   	wypelnijTymczasowyBezposredniLink(query);
	   	return query.list();
   }
    
    
    public List<Picture> getPictureToNewAlbumByMostComment(List idUserList, Long Id_Event, int quantityOfPicture,int minMark, int maxMark){
    	System.out.println("Wybrany event= " + Id_Event);
    	Query query =  getSession().createSQLQuery("Select a.* from " +
    			" Comment c RIGHT JOIN (SELECT p.* from Picture p LEFT JOIN Rating r on (p.Id_Picture=r.Id_Picture) group by p.Id_Picture " +
    			" HAVING avg(r.value) between :minMark and :maxMark or avg(r.Value) is null) as a ON c.Id_Picture=a.Id_Picture " +
    			" WHERE a.Id_User in (:idUserList)  and a.Id_Event=:Id_Event GROUP BY a.Id_Picture order by count(c.Id_Picture) DESC")
    	    	.addEntity(Picture.class);
        	
	   	query.setParameter("Id_Event", Id_Event);
	   	query.setParameterList("idUserList", idUserList);
	   	query.setInteger("minMark", minMark);
	   	query.setInteger("maxMark", maxMark);
	   	query.setMaxResults(quantityOfPicture);
	   	wypelnijTymczasowyBezposredniLink(query);
	   	return query.list();
   }
    
    
    public List<Picture> getPictureToNewAlbumByMostRated(List idUserList, Long Id_Event, int quantityOfPicture,int minMark, int maxMark){

    	Query query =  getSession().createSQLQuery("Select a.* from " +
    			" Rating c RIGHT JOIN (SELECT p.* from Picture p LEFT JOIN Rating r on (p.Id_Picture=r.Id_Picture) group by p.Id_Picture " +
    			" HAVING avg(r.value) between :minMark and :maxMark or avg(r.Value) is null) as a ON c.Id_Picture=a.Id_Picture " +
    			" WHERE a.Id_User in (:idUserList)  and a.Id_Event=:Id_Event GROUP BY a.Id_Picture order by count(c.Id_Picture) DESC")
    	    	.addEntity(Picture.class);
        	
	   	query.setParameter("Id_Event", Id_Event);
	   	query.setParameterList("idUserList", idUserList);
	   	query.setInteger("minMark", minMark);
	   	query.setInteger("maxMark", maxMark);
	   	query.setMaxResults(quantityOfPicture);
	   	wypelnijTymczasowyBezposredniLink(query);
	   	return query.list();
   }
    
    
    public List getIdUsersWhoWasOnParty(Long Id_Event){
    	Query query = getSession().createSQLQuery("select distinct(Id_User) from Picture where Id_Event=:Id_Event ");
    	query.setParameter("Id_Event",Id_Event);
		return query.list();
    	
    }
    
// ############################################### - koniec publikacja dao -skwaro - ####################################
    
    
    
    
    
    
    
    
    
    
    
    
  //zwraca liste zdjec zalogowanego uzytkownika
    public void getUserPictures(long Id_User){
        Query query =  getSession().createSQLQuery("Select * from Picture as p, User as u where p.Id_User = u.Id_User AND u.Id_User = :Id_User ")
        		.addEntity(Picture.class);
        		//.addEntity(User.class);
        query.setParameter("Id_User", Id_User);
        getLinkList(query.list());
    }
    
    public void getLinkList(List list){
    	List lista = list;
    	List<String> listaZLinkami = new ArrayList<String>();
    	java.util.ListIterator li = lista.listIterator();
    	
    	while(li.hasNext()){
    		Picture p = (Picture) li.next();
    		listaZLinkami.add(p.getLink());  		
    	}

    	
    	String tmpLink = "";
    	for(int i=0;i<listaZLinkami.size();i++){
    		tmpLink = listaZLinkami.get(i);
    		if(tmpLink.substring(0, "https://skydrive".length()).equals("https://skydrive")){
    			transformSkyDriveLink(listaZLinkami.get(i));
    		}else if(tmpLink.substring(0, "http://db".length()).equals("http://db")){
    			transformDropBoxLink(listaZLinkami.get(i));
    		}else{
    			System.out.println("przepisuje");
    			replace(listaZLinkami.get(i),listaZLinkami.get(i));
    		}
    	}
    	
        
    }
    
    
	public void replace(String s1, String s2){
    	Session session = getSession();
    	//w workbenchu musialem to zrobic zeby update zrobic
    	//Query query1 =  session.createQuery("SET SQL_SAFE_UPDATES=0");
    	Transaction tr = session.beginTransaction();
    	
    	Query query =  session.createQuery("UPDATE Picture set TymczasowyBezposredniLink = :LinkTmp where Link = :Link");
    	query.setString("Link", s1);
    	query.setString("LinkTmp", s2).executeUpdate();
    	tr.commit();
    	
    	//session.close();
    }
    
    
    public void transformDropBoxLink(String s1){
    	String shareAddress = getShareURL(s1).replaceFirst("https://www", "https://dl");
		//shareAddress += "?dl=1";
		System.out.println("dropbox share link " + shareAddress);
		replace(s1 , shareAddress);
		
    
    }
    
    public static String getShareURL(String strURL) {
		URLConnection conn = null;
		try {
			URL inputURL = new URL(strURL);
			conn = inputURL.openConnection();

		} catch (MalformedURLException e) {
			System.out.println("zjebalo sie 1");
		} catch (IOException ioe) {
			System.out.println("zjebalo sie 2");
		}

		return conn.getHeaderField("location");

	}
    
    
    
    public void transformSkyDriveLink(String s1){
    	
    	String s = s1;
    	//String skyLink = "https://apis.live.net/v5.0/photo.938823656776a0a0.938823656776A0A0!111?access_token="
    	String skyLink = "https://apis.live.net/v5.0/photo.";
    	    	
    	//wycinanie cida
		int index = s.indexOf("cid="); //Szukasz wzorca  
			if(index == -1) return ; // nie znalazl  
			String cid = s.substring(index+"cid=".length()); 
			index = cid.indexOf("&");
			cid = cid.substring(0, index);
			skyLink += cid;
			skyLink += ".";
			
		//wycinanie id i sklejenie linku 
		int index1 = s.indexOf("&resid=");
			if(index1 == -1) return; // nie znalazl  
			String id = s.substring(index1+"&resid=".length()); 
			index1 = id.indexOf("&");
			id = id.substring(0, index1);
			skyLink += id;
			skyLink += "?access_token=";
			
			System.err.println("before \n\n\n\n\n");
			if(SkydriveCallBack.accessToken != null){
				System.err.println("after \n\n\n\n\n");
				skyLink += SkydriveCallBack.accessToken.getToken().toString();
				OAuthRequest request = new OAuthRequest(Verb.GET, skyLink );
		        Response response = request.send();
		        
		        String responseS = response.getBody();

		        //wycinanie source
		        int index2 = responseS.indexOf("\"source\": \""); 
		       
		        if(index2 == -1) return; // nie znalazl  
					String source = responseS.substring(index2+"\"source\": \"".length()); 
					int index3 = source.indexOf("\"");
					source = source.substring(0, index3);
					replace(s1 , source);
			}
			
			
			
		System.out.println("koniec skydrive");		
			
    }
    
  //obowiazkowe przy SessionAware
    public void setSession(Map map) {
		this.sessionAware = map;
		
	}
    

    //dla MojeAlbumy i mojeImprezy
    public void wypelnijTymczasowyBezposredniLink(Query query){
    	
    	sessionAware = ActionContext.getContext().getSession();
    	
    	List<Picture> result = query.list();
    	String linkTymczasowy;
    	for(Picture p : result){
    		linkTymczasowy="";
    		if(p.getLink().contains("db.tt")){ // jezeli jest to dropbox
    			
    			linkTymczasowy = getShareURL(p.getLink()).replaceFirst("https://www", "https://dl");
    			//linkTymczasowy += "?dl=1";
    			
    			
    			try {
					updatePicture(p.getId_Picture(),p.getName(),p.getCreatedAt(),p.getLink(),linkTymczasowy,p.getId_Event(),p.getId_Album(),p.getId_User());
				} catch (AdException e) {
					System.err.println("ZJEBALO SIE NA <MAXA1");
					e.printStackTrace();
				}
    		
    			
    			
    		} else if(p.getLink().contains("skydrive.live.com")){ // jezeli jest to skydrive
    			String login = (String) sessionAware.get("login");
    			if(login.equals("sd")){
	    			String link = p.getLink();
	    			//wyluskanie cid oraz resid
	    			String cid;
	    			String resid;
	    			int i = link.indexOf("cid");
	    			
	    		    int j = i + 1;
	    		    while(link.charAt(j) != '&'){
	    		    	j++;
	    		    }
	    		    cid = link.substring(i+4, j);	
	    		    i=link.indexOf("resid");
	    			j=i+1;
	    			while(link.charAt(j) != '&'){
	    		    	j++;
	    		    }
	    		    resid = link.substring(i+6, j);
	    		    
	    		    Token accessToken = (Token) sessionAware.get("accessToken");
	    	        OAuthRequest request = new OAuthRequest(Verb.GET, "https://apis.live.net/v5.0/photo."+cid+"."+resid+"?access_token="+accessToken.getToken() );
	    	        Response response = request.send();
	    	        
	    	        String tmp = response.getBody(); 
	    	        i=tmp.indexOf("source");
	    	        i+=10;
	    			j=i+1;
	    			while(tmp.charAt(j) != '\"'){
	    		    	j++;
	    		    }
	    	        
	    	        
	    	        linkTymczasowy = tmp.substring(i, j);
	    	        
	    	        
	    	        try {
						updatePicture(p.getId_Picture(),p.getName(),p.getCreatedAt(),p.getLink(),linkTymczasowy,p.getId_Event(),p.getId_Album(),p.getId_User());
					} catch (AdException e) {
						System.err.println("ZJEBALO SIE NA <MAXA2");
						e.printStackTrace();
					}
    			}

    		} else { // dla innych linkow: docelowo tego nie bedzie. Jest tylko dlatego, ze tak baze mamy uzupelniona
    			 try {
  					updatePicture(p.getId_Picture(),p.getName(),p.getCreatedAt(),p.getLink(),p.getLink(),p.getId_Event(),p.getId_Album(),p.getId_User());
  				} catch (AdException e) {
  					System.err.println("ZJEBALO SIE NA <MAXA3");
  					e.printStackTrace();
  				}
    		}
    	}
    	
    	
    }
    
    //dla mojeZdjecia
    public void wypelnijTymczasowyBezposredniLink2(Query query){
    	
    	sessionAware = ActionContext.getContext().getSession();
    	
    	List<Object[]> all =  query.list();
    	
    	String linkTymczasowy;
    	for(int k=0; k<all.size(); k++){
    		Picture p = (Picture)all.get(k)[0];
    		
    		linkTymczasowy="";
    		if(p.getLink().contains("db.tt")){ // jezeli jest to dropbox
    			
    			linkTymczasowy = getShareURL(p.getLink()).replaceFirst("https://www", "https://dl");
    			//linkTymczasowy += "?dl=1";
    			
    			
    			try {
					updatePicture(p.getId_Picture(),p.getName(),p.getCreatedAt(),p.getLink(),linkTymczasowy,p.getId_Event(),p.getId_Album(),p.getId_User());
				} catch (AdException e) {
					System.err.println("ZJEBALO SIE NA <MAXA4");
					e.printStackTrace();
				}
    		
    			
    			
    		} else if(p.getLink().contains("skydrive.live.com")){ // jezeli jest to skydrive
    			String login = (String) sessionAware.get("login");
    			if(login.equals("sd")){
    				
    				String link = p.getLink();
        			//wyluskanie cid oraz resid
        			String cid;
        			String resid;
        			int i = link.indexOf("cid");
        			
        		    int j = i + 1;
        		    while(link.charAt(j) != '&'){
        		    	j++;
        		    }
        		    cid = link.substring(i+4, j);	
        		    i=link.indexOf("resid");
        			j=i+1;
        			while(link.charAt(j) != '&'){
        		    	j++;
        		    }
        		    resid = link.substring(i+6, j);
        		    Token accessToken = (Token) sessionAware.get("accessToken");
        	        OAuthRequest request = new OAuthRequest(Verb.GET, "https://apis.live.net/v5.0/photo."+cid+"."+resid+"?access_token="+accessToken.getToken() );
        	        Response response = request.send();
        	        
        	        String tmp = response.getBody(); 
        	        i=tmp.indexOf("source");
        	        i+=10;
        			j=i+1;
        			while(tmp.charAt(j) != '\"'){
        		    	j++;
        		    }
        	       	        
        	        linkTymczasowy = tmp.substring(i, j);
        
        	        try {
    					updatePicture(p.getId_Picture(),p.getName(),p.getCreatedAt(),p.getLink(),linkTymczasowy,p.getId_Event(),p.getId_Album(),p.getId_User());
    				} catch (AdException e) {
    					System.err.println("ZJEBALO SIE NA <MAXA5");
    					e.printStackTrace();
    				}
        	        
    				
    			}
    			
    			
    			
    		} else { // dla innych linkow: docelowo tego nie bedzie. Jest tylko dlatego, ze tak baze mamy uzupelniona
    			
    			 try {
 					updatePicture(p.getId_Picture(),p.getName(),p.getCreatedAt(),p.getLink(),p.getLink(),p.getId_Event(),p.getId_Album(),p.getId_User());
 				} catch (AdException e) {
 					System.err.println("ZJEBALO SIE NA <MAXA6");
 					e.printStackTrace();
 				}
    			
    		}
    	}
    	
    	
    }
    
    
    
    
    public List<Event> getPictureListFromEventsWithoutAlbum(long id){ 
        Query query =  getSession().createSQLQuery("select distinct(p.Id_Picture), p.CreatedAt, p.link,p.TymczasowyBezposredniLink,p.Id_Event,p.Id_User,p.Id_Album "+ 
                " from Picture p,Event e left join Invitation i on e.Id_Event = i.Id_Event" + 
                " where ((i.Id_Event=e.Id_Event and i.Id_User=:Id_User) or e.Id_User=:Id_UserInvent ) and e.Id_Album is null ").addEntity(Picture.class)
                
                .addEntity(Event.class)
                .addEntity(Picture.class); 
        query.setParameter("Id_User", id); 
        query.setParameter("Id_UserInvent", id);
        	
        	
        
//       System.err.println("SASDASDASDASAS:ID:"+id +",    size:"+(query.list()).size()); 
        
        return query.list(); 
        
    }
    
    
    // DO MojeZdjeciaEventAction
    public List getUserPicturesDataEvent(long Id_User,long Id_Album){
        Query query = getSession().createSQLQuery("select * FROM Picture p LEFT JOIN Rating r ON p.Id_Picture = r.Id_Picture and r.Id_User= :Id_User , User u LEFT JOIN Invitation i ON u.Id_User = i.Id_User,Event e WHERE p.Id_Event = :Id_Album GROUP BY p.Id_Picture")
        		.addEntity(Picture.class)
        		.addEntity(Rating.class)
        		.addEntity(Event.class)
        		.addEntity(Invitation.class);
        query.setParameter("Id_User", Id_User);
        query.setParameter("Id_Album", Id_Album);
        
        wypelnijTymczasowyBezposredniLink2(query);
        
        return query.list();
    }
 
    //DO MOJE EVENTY ACTION
    public List<Event> getEventListWithoutAlbum1(long id){
    	Query query =  getSession().createSQLQuery("select distinct(e.Id_Event), e.CreatedAt, e.EditedAt,e.Name,e.Id_User, e.Id_Album" +
    			" from Event e left join Invitation i on e.Id_Event = i.Id_Event" +
    			" where ((i.Id_Event=e.Id_Event and i.Id_User=:Id_User) or e.Id_User=:Id_UserInvent ) and e.Id_Album is null ")
    			.addEntity(Event.class)
    			.addEntity(Event.class);
    	query.setParameter("Id_User", id);
    	query.setParameter("Id_UserInvent", id);
    	
 //   	System.err.println("SASDASDASDASAS:ID:"+id +",    size:"+(query.list()).size());
    	
    return query.list();
    	
    }
    
    //DO LOSOWEGO ZDJECIA
    public List getPicturesListEvent(long Id_Album){
    	Query query =  getSession().createSQLQuery("SELECT * from Picture where Id_Event = :Id_Album").addEntity(Picture.class);
    	query.setParameter("Id_Album", Id_Album);
    	
    	wypelnijTymczasowyBezposredniLink(query);
    	
    	return query.list();
    }
    
    public void deletePicture(long Id_Picture) throws AdException{
		try{
			begin();
			getSession().delete((Picture)getSession().get(Picture.class, Id_Picture));
			commit();
			System.err.println("zdjecie w pizdu");
		} catch (HibernateException e){
			rollback();
			throw new AdException("nie udalo sie usunac zdjecia",e);
		}

    	//Query query =  getSession().createSQLQuery("DELETE FROM Picture where Id_Picture = :Id_Picture");
    	//query.setParameter("Id_Picture", Id_Picture);
    	
    }
    
  //tworca ecetnu
    public List getTworcaEventu(long Id_Event){
    	System.err.println("IDE:"+Id_Event);
    	Query query =  getSession().createSQLQuery("SELECT * from User u,Event e where u.Id_User=e.ID_User AND e.Id_Event = :Id_Event").addEntity(User.class);
    	query.setParameter("Id_Event", Id_Event);
    	
    	//wypelnijTymczasowyBezposredniLink(query);
    	System.err.println("DOSZLO??");
    	return query.list();
    }
    
    
    public List getTworcaZdjecia(long Id_Picture){
    	System.err.println("IDP:"+Id_Picture);
    	Query query =  getSession().createSQLQuery("SELECT u.* from User u,Picture p where u.Id_User=p.Id_User AND p.Id_Picture = :Id_Picture").addEntity(User.class);
    	query.setParameter("Id_Picture", Id_Picture);
    	
    	//wypelnijTymczasowyBezposredniLink(query);
    	System.err.println("DOSZLO??");
    	return query.list();
    }
    
    
    
}
