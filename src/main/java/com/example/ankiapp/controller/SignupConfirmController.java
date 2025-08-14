package com.example.ankiapp.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ankiapp.constant.MessageConst;
import com.example.ankiapp.constant.SessionKeyConst;
import com.example.ankiapp.constant.SignupConfirmStatus;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.service.SignupConfirmService;
import com.example.ankiapp.utilty.AppUtility;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * ユーザー登録コントローラー
 */

@Controller
@RequiredArgsConstructor
public class SignupConfirmController {

	/**ユーザー登録画面 */
	private final SignupConfirmService service;

	/**セッション情報*/
	private final HttpSession session;

	/**メッセージソース*/
	private final MessageSource messageSource;
	
	 /**
     * 仮登録ユーザー認証画面の表示
     * 
     * @param model モデル
     * @return 仮登録ユーザー認証画面
     */
    @GetMapping("/signupActivation")
    public String viewActivation(Model model) {
        return "signupActivation";
    }
    
    /**
     * 仮登録ユーザーをワンタイムコード入力画面に誘導します
     * パスワード認証も追加
     */
//    @PostMapping("/login/temporary")
//    public String redirectToSignupConfirm(String loginId, String password, RedirectAttributes redirectAttributes) {
//        // パスワードの検証（パスワードが正しいかどうか）
//        boolean isValidPassword = service.verifyTemporaryUserPassword(loginId, password);
//        
//        if (!isValidPassword) {
//            redirectAttributes.addFlashAttribute("errorMsg", 
//                "パスワードが正しくありません。");
//            return "redirect:/signupActivation";
//        }
//        return "signupConfirm";
//    }
	@GetMapping("/signupConfirm")
	public String view(Model model, RedirectAttributes redirectAttributes) {
	    // セッションからログインIDを取得
	    var loginId = (String) session.getAttribute(SessionKeyConst.ONE_TIME_AUTH_LOGIN_ID);
	    
	    if (loginId == null) {
	        // セッションにログインIDがない場合
	        redirectAttributes.addFlashAttribute("errorMsg", 
	            "無効な操作です。ログイン画面から仮登録ユーザー向けのフォームをご利用ください。");
	        return "redirect:/login";
	    }
	    
	    // サービスを使用して仮登録状態をチェック（念のため）
	    boolean isTemporary = service.isTemporaryRegistrationUser(loginId);
	    if (!isTemporary) {
	        // 仮登録状態でない場合（既に本登録完了など）
	        session.removeAttribute(SessionKeyConst.ONE_TIME_AUTH_LOGIN_ID);
	        redirectAttributes.addFlashAttribute("errorMsg", 
	            "既に本登録が完了しているか、ユーザーが存在しません。");
	        return "redirect:/login";
	    }
	    
	    return "signupConfirm";
	}

	@PostMapping(UrlConst.SIGNUP_CONFIRM)
	public String signup(String oneTimeCode, RedirectAttributes redirectAttributes) {
		var loginId = (String) session.getAttribute(SessionKeyConst.ONE_TIME_AUTH_LOGIN_ID);
		if (loginId == null) {
			redirectAttributes.addFlashAttribute("message", MessageConst.SIGNUP_CONFIRM_INVALID_OPERATION);
			redirectAttributes.addFlashAttribute("isError", true);
			return AppUtility.doRedirect(UrlConst.SIGNUP_CONFIRM);
		}
		
		var signupConfirmStatus = service.updateUserAsSignupCompletion(loginId, oneTimeCode);

		//次画面にワンタイムコード認証結果の情報を渡す
		var messageId = AppUtility.getMessage(messageSource, signupConfirmStatus.getMessageId());
		var isError = signupConfirmStatus != SignupConfirmStatus.SUCCEED;
		redirectAttributes.addFlashAttribute("message", messageId);
		redirectAttributes.addFlashAttribute("isError", isError);
		if (isError) {
			return AppUtility.doRedirect(UrlConst.SIGNUP_CONFIRM);
		}

		session.removeAttribute(SessionKeyConst.ONE_TIME_AUTH_LOGIN_ID);
		return AppUtility.doRedirect(UrlConst.SIGNUP_COMPLETION);
	}
	
	/**
	 * ワンタイムコードを再送します。
	 * 
	 * @param redirectAttributes リダイレクト用モデル
	 * @return ユーザー登録確認画面
	 */
	@PostMapping(UrlConst.SIGNUP_CONFIRM+"/resend")
	public String resendOneTimeCode(RedirectAttributes redirectAttributes) {
		 var loginId = (String) session.getAttribute(SessionKeyConst.ONE_TIME_AUTH_LOGIN_ID);
		    if (loginId == null) {
		        redirectAttributes.addFlashAttribute("message", 
		            AppUtility.getMessage(messageSource, MessageConst.SIGNUP_CONFIRM_INVALID_OPERATION));
		        redirectAttributes.addFlashAttribute("isError", true);
		        return AppUtility.doRedirect(UrlConst.SIGNUP_CONFIRM);
		    }
		    
		    var resendResult = service.resendOneTimeCode(loginId);
		    var isError = !resendResult;
		    
		    if (isError) {
		        redirectAttributes.addFlashAttribute("message", 
		            AppUtility.getMessage(messageSource, MessageConst.SIGNUP_CONFIRM_RESEND_FAILURE));
		    } else {
		        redirectAttributes.addFlashAttribute("message", 
		            AppUtility.getMessage(messageSource, MessageConst.SIGNUP_CONFIRM_RESEND_SUCCESS));
		    }
		    
		    redirectAttributes.addFlashAttribute("isError", isError);
		    
		    // デバッグ用にリダイレクト先を明示的に指定
		    return AppUtility.doRedirect(UrlConst.SIGNUP_CONFIRM); 
	}
}
