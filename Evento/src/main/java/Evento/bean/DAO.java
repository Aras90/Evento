/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evento.bean;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



import Evento.model.*;
import Evento.skydrive.SkydriveCallBack;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

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
        Query query = getSession().createSQLQuery("select * FROM Picture p LEFT JOIN Rating r ON p.Id_Picture = r.Id_Picture and r.Id_User= :Id_User , User u LEFT JOIN Invitation i ON u.Id_User = i.Id_User,Event e WHERE p.Id_Album = :Id_Album GROUP BY p.Id_Picture")
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
    
    
    public List<Picture> getPicturesListWithoutAlbum(String Email, long Id_Album){
    	Query query =  getSession().createSQLQuery("SELECT * from picture p, user u where p.Id_Album=:Id_Album and u.Email=:Email ").addEntity(Picture.class);
    	query.setParameter("Id_Album", Id_Album);
    	return query.list();
    }
 
    public void assignPictureToAlbum(long Id_Picture, long Id_Album) {
    	
    			Session session = getSession();
    			Transaction transaction = session.beginTransaction();
    	
    	
    			transaction.commit();
    			session.close(); 	
    		}
    
    public List<Event> getEventDataById(long iD_EVENT, long id){
    	Query query =  getSession().createSQLQuery("SELECT * from event e, user u where e.Id_Event=:Id_Event and u.Id_User=:Id_User and u.Id_User=e.Id_User ").addEntity(Event.class);
    	query.setParameter("Id_Event", iD_EVENT);
    	query.setParameter("Id_User", id);
    	
    	return query.list();
    }
    
    public void createNewAlbum(List choosenList, Event event, long User_Id){
    	Album album = new Album();
    	album.setCreatedAt("2012-05-05");
    	album.setId_Event(event);
    	
    	Session session = getSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		
		session.saveOrUpdate(album);
    	
		long Id_Album = getNewAlbumId(session);
	       long Id_Event = event.getId_Event();
	 	
    	//session.save(album);
    	
    	 
        String eventUpdateHql = "update Event e set e.Id_Album = :Id_Album where e.Id_Event = :Id_Event";
		int updatedEvent = session.createQuery(eventUpdateHql)
				.setLong("Id_Album", Id_Album)
				.setLong("Id_Event", Id_Event).executeUpdate();
     
    	
    	for(int i=0; i < choosenList.size(); i++){
    		String Id_Picture = (String) choosenList.get(i);
    		System.out.println(Id_Picture);
    		String hqlUpdate = "update Picture c set c.Id_Album = :Id_Album where c.Id_Picture = :Id_Picture";
    		int updatedEntities = session.createQuery(hqlUpdate)
    				.setString("Id_Picture", Id_Picture)
    				.setLong("Id_Album", Id_Album).executeUpdate();
    	}
    	
    	
    	transaction.commit();
//		session.close();
		
    }
 
    
    public Long getNewAlbumId(Session session){
    	
    	long Id_Album;
    	
    	String HQL_QUERY = "select max(Id_Album) from Album a";
        Query query = session.createQuery(HQL_QUERY);
        System.out.println(query.list().get(0) + " rozmiar listy ");
        if(query.list().get(0) != null){
        	Id_Album = (Long) query.list().get(0);
        }else{
        	Id_Album =1;
        }
        
        System.out.println(Id_Album + " idAlbumu");
        
      
        	return Id_Album;       
    }
    
    
    public List<Event> getEventDataWhichHaveAlbum(long id){
    	Query query =  getSession().createSQLQuery("SELECT * from event e, user u where e.Id_Album is not null and u.Id_User=:Id_User and u.Id_User=e.Id_User").addEntity(Event.class);
    	query.setParameter("Id_User", id);
    	
    	return query.list();
    }
    
    
    public List<Picture> getPictureToPublish(Long id,long Id_Album){
    	Query query =  getSession().createSQLQuery("SELECT * from picture p, user u where p.Id_Album is not null and u.Id_User=:Id_User and u.Id_User=p.Id_User and p.Id_Album=:Id_Album").addEntity(Picture.class);
    	query.setParameter("Id_User", id);
    	query.setParameter("Id_Album", Id_Album);
    	return query.list();
    }
    
    public List getEventListWithoutAlbum(long id){
    	Query query =  getSession().createSQLQuery("select * from event e, user u where e.Id_Album is null and u.Id_User=:Id_User and u.Id_User=e.Id_User").addEntity(Event.class);
    	query.setParameter("Id_User", id);
    	return query.list();
    	
    }
    
    public List<Picture> getPictureToNewAlbum(Long Id_User, Long Id_Event){
		Query query =  getSession().createSQLQuery("select * from picture p, user u where u.Id_User=:Id_User and u.Id_User=p.Id_User and p.Id_Event=:Id_Event and p.Id_Album is NULL").addEntity(Picture.class);
	   	query.setParameter("Id_User",Id_User);
	   	query.setParameter("Id_Event", Id_Event);
	   	return query.list();
  
   }
    
    
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
		shareAddress += "?dl=1";
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
    
    
 
}
