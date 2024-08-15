package com.srinivasa.medanki;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//@RequestMapping({ "/contentIssue" })
public class MainController  {

	public static final String SEND_REQUEST = "/sendRequest";
	public static final String RECEIVE_REQUEST = "/receiveRequest";


	@GetMapping(SEND_REQUEST)
	public ResponseEntity<String> sendRequest()
			throws Exception {
		String request= "After gzip content length is not set properly in spring web 6.1.5+??"
				+ "	I have raised an issue with spring team in git. They have responded."
				+ " I am happy that i found some bugs in my spring's life";
		System.out.println(request);
		HttpHeaders httpHeaders = new HttpHeaders();
	    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/gzip");
	    httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "gzip");
	    httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
		HttpEntity<Object> requestEntity = new HttpEntity<>(request, httpHeaders);
		String sendUrl = "http://127.0.0.1:8080/receiveRequest";
		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate restTemplate = new RestTemplate();
	   restTemplate.getInterceptors().add( new CompressingClientHttpRequestInterceptor());
		return restTemplate.exchange(sendUrl, HttpMethod.POST, requestEntity,
				String.class);


	}

	@PostMapping(RECEIVE_REQUEST)
	public String receiveRequest(@RequestBody String message,
			@RequestHeader HttpHeaders headers) throws Exception {
		System.out.println(headers.getContentLength());
		return "I got you" ;

	}

	}
