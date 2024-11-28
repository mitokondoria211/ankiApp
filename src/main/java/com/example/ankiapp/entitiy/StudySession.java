package com.example.ankiapp.entitiy;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "studySessions")
public class StudySession {
	//	セッションID(主キー)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "session_id")
	 private Long sessionId;
	 //ユーザーID(外部キー)
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User2 user;
	//デッキID(外部キー)
//    @ManyToOne
//    @JoinColumn(name = "deck_id", nullable = false)
//    private Deck deck;
	//作成日
    @Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;
	//更新日
    @Column(name = "updated_at")
    @UpdateTimestamp
	private LocalDateTime upadatedAt;
}
