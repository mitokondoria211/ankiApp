package com.example.ankiapp.entitiy;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cardReviews")
public class CardReview {
	//レビューID(主キー)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long reviewId;
	 //ユーザーID(外部キー)
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User2 user;
	//カードID(外部キー)
//    @ManyToOne
//    @JoinColumn(name = "card_id", nullable = false)
//    private Card card;
	//セッションID(外部キー)
//    @ManyToOne
//    @JoinColumn(name = "session_id", nullable = false)
//    private StudySession session;
    //復習時間
    @Column(name = "review_time", nullable = false)
    private LocalDateTime reviewTime;
    //カード評価
    @Column(name = "performance_rating", nullable = false)
    private Integer performanceRating;
    //次回復習する時間
    @Column(name = "next_review_date", nullable = false)
    private LocalDateTime nextReviewDate;
}
