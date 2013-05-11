package Evento.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JFileChooser;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionSupport;


public class DownloadFileAction extends ActionSupport implements ServletRequestAware{
		 
		/**
	 * 
	 */
	private JFileChooser jfc;
	private static final long serialVersionUID = 1L;
		private InputStream fileInputStream;
		private HttpServletRequest servletRequest;
	 
		public InputStream getFileInputStream() {
			return fileInputStream;
		}
	 
		public String execute() throws Exception {
			String filePath = servletRequest.getRealPath("/");
			System.out.println("Server path:" + filePath);
		   fileInputStream = new FileInputStream(new File(filePath,"nowy.zip"));
	    	 
		    return SUCCESS;
		    
		}

		public void setServletRequest(HttpServletRequest servletRequest) {
			this.servletRequest = servletRequest;
			
		}
	}


