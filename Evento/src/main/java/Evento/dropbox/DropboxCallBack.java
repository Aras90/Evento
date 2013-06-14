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
        
        List idList = DAO.getUserIdHavingEmail(email);
        long id = 0;
        if(idList.size()==1){ 
            
            id = ((Evento.model.User)idList.get(0)).getId_User(); 
            session.clear(); 
            session.put("idUser", id);
            session.put("login", "db");
            session.put("email", email);
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
        if(name.contains("\\u0104")) name = name.replace("\\u0104", "•");
        if(name.contains("\\u0105")) name = name.replace("\\u0105", "π");
        if(name.contains("\\u0106")) name = name.replace("\\u0106", "∆");
        if(name.contains("\\u0107")) name = name.replace("\\u0107", "Ê");
        if(name.contains("\\u0118")) name = name.replace("\\u0118", " ");
        if(name.contains("\\u0119")) name = name.replace("\\u0119", "Í");
        if(name.contains("\\u0141")) name = name.replace("\\u0141", "£");
        if(name.contains("\\u0142")) name = name.replace("\\u0142", "≥");
        if(name.contains("\\u0143")) name = name.replace("\\u0143", "—");
        if(name.contains("\\u0144")) name = name.replace("\\u0144", "Ò");
        if(name.contains("\\u00d3")) name = name.replace("\\u00d3", "”");
        if(name.contains("\\u00f3")) name = name.replace("\\u00f3", "Û");
        if(name.contains("\\u015a")) name = name.replace("\\u015a", "å");
        if(name.contains("\\u015b")) name = name.replace("\\u015b", "ú");
        if(name.contains("\\u0179")) name = name.replace("\\u0179", "è");
        if(name.contains("\\u017a")) name = name.replace("\\u017a", "ü");
        if(name.contains("\\u017b")) name = name.replace("\\u017b", "Ø");
        if(name.contains("\\u017c")) name = name.replace("\\u017c", "ø");
        System.err.println(name);
        
        session.put("name", name);
       
        
//        DAO dao = new DAO();
//        dao.getUserPictures(id);
        /* Przyklad zamiany linku skroconego na zwykly
         * 
         * 
         * 
        	//atrybut i metoda w klasie
        private String link = "http://db.tt/e6JcxkQw";

		public String getShareURL(String strURL) {
			URLConnection conn = null;
			try {
				URL inputURL = new URL(strURL);
				conn = inputURL.openConnection();
	
			} catch (MalformedURLException e) {
				System.out.println("zjebalo sie 1");
			} catch (IOException ioe) {
				System.out.println("zjebalo sie 2");
			}

			return conn.getHeaderField("location");

		}

			//kod wykonywalny
		String shareAddress = getShareURL(link).replaceFirst("https://www", "https://dl");
		shareAddress += "?dl=1";
		System.out.println("dropbox share link " + shareAddress);
	
       	 *	
         *
         */
        
        
        
        
        
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
