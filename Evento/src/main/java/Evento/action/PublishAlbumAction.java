package Evento.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import Evento.bean.DAO;
import Evento.model.Event;
import Evento.model.Picture;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class PublishAlbumAction extends ActionSupport implements SessionAware  {

	
 	private List<Event> albumList;
 	private List<Picture> pictureList;
 	private String choosenEvent;
  
   
    public String getChoosenEvent() {
		return choosenEvent;
	}







	public void setChoosenEvent(String choosenEvent) {
		this.choosenEvent = choosenEvent;
	}







	public List<Picture> getPictureList() {
		return pictureList;
	}







	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}







	private Map<String, Object> session;
    
 


    
    public String execute() throws Exception {
      	session = ActionContext.getContext().getSession();
//    	String email = (String)session.get("email");
    	long id = (Long) session.get("idUser");
    	Long Id_Album = null;

    	try{
    		 Id_Album = Long.parseLong(choosenEvent);
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    	if(Id_Album!= null){
    		pictureList = DAO.getPictureToPublish(id, Id_Album);
    		return SUCCESS;
    	}else{
    		return "bad";
    	}
    	
    		
    	
    }
    
    
  




	public List<Event> getAlbumList() {
		return albumList;
	}







	public void setAlbumList(List<Event> albumList) {
		this.albumList = albumList;
	}







	public void setSession(Map map) {
		this.session = map;
	}

	
}
