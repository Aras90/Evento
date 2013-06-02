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
public class AddCommentAction extends ActionSupport implements SessionAware  {
    
    private int Id_Picture;
    private String Description;
    private Map<String, Object> session;
    private String email ="qq";
        
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String Description) {
		this.Description = Description;
	}
	public int getId_Picture() {
		return Id_Picture;
	}
	public void setId_Picture(int Id_Picture) {
		this.Id_Picture = Id_Picture;
	}
	
	public String execute() throws Exception {
		DAO mc = new DAO();
		session = ActionContext.getContext().getSession();
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	if(id == 0){
    		return ERROR;
    	}
    	else{
    		//email = "aa";//(String)session.get("email");
    		java.util.Date date= new java.util.Date();		
    		mc.createComment(Description, new Timestamp(date.getTime()), (long)Id_Picture, id);
    		return SUCCESS;
    	}
    }
	public void setSession(Map map) {
		this.session = map;
		
	}
}
