package com.ramazanbatbay.security_webapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdAt;

    public Note(String title, String content, Integer userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
