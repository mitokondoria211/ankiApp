package com.example.ankiapp.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.db.CardAnswerResult;
import com.example.ankiapp.dto.CardUpdateInfo;
import com.example.ankiapp.entitiy.CardInfo;
import com.example.ankiapp.form.CardUpdateForm;
import com.example.ankiapp.service.CardEditorService;
import com.example.ankiapp.service.ImageStorageService;
import com.example.ankiapp.utilty.AppUtility;
import com.github.dozermapper.core.Mapper;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class CardUpdateController {
    
    private final CardEditorService cardEditorService;
    
    private final ImageStorageService imageStorageService;
    
    
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
//  public String deckListView(@RequestParam Long deckId, Model model) {
  public String cardView(@PathVariable Long cardId, Model model) throws IOException {
//      var deckList =  deckListService.editDeckList();
      
      CardInfo cardInfo = cardEditorService.findCardInfoByCardId(cardId);
      CardUpdateInfo updateInfo = mapper.map(cardInfo, CardUpdateInfo.class);
      String userName = AppUtility.getUsername();
      Long deckId = cardInfo.getDeckInfo().getDeckId();
      String questionImage = imageStorageService.displayQuestionCardImage(userName, deckId, cardId);
      String answerImage = imageStorageService.displayAnswerCardImage(userName, deckId, cardId);
      var form = new CardUpdateForm();
      //カードフォームの初期値をセット
      form.setCardName(cardInfo.getCardName());
      form.setQuestion(cardInfo.getQuestion());
      form.setAnswer(cardInfo.getAnswer());
      form.setCardId(cardId);
      
      model.addAttribute("updateCard", updateInfo);
      model.addAttribute("questionImage", questionImage);
      model.addAttribute("answerImage", answerImage);
      model.addAttribute("cardResults", CardAnswerResult.values());
      model.addAttribute("updateCardForm", form);
//      model.addAttribute("deckListForm", new DeckListForm());  // この行を追加
//      model.addAttribute("deckForm", new DeckForm());
      return UrlConst.UPDATE_CARD;
  }
    
    @PostMapping(UrlConst.UPDATE_CARD)
    public String updateDeck(@ModelAttribute CardUpdateForm form, Model model, RedirectAttributes attributes) throws IOException {
        CardInfo cardInfo = cardEditorService.findCardInfoByCardId(form.getCardId());
        cardInfo = cardEditorService.updateCardEditorInfo(cardInfo, form);
        CardUpdateInfo updateInfo = mapper.map(cardInfo, CardUpdateInfo.class);
        String userName = AppUtility.getUsername();
        Long deckId = cardInfo.getDeckInfo().getDeckId();
        Long cardId = form.getCardId();
        String questionImage = imageStorageService.displayQuestionCardImage(userName, deckId, cardId);
        String answerImage = imageStorageService.displayAnswerCardImage(userName, deckId, cardId);
        
//        DeckUpdateInfo updatedInfo = mapper.map(updateInfo, DeckUpdateInfo.class);
//        attributes.addFlashAttribute("updateDeck", updatedInfo);
//        attributes.addFlashAttribute("updateDeckForm", form);
        model.addAttribute("updateCard", updateInfo);
        model.addAttribute("questionImage", questionImage);
        model.addAttribute("answerImage", answerImage);
        model.addAttribute("cardResults", CardAnswerResult.values());
        model.addAttribute("updateCardForm", form);
//        return AppUtility.doRedirect(UrlConst.UPDATE_DECK);
        return UrlConst.UPDATE_CARD;
    }

    
}
