package com.example.ankiapp.controller;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ankiapp.constant.CardUpadateResult;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.dto.CardUpdateInfo;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.form.CardUpdateForm;
import com.example.ankiapp.service.CardEditService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class CardUpdateController {
    
    private final CardEditService cardEditorService;
    
    /**メッセージソース*/
    private final MessageSource messageSource;
    
    /**画面で使用するフォームクラス*/
    private static final String FORM_CLASS_NAME ="cardUpdateForm";
    
    /**Dozer Mapper*/
    private final Mapper mapper;
    /**
     * デッキリスト画面の初期表示を行います。
     * 
     * @param model モデル
     * @return デッキメニュー画面
     * @throws IOException 
     */
    
    @GetMapping(UrlConst.UPDATE_CARD + "/{cardId}")

  public String cardView(@PathVariable Long cardId, Model model,CardUpdateForm form) throws IOException {
      
      CardInfo cardInfo = cardEditorService.findCardInfoByCardId(cardId);
      CardUpdateInfo updateInfo = mapper.map(cardInfo, CardUpdateInfo.class);
      form = new CardUpdateForm();
      
      //カードフォームの初期値をセット
      form.setCardName(cardInfo.getCardName());
      form.setQuestion(cardInfo.getQuestion());
      form.setAnswer(cardInfo.getAnswer());
      form.setCardId(cardId);
      
      model.addAttribute("updateCard", updateInfo);
      model.addAttribute("questionUrl", cardInfo.getQuestionImageUrl());
      model.addAttribute("answerUrl", cardInfo.getAnswerImageUrl());
      model.addAttribute("cardResults", CardAnswerResult.values());
      model.addAttribute("cardUpdateForm", form);

      return ViewNameConst.UPDATE_CARD;
  }
    
    @PostMapping(UrlConst.UPDATE_CARD)
    public String updateDeck(@Validated @ModelAttribute CardUpdateForm form, 
                                                BindingResult bdResult , 
                                                Model model, 
                                                RedirectAttributes redirectAttributes) throws IOException {
        CardInfo cardInfo = cardEditorService.findCardInfoByCardId(form.getCardId());
        Long cardId = form.getCardId();
        
        if(bdResult.hasErrors()) {
//            CardUpdateInfo updateInfo = mapper.map(cardInfo, CardUpdateInfo.class);
            // 必要な属性を設定
            model.addAttribute("updateCard", mapper.map(cardInfo, CardUpdateInfo.class));
            model.addAttribute("questionUrl", cardInfo.getQuestionImageUrl());
            model.addAttribute("answerUrl", cardInfo.getAnswerImageUrl());
            model.addAttribute("cardResults", CardAnswerResult.values());
            model.addAttribute("isError", true);
            model.addAttribute("cardUpdateForm", form);
            return ViewNameConst.UPDATE_CARD + "/" + cardId;
        }
        
        var result = cardEditorService.updateCardEditorInfo(cardInfo, form);
        boolean isError = result != CardUpadateResult.SUCCEED;
        
        if(isError) {
            editGuideMessage(form, bdResult, result.getMessageId(), redirectAttributes);
            return AppUtility.doRedirect(UrlConst.UPDATE_CARD + "/" + form.getCardId());
        }
        
        cardInfo = cardEditorService.findCardInfoByCardId(form.getCardId());
        CardUpdateInfo updateInfo = mapper.map(cardInfo, CardUpdateInfo.class);
        
        model.addAttribute("updateCard", updateInfo);
        model.addAttribute("questionUrl", cardInfo.getQuestionImageUrl());
        model.addAttribute("answerUrl", cardInfo.getAnswerImageUrl());
        model.addAttribute("cardResults", CardAnswerResult.values());
        model.addAttribute("cardUpdateForm", form);
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, result.getMessageId()));
        redirectAttributes.addFlashAttribute("isError", false);
        return AppUtility.doRedirect(UrlConst.UPDATE_CARD+ "/" + form.getCardId());
    }
    
    private void editGuideMessage(CardUpdateForm form, BindingResult bdResult, String messageId, 
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, messageId));
        redirectAttributes.addFlashAttribute("isError", true);
        redirectAttributes.addFlashAttribute("cardUpdateForm",form);
        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_CLASS_NAME, bdResult);
    }

    
}
