package Evento.facebook;

import Evento.bean.DAO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class FacebookCallback extends ActionSupport implements SessionAware {

	    private Map<String, Object> session;
	    private String key;
	    private String secret;
	    //returned from fb
	    private String oauth_token;
	    private String oauth_verifier;
	    private static final Token EMPTY_TOKEN = null;
	    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";

	    @Override
	    public String execute() {
	    	
	    	System.err.println("aaa");
	    	
	        OAuthService fbService = (OAuthService) session.get("fbService");
	        System.err.println("fbService: " + fbService);
	       
	        
	        HttpServletResponse response = ServletActionContext.getResponse();
	        System.err.println("response: "+response.toString());
	        Map<String, Object> params = (Map<String, Object>)ActionContext.getContext().getParameters();  
	        for(Entry<String, Object> entry : params.entrySet()) {  
	           //System.err.println(entry.getKey()+": "+((String[])entry.getValue())[0]);  
	            this.setOauth_verifier(((String[])entry.getValue())[0]);
	            
	        }  
	        System.err.println("VERIFIER: " + this.getOauth_verifier());
	        Token accessToken = fbService.getAccessToken(EMPTY_TOKEN, new Verifier(this.getOauth_verifier()));
	        System.err.println("accessToken: "+accessToken);
	        
	        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
	        fbService.signRequest(accessToken, request);
	        Response response1 = request.send();
	        System.out.println("Got it! Lets see what we found...");
	        System.out.println();
	        System.out.println(response1.getCode());
	        System.out.println(response1.getBody());
	        
	        String tmp = response1.getBody();
	        
	        System.err.println(tmp);
	        
	        
	        System.err.println("index: "+tmp.indexOf("email\":\""));
	        int index = tmp.indexOf("email\":\"") + 8;
	        System.err.println("sub:"+tmp.substring(index));
	      
	        int index1 = index;
	        while(!tmp.substring(index,index+1).equals("\"")){
	        	index++;
	        }
	        
	       // System.out.println("index na koncu:"+index);
	        
	        
	       
	        
	        String email = tmp.substring(index1, index);
	        
	        email = email.replace("\\u0040", "@");
	        System.err.println("email: "+email);
	        
	        DAO mc = new DAO();
	        List idList = mc.getUserIdHavingEmail(email);
	        long id = 0;
	        if(idList.size()==1){ 
                
                id = ((Evento.model.User)idList.get(0)).getId_User(); 
                session.clear(); 
                session.put("idUser", id);
                session.put("login", "fb");
                session.put("email", email);
                
            } else if (idList.size()==0){ 
                
            	id = 0;
                System.err.println("Dodalem do sesji brak"); 
                session.clear(); 
                session.put("idUser", id);
                session.put("login", "nic");
                return "brak";
            }
	        System.out.println("id:"+id);
	        session.put("accessToken", accessToken);
	        this.setKey(accessToken.getToken()); //just to see something happen
	        this.setSecret(accessToken.getSecret());//just to see something happen
	        return SUCCESS;
	    }

	   
	    public void setSession(Map map) {
	        this.session = map;
	    }

	    public String getKey() {
	        return key;
	    }

	    public void setKey(String key) {
	        this.key = key;
	    }

	    public String getSecret() {
	        return secret;
	    }

	    public void setSecret(String secret) {
	        this.secret = secret;
	    }

	    public String getOauth_token() {
	        return oauth_token;
	    }

	    public void setOauth_token(String oauth_token) {
	        this.oauth_token = oauth_token;
	    }

	    public String getOauth_verifier() {
	        return oauth_verifier;
	    }

	    public void setOauth_verifier(String oauth_verifier) {
	        this.oauth_verifier = oauth_verifier;
	    }
}
