package com.example.ankiapp.form;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import com.example.ankiapp.form.ValidGroups.ValidGroup1;
import com.example.ankiapp.form.ValidGroups.ValidGroup2;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * カード編集 Form
 */
@Data
public class CardEditorForm {
    
    /**デッキID*/
    private Long deckId;
    
    /**カード名*/
    @NotBlank
    @Length(min = 4, max = 25, message="{cardName.cardEditorForm.length}")
    private String cardName;
    
    /**問題*/
    @NotBlank
    @Length(min = 4, max = 1000, message="{question.cardEditorForm.length}")
    private String question;
    
    /**解答*/
    @NotBlank
    @Length(min = 1, max = 1000, message="{answer.cardEditorForm.length}")
    private String answer;
    
    /**問題画像*/
    private MultipartFile QuestionImageFile;
    
    /**解答画像*/
    private MultipartFile AnswerImageFile;
}
