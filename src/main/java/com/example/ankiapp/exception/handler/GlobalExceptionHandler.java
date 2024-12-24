package com.example.ankiapp.exception.handler;

import java.net.URL;
import javax.naming.spi.DirStateFactory.Result;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.ankiapp.constant.CreateCardResult;
import com.example.ankiapp.constant.CreateDeckResult;
import com.example.ankiapp.constant.MessageConst;
import com.example.ankiapp.constant.UpdateCardResult;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.exception.response.ErrorResponse;
import com.example.ankiapp.service.CardEditService;
import com.example.ankiapp.service.ImageStorageService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.var;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    
    private final MessageSource messageSource;

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
//            var result = CreateCardResult.FAILURE_BY_IMAGE_SIZE_ERROR;
//            redirectAttributes.addFlashAttribute(
//                    "message", AppUtility.getMessage(messageSource, result.getMessageId()));
//            redirectAttributes.addFlashAttribute("error", result);
//            redirectAttributes.addFlashAttribute("isError", true);
            addErrorAttribute(
                    redirectAttributes, 
                    messageSource,
                    CreateCardResult.FAILURE_BY_IMAGE_SIZE_ERROR);
        }else if(originalUrl.contains(UrlConst.UPDATE_CARD)) {
//            var result = UpdateCardResult.FAILURE_BY_IMAGE_SIZE_ERROR;
//            redirectAttributes.addFlashAttribute(
//                    "message", AppUtility.getMessage(messageSource, result.getMessageId()));
//            redirectAttributes.addFlashAttribute("error", result);
//            redirectAttributes.addFlashAttribute("isError", true);
            addErrorAttribute(
                    redirectAttributes,
                    messageSource,
                    UpdateCardResult.FAILURE_BY_IMAGE_SIZE_ERROR);
        }else if(originalUrl.contains(UrlConst.CREATE_DECK)) {
            addErrorAttribute(redirectAttributes,
                    messageSource,
                    CreateDeckResult.FAILURE_BY_IMAGE_SIZE_ERROR);
        }
//        } else if (requestUrl.contains("/deck/")) {
//            redirectAttributes.addFlashAttribute("createDeckResult", 
//                CreateDeckResult.FAILURE_BY_IMAGE_SIZE_ERROR);
//        }
        
        return redirectUrl;
    }
    
    private void addErrorAttribute(
            RedirectAttributes redirectAttributes, MessageSource messageSource, Enum<?> result) {
        redirectAttributes.addFlashAttribute(
                "message", AppUtility.getMessage(messageSource, result.toString()));
        redirectAttributes.addFlashAttribute("error", result);
        redirectAttributes.addFlashAttribute("isError", true);
    }
    

}
