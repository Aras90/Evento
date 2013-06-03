/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Evento.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import Evento.model.Event;
import Evento.model.Picture;
import Evento.model.Album;
import Evento.model.User;
import Evento.bean.DAO;

/**
 * 
 */
@Conversion()
public class MojeZdjeciaEventAction extends ActionSupport  implements SessionAware {
    
    private String id, Name;
	private List<Object[]> picturesList;
	private Map<String, Object> session;
	int i = 0;
	 DAO mc = new DAO();
    public List getPicturesList() {return picturesList; }
    public void setPicturesList(long idUser){
    	DAO mc = new DAO();
    	picturesList = mc.getUserPicturesDataEvent(idUser, Long.parseLong(id)); //id_user, id_album
    		
    }
    
    public boolean check(){
    	
    	long aktualnieZalogowany = (Long)session.get("idUser");
    	//long autorZdjecia
    	long tworcaEventu;
    	long idZdjecia = ((Picture)picturesList.get(i++)[0]).getId_Picture();
    	System.err.println("id:"+Long.parseLong(id));
    	List<User> tmp = mc.getTworcaEventu(Long.parseLong(id));
    	
    	tworcaEventu = tmp.get(0).getId_User();
        System.err.println("IdUser:"+tworcaEventu);
        		
        System.err.println("tE:"+tworcaEventu +" aZ:"+aktualnieZalogowany);
    	
    	
    	User tZdjecia = (User)mc.getTworcaZdjecia(idZdjecia).get(0);
    	System.err.println("TwoZdj::"+tZdjecia.getId_User());
    	
    	if(tworcaEventu == aktualnieZalogowany || aktualnieZalogowany == tZdjecia.getId_User()){
    			
    		return true;
    	} 
    	
    	
    	return false;
    }
    
    public void check1(){
    	
    }
    
    public String execute() throws Exception {
        
    	session = ActionContext.getContext().getSession();
    	long id = (Long)session.get("idUser") != null ? (Long)session.get("idUser") : 0;
    	if(id == 0){
    		return ERROR;
    	}
    	else{
    		 setPicturesList(id);
    		 return SUCCESS;
    	}
    	
    	
       
    }
    
    public void setId(String id){
    	this.id = id;
    	
    }
    
    public String getId(){
    	return this.id;
    }
    public void setSession(Map map) {
		this.session = map;
		
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
    
   
    

    
}
