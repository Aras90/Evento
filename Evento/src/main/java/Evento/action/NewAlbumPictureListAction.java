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
	private String makePhotoBy="ONE_AUTHOR";
	
	 private List<Picture> pictureToAlbumList;
	 private Map<String, Object> session;
	 private String choosenEvent;
	 DAO dao;
	 private String publicationOption="TOP";
			
			
	
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
		System.out.println("execute-1");
		dao = new DAO();
		session = ActionContext.getContext().getSession();
		List idUserList = new ArrayList();
		
    	String email = (String)session.get("email");
    	long id = (Long) session.get("idUser");
    	System.out.println("execute-2");
    
    	
    	Long eventId = null;
    	
    	try{
    		System.out.println("execute-3");
    		System.out.println("Choosen event: " + choosenEvent);
    		 eventId = Long.parseLong(choosenEvent);
    	}catch(Exception e){
    		e.printStackTrace();
    		return "error";
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
    	
    	
    	
    	if(makePhotoBy.equals(ONE_AUTHOR) ){
    		idUserList.add(id);
    	}else if(makePhotoBy.equals(ALL)){
    		idUserList = dao.getIdUsersWhoWasOnParty(eventId);
    	}else{
    		idUserList.add(id);
    	}
    	
    	
    	if(publicationOption.equals(TOP)  ){
    		System.out.println("publication option = top");
    		pictureToAlbumList = dao.getPictureToNewAlbumByTopRating(idUserList, eventId, nr);
    	}else if(publicationOption.equals(DOWN)){
    		System.out.println("publication option = down");
    		pictureToAlbumList = dao.getPictureToNewAlbumByWorstRating(idUserList, eventId, nr);
    	}else if(publicationOption.equals(MOST_COMMENT)){
    		System.out.println("publication option = mostComment");
    		pictureToAlbumList = dao.getPictureToNewAlbumByMostComment(idUserList, eventId, nr, minMark, maxMark);
    	}else if(publicationOption.equals(MOST_RATED)){	
    		System.out.println("publication option = most rated");
    		pictureToAlbumList = dao.getPictureToNewAlbumByMostRated(idUserList, eventId, nr, minMark, maxMark);
    	}else{
    		System.out.println("publication option = else");
    		pictureToAlbumList = dao.getPictureToNewAlbumByTopRating(idUserList, eventId, nr);
    	}
    	if( pictureToAlbumList.isEmpty() || pictureToAlbumList==null){
    		return "noPictureError";
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