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
public class RatingAction extends ActionSupport implements SessionAware  {
    
    private int idBox;
    private int rate;
    private Map<String, Object> session;
        
    public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getIdBox() {
		return idBox;
	}
	public void setIdBox(int idBox) {
		this.idBox = idBox;
	}
	
	public String execute() throws Exception {
		DAO mc = new DAO();
		session = ActionContext.getContext().getSession();
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	if(id == 0){
    		return ERROR;
    	}
    	else{
    		java.util.Date date= new java.util.Date();
    		if(mc.getUserRatingData(id, (long)idBox).size() == 0)
    			
    			mc.createRating( new Timestamp(date.getTime()), rate, (long)idBox, id);
    		else
    			mc.updateRating(((Rating)mc.getUserRatingData(id, (long)idBox).get(0)).getId_Rating(), new Timestamp(date.getTime()), rate, (long)idBox, id);
    		return SUCCESS;
    	}
    }
	public void setSession(Map map) {
		this.session = map;
		
	}
}
