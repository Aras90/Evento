package Evento.skydrive;

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

public class SkydriveCallBack extends ActionSupport implements SessionAware {
	
	private Map<String, Object> session;
	private String key;
    private String secret;
    //returned from skydrive
    private String oauth_token;
    private String oauth_verifier;
    private static final Token EMPTY_TOKEN = null;
    private static final String PROTECTED_RESOURCE_URL = "https://api.foursquare.com/v2/users/self/friends?oauth_token=";
    
    @Override
    public String execute() {
    	
    	System.err.println("SkyDriveCallBack");
    	
    	OAuthService SkyDriveService = (OAuthService) session.get("SkyDriveService");
    	System.err.println("SkyDriveService:" + SkyDriveService);
    	
    	HttpServletResponse response = ServletActionContext.getResponse();
        System.err.println("response: "+response.toString());
        Map<String, Object> params = (Map<String, Object>)ActionContext.getContext().getParameters();  
        for(Entry<String, Object> entry : params.entrySet()) {
            this.setOauth_verifier(((String[])entry.getValue())[0]);
        } 

        System.err.println("VERIFIER: " + this.getOauth_verifier());
        Token accessToken = SkyDriveService.getAccessToken(EMPTY_TOKEN, new Verifier(this.getOauth_verifier()));
        System.err.println("accessToken: "+accessToken);
        
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL + accessToken.getToken());
        SkyDriveService.signRequest(accessToken, request);
        Response response2 = request.send();
        
        System.err.println(response2.getBody());
        
        session.put("idUser", 2);
        
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
