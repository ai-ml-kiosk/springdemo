package com.axwayaustralia.springmicro.data.pdf;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

@Service
public class PDFGeneratorService {

	
	//generic method
	public Document generatePDFDocument(String fileName, String text) throws FileNotFoundException, Exception {
		
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));
		Document document = new Document(pdfDoc);
		
		PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
		document.setFont(font);
		Paragraph para = new Paragraph(text);

		document.add(para);
		document.close();
		
		return document;
	}
	
	//creates a formatted report
	public Document generateReportPDFDocument(String fileName, String text) throws FileNotFoundException, Exception {
		//null check text before this
		
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));
		Document document = new Document(pdfDoc);
		
		addHeader(document,"AXWAY REPORT",40);
		
		JSONObject completeJson = new JSONObject(text);
		
		Iterator<String> keys = completeJson.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			addHeader(document,key.toUpperCase(), 24);
			handleData(document, completeJson.get(key).toString());
		}
		
		document.close();
		
		return document;
	}

	//adds a header
	public void addHeader(Document document, String header, float size) throws Exception {
		PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
		
		Paragraph para = new Paragraph(header).setFont(font).setFontSize(size);

		document.add(para);
	}

	public void handleData(Document document, String text) throws Exception {
		Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
		
		//high level array
		JSONArray jsonArray = new JSONArray(text);
		
		//get the table headers
		generateTableHeaderRow(jsonArray.getJSONObject(0), table);
		
		generateTableDataRows(jsonArray, table);
		
		document.add(table);
	}
	
	//creates the rows
	private void generateTableDataRows(JSONArray jsonArray, Table table) {
		
		for (int i = 0; i < jsonArray.length(); i++) {
			table.startNewRow();
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			
			Iterator<String> keys = jsonObject.keys();
			while(keys.hasNext()) {
				String key = keys.next();
				table.addCell(jsonObject.get(key).toString());
			}
		}
	}

	//generates the header row using the field names from a sample json object
	public void generateTableHeaderRow(JSONObject jsonObject, Table table) {
		
		JSONArray jsonArray = jsonObject.names();
		List<Object> lstValues = jsonArray.toList();
		for (Object thisValue : lstValues) {
			table.addHeaderCell(thisValue.toString());
		}
		
	}
}
