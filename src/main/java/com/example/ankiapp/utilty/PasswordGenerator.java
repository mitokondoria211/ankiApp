package com.example.ankiapp.utilty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	//ハッシュ化した文字列を返すクラス
	public static void main(String [] args) {
		// 「BCrypt」のインスタンス化
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// 入力値
		String rawPassword = "demo1234";
		// パスワードをハッシュ化
		String encodedPassword = encoder.encode(rawPassword);
		// 表示
		System.out.println("ハッシュ化されたパスワード" + encodedPassword);
	}
}
