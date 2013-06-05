package Evento.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import Evento.bean.DAO;
import Evento.model.Picture;
import Evento.model.Rating;
import Evento.model.User;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;

/**
 * 
 */
@Conversion()
public class DeleteAlbumAction extends ActionSupport implements SessionAware  {
    
    private int idAlbum;
    private Map<String, Object> session;
    
	public int getIdAlbum() {
		return idAlbum;
	}
	public void setIdAlbum(int idAlbum) {
		this.idAlbum = idAlbum;
	}

	public String execute() throws Exception {
		DAO mc = new DAO();
		session = ActionContext.getContext().getSession();
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	if(id == 0){
    		return ERROR;
    	}
    	else{
    		System.out.println("ppppppppppppp" + idAlbum);
    		//mc.deleteAlbum(idAlbum);
    		
    		//update wszystkich zdjec gdzie Id_Album = 2 na IdAlbum = NULL
    		// UPDATE Picture set Id_Album = NULL where Id_Album = 2
    		
    		mc.ClearPicturesAndClearEventAndDeleteAlbum(idAlbum);
    		
    		// UPDATE Event set Id_Album = NULL where Id_Album = 2
    		
    		// DELETE From Album where Id_Album = 2
    		
    		java.util.Date date= new java.util.Date();
    		return SUCCESS;
    	}
    }
	public void setSession(Map map) {
		this.session = map;
		
	}
}
