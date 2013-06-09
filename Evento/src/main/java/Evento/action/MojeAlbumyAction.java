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
public class MojeAlbumyAction extends ActionSupport implements SessionAware  {
 
        /**
         * 
         */
	private static final long serialVersionUID = 1L;
    private List albumList;
    private List picturesList;
    private Map<String, Object> session;
    DAO mc;
    
    public String getPictureLink(long Id_Album){
    	picturesList = mc.getPicturesList(Id_Album);
    	Random r = new Random(); 
    	int a = r.nextInt(picturesList.size());
    	return ((Picture)picturesList.get(a)).getTymczasowyBezposredniLink();
    }
    public List getAlbumList() {return albumList; }
    public void setAlbumList(long idUser){
    
    	albumList = mc.getAlbumsHavingIdUser(idUser);
    	
    	
    }
    
    public String execute() throws Exception {
            
    	session = ActionContext.getContext().getSession();
    	mc =(DAO)session.get("dao");
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	if(id == 0){
    		return ERROR;
    	}
    	else{
    		 setAlbumList(id);
    		 return SUCCESS;
    	}
    	
    	
           
           
    }
    public void setSession(Map map) {
		this.session = map;
		
	}
}