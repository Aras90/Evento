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
    DAO mc = new DAO();
    private Map<String, Object> session;
   
    public String getPictureLink(long Id_Album){
    	picturesList = mc.getPicturesList(Id_Album);
    	Random r = new Random(); 
    	int a = r.nextInt(picturesList.size());
    	return ((Picture)picturesList.get(a)).getLink();
    }
    public List getAlbumList() {return albumList; }
    public void setAlbumList(long idUser){
    	
    	albumList = mc.getAlbumsHavingIdUserOrInvitation(idUser);
    	
    }
    
    public String execute() throws Exception {
            
    	session = ActionContext.getContext().getSession();
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	if(id == 0){
    		return ERROR;
    	}
    	else{
    		 setAlbumList(id);
    		 return SUCCESS;
    	}
    	
    	
    		
    }
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}
}