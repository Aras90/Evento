package Evento.dropbox;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.*;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class DropboxGrandAccess extends ActionSupport implements SessionAware {
	
	private Map<String, Object> session;
    private String authorizationURL = null;
    private Token requestToken = null;
    
    @Override
    public String execute() {
    	
    	System.err.println("DropBox login");
    	String consumer_key = "eoswf5cgr3vlr1t";
        String consumer_secret = "ng5wo7bgj6iakjz";
        
        OAuthService DropboxService = new ServiceBuilder()
        	.provider(DropBoxApi.class)
        	.apiKey(consumer_key)
        	.apiSecret(consumer_secret)
        	//.scope("wl.offline_access,wl.emails")
        	//.callback("http://www.evento.com:8080/Evento/DropboxCallBack.action")
        	.build();
        System.err.println("aaa");
        
        requestToken = DropboxService.getRequestToken();
        
        authorizationURL = DropboxService.getAuthorizationUrl(requestToken) + "&oauth_callback=http://www.evento.com:8080/Evento/DropboxCallBack.action";
        
        System.err.println("Service: "+ DropboxService +", AutorizationURL: "+ authorizationURL);
    	
        session.put("DropboxService", DropboxService);
        session.put("Token", requestToken);

        
    	return SUCCESS;
    }
    
    public String getAuthorizationURL() {
        return this.authorizationURL;
    }
    
	public void setSession(Map map) {
		this.session = map;
	}

}
