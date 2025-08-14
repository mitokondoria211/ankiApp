package com.example.ankiapp.controller;


import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ankiapp.constant.SessionKeyConst;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.form.LoginForm;
import com.example.ankiapp.service.SignupConfirmService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * ログインコントローラー
 */

@Controller
@RequiredArgsConstructor
public class LoginController {
    
	/**セッション情報*/
	private final HttpSession session;
	
	private final SignupConfirmService signupConfirmService;
	
	/**
     * 画面の初期表示を行います。
     * 
     * @param model モデル
     * @param form 入力情報
     * @return ログイン画面
     */
	@GetMapping(UrlConst.LOGIN)
	public String view(Model model, LoginForm form) {
	    return ViewNameConst.LOGIN;
	}
	
	/**
	 * ログインエラー画面表示
	 * 
	 * @param model モデル
	 * @param form 入力情報
	 * @return 表示画面
	 */
	@GetMapping(value = UrlConst.LOGIN, params = "error")
	public String viewWithError(Model model, LoginForm form) {
		var errorInfo = (Exception)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		model.addAttribute("errorMsg", errorInfo.getMessage());
		return ViewNameConst.LOGIN;
	}
	
	/**
	 * 仮登録ユーザーをワンタイムコード入力画面に誘導します
	 */
	@PostMapping("/signupActivation")
	public String redirectToSignupConfirm(String loginId, RedirectAttributes redirectAttributes) {
	    // サービスを使用して仮登録状態をチェック
	    boolean isTemporary = signupConfirmService.isTemporaryRegistrationUser(loginId);
	    
	    if (!isTemporary) {
		    // 仮登録状態でない場合
		    redirectAttributes.addFlashAttribute("errorMsg", 
		        "入力されたログインIDは仮登録状態ではありません。");
		    return "redirect:/signupActivation";
	    }
	    
        // 仮登録状態の場合
        session.setAttribute(SessionKeyConst.ONE_TIME_AUTH_LOGIN_ID, loginId);
        
        redirectAttributes.addFlashAttribute("message", 
            "ワンタイムコードを入力して本登録を完了してください。");
        redirectAttributes.addFlashAttribute("isError", false);
        
        return "redirect:/signupConfirm";

	    

	}
}
