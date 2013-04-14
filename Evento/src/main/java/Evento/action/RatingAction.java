package Evento.action;

import java.util.Date;
import java.util.List;

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
public class RatingAction extends ActionSupport {
    
    private int idBox;
    private int rate;
    private boolean isInTheDB;
        
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
		isInTheDB = false;
		DAO mc = new DAO();
		System.out.println("test");
		for(Object el : mc.getRatingData()){
			if( ((Rating)el).getId_Picture() == DAO.getSession().get(Picture.class, (long)idBox) ){
				isInTheDB = true;
				mc.updateRating(((Rating)el).getId_Rating(), new Date().toString(), rate, (long)idBox, 1l);
				break;
			}
			
		}
		if(isInTheDB == false){
			mc.createRating(new Date().toString(), rate, (long)idBox, 1l);
		}
        return SUCCESS;
    }
}
