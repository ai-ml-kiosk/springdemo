package com.axwayaustralia.springmicro.data.pdf;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

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

	private final String ORDER_KEY = "PdfGenSort";
	private final String ORDER_DOC_KEY = "PdfReportOrder";
	private final String ORDER_DELIMITER = ",";
	private Map<String,String[]> orderMap = null;
	
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
		getOrder(completeJson);
		
		if (orderMap.containsKey(ORDER_DOC_KEY)) {
			for (String thisSection : orderMap.get(ORDER_DOC_KEY)) {
				addHeader(document, thisSection, 24);
				handleData(document, completeJson.get(thisSection).toString(), thisSection);
			}
		} else {
			Iterator<String> keys = completeJson.keys();
			while(keys.hasNext()) {
				String key = keys.next();
				if (!key.equals(ORDER_KEY)) {
					addHeader(document,key.toUpperCase(), 24);
					handleData(document, completeJson.get(key).toString(), key);
				}
			}
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

	public void handleData(Document document, String text, String key) throws Exception {
		Table table = new Table(UnitValue.createPercentArray(8)).useAllAvailableWidth();
		
		//high level array
		JSONArray jsonArray = new JSONArray(text);
		
		//get the table headers
		generateTableHeaderRow(jsonArray.getJSONObject(0), table, key);
		
		generateTableDataRows(jsonArray, table, key);
		
		document.add(table);
	}

	//creates the rows
	private void generateTableDataRows(JSONArray jsonArray, Table table, String key) {
		
		boolean hasOrder = orderMap.containsKey(key);
	 
		for (int i = 0; i < jsonArray.length(); i++) {
			table.startNewRow();
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			
			if (hasOrder == true) {
				for (String thisValue : orderMap.get(key)) {
					table.addCell(jsonObject.get(thisValue).toString());
				}
			} else {
			Iterator<String> keys = jsonObject.keys();
			while(keys.hasNext()) {
				String objkey = keys.next();
				table.addCell(jsonObject.get(objkey).toString());
			}
		}
		}
	}

	//generates the header row using the field names from a sample json object
	public void generateTableHeaderRow(JSONObject jsonObject, Table table, String key) {
		
		//check if an order has been specified and use it
		if (orderMap.containsKey(key)) {
			for (String thisValue : orderMap.get(key)) {
				table.addHeaderCell(thisValue.toString());
			}
		} else { //no order - add as provided
			JSONArray jsonArray = jsonObject.names();
			List<Object> lstValues = jsonArray.toList();
			for (Object thisValue : lstValues) {
				table.addHeaderCell(thisValue.toString());
			}
		}
	}

	//get the order of everything
	private void getOrder(JSONObject completeJson) {
		if (completeJson.keySet().contains(ORDER_KEY)) {
			orderMap = new HashMap<String, String[]>();
			JSONArray orderArr = completeJson.getJSONArray(ORDER_KEY);
			JSONObject orderObj = orderArr.getJSONObject(0);
		
			Iterator<String> keys = orderObj.keys();
			while(keys.hasNext()) {
				String key = keys.next();
				orderMap.put(key, orderObj.getString(key).split(ORDER_DELIMITER));
			}
		}
	}
}
