/*
 * Copyright 2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Evento.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;


/**
 * 
 */
@Conversion()
public class ZapisDoZipaAction extends ActionSupport {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String destinationFile;
	private InputStream is;
	private ZipOutputStream outStream;
	private String katalog;
	private String [] invoke;
	
    

	
	
	
	
    public void createZipFile(String[] imageUrl,String zipFileName) throws FileNotFoundException {
    	outStream = new ZipOutputStream(new FileOutputStream(zipFileName));
        try {
        	
        	for(int j=0;j<imageUrl.length;j++){
        	URL url = new URL(imageUrl[j]);
            is = url.openStream();
        	
           
            
           
            	outStream.putNextEntry(new ZipEntry(j+".jpg"));
            	 byte[] buffer = new byte[1024];
                 int bytesRead;
                 
                
                 while ((bytesRead = is.read(buffer)) > 0) {
                     outStream.write(buffer, 0, bytesRead);
                 }
              
                 outStream.closeEntry();
                 
        	} 
        	
            
           
        	outStream.close();
            is.close();
          
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    

   
		
        
    

	public String execute() throws Exception {
		
		ZapisDoZipaAction zapis=new ZapisDoZipaAction();
		 zapis.createZipFile(invoke,"nowy2.zip");
		
		 return SUCCESS;
    }


	
	







	public String[] getInvoke() {
		return invoke;
	}


	public void setInvoke(String[] invoke) {
		this.invoke = invoke;
	}


	public String getKatalog() {
		return katalog;
	}


	public void setKatalog(String katalog) {
		this.katalog = katalog;
	}



	

	
	

	
	
}
