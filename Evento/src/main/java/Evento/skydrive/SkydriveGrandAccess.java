package Evento.skydrive;

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

public class SkydriveGrandAccess extends ActionSupport implements SessionAware {
	
	private Map<String, Object> session;
    private String authorizationURL = null;
    private static final Token EMPTY_TOKEN = null;
    
    @Override
    public String execute() {
    	
    	System.err.println("SkyDrive login");
    	String consumer_key = "00000000480F1854";
        String consumer_secret = "ahdDvnX0Zu7AyBEqUvdHVBMx7GjgKimL";
        
        OAuthService SkyDriveService = new ServiceBuilder()
        	.provider(LiveApi.class)
        	.apiKey(consumer_key)
        	.apiSecret(consumer_secret)
        	.scope("wl.offline_access,wl.emails,wl.photos,wl.contacts_photos,wl.contacts_skydrive")
        	.callback("http://www.evento.com:8080/Evento/SkydriveCallBack.action")
        	.build();
        
        authorizationURL = SkyDriveService.getAuthorizationUrl(EMPTY_TOKEN);
        
        System.err.println("Service: "+ SkyDriveService +", AutorizationURL: "+ authorizationURL);
    	
        session.put("SkyDriveService", SkyDriveService);

        
    	return SUCCESS;
    }
    
    public String getAuthorizationURL() {
        return this.authorizationURL;
    }

	public void setSession(Map map) {
		this.session = map;
	}

}
