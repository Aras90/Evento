package Evento.action;
 
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.struts2.interceptor.SessionAware;

import Evento.model.*;
 
import Evento.bean.DAO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
 
@Conversion()
public class MojeImprezyAction extends ActionSupport implements SessionAware  {
 
        /**
         * 
         */
	private static final long serialVersionUID = 1L;
    private List albumList;
    private List picturesList;
    DAO mc;
    private Map<String, Object> session;
    private List<Event> albumEventList;
    private List<Event> eventList;
   
    public List<Event> getEventList() {
		return eventList;
	}
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	public List<Event> getAlbumEventList() {
		return albumEventList;
	}
	public void setAlbumEventList(List<Event> albumEventList) {
		this.albumEventList = albumEventList;
	}
	public String getPictureLink(long Id_Album){
    	picturesList = mc.getPicturesList(Id_Album);
    	Random r = new Random(); 
    	int a = r.nextInt(picturesList.size());
    	return ((Picture)picturesList.get(a)).getTymczasowyBezposredniLink();
    }
    public List getAlbumList() {return albumList; }
    public void setAlbumList(long idUser){
    	
    	albumList = mc.getAlbumsHavingIdUserOrInvitation(idUser);
    	
    }
    
    public String execute() throws Exception {
            
    	session = ActionContext.getContext().getSession();
    	mc = (DAO)session.get("dao");
    	mc.getSession();
    
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	
    	if(id == 0){
    		mc.close();
    		return ERROR;
    	}
    	else{
    		 setAlbumList(id);
    		 mc.close();
    		 return SUCCESS;
    	}
    	
    	
    		
    }
    public void setSession(Map map) {
		this.session = map;
		
	}
}