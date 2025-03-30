package com.example.ankiapp.entitiy;

import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

/**
 * デッキ情報テーブル Entity
 */

@Entity
@Data
@Table (name = "deck_info",
uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_user_deck_name",
            columnNames = {"user_login_id", "name"}
        )
    })
public class DeckInfo {
    
    /**デッキID*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deck_id")
    private Long deckId;
    
    /**タイトル*/
    @Column(name = "name", nullable = false)
    private String title;

    /**説明*/
    @Column(columnDefinition = "TEXT")
    private String description;
    
    /**デッキの画像URL*/
    @Column(name="image_url")
    private String imageUrl;
    
    /**ユーザー*/
    @ManyToOne
    @JoinColumn(name = "user_login_id", referencedColumnName = "login_id")
    private UserInfo userInfo;
    
    /**作成日*/
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**更新日*/
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    
}
