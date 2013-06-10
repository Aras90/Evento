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
public class CommentAction extends ActionSupport implements SessionAware  {
    
    private int idPicture;
    private List comments;
    private String email;
    private Map<String, Object> session;
    
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List getComments() {
		return comments;
	}
	public void setComments(List comments) {
		this.comments = comments;
	}
	
        
	public int getIdPicture() {
		return idPicture;
	}
	public void setIdPicture(int idPicture) {
		this.idPicture = idPicture;
	}
	
	public String execute() throws Exception {
		
		session = ActionContext.getContext().getSession();
		DAO mc = (DAO)session.get("dao");
		mc.getSession();
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	if(id == 0){
    		mc.close();
    		return ERROR;
    	}
    	else{
    		java.util.Date date= new java.util.Date();
    		email = (String)session.get("email");
    		comments = mc.getComments(idPicture);
    		mc.close();
    		return SUCCESS;
    	}
    }
	public void setSession(Map map) {
		this.session = map;
		
	}
}
