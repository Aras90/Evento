package Evento.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import org.apache.struts2.interceptor.SessionAware;

import Evento.bean.DAO;
import Evento.model.Picture;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class NewAlbumPictureListAction extends ActionSupport implements SessionAware {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String TOP="TOP";
	private static final String DOWN="DOWN";
	private static final String ONE_AUTHOR="ONE_AUTHOR";
	private static final String ALL="ALL";
	private static final String MOST_COMMENT="MOST_COMMENT";
	private static final String MOST_RATED = "MOST_RATED";
	
	private static  final  String RANGE="RANGE";
	private int minMark = 1;
	private int maxMark = 5;
	private String downMark;
	private String topMark;
	private String nrOfPicture;
	private String makePhotoBy;
	
	 private List<Picture> pictureToAlbumList;
	 private Map<String, Object> session;
	 private String choosenEvent;
	 DAO dao;
	 private String publicationOption;
			
			
	
	public String getMakePhotoBy() {
		return makePhotoBy;
	}

	public void setMakePhotoBy(String makePhotoBy) {
		this.makePhotoBy = makePhotoBy;
	}

	public String getNrOfPicture() {
		return nrOfPicture;
	}

	public void setNrOfPicture(String nrOfPicture) {
		this.nrOfPicture = nrOfPicture;
	}

	public String getDownMark() {
		return downMark;
	}

	public void setDownMark(String downMark) {
		this.downMark = downMark;
	}

	public String getTopMark() {
		return topMark;
	}

	public void setTopMark(String topMark) {
		this.topMark = topMark;
	}
	 
	public String getPublicationOption() {
		return publicationOption;
	}

	public void setPublicationOption(String publicationOption) {
		this.publicationOption = publicationOption;
	}

	public String getChoosenEvent() {
		return choosenEvent;
	}

	public void setChoosenEvent(String choosenEvent) {
		this.choosenEvent = choosenEvent;
	}

	
	public List<Picture> getPictureToAlbumList() {
		return pictureToAlbumList;
	}

	public void setPictureToAlbumList(List<Picture> pictureToAlbumList) {
		this.pictureToAlbumList = pictureToAlbumList;
	}

	public String execute(){
		
		dao = new DAO();
		session = ActionContext.getContext().getSession();
		List idUserList = new ArrayList();
		
    	String email = (String)session.get("email");
    	long id = (Long) session.get("idUser");
    	
    	
    	System.out.println("Id uzytkownika: " + id + " zalogowany na " + session.get("login"));
    	
    	Long eventId = null;
    	
    	try{
    		 eventId = Long.parseLong(choosenEvent);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	Integer nr = null;
    	try{
    		nr = Integer.parseInt(nrOfPicture);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	try{
    		maxMark = Integer.parseInt(topMark);
    		minMark = Integer.parseInt(downMark);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	
    	
    	if(makePhotoBy.equals(ONE_AUTHOR)){
    		idUserList.add(id);
    	}else if(makePhotoBy.equals(ALL)){
    		idUserList = dao.getIdUsersWhoWasOnParty(eventId);
    	}
    	
    	
    	if(publicationOption.equals(TOP)  ){
    		pictureToAlbumList = dao.getPictureToNewAlbumByTopRating(idUserList, eventId, nr);
    	}else if(publicationOption.equals(DOWN)){
    		pictureToAlbumList = dao.getPictureToNewAlbumByWorstRating(idUserList, eventId, nr);
    	}else if(publicationOption.equals(MOST_COMMENT)){
    		pictureToAlbumList = dao.getPictureToNewAlbumByMostComment(idUserList, eventId, nr, minMark, maxMark);
    	}else if(publicationOption.equals(MOST_RATED)){	
    		pictureToAlbumList = dao.getPictureToNewAlbumByMostRated(idUserList, eventId, nr, minMark, maxMark);
    	}
    	return SUCCESS;
	}
	
	

	
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map map) {
		this.session = map;
	}

}