package com.example.ankiapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;

/**
 * ユーザー登録情報確認結果Controllerクラス
 */

@Controller
@RequiredArgsConstructor
public class SignupCompletionController {
	
	/**
     * 画面の初期表示を行います。
     * 
     * @return ユーザー登録情報確認結果画面
     */
	@GetMapping(UrlConst.SIGNUP_COMPLETION)
	public String view(Model modell) {
	    return ViewNameConst.SIGNUP_COMPLETION;
	}
	   
}
