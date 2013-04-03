package Evento.action;
 
import java.util.List;
 
import Evento.bean.MainClass;
import Evento.model.Album;
import Evento.model.User;
 
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
 
@Conversion()
public class PoczekalniaAction extends ActionSupport {
 
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private List albumList;
        
        public List getAlbumList() {return albumList; }
        public void setAlbumList(long idUser){
        	MainClass mc = new MainClass();
        	albumList = mc.getPicturesFromAlbum(1);
        	//albumList = mc.getUserIdHavingEmail("Aa");
        	System.err.println("AAAAAA"+albumList);
        }
        
        public String execute() throws Exception {
                setAlbumList(1);
                return SUCCESS;
        }
}