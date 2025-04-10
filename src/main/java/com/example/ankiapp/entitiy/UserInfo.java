package com.example.ankiapp.entitiy;

import java.time.LocalDateTime;

import com.example.ankiapp.constant.db.AuthorityKind;
import com.example.ankiapp.constant.db.UserStatusKind;
import com.example.ankiapp.entitiy.converter.UserAuthorityConverter;
import com.example.ankiapp.entitiy.converter.UserStatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ユーザ情報テーブル Entity
 */
@AllArgsConstructor
@Entity
@Data
@Table (name = "user_info")
public class UserInfo {
	
	/**ログインID*/
	@Id
	@Column(name = "login_id", nullable = false)
	private String loginId;
	
	/**パスワード*/
	@Column(nullable = false)
	private String password;
	
	/**メールアドレス*/
	@Column(name="mail_address", nullable = false)
	private String mailAddress;
	
	/**ワンタイムコード*/
    @Column(name="one_time_code")
	private String oneTimeCode;
    
    /**ワンタイムコード有効期限*/
    @Column(name="one_time_code_send_time")
	private LocalDateTime oneTimeCodeSendTime;
    
	/**ログイン失敗回数*/
	@Column(name = "login_failure_count")
	private int loginFailureCount;
	
	/**アカウントロック日時*/
	@Column(name = "account_locked_time")
	private LocalDateTime accountLockedTime;

	/**ユーザー情報種別*/
	@Column(name = "is_disabled", nullable = false)
	@Convert(converter = UserStatusConverter.class)
	private UserStatusKind userStatusKind;
	
	/**ユーザー権限種別*/
	@Column(name = "authority", nullable = false)
	@Convert(converter = UserAuthorityConverter.class)
	private AuthorityKind authorityKind;
	
	/**登録完了*/
	@Column(name = "signup_completed")
	private boolean signupCompleted = false;
	
	   /**登録日時*/
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;
    
    /**最終更新日時*/
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
    
    /**最終更新ユーザー*/
    @Column(name= "update_user", nullable = false)
    private String updateUser;
    
    /**仮登録完了かどうか*/
    @Column(name = "is_signup_completed")
    private boolean isSignupCompleted;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public UserInfo() {
		
	}
	/**
	 * ログイン失敗回数をインクリメントする
	 * 
	 * @return ログイン失敗回数がインクリメントされたUseInfo
	 */
	
	public UserInfo incrementLoginFailureCount() {
		return new UserInfo(loginId, password, mailAddress, oneTimeCode, oneTimeCodeSendTime, ++loginFailureCount, 
		        accountLockedTime, userStatusKind, authorityKind,signupCompleted, createTime, updateTime, updateUser, isSignupCompleted);
	}
	
	/**
	 * ログイン失敗情報をリセットする
	 * 
	 * @return ログイン失敗情報がリセットされたUseInfo
	 */
	
	public UserInfo resetLoginFailureInfo() {
		return new UserInfo(loginId, password, mailAddress, oneTimeCode, oneTimeCodeSendTime, 0, null, userStatusKind, authorityKind, 
		        signupCompleted, createTime, updateTime, updateUser, isSignupCompleted);
	}
	
	/**
	 * アカウントロック状態に更新する
	 * 
	 * @return ログイン失敗回数、アカウントロック日時が更新されたUseInfo
	 */
	
	public UserInfo updateAccountLocked() {
	
		return new UserInfo(loginId, password, mailAddress, oneTimeCode, oneTimeCodeSendTime, 0, LocalDateTime.now(), userStatusKind, authorityKind, 
		        signupCompleted, createTime, updateTime, updateUser, isSignupCompleted);
	}

}
