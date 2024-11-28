package com.example.ankiapp.entitiy;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

//ユーザー情報
@Entity
@Data
@Table (name = "users")
public class User2 {
	//ユーザーid(主キー)
	@Id
	@Column(name = "user_id")
	private Long userId;
	//ユーザー名
	@Column(nullable = false, unique = true)
	private String name;
	//メールアドレス
	@Column(nullable = false, unique = true)
	private String mail;
	//パスワード
	@Column(nullable = false)
	private String password;
	//作成日
	@Column(nullable = false, updatable = false)
	@CreatedDate
	private LocalDateTime createdAt;
}
