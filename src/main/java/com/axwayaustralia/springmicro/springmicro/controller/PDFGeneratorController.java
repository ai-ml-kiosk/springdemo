package com.axwayaustralia.springmicro.springmicro.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.axwayaustralia.springmicro.data.pdf.PDFGeneratorService;

@RestController
public class PDFGeneratorController {

	@Autowired
	private PDFGeneratorService pdfService;
	
	@RequestMapping(path = "/api/getPdf", method = RequestMethod.POST)
	public ResponseEntity<Resource> getPdf(@RequestBody String param) throws Exception {

		String name = "AxwayReport_" + getCurrentDateAsString() + ".pdf";
		String fileName = "/tmp/" + name;
		
		pdfService.generatePDFDocument(fileName, param);

		InputStream fstream = accessFile(fileName);
				
		String contentDisposition = "attachment; filename=" + name;

	    HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        ByteArrayResource resource = new ByteArrayResource(IOUtils.toByteArray(fstream));
        
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(resource.contentLength())
	            .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
	            .body(resource);
	}
	
	@RequestMapping(path = "/api/createPdf", method = RequestMethod.POST)  //generic
	public ResponseEntity<String> createPdf(@RequestBody String param) throws Exception {

		String name = "AxwayReport_" + getCurrentDateAsString() + ".pdf";
		String fileName = "/tmp/" + name;
		//String fileName = "c:\\testDir\\" + name;
		
		pdfService.generatePDFDocument(fileName, param);

	    return ResponseEntity.ok(name);
	}
	
	@RequestMapping(path = "/api/createReportPdf", method = RequestMethod.POST)
	public ResponseEntity<String> createReportPdf(@RequestBody String body, @RequestParam("filename") String param) throws Exception {

		String name = param + ".pdf";
		//String name = "AxwayReport_" + getCurrentDateAsString() + ".pdf";
		String fileName = "/tmp/" + name;
		//		String fileName = "c:\\testDir\\" + name;
		
		pdfService.generateReportPDFDocument(fileName, body);

	    return ResponseEntity.ok(name);
	}
	
	@RequestMapping(path = "/api/getExistingPdf", method = RequestMethod.GET)
	public ResponseEntity<Resource> getExistingPdf(@RequestParam("filename") String param) throws Exception {

		String fileName = "/tmp/" + param;
	//	String fileName = "c:\\testDir\\" + param;
		
		InputStream fstream = accessFile(fileName);
		
		String contentDisposition = "attachment; filename=" + param;

	    HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        ByteArrayResource resource = new ByteArrayResource(IOUtils.toByteArray(fstream));
        
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(resource.contentLength())
	           // .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .contentType(MediaType.parseMediaType(MediaType.APPLICATION_PDF_VALUE))
	            .body(resource);
	}

	public InputStream accessFile(String filename) {

        // this is the path within the jar file
        InputStream input = PDFGeneratorController.class.getResourceAsStream(filename);
        if (input == null) {
            // this is how we load file within editor (e.g. eclipse)
            input = PDFGeneratorController.class.getClassLoader().getResourceAsStream(filename);
        }
        if (input == null) {
        	File file = new File(filename);
    		
        	try {
   		 		input = new FileInputStream(file);
        	} catch(Exception ex) {
        		
        	}
        }

        return input;
    }

	public static String getCurrentDateAsString() {
		DateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}
}
