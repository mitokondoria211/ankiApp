package com.example.ankiapp.form;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * ユーザー登録画面 Form
 */
@Data
public class SignupForm {
	/**ログインID*/
	@Length(min = 8, max = 20)
	private String loginId;
	/**	パスワード*/
	@Length(min=8, max=20)
	private String password;
	
	@Length(max=100)
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9][A-Za-z0-9-]*[A-Za-z0-9]*\\.)+[A-Za-z]{2,}$", message = "{signup.invalidMailAddress}")
	private String mailAddress;
}
