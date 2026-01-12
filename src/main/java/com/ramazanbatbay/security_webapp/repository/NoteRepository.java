package com.ramazanbatbay.security_webapp.repository;

import com.ramazanbatbay.security_webapp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findByUserId(Integer userId);

    // Requirement: At least one raw SQL query with prepared statements
    @Query(value = "SELECT * FROM notes WHERE user_id = :userId ORDER BY created_at DESC", nativeQuery = true)
    List<Note> findAllByUserIdOrderByCreatedAtDesc(@Param("userId") Integer userId);
}
