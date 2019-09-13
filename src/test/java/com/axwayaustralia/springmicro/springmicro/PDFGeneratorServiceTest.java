package com.axwayaustralia.springmicro.springmicro;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import com.axwayaustralia.springmicro.data.pdf.PDFGeneratorService;
import com.axwayaustralia.springmicro.springmicro.controller.PDFGeneratorController;

class PDFGeneratorServiceTest {

	PDFGeneratorService pdfService = new PDFGeneratorService();
	
	void test() throws Exception {
	//	pdfService.generatePDFDocument("/resources/pdfFile.pdf","HELLO WORLD!");
		
		//InputStream inpit = accessFile("/resources/pdfFile.pdf");
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
	
	public void testJSONParser() {
		
	}
	

}
