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
import lombok.Data;

@Entity
@Data
@Table(name = "cards")
public class Card {
	//	カードーID(主キー)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "card_id")
	 private Long cardId;
	//デッキID(外部キー)
//    @ManyToOne
//    @JoinColumn(name = "deck_id", nullable = false)
//    private Deck deck;
    //表面の内容
    @Column(name = "front_content", columnDefinition = "TEXT")
    private String frontContent;

    //裏面の内容
    @Column(name = "back_content", columnDefinition = "TEXT")
    private String backContent;
	//作成日
    @Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	//更新日
    @Column(name = "updated_at")
    @UpdateTimestamp
	private LocalDateTime upadatedAt;
}
