package Evento.action;

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
    		if(mc.getUserRatingData(1l, (long)idBox).size() == 0)
    			mc.createRating( new Date().toString(), rate, (long)idBox, 1l);
    		else
    			mc.updateRating(((Rating)mc.getUserRatingData(1l, (long)idBox).get(0)).getId_Rating(), new Date().toString(), rate, (long)idBox, 1l);
    		return SUCCESS;
    	}
    }
	public void setSession(Map map) {
		this.session = map;
		
	}
}
