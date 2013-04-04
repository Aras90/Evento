package Evento.action;
 
import java.util.List;
 
import Evento.bean.MainClass;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
 
@Conversion()
public class MojeAlbumyAction extends ActionSupport {
 
        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private List albumList;
        
        public List getAlbumList() {return albumList; }
        public void setAlbumList(long idUser){
        	MainClass mc = new MainClass();
        	albumList = mc.getPicturesFromAlbum(1);
        	
        }
        
        public String execute() throws Exception {
                setAlbumList(1);
                return SUCCESS;
        }
}