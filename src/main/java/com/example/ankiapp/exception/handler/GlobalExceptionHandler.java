package com.example.ankiapp.exception.handler;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ankiapp.constant.CardCreateResult;
import com.example.ankiapp.constant.CardUpadateResult;
import com.example.ankiapp.constant.DeckCreateResult;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.utilty.AppUtility;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


/**
 * 例外処理をまとめたクラス
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    /**メッセージソース*/
    private final MessageSource messageSource;

    
    /**
     * サーバから受け取った画像ファイルが大きすぎたときの例外処理
     * @param MaxUploadSizeExceededException e
     * @param redirectAttributes
     * @param HttpServletRequest request
     * @return リダイレクト先のURL
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException e,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        
        // リクエストURLからどのページからのアップロードかを判断
        String originalUrl = (String) request.getAttribute("javax.servlet.error.request_uri");
         originalUrl = request.getHeader("Referer");
        if (originalUrl == null) {
            originalUrl = request.getRequestURI();
        }
        String redirectUrl;
        String entityType;
        
        System.out.println("Actual Request URL: " + originalUrl);
        System.out.println("Expected URL Pattern: " + UrlConst.CARD_EDITOR);
        
        if(originalUrl.contains(UrlConst.CARD_EDITOR)) {
            redirectUrl = AppUtility.doRedirect(UrlConst.CARD_EDITOR);
            entityType = "カード";
        }else if(originalUrl.contains(UrlConst.UPDATE_CARD)) {
         // URLからカードIDを抽出
            String[] urlParts = originalUrl.split("/");
            String cardId = urlParts[urlParts.length - 1];
            // カードIDを含むURLにリダイレクト
            redirectUrl = AppUtility.doRedirect(UrlConst.UPDATE_CARD + "/" + cardId);
            entityType = "カード";
        }else if(originalUrl.contains(UrlConst.CREATE_DECK)) {
            redirectUrl = AppUtility.doRedirect(UrlConst.CREATE_DECK);
            entityType = "デッキ";
        }else if(originalUrl.contains(UrlConst.UPDATE_DECK)) {
            redirectUrl = AppUtility.doRedirect(UrlConst.UPDATE_DECK);
            entityType = "デッキ";
        }else {
            redirectUrl = "redirect:/error";
            entityType = "ファイル";
        }
        // エラーメッセージの設定
        String errorMessage = String.format(
            "%sの画像サイズが制限を超えています<br>"
            + "（単一ファイル：1MB、全体：10MB）",
            entityType
        );
        // リダイレクト時にメッセージを保持
        redirectAttributes.addFlashAttribute("message", errorMessage);
        
        // CreateCardResultまたはCreateDeckResultの設定
        if (originalUrl.contains(UrlConst.CARD_EDITOR)) {
            addErrorAttribute(
                    redirectAttributes, 
                    messageSource,
                    CardCreateResult.FAILURE_BY_IMAGE_SIZE_ERROR);
        }else if(originalUrl.contains(UrlConst.UPDATE_CARD)) {
            addErrorAttribute(
                    redirectAttributes,
                    messageSource,
                    CardUpadateResult.FAILURE_BY_IMAGE_SIZE_ERROR);
        }else if(originalUrl.contains(UrlConst.CREATE_DECK)) {
            addErrorAttribute(redirectAttributes,
                    messageSource,
                    DeckCreateResult.FAILURE_BY_IMAGE_SIZE_ERROR);
        }
//        } else if (requestUrl.contains("/deck/")) {
//            redirectAttributes.addFlashAttribute("createDeckResult", 
//                CreateDeckResult.FAILURE_BY_IMAGE_SIZE_ERROR);
//        }
        
        return redirectUrl;
    }
    
    /**
     * エラーメッセージを表示するメソッド
     * @param result 様々なEnumクラス
     */
    private void addErrorAttribute(
            RedirectAttributes redirectAttributes, MessageSource messageSource, Enum<?> result) {
        redirectAttributes.addFlashAttribute(
                "message", AppUtility.getMessage(messageSource, result.toString()));
        redirectAttributes.addFlashAttribute("error", result);
        redirectAttributes.addFlashAttribute("isError", true);
    }
    
    
//    @ExceptionHandler(Exception.class)
//    public String handleException(Exception e, Model model) {
//        model.addAttribute("errorMessage", e.getMessage());
//        model.addAttribute("stackTrace", e.getStackTrace());
//        return "error";
//    }
    

}
