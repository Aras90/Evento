package Evento.dropbox;

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

public class DropboxCallBack extends ActionSupport implements SessionAware {
	
	private Map<String, Object> session;
	private String key;
    private String secret;
    //returned from dropbox
    private String oauth_token;
    private String oauth_verifier;
    private static final Token EMPTY_TOKEN = null;
    private static final String PROTECTED_RESOURCE_URL = "https://api.dropbox.com/1/account/info";
    private static final String redirectURL = "https://www.dropbox.com/logout";
    
    @Override
    public String execute() {
    	
    	System.err.println("DropboxCallBack");
    	System.err.println("keySet:"+session.keySet());
    	OAuthService DropboxService = (OAuthService) session.get("DropboxService");
    	System.err.println("DropboxService:" + DropboxService);
      
    	HttpServletResponse response = ServletActionContext.getResponse();
        System.err.println("response: "+response.toString());
        Map<String, Object> params = (Map<String, Object>)ActionContext.getContext().getParameters();  
        for(Entry<String, Object> entry : params.entrySet()) {
            this.setOauth_verifier(((String[])entry.getValue())[0]);
        }
        
        Token requestToken = (Token)session.get("Token");

        System.err.println("VERIFIER: " + this.getOauth_verifier());
        Token accessToken = DropboxService.getAccessToken(requestToken, new Verifier(this.getOauth_verifier()));
        System.err.println("accessToken: "+accessToken);
        OAuthRequest request = new OAuthRequest(Verb.POST, PROTECTED_RESOURCE_URL);
        
        DropboxService.signRequest(accessToken, request);
        Response response2 = request.send();
        
        System.err.println(response2.getBody());
        
        String tmp = response2.getBody();        
        int i = tmp.indexOf("email");
        int j = i + 9;
        while(tmp.charAt(j) != '\"' || tmp.charAt(j+1) != '}'){
        	j++;
        }
        String email = tmp.substring(i+9, j);
        System.err.println(email);
        
        DAO mc = new DAO();
        List idList = mc.getUserIdHavingEmail(email);
        long id = 0;
        if(idList.size()==1){ 
            
            id = ((Evento.model.User)idList.get(0)).getId_User(); 
            session.clear(); 
            session.put("idUser", id);
            session.put("login", "db");
            
        } else if (idList.size()==0){ 
            
        	id = 0;
            System.err.println("Dodalem do sesji brak"); 
            session.clear(); 
            session.put("idUser", id);
            session.put("login", "nic");
            return "brak";
        }
        System.err.println("id:"+id);
        
        i = tmp.indexOf("display_name");
        j = i + 16;
        while(tmp.charAt(j) != '\"' || tmp.charAt(j+1) != ','){
        	j++;
        }
        String name = tmp.substring(i+16, j);
        System.err.println(name);
        
        session.put("name", name);
        //session.put("accessToken", accessToken);
        
        
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
