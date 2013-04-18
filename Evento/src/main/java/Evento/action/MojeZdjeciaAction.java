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

import Evento.model.Picture;
import Evento.model.Album;
import Evento.bean.DAO;

/**
 * 
 */
@Conversion()
public class MojeZdjeciaAction extends ActionSupport  implements SessionAware {
    
    private String id;
	private List picturesList;
	private Map<String, Object> session;
    
    public List getPicturesList() {return picturesList; }
    public void setPicturesList(long idUser){
    	DAO mc = new DAO();
    	picturesList = mc.getUserPicturesData(idUser, Long.parseLong(id)); //id_user, id_album
    		
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
    
   
    

    
}
