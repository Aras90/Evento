package Evento.action;
 
import java.util.List;
import java.util.Random;
import Evento.model.*;
 
import Evento.bean.DAO;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
 
@Conversion()
public class MojeAlbumyAction extends ActionSupport {
 
        /**
         * 
         */
	private static final long serialVersionUID = 1L;
    private List albumList;
    private List picturesList;
    DAO mc = new DAO();
   
    public String getPictureLink(long Id_Album){
    	picturesList = mc.getPicturesList(Id_Album);
    	Random r = new Random(); 
    	int a = r.nextInt(picturesList.size());
    	return ((Picture)picturesList.get(a)).getLink();
    }
    public List getAlbumList() {return albumList; }
    public void setAlbumList(long idUser){
    	
    	albumList = mc.getAlbumsHavingIdUser(idUser);
    	
    }
    
    public String execute() throws Exception {
            setAlbumList(1);
           
            return SUCCESS;
    }
}