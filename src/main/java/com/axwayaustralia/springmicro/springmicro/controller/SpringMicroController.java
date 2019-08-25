package com.axwayaustralia.springmicro.springmicro.controller;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.axwayaustralia.springmicro.data.Stock;

import reactor.core.publisher.Flux;

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
	
	@GetMapping(path = "/api/stock")
	public ResponseEntity<List<Stock>> getDataStream() {
		String stockname1 = "JOE";
		String stockname2 = "PETER";
		String stockname3 = "BAV";		
		
		Stock stock1 = new Stock(stockname1, getRandomPriceAsString());
		Stock stock2 = new Stock(stockname2, getRandomPriceAsString());
		Stock stock3 = new Stock(stockname3, "1.00");  //fix this to force case that doesn't change value
		
		List<Stock> lstStocks = new ArrayList<Stock>();
		lstStocks.add(stock1);
		lstStocks.add(stock2);
		lstStocks.add(stock3);
		
		return ResponseEntity.ok(lstStocks);
	}
	
	@GetMapping(path = "/api/stream-flux")
	public Flux<ServerSentEvent<String>> streamEvents() {
	    return Flux.interval(Duration.ofSeconds(1))
	      .map(sequence -> ServerSentEvent.<String> builder()
	        .id(String.valueOf(sequence))
	          .event("periodic-event")
	          .data("SSE - " + LocalTime.now().toString())
	          .build());
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
	
	private String getRandomPriceAsString() {
		String value = "0.00"; 
		double leftLimit = 1D;
		double rightLimit = 100D;
		double generatedDouble = leftLimit + Math.random() * (rightLimit - leftLimit);
		Double truncatedDouble = BigDecimal.valueOf(generatedDouble)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		value = Double.toString(truncatedDouble);
		return value;
	}
	
}
