package com.example.ankiapp.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ankiapp.constant.MessageConst;
import com.example.ankiapp.constant.SessionKeyConst;
import com.example.ankiapp.constant.SignupResult;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.dto.SignupInfo;
import com.example.ankiapp.form.SignupForm;
import com.example.ankiapp.service.SignupService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * ユーザー登録コントローラー
 */

@Controller
@RequiredArgsConstructor
public class SignupController {

	/**ユーザー登録画面 */
	private final SignupService service;

	/**セッション情報*/
	private final HttpSession session;

	/**Dozer Mapper*/
	private final Mapper mapper;

	/**メッセージソース*/
	private final MessageSource messageSource;

	/**画面で使用するフォームクラス*/
	private static final String FORM_CLASS_NAME = "signupForm";

	/**
	 * 画面の初期表示を行います。
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return ユーザー登録画面
	 */
	@GetMapping(UrlConst.SIGNUP)
	public String view(Model model) {
		//追加コード
		var isIntialDisp = !model.containsAttribute(FORM_CLASS_NAME);
		if (isIntialDisp) {
			model.addAttribute(FORM_CLASS_NAME, new SignupForm());
		}
		//*
		return ViewNameConst.SIGNUP;
	}

	/**
	 * 画面の入力情報からユーザー登録処理を呼び出します。
	 * 
	 * <p>ただし、入力チェックでエラーになった場合や登録済みのログインIDを使っていた場合は<br>
	 * エラーメッセージを画面に表示します。
	 * 
	 * @param form 入力情報
	 * @param bdResult 入力内容の単項目チェック結果
	 * @param redirectAttributes リダイレクト用モデル
	 * @return リダイレクトURL
	 */
	@PostMapping(UrlConst.SIGNUP)
	public String signup(@Validated SignupForm form, BindingResult bdResult, RedirectAttributes redirectAttributes) {
		if (bdResult.hasErrors()) {
			editGuideMessage(form, bdResult, MessageConst.FORM_ERROR, redirectAttributes);
			return AppUtility.doRedirect(UrlConst.SIGNUP);
		}

		var signupResult = service.signup(mapper.map(form, SignupInfo.class));
		var isError = signupResult != SignupResult.SUCCEED;
		if (isError) {
			editGuideMessage(form, bdResult, signupResult.getMessageId(), redirectAttributes);
			return AppUtility.doRedirect(UrlConst.SIGNUP);
		}

		session.setAttribute(SessionKeyConst.ONE_TIME_AUTH_LOGIN_ID, form.getLoginId());
		return AppUtility.doRedirect(UrlConst.SIGNUP_CONFIRM);
	}
	
	
	/**
	 * メッセージIDを使ってプロパティファイルからメッセージを取得し、画面に表示します。
	 * 
	 * <p>また、画面でメッセージを表示する際に通常メッセージとエラーメッセージとで色分けをするため、<br>
	 * その判定に必要な情報も画面に渡します。
	 * 
	 * @param form 入力情報
	 * @param bdResult 入力内容の単項目チェック結果
	 * @param messageId プロパティファイルから取得したいメッセージのID
	 * @param redirectAttributes リダイレクト用モデル
	 */
	private void editGuideMessage(SignupForm form, BindingResult bdResult, String messageId,
			RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, messageId));
		redirectAttributes.addFlashAttribute("isError", true);
		redirectAttributes.addFlashAttribute(form);
		redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_CLASS_NAME, bdResult);
	}

}
