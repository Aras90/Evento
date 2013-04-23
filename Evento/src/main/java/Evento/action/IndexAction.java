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

import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * 
 */
@Conversion()
public class IndexAction extends ActionSupport implements SessionAware  {
    
    private Date now = new Date(System.currentTimeMillis());
    private Map<String, Object> session;

    @TypeConversion(converter = "Evento.DateConverter")
    public Date getDateNow() { return now; }

	public String execute() throws Exception {
        now = new Date(System.currentTimeMillis());
        return SUCCESS;
    }

	public void setSession(Map map) {
		this.session = map;
		
	}
	
	public Map<String, Object> getSession() {
		return session;
	}

	public long getIdUser(){
		return (Long)session.get("idUser");
	}
	
	public String getLogin(){
		return (String)session.get("login");
	}
	
	public String getName(){
		return (String)session.get("name");
	}
	
	public void shutDownSession(){
		
		session.put("IdUser",0);
		
	}
}
