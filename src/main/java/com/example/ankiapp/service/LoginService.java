package com.example.ankiapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ankiapp.entitiy.UserInfo;
import com.example.ankiapp.repository.UserInfoRepository;

import lombok.RequiredArgsConstructor;

/**
 * ログイン画面 Service
 */


/**
 * 
 */
@Service
@RequiredArgsConstructor
//@Transactional
public class LoginService {
	/** ログイン情報テーブルDIO*/
	private final UserInfoRepository repository;
	/**
	 * ユーザー情報テーブル　主キー検索
	 * 
	 * @param loginId　ログインID
	 * @return ユーザー情報テーブルをキー検索した結果(１件)
	 */
	public Optional<UserInfo>searchUserById(String loginId) {
		return repository.findById(loginId);
	}
}
