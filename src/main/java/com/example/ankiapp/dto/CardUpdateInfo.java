package com.example.ankiapp.dto;

import org.springframework.web.multipart.MultipartFile;
import com.example.ankiapp.constant.db.CardAnswerResult;
import lombok.Data;

/**
 * カード更新情報DTOクラス
 */
@Data
public class CardUpdateInfo {
    
    /**カードID*/
    private Long cardId;
    
    /**カード名*/
    private String cardName;
    
    /**カード問題*/
    private String question;
    
    /**カード解答*/
    private String answer;
    
    /**カード評価*/
    private CardAnswerResult cardResult;
    
    /**カード質問画像*/
    private MultipartFile questionImageFile;
    
    /**カード解答画像*/
    private MultipartFile answerImageFile;
}
