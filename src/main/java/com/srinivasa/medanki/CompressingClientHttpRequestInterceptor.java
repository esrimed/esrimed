package com.srinivasa.medanki;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class CompressingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {


	public static byte[] compress(byte[] body, HttpHeaders headers) throws IOException {
	    
	    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos)) {
	        gzipOutputStream.write(body);
	        gzipOutputStream.finish();

	        gzipOutputStream.flush();
	        byte [] gzipBytes = baos.toByteArray();
		    //Resetting the content length after gzip.
	        System.out.println("After gzip, my content length is " + gzipBytes.length);
	        System.out.println("After Interceptor of gzip, headers are" + headers.toSingleValueMap());
	        System.out.println("Fix is, explicitly setting the content length - headers.setContentLength(gzipBytes.length)");
			headers.setContentLength(gzipBytes.length);
	        System.out.println("After Fix, modified headers are" + headers.toSingleValueMap());
		    return gzipBytes;
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	    //if any failure send without gzip as is..not so good but being dump to accept failure
    	return body;
	}
	
	public ClientHttpResponse intercept(HttpRequest req, byte[] body, ClientHttpRequestExecution exec)
			  throws IOException {
        System.out.println("Before Interceptor of gzip, my content length is " + body.length);			    
        HttpHeaders httpHeaders = req.getHeaders();
        System.out.println("Before Interceptor of gzip, headers are" + req.getHeaders().toSingleValueMap());			    

			    httpHeaders.remove(HttpHeaders.CONTENT_TYPE);
			    httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/gzip");
			    httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "gzip");
			    httpHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
			    return exec.execute(req, this.compress(body,httpHeaders));
	}
	

}
