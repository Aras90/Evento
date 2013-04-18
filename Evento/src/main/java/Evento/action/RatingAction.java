package Evento.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import Evento.bean.DAO;
import Evento.model.Picture;
import Evento.model.Rating;
import Evento.model.User;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;

/**
 * 
 */
@Conversion()
public class RatingAction extends ActionSupport implements SessionAware  {
    
    private int idBox;
    private int rate;
        
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
		DAO.getSession().flush();
		System.out.println("idBox = " + DAO.getSession().get(Rating.class, (long)idBox));
		if(DAO.getSession().get(Rating.class, (long)idBox) == null)
			mc.createRating( new Date().toString(), rate, (long)idBox, 1l);
		else
			mc.updateRating((long)idBox, new Date().toString(), rate, (long)idBox, 1l);	
        return SUCCESS;
    }
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}
}
