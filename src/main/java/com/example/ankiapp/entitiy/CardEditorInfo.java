package com.example.ankiapp.entitiy;

import java.time.LocalDateTime;
import org.hibernate.annotations.UpdateTimestamp;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.entitiy.converter.CardAnswerResultConverter;
import com.example.ankiapp.entitiy.converter.UserAuthorityConverter;
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
 * ユーザ情報テーブル Entity
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        name = "card_editor_info",
        uniqueConstraints = {
            @UniqueConstraint(
                name = "uk_deck_card_name",
                columnNames = {"deck_id", "card_name"}
            )
        }
    )
public class CardEditorInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardEditor_id")
     private Long cardId;
    
    @ManyToOne
    @JoinColumn(name = "deck_id")
    private DeckInfo deckInfo;
    
    @ManyToOne
    @JoinColumn(name = "login_id")
    private UserInfo userInfo;
    
    @Column(name="card_name")
    private String cardName;
    
    @Column(columnDefinition = "TEXT")
    private String question;
    
    @Column(columnDefinition = "TEXT")
    private String answer;
    
    @Column(name="question_image_path")
    private String questionImagePath;
    
    @Column(name="answer_image_path")
    private String answerImagePath;
    
    @Column(name="card_result")
    @Convert(converter = CardAnswerResultConverter.class)
    private CardAnswerResult cardResult;
    
    //作成日
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    //更新日
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
//    @Column(name="user_id")
//    private String userId;
    
//    public CardEditorInfo() {
//        
//    }
    
}
