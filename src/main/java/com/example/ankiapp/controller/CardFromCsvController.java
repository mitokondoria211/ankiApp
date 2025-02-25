package com.example.ankiapp.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.ankiapp.constant.CardCsvImportResult;
import com.example.ankiapp.constant.UrlConst;
import com.example.ankiapp.constant.ViewNameConst;
import com.example.ankiapp.form.CardCsvForm;
import com.example.ankiapp.service.CardDisplayService;
import com.example.ankiapp.service.CardFromCsvService;
import com.example.ankiapp.service.DeckInfoService;
import com.example.ankiapp.utilty.AppUtility;
import lombok.RequiredArgsConstructor;
import lombok.var;

@RequiredArgsConstructor
@Controller
public class CardFromCsvController {
    /**カード表示Service*/
    private final CardDisplayService cardDisplayService;
    
    /**CSVからカードを取得するService*/
    private final CardFromCsvService cardFromCsvService;
    
    /**デッキ情報Service*/
    private final DeckInfoService deckInfoService;
    
    /**メッセージソース*/
    private final MessageSource messageSource;
    
    /**画面で使用するフォームクラス*/
    private static final String FORM_CLASS_NAME ="cardEditorForm";
    
    @GetMapping(UrlConst.CARD_FROM_CSV)
    public String cardCsvForm(Model model) {
        var deckInfos = deckInfoService.findDeckInfo();
        model.addAttribute("deckInfos", deckInfos);
        model.addAttribute("cardCsvForm", new CardCsvForm());
        return ViewNameConst.CARD_FROM_CSV;
    }
    
    @PostMapping(UrlConst.CARD_FROM_CSV)
    public String getCardFromCsv(Model model, CardCsvForm cardCsvForm,BindingResult bdResult ,RedirectAttributes redirectAttributes) {
//        model.addAttribute("deckInfos", deckInfos);
        var cardCsvInfos = cardFromCsvService.getCardFromCsv(cardCsvForm);
        var result = cardFromCsvService.registerCardsFromCsv(cardCsvInfos);
        boolean isError = result != CardCsvImportResult.SUCCEED;
        
        if (isError) {
            editGuideMessage(cardCsvForm, bdResult, result.getMessageId(), redirectAttributes);
            return AppUtility.doRedirect(ViewNameConst.CARD_FROM_CSV);
        }
        
        cardCsvForm.setCards(cardCsvInfos);
        model.addAttribute("cardCsvInfos", cardCsvInfos);
        model.addAttribute("cardCsvForm", cardCsvForm);
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, result.getMessageId()));
        redirectAttributes.addFlashAttribute("isError", false);
        return ViewNameConst.CARD_FROM_CSV;
    }
    
    private void editGuideMessage(CardCsvForm form, BindingResult bdResult, String messageId, 
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", AppUtility.getMessage(messageSource, messageId));
        redirectAttributes.addFlashAttribute("isError", true);
        redirectAttributes.addFlashAttribute(form);
        redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + FORM_CLASS_NAME, bdResult);
    }
}
