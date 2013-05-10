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
public class ShutDownSessionAction extends ActionSupport implements SessionAware  {
    
  
    private Map<String, Object> session;
    long zero = 0;
    String login = "nic";
    private static final String redirectURL = "https://login.live.com/oauth20_logout.srf?client_id=00000000480F1854&redirect_uri=http://www.evento.com:8080/Evento/";
    private static final String redirectURL2 = "https://www.dropbox.com/logout";
   

	public String execute() throws Exception {
		if(session.get("login").equals("sd")){
			session.clear();
			session.put("IdUser", zero);
			session.put("login", login);
			System.err.println(redirectURL);
	        return "sd";
		}else if(session.get("login").equals("db")){
			session.clear();
			session.put("IdUser", zero);
			session.put("login", login);
			System.err.println(redirectURL2);
	        return "db";
		}else{
			session.clear();
			session.put("IdUser", zero);
			session.put("login", login);
			return SUCCESS;
		}
    }

	public void setSession(Map map) {
		this.session = map;
		
	}

}
