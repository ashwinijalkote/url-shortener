package com.ashwini.urlshortener.controller;

import com.ashwini.urlshortener.dto.Request;
import com.ashwini.urlshortener.exceptions.ShortURLNotFoundException;
import com.ashwini.urlshortener.service.URLService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.MalformedURLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class URLControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private URLService urlService;

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    @Test
    public void testCreateFullEntry() throws  Exception {
        Request request = new Request("https://testurl");
        when(urlService.saveURL(any(), any())).thenReturn("http://shorturl");
        mockMvc.perform(post("/api/url")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateFullEntryForInvalidURL() throws  Exception {
        Request request = new Request("https://testurl");
        when(urlService.saveURL(any(), any())).thenThrow(new MalformedURLException("malformed excpetion"));
        mockMvc.perform(post("/api/url")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRedirectToFullURL() throws  Exception {
        when(urlService.getFullURL(any())).thenReturn("http://shorturl");
        mockMvc.perform(get("/api/url/shortString")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testRedirectToFullURLForInvalidRequest() throws Exception {
        when(urlService.getFullURL(any())).thenThrow(new ShortURLNotFoundException("not found"));
        mockMvc.perform(get("/api/url/shortString")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}