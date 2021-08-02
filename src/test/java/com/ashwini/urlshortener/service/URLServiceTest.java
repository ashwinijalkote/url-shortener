package com.ashwini.urlshortener.service;

import com.ashwini.urlshortener.exceptions.ShortURLNotFoundException;
import com.ashwini.urlshortener.model.URL;
import com.ashwini.urlshortener.repository.URLRepository;
import com.ashwini.urlshortener.util.BaseConversion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class URLServiceTest {

    @Mock
    private URLRepository urlRepository;

    @InjectMocks
    URLService urlService;

    @Test
    public void testGetFullURL() throws ShortURLNotFoundException {
        URL url = new URL();
        url.setFullUrl("http://testurl");
        when(urlRepository.findById(any())).thenReturn(java.util.Optional.of(url));
        assertEquals("http://testurl", urlService.getFullURL("abcdef"));
    }

    @Test(expected = ShortURLNotFoundException.class)
    public void testGetFullURLThrowsException() throws ShortURLNotFoundException{
        URL url = new URL();
        url.setFullUrl("http://testurl");
        when(urlRepository.findById(any())).thenReturn(Optional.empty());
        urlService.getFullURL("abcdef");
    }

    @Test
    public void testSaveURL() throws MalformedURLException, URISyntaxException {
        URL url = new URL();
        url.setFullUrl("http://testurl");
        url.setId(10L);
        when(urlRepository.save(any())).thenReturn(url);
        assertEquals(BaseConversion.idToShortString(10L), urlService.saveURL("http://testurl"));
    }

    @Test(expected = MalformedURLException.class)
    public void testSaveURLForInvalidURL() throws MalformedURLException, URISyntaxException {
        urlService.saveURL("htt//testurl");
    }
}