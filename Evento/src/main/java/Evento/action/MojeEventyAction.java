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
public class MojeEventyAction extends ActionSupport implements SessionAware  {
 
        /**
         * 
         */
	private static final long serialVersionUID = 1L;
    private List eventList;
    private List picturesList;
    private Map<String, Object> session;
    
   
    public String getPictureLink(long Id_Event){
    	picturesList = DAO.getPicturesListEvent(Id_Event);
    	Random r = new Random(); 
    	int a = r.nextInt(picturesList.size());
    	return ((Picture)picturesList.get(a)).getTymczasowyBezposredniLink();
    }
    public List getEventList() {return eventList; }
    
    public void setEventList(long idUser){
    	
    	eventList = DAO.getEventListWithoutAlbum1(idUser);
    	
    	
    }
    
    
   
    
    public String execute() throws Exception {
            
    	session = ActionContext.getContext().getSession();
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	if(id == 0){
    		return ERROR;
    	}
    	else{
    		 setEventList(id);
    		 return SUCCESS;
    	}
    	
    	
           
           
    }
    public void setSession(Map map) {
		this.session = map;
		
	}
}