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

import java.io.FileOutputStream;
import java.net.URL;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;

/**
 * 
 */
@Conversion()
public class ZapisDoPdfAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String[] pdff;
	private String katalog1;

	public void createPDF(String[] imgURL, String place) {
		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream(place));
			document.open();
			for (int i = 0; i < imgURL.length; i++) {
				Image image2 = Image.getInstance(new URL(imgURL[i]));
				document.add(image2);
			}

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String execute() throws Exception {

		ZapisDoPdfAction pdf = new ZapisDoPdfAction();
		pdf.createPDF(pdff, "Album.pdf");
		return SUCCESS;
	}

	public String[] getPdff() {
		return pdff;
	}

	public void setPdff(String[] pdff) {
		this.pdff = pdff;
	}

	public String getKatalog1() {
		return katalog1;
	}

	public void setKatalog1(String katalog1) {
		this.katalog1 = katalog1;
	}

}
