package com.example.ankiapp.utilty;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * アプリーション共通クラス
 */
public class AppUtility {
    
    /**
     * メッセージIDからメッセージを取得する
     * @param messageSource　メッセージソース
     * @param key　メッセージキー
     * @param params　置換文字群
     * @return
     */
     public static String getMessage(MessageSource messageSource,String key,Object... params) {
    		return messageSource.getMessage(key, params,Locale.JAPAN);
     }
     
     /**
      * DBのLIKE検索用に、パラメーターにワイルドカードを付与します
      * 
     * @param param パラメーター
     * @return 前後にワイルドカードがついたパラメーター
     */
    public static String addWildcard(String param) {
         return "%" + param + "%";
     }
    
    /**
     * リダイレクト先のURLを受け取って、リダイレクトURLを作成します
     * @param url リダイレクト先URL
     * @return リダイレクトのURL
     */
    public static String doRedirect(String url) {
        return "redirect:" + url;
    }
    
    public static String getUsername() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userName;
    }
    
    public static String generatedRandomString(int oneTimeCodeLength) {
        var sb = new StringBuilder();
        for (int i = 0; i < oneTimeCodeLength; i++) {
            var randomNum = ThreadLocalRandom.current().nextInt(10);
            sb.append(randomNum);
        }
        return sb.toString();
    }
}
