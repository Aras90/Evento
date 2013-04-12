package Evento.action;
 
import java.util.List;
 
import Evento.bean.MainClass;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
 
@Conversion()
public class RatingAction extends ActionSupport {
 
       
        private String result;
        private String dupa;
                    
         
        public void rating(){
        	System.err.println("DUPA: "+dupa);
        
        	
        }
        
        public void setDupa(String dupa){
        	this.dupa = dupa;
        }
        public String getDupa(){
        	return dupa;
        }
        
        @Override
        public String execute() throws Exception {
            result = "\"{\"age\":100,\"name\":\"dude\"}"; 
            
            
            

            return SUCCESS;
        }
}