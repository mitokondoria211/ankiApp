package com.example.ankiapp.form;


import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import com.example.ankiapp.constant.db.CardAnswerResult;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * カード更新画面Formクラス
 */
@Data
public class CardUpdateForm {
    
    /**カードID*/
    private Long cardId;
    
    /**カード名*/
    @NotBlank
    @Length(min = 4, max = 25, message="{question.cardUpdateForm.length}")
    private String cardName;
    
    @NotBlank
    @Length(min = 4, max = 1000, message="{question.cardUpdateForm.length}")
    /**カード問題*/
    private String question;
    
    @NotBlank
    @Length(min = 1, max = 1000, message="{answer.cardUpdateForm.length}")
    /**カード解答*/
    private String answer;
    
    /**カード評価*/
    private CardAnswerResult cardResult;
    
    /**カード質問画像*/
    private MultipartFile questionImageFile;
    
    /**カード質問画像パス*/
    private String questionImagePath;
    
    /**カード解答画像*/
    private MultipartFile answerImageFile;
    
    /**カード質問画像パス*/
    private String answerImagePath;
}
