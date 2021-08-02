package com.ashwini.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class URL {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  @Id  Long id;

    private String fullUrl;
}

