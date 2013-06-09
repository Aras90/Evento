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
public class DeletePhotoAction extends ActionSupport implements SessionAware  {
    
    private int idPicture;
    private Map<String, Object> session;
    
       
	public int getIdPicture() {
		return idPicture;
	}
	public void setIdPicture(int idPicture) {
		this.idPicture = idPicture;
	}
	
	public String execute() throws Exception {
		
		session = ActionContext.getContext().getSession();
		DAO mc = (DAO)session.get("dao");
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	if(id == 0){
    		return ERROR;
    	}
    	else{
    		System.out.println("ppppppppppppp" + idPicture);
    		mc.deletePicture(idPicture);
    		java.util.Date date= new java.util.Date();
    		return SUCCESS;
    	}
    }
	public void setSession(Map map) {
		this.session = map;
		
	}
}
