package com.axwayaustralia.springmicro.springmicro.controller;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringMicroController {
	
	@Autowired
	ResourceLoader resourceLoader;

	@GetMapping("/api/hello")
	public String getMessage() {
		return "Hello world!";
	}
	
	@RequestMapping(path = "/api/getFile", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(String param) throws Exception {

		InputStream fstream = SpringMicroController.accessFile();
		
	    HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=testFile.txt");


        ByteArrayResource resource = new ByteArrayResource(IOUtils.toByteArray(fstream));
        
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(resource.contentLength())
	           // .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .contentType(MediaType.parseMediaType("text/plain"))
	            .body(resource);
	}
	
	public static InputStream accessFile() {
        String resource = "testFile.txt";

        // this is the path within the jar file
        InputStream input = SpringMicroController.class.getResourceAsStream("/resources/" + resource);
        if (input == null) {
            // this is how we load file within editor (e.g. eclipse)
            input = SpringMicroController.class.getClassLoader().getResourceAsStream(resource);
        }

        return input;
    }
	
}
