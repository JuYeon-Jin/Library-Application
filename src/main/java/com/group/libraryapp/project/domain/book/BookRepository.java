package com.group.libraryapp.project.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByDeletedFalse();
    Book findById(int id);

    @Modifying
    @Query("UPDATE Book b SET b.status = :status WHERE b.id = :id")
    void updateStatusById(int id, boolean status);

}
