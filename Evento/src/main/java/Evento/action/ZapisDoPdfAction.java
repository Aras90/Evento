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

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.conversion.annotations.Conversion;
import com.itextpdf.text.pdf.GrayColor;

/**
 * 
 */
@Conversion()
public class ZapisDoPdfAction extends ActionSupport implements
		ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private String[] pdff;
	private String nameAlbum;
	private float szerokosc = 1024;
	private float wysokosc = 768;
	private String zdjecieTla = "http://www.wallsoc.com/images/1024x768/2012/08/03/black-red-202217.jpg";
	private HttpServletRequest servletRequest;

	public void createPDF(String[] imgURL, String place, String album)
			throws DocumentException {
		Document document = new Document();
		Rectangle pageSize = new Rectangle(szerokosc, wysokosc);
		document.setPageSize(pageSize);

		try {

			PdfWriter.getInstance(document, new FileOutputStream(new File(
					place, "nowy.pdf")));
			document.open();

			Image tlo = Image.getInstance(new URL(zdjecieTla));
			tlo.setAbsolutePosition(0f, 0f);
			document.add(tlo);
			Paragraph preface = new Paragraph(album, new Font(
					FontFamily.HELVETICA, 72, Font.BOLDITALIC, new BaseColor(
							255, 255, 255)));
			preface.setAlignment(Element.ALIGN_CENTER);
			document.add(preface);
			document.newPage();
			for (int i = 0; i < imgURL.length; i++) {

				Image tlo2 = Image.getInstance(new URL(zdjecieTla));
				tlo2.setAbsolutePosition(0f, 0f);
				document.add(tlo2);
				Image image2 = Image.getInstance(new URL(imgURL[i]));
				if (szerokosc * 1.5f <= image2.getWidth()|| wysokosc * 1.5f <= image2.getHeight()) {
					image2.scaleAbsolute(image2.getWidth() * 0.25f,image2.getHeight() * 0.25f);
					image2.setAbsolutePosition(szerokosc / 2f - (image2.getWidth() * 0.25f) / 2,wysokosc / 2 - (image2.getHeight() * 0.25f) / 2);
				}else if((szerokosc * 0.8f <= image2.getWidth()|| wysokosc * 0.8f <= image2.getHeight())&&(szerokosc * 1.2f >= image2.getWidth()|| wysokosc * 1.2f >= image2.getHeight())){
					image2.scaleAbsolute(image2.getWidth() * 0.8f,image2.getHeight() * 0.8f);
					image2.setAbsolutePosition(szerokosc / 2f - (image2.getWidth() * 0.8f) / 2,wysokosc / 2 - (image2.getHeight() * 0.8f) / 2);
				}else if((szerokosc * 0.4f >= image2.getWidth()|| wysokosc * 0.4f >= image2.getHeight())&&(szerokosc * 0.7f <= image2.getWidth()|| wysokosc * 0.7f <= image2.getHeight())){
					image2.scaleAbsolute(image2.getWidth()*1.4f,image2.getHeight()*1.4f);
					image2.setAbsolutePosition(szerokosc / 2f - (image2.getWidth()*1.4f) / 2, wysokosc/ 2 - (image2.getHeight()*1.4f) / 2);
				}
				else{
					image2.scaleAbsolute(image2.getWidth(),image2.getHeight());
					image2.setAbsolutePosition(szerokosc / 2f - (image2.getWidth()) / 2, wysokosc/ 2 - (image2.getHeight()) / 2);
				}
				document.add(image2);
				document.newPage();
			}

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String execute() throws Exception {

		ZapisDoPdfAction pdf = new ZapisDoPdfAction();
		String filePath = servletRequest.getRealPath("/");
		System.out.println("Server path:" + filePath);
		pdf.createPDF(pdff, filePath, nameAlbum);

		return SUCCESS;
	}

	public String[] getPdff() {
		return pdff;
	}

	public void setPdff(String[] pdff) {
		this.pdff = pdff;
	}

	public String getNameAlbum() {
		return nameAlbum;
	}

	public void setNameAlbum(String nameAlbum) {
		this.nameAlbum = nameAlbum;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;

	}

}
