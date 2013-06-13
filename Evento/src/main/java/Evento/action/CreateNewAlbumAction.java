package Evento.action;

import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import Evento.bean.DAO;
import Evento.model.Event;
import Evento.model.Picture;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CreateNewAlbumAction extends ActionSupport implements SessionAware {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List choosenList;
	private String eventList;
	private Map<String, Object> session;
   private List<Event> albumList;
   private String idEvent;
   
	
	
	
	public String getIdEvent() {
	return idEvent;
}




public void setIdEvent(String idEvent) {
	this.idEvent = idEvent;
}




	public List<Event> getAlbumList() {
	return albumList;
}




public void setAlbumList(List<Event> albumList) {
	this.albumList = albumList;
}









	public String getEventList() {
	return eventList;
}




public void setEventList(String eventList) {
	this.eventList = eventList;
}




	public String getChoosenEvent() {
		return choosenEvent;
	}




	public void setChoosenEvent(String choosenEvent) {
		this.choosenEvent = choosenEvent;
	}




	public List getChoosenList() {
		return choosenList;
	}




	public void setChoosenList(List choosenList) {
		this.choosenList = choosenList;
	}


	String choosenEvent;

	Picture picture;
	

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}


	DAO mc;
	

	public String execute(){
		System.out.println("idEvent createNewAlbum: " + idEvent);
		session = ActionContext.getContext().getSession();
	    mc = (DAO)session.get("dao");
	    mc.getSession();
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	
    	if(id == 0){
    		mc.close();
    		return ERROR;
    	}
    
  
    	Event event = null;
		long ID_EVENT=Long.parseLong(idEvent);
		System.err.println("ChoosekListSize:"+choosenList.size());
    	System.out.println("Id_event: " + ID_EVENT);
    	try{
    		event = (Event) mc.getEventDataById(ID_EVENT).get(0);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		mc.createNewAlbum(choosenList,event,id);
		
		mc.close();
		
		
	
	return SUCCESS;
	}




	public void setSession(Map map) {
		this.session=map;
		
	}










}