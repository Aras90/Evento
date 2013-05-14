package Evento.action;

import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import org.apache.struts2.interceptor.SessionAware;

import Evento.bean.DAO;
import Evento.model.Picture;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NewAlbumPictureListAction extends ActionSupport implements SessionAware {

	
	private List<Picture> pictureToAlbumList;
	 private Map<String, Object> session;
	 private String choosenEvent;
	 public String getChoosenEvent() {
		return choosenEvent;
	}

	public void setChoosenEvent(String choosenEvent) {
		this.choosenEvent = choosenEvent;
	}

	DAO dao;
	
	public List<Picture> getPictureToAlbumList() {
		return pictureToAlbumList;
	}

	public void setPictureToAlbumList(List<Picture> pictureToAlbumList) {
		this.pictureToAlbumList = pictureToAlbumList;
	}

	public String execute(){
		dao = new DAO();
		session = ActionContext.getContext().getSession();
    	String email = (String)session.get("email");
    	long id = (Long) session.get("idUser");
    	Long eventId = null;
    	try{
    		 eventId = Long.parseLong(choosenEvent);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
    	if(eventId != null){
    		pictureToAlbumList = dao.getPictureToNewAlbum(id, eventId);
    		System.out.println(pictureToAlbumList.size());
    		System.out.println(pictureToAlbumList.get(0).getTymczasowyBezposredniLink());
    		return SUCCESS;
    	}else{
    		return ERROR;
    	}
	}

	

	
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map map) {
		this.session = map;
	}

	
}
