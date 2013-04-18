package Evento.facebook;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.*;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class FacebookGrandAccess extends ActionSupport implements SessionAware {

    private Map<String, Object> session;
    private String authorizationURL = null;
    private static final Token EMPTY_TOKEN = null;

    @Override
    public String execute() {
    	
    	System.err.println("WESZLO?");
        //Twitter twitter = new TwitterFactory().getInstance();
        String consumer_key = "170054506477361";
        String consumer_secret = "1c0fafa059c6cb11bbcc640e2698e3f2";

        OAuthService fbService = new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(consumer_key)
                .apiSecret(consumer_secret)
                .scope("email,offline_access")
                .callback("http://localhost:8080/Evento/facebookCallback.action")
                .build();
       
        authorizationURL = fbService.getAuthorizationUrl(EMPTY_TOKEN);
        
        System.err.println("Service: "+fbService+", AutorizationURL: "+authorizationURL);
        
        session.put("fbService", fbService);
        
        
        
        
        return SUCCESS;
    }

    public String getAuthorizationURL() {
        return this.authorizationURL;
    }

   
    public void setSession(Map map) {
        this.session = map;
    }
}
