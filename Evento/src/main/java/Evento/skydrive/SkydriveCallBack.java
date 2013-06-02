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
    private static final String PROTECTED_RESOURCE_URL = "https://apis.live.net/v5.0/me";
    private static final String redirectURL = "https://login.live.com/oauth20_logout.srf?client_id=00000000480F1854&redirect_uri=http://www.evento.com:8080/Evento/";
    public static Token accessToken;
    @Override
    public String execute() {
    	
    	System.err.println("SkyDriveCallBack");
    	System.err.println("keySet:"+session.keySet());
    	OAuthService SkyDriveService = (OAuthService) session.get("SkyDriveService");
    	System.err.println("SkyDriveService:" + SkyDriveService);
      
    	HttpServletResponse response = ServletActionContext.getResponse();
        System.err.println("response: "+response.toString());
        Map<String, Object> params = (Map<String, Object>)ActionContext.getContext().getParameters();  
        for(Entry<String, Object> entry : params.entrySet()) {
            this.setOauth_verifier(((String[])entry.getValue())[0]);
        }

        System.err.println("VERIFIER: " + this.getOauth_verifier());
        accessToken = SkyDriveService.getAccessToken(EMPTY_TOKEN, new Verifier(this.getOauth_verifier()));
        System.err.println("accessToken: "+accessToken.getToken());
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        request.setCharset("UTF-8");
        SkyDriveService.signRequest(accessToken, request);
        Response response2 = request.send();
        
       // System.out.println("sex  a  a a a ");
        System.err.println(response2.getBody());
        
        String tmp = response2.getBody();        
        int i = tmp.indexOf("account");
        int j = i + 11;
        while(tmp.charAt(j) != '\"' || tmp.charAt(j+1) != ','){
        	j++;
        }
        String email = tmp.substring(i+11, j);
        System.err.println(email);
        
        DAO mc = new DAO();
        List idList = mc.getUserIdHavingEmail(email);
        long id = 0;
        if(idList.size()==1){ 
            
            id = ((Evento.model.User)idList.get(0)).getId_User(); 
            session.clear(); 
            session.put("idUser", id);
            session.put("login", "sd");
            session.put("email", email);
        } else if (idList.size()==0){ 
            
        	id = 0;
            System.err.println("Dodalem do sesji brak ab"); 
            session.clear(); 
            session.put("idUser", id);
            session.put("login", "nic");
            return "brak";
        }
        System.err.println("id:"+id);
        
        i = tmp.indexOf("name");
        j = i + 8;
        while(tmp.charAt(j) != '\"' || tmp.charAt(j+1) != ','){
        	j++;
        }
        String name = tmp.substring(i+8, j);
        session.put("name", name);
        
        session.put("accessToken", accessToken);
        
        
        //PRZYKLAD pobierania dokladniego linku ze skydrive ( wynik trzeba przeparsowac)
      //  OAuthRequest request1 = new OAuthRequest(Verb.GET, "https://apis.live.net/v5.0/photo.938823656776a0a0.938823656776A0A0!111?access_token="+accessToken.getToken() );
      //  Response response3 = request1.send();
        //System.out.println(" a a  a a a a a  a a a");
        //System.err.println(response3.getBody());
//        mc.getUserPictures(id);
        
        
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
