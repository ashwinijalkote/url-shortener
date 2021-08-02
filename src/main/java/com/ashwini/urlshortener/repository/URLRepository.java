package com.ashwini.urlshortener.repository;

import com.ashwini.urlshortener.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends JpaRepository<URL, Long> {
}

