package com.ashwini.urlshortener.service;

import com.ashwini.urlshortener.exceptions.ShortURLNotFoundException;
import com.ashwini.urlshortener.model.URL;
import com.ashwini.urlshortener.repository.URLRepository;
import com.ashwini.urlshortener.util.BaseConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;

@Service
public class URLService {

    Logger logger = LoggerFactory.getLogger(URLService.class);

    private final URLRepository urlRepository;

    @Autowired
    public URLService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String getFullURL(String shortString) throws ShortURLNotFoundException {
        Long id = BaseConversion.shortStringToId(shortString);
        logger.info(shortString + " converted to id: " + id);
        Optional<URL> url = urlRepository.findById(id);
        url.orElseThrow(() -> new ShortURLNotFoundException("Short URL not found"));
        return url.get().getFullUrl();
    }

    public String saveURL(String fullURL, String requesUrl) throws MalformedURLException, URISyntaxException {
        new java.net.URL(fullURL).toURI();
        URL url = new URL();
        url.setFullUrl(fullURL);
        Long id  = urlRepository.save(url).getId();
        logger.info(fullURL + " converted to id: " + id);
        return getBaseURL(requesUrl)  + "api/" + BaseConversion.idToShortString(id);
    }

    private String getBaseURL(String url) throws MalformedURLException {
        java.net.URL reqURL = new java.net.URL(url);
        return reqURL.getProtocol() + ":" +"//" + reqURL.getHost() +
                (reqURL.getPort() != -1 ?  (":"+ reqURL.getPort()) : "") + "/";
    }
}
