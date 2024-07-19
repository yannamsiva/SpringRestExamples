package com.shiva.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shiva.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
