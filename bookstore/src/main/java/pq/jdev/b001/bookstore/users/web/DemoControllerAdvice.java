package pq.jdev.b001.bookstore.users.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DemoControllerAdvice {
	 @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	    public ResponseEntity<String> handleIOException(HttpRequestMethodNotSupportedException ex) {
	        // prepare responseEntity
	        return ResponseEntity.notFound().build();
	    }
}
