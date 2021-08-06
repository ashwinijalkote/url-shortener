package com.ashwini.urlshortener.controller;

import com.ashwini.urlshortener.dto.Request;
import com.ashwini.urlshortener.exceptions.ShortURLNotFoundException;
import com.ashwini.urlshortener.service.URLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("api/")
public class URLController {

    Logger logger = LoggerFactory.getLogger(URLController.class);
    private final URLService urlService;

    @Autowired
    public URLController(URLService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/url")
    public ResponseEntity<String> createFullURLEntry(@RequestBody Request requestBody, HttpServletRequest request) throws MalformedURLException, URISyntaxException {
        String shortUrl = urlService.saveURL(requestBody.getFullURL(), request.getRequestURL().toString());
        logger.info("shortURL for request {}: {}", requestBody.getFullURL(), shortUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    @ExceptionHandler({MalformedURLException.class, URISyntaxException.class, ShortURLNotFoundException.class})
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @GetMapping("/{shortString}")
    public ResponseEntity<String> redirectToFullURL(@PathVariable String shortString, HttpServletResponse response) throws ShortURLNotFoundException {
        String fullURL = urlService.getFullURL(shortString);
        try {
            logger.info("Redirecting to url {}", fullURL);
            response.sendRedirect(fullURL);
        } catch (IOException exception) {
            logger.debug("Error in redirection to {}", fullURL);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in redirection");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(shortString);
    }

}
