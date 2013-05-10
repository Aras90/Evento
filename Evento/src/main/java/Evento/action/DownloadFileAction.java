package Evento.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;


public class DownloadFileAction extends ActionSupport{
		 
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private InputStream fileInputStream;
	 
		public InputStream getFileInputStream() {
			return fileInputStream;
		}
	 
		public String execute() throws Exception {
		   fileInputStream = new FileInputStream(new File("nowy2.zip"));
		    return SUCCESS;
		}
	}


