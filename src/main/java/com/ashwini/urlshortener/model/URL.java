package com.ashwini.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class URL {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  @Id  Long id;

    private String fullUrl;
}

