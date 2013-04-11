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

import Evento.bean.MainClass;

import com.opensymphony.xwork2.ActionSupport;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import com.opensymphony.xwork2.conversion.annotations.Conversion;

/**
 * 
 */
@Conversion()
public class KalendarzAction extends ActionSupport {
   
	private List userEvents;
	
    public List getUserEvents() {
		return userEvents;
	}
    public void setUserEvents() {
    	MainClass mc = new MainClass();
    	userEvents = mc.getUserEvents(1);
	}


    public String execute() throws Exception {
    	
    	setUserEvents();
      
    	
        return SUCCESS;
    }
}
