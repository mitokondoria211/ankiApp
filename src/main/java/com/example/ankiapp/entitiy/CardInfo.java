package com.example.ankiapp.entitiy;

import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.converter.CardAnswerResultConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * カード情報テーブル Entity
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        name = "card_info",
        uniqueConstraints = {
            @UniqueConstraint(
                name = "uk_deck_card_name",
                columnNames = {"deck_id", "card_name"}
            )
        }
    )
public class CardInfo {
    
    /**カードID*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
     private Long cardId;
    
    /**デッキ*/
    @ManyToOne
    @JoinColumn(name = "deck_id")
    private DeckInfo deckInfo;
    
    /**ユーザー*/
    @ManyToOne
    @JoinColumn(name = "login_id")
    private UserInfo userInfo;
    
    /**カード名*/
    @Column(name="card_name")
    private String cardName;
    
    /**質問*/
    @Column(columnDefinition = "TEXT")
    private String question;
    
    /**解答*/
    @Column(columnDefinition = "TEXT")
    private String answer;
    
    /**質問カードの画像パス*/
    @Column(name="question_image_path")
    private String questionImagePath;
    
    /**解答カードの画像パス*/
    @Column(name="answer_image_path")
    private String answerImagePath;
    
    /**カードの回答結果*/
    @Column(name="card_result")
    @Convert(converter = CardAnswerResultConverter.class)
    private CardAnswerResult cardResult;
    
    /**作成日*/
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    /**更新日*/
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
}
